/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <stdio.h>
#include <jni.h>
#include <unistd.h>
#include <android/log.h>
#include <android/bitmap.h>
#include "SD.h"

#define ABI "armeabi"

#define RED_565(a)      ((((a) & 0x0000f800) >> 11) << 3)
#define GREEN_565(a)    ((((a) & 0x000007e0) >> 5) << 2)
#define BLUE_565(a)     ((((a) & 0x0000001f) << 3))

#define RED_8888(a)      (((a) & 0x00ff0000) >> 16)
#define GREEN_8888(a)    (((a) & 0x0000ff00) >> 8)
#define BLUE_8888(a)     (((a) & 0x000000ff))

#define RED_4444(a)      (((a) & 0x00000f00) >> 8)
#define GREEN_4444(a)    (((a) & 0x000000f0) >> 4)
#define BLUE_4444(a)     (((a) & 0x0000000f))

static int SD_Handle = 0;
static int NumDecodes = 0;
static char ResultString[10000], *ResultPtr;
static unsigned char *ImageBuffer = NULL;
static int ImageSize = 0;

static void SD_CB_Result(int Handle);
static int CheckedSD_Set(int Property, void *Value);
static int Init_SD(void);

jint Java_com_example_sdembeddeddemo_MainActivity_LoadSD( JNIEnv* env, jobject thiz)
{
	//Create SD Handle
	ResultPtr = ResultString;
	SD_Handle = SD_Create();
	if (0 == SD_Handle)
	{
		__android_log_print(ANDROID_LOG_ERROR, "SwiftDecoder", "Failed to create SD object");
		return 0;
	}
	return Init_SD();
}

jint Java_com_example_sdembeddeddemo_MainActivity_UnloadSD( JNIEnv* env, jobject thiz)
{
	if ((SD_Handle == 0) || (0 == SD_Destroy(SD_Handle)))
	{
		__android_log_print(ANDROID_LOG_ERROR, "SwiftDecoder", "Failed to destroy SD object");
		return 0;
	}
	return 1;
}


jstring Java_com_example_sdembeddeddemo_MainActivity_GetResultSD( JNIEnv* env, jobject thiz)
{
	return (*env)->NewStringUTF(env, ResultString);
}


jint Java_com_example_sdembeddeddemo_MainActivity_DecodeImageSD( JNIEnv* env, jobject thiz, jobject bitmap)
{
	AndroidBitmapInfo info;
	int i = 0;
	uint32_t* rgbsbuffer;

	// Reinit output buffer
	ResultPtr = ResultString;
	memset(ResultString, 0, sizeof(ResultString));

	//Open Image file --> Convert to Bitmap --> Convert to greyscale buffer --> send to decoder --> decode
	if (AndroidBitmap_getInfo(env, bitmap, &info) < 0)
	{
		__android_log_print(ANDROID_LOG_ERROR, "SwiftDecoder", "Can't get bitmap pixels");
		return 0;
	}

	if (AndroidBitmap_lockPixels(env, bitmap, (void*)&rgbsbuffer) < 0)
	{
		__android_log_print(ANDROID_LOG_ERROR, "SwiftDecoder", "Can't get bitmap pixels");
		return 0;
	}

	ImageSize =  info.width*info.height;
	ImageBuffer = (unsigned char*)malloc(ImageSize);

	if (ImageBuffer == NULL)
	{
		__android_log_print(ANDROID_LOG_ERROR, "SwiftDecoder", "Can't allocate image buffer");
		AndroidBitmap_unlockPixels(env, bitmap);
	    return 0;
	}

	switch (info.format)
	{
		case ANDROID_BITMAP_FORMAT_NONE:
			memcpy(ImageBuffer, rgbsbuffer, ImageSize);
			break;
		case ANDROID_BITMAP_FORMAT_RGBA_8888:
			for (i = 0; i < ImageSize; i++)
				ImageBuffer[i] = (GREEN_8888(rgbsbuffer[i]) + RED_8888(rgbsbuffer[i]) + BLUE_8888(rgbsbuffer[i])) / 3;
			break;
		case ANDROID_BITMAP_FORMAT_RGB_565:
			for (i = 0; i < ImageSize; i++)
				ImageBuffer[i] = (RED_565(rgbsbuffer[i]) + GREEN_565(rgbsbuffer[i]) + BLUE_565(rgbsbuffer[i])) / 3;
			break;
		case ANDROID_BITMAP_FORMAT_RGBA_4444:
			for (i = 0; i < ImageSize; i++)
				ImageBuffer[i] = (GREEN_4444(rgbsbuffer[i]) + RED_4444(rgbsbuffer[i]) + BLUE_4444(rgbsbuffer[i])) / 3;
			break;
		default:
			__android_log_print(ANDROID_LOG_INFO,"SwiftDecoder", "Bitmap Format unknown");
			AndroidBitmap_unlockPixels(env, bitmap);
			free(ImageBuffer);
			return 0;
	}

	AndroidBitmap_unlockPixels(env, bitmap);

	/* Set image related properties to SwiftDecoder library*/
	if ((0 == CheckedSD_Set(SD_PROP_IMAGE_HEIGHT, (void*)info.height))
	||  (0 == CheckedSD_Set(SD_PROP_IMAGE_LINE_DELTA, (void*)info.width))
	||  (0 == CheckedSD_Set(SD_PROP_IMAGE_POINTER, (void*)ImageBuffer))
	||  (0 == CheckedSD_Set(SD_PROP_IMAGE_WIDTH, (void*)info.width)))
	{
		__android_log_print(ANDROID_LOG_ERROR, "SwiftDecoder", "Problem passing image to SD");
		free(ImageBuffer);
		return 0;
	}

	if (0 == SD_Decode(SD_Handle))
	{
	   __android_log_print(ANDROID_LOG_ERROR,  "SwiftDecoder","SD_Decode failed, Error = %d\n", SD_GetLastError());
	   free(ImageBuffer);
	   return 0;
	}

    free(ImageBuffer);
    return 1;
}


static void SD_CB_Result(int Handle)
{
   int Symbology;
   int SymbologyEx;
   int Modifier;
   int Length;

   SD_Get(Handle, SD_PROP_RESULT_SYMBOLOGY, &Symbology);
   SD_Get(Handle, SD_PROP_RESULT_SYMBOLOGY_EX, &SymbologyEx);
   SD_Get(Handle, SD_PROP_RESULT_MODIFIER, &Modifier);
   SD_Get(Handle, SD_PROP_RESULT_LENGTH, &Length);
   SD_Get(Handle, SD_PROP_RESULT_STRING, ResultPtr);

   ResultPtr += sprintf(ResultPtr, "Symbology = 0x%08x\nSymbologyEx = 0x%08x\nModifier = %c\nLength = %d\nString = ", Symbology, SymbologyEx, Modifier, Length);
   SD_Get(Handle, SD_PROP_RESULT_STRING, ResultPtr);
   ResultPtr += Length;
   ResultPtr += sprintf(ResultPtr, "\n");
   NumDecodes++;
}

static int Init_SD(void)
{
	int ENA = 1;

	// Set the result handler callback address
	if ((0 == CheckedSD_Set(SD_PROP_CALLBACK_RESULT, (void *) &SD_CB_Result))
	||  (0 == CheckedSD_Set(SD_PROP_CALLBACK_PROGRESS, NULL))
	||  (0 == CheckedSD_Set(SD_PROP_C128_ENABLED, (void*) ENA))
	||  (0 == CheckedSD_Set(SD_PROP_C39_ENABLED, (void*) ENA))
	||  (0 == CheckedSD_Set(SD_PROP_DM_ENABLED, (void*) ENA))
	||  (0 == CheckedSD_Set(SD_PROP_QR_ENABLED, (void*) ENA)))
		return 0;
	return 1;
}

static int CheckedSD_Set(int Property, void* Value)
{
   int result;
   __android_log_print(ANDROID_LOG_INFO, "SwiftDecoder","Setting property 0x%08x =\n   0x%08x (%d)\n", Property, (int)Value, (int)Value);
   result = SD_Set(SD_Handle, Property, Value);
   if (result == 0)
	   __android_log_print(ANDROID_LOG_ERROR, "SwiftDecoder", "SD_Set failed,Handle =%d, Result = %d, Error = %d\n", SD_Handle,result, SD_GetLastError());
   return result;
}



