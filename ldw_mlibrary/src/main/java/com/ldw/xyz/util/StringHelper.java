package com.ldw.xyz.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 使用StringHelper2
 *
 */

@Deprecated
public class StringHelper {

	private static final String[] SPECIAL_ENTITIES = new String[] { "&quot;",
			"&amp;", "&lt;", "&gt;", "&nbsp;", "&(#|\\w){2,8};" };
	private static final String[] ENTITY_STRINGS = new String[] { "\"", "&",
			"<", ">", " ", "" };

	public static final String LINESYMBOL = "\n";

	public static final String EMPTYSYMBOL = "";

	public static final String DOCUMENTSYMBOL = "/";

	public static String formatTime(int t) {
		if (t < 10)
			return "0" + t;
		return "" + t;
	}

	public static Spannable strikeThrough(String string) {
		Spannable word = new SpannableString(string);
		word.setSpan(new StrikethroughSpan(), 0, word.length(), 0);
		return word;
	}

	public static Spannable backgroundColor(String string, int color) {
		Spannable word = new SpannableString(string);
		word.setSpan(new BackgroundColorSpan(color), 0, word.length(), 0);
		return word;
	}

	public static Spannable foregroundColor(String string, int start, int end,
			int color) {
		Spannable word = new SpannableString(string);
		word.setSpan(new ForegroundColorSpan(color), start, end, 0);
		return word;
	}

	public static String inputStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {

			sb.append(line);
		}
		is.close();
		return sb.toString();
	}

	public static String[] parseInfo(String line, String split, int length) {
		if (TextUtils.isEmpty(line))
			return null;
		String[] data = line.trim().split(split, length);
		return data.length == length ? data : null;
	}

	public static String htmlToText(String sourceStr) {
		if (isNull(sourceStr)) {
			sourceStr = sourceStr.replaceAll("&#039;", "'")
					.replaceAll("<p>", LINESYMBOL).replaceAll("&amp;", "&")
					.replaceAll("&nbsp;", " ")
					.replaceAll("</(?i)p>", LINESYMBOL)
					.replaceAll("<(?i)br\\s?/?>", LINESYMBOL)
					.replaceAll("</(?i)h\\d>", LINESYMBOL)
					.replaceAll("</(?i)tr>", LINESYMBOL)
					.replaceAll("<!--.*?-->", EMPTYSYMBOL)
					.replaceAll("<[^>]+>", EMPTYSYMBOL);

		}
		return sourceStr;
	}

	public static String getTextFromHtml(String sourceStr) {
		Object obj = null;
		if (!TextUtils.isEmpty(sourceStr)) {
			sourceStr = sourceStr
					.replaceAll("<(?i)head[^>]*?>[\\s\\S]*?</(?i)head>",
							LINESYMBOL)
					.replaceAll("<(?i)style[^>]*?>[\\s\\S]*?</(?i)style>",
							LINESYMBOL)
					.replaceAll("<(?i)script[^>]*?>[\\s\\S]*?</(?i)script>",
							LINESYMBOL).replaceAll("</(?i)div>", LINESYMBOL)
					.replaceAll("</(?i)p>", LINESYMBOL)
					.replaceAll("<(?i)br\\s?/?>", "\n")
					.replaceAll("</(?i)h\\d>", LINESYMBOL)
					.replaceAll("</(?i)tr>", LINESYMBOL)
					.replaceAll("<!--.*?-->", EMPTYSYMBOL)
					.replaceAll("<[^>]+>", EMPTYSYMBOL);
			obj = "\r\n\\s*";
			sourceStr = sourceStr.replaceAll(((String) (obj)), LINESYMBOL
					+ LINESYMBOL);
			int i = 0;
			do {
				int temp = SPECIAL_ENTITIES.length;
				if (i >= temp)
					break;
				obj = SPECIAL_ENTITIES[i];
				String s3 = ENTITY_STRINGS[i];
				sourceStr = sourceStr.replaceAll(((String) (obj)), s3);
				i++;
			} while (true);
			obj = sourceStr;
		}
		return obj.toString();
	}

	/**
	 * 将带有HTML标记的数据转换为纯文本格式
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String getTextForHtml(String sourceStr) {
		String lineSymbol = "\n";
		String emptySymbol = "";
		Object obj = null;
		if (!TextUtils.isEmpty(sourceStr)) {
			sourceStr = sourceStr
					.replaceAll("<(?i)head[^>]*?>[\\s\\S]*?</(?i)head>",
							emptySymbol)
					.trim()
					.replaceAll("<(?i)style[^>]*?>[\\s\\S]*?</(?i)style>",
							emptySymbol)
					.trim()
					.replaceAll("<(?i)script[^>]*?>[\\s\\S]*?</(?i)script>",
							emptySymbol).replaceAll("</(?i)div>", lineSymbol)
					.trim().replaceAll("</(?i)p>", lineSymbol).trim()
					.replaceAll("<(?i)br\\s?/?>", lineSymbol).trim()
					.replaceAll("</(?i)h\\d>", lineSymbol).trim()
					.replaceAll("</(?i)tr>", lineSymbol).trim()
					.replaceAll("<!--.*?-->", emptySymbol).trim()
					.replaceAll("<[^>]+>", emptySymbol).trim();
			obj = "\r\n\\s*";
			sourceStr = sourceStr.replaceAll(((String) (obj)), lineSymbol)
					.trim();
			int i = 0;
			do {
				int temp = SPECIAL_ENTITIES.length;
				if (i >= temp)
					break;
				obj = SPECIAL_ENTITIES[i];
				String s3 = ENTITY_STRINGS[i];
				sourceStr = sourceStr.replaceAll(((String) (obj)), s3).trim();
				i++;
			} while (true);
			obj = sourceStr;
		}
		return obj.toString().trim();
	}

	/**
	 * 去除HTML标记
	 * 
	 * @param sourceStr
	 * @return
	 */
	public static String removeHtmlTag(String sourceStr) {
		if (isNull(sourceStr)) {
			sourceStr = sourceStr.replaceAll("<[^>]+>", "");
		}
		return sourceStr;
	}

	/**
	 * 得到格式化时间
	 * 
	 * @param timeInSeconds
	 * @return
	 */
	public static String getFormatTimeMsg(int timeInSeconds) {
		int hours, minutes, seconds;
		hours = timeInSeconds / 3600;
		timeInSeconds = timeInSeconds - (hours * 3600);
		minutes = timeInSeconds / 60;
		timeInSeconds = timeInSeconds - (minutes * 60);
		seconds = timeInSeconds;

		String minStr = String.valueOf(minutes);
		String secStr = String.valueOf(seconds);

		if (minStr.length() == 1)
			minStr = "0" + minStr;
		if (secStr.length() == 1)
			secStr = "0" + secStr;

		return (minStr + "分" + secStr + "秒");
	}

	/**
	 * 判断输入是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		String strPattern = "[0-9]*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(str.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入是否是数字或者字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumOrLetter(String str) {
		String strPattern = "^[A-Za-z0-9]+$";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(str.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符长度
	 * 
	 * @param str
	 * @param maxLen
	 * @return
	 */
	public static boolean isLength(String str, int maxLen) {
		char[] cs = str.toCharArray();
		int count = 0;
		int last = cs.length;
		for (int i = 0; i < last; i++) {
			if (cs[i] > 255)
				count += 2;
			else
				count++;
		}
		if (count >= maxLen)
			return true;
		else
			return false;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		if (null == obj || "".equals(obj) || "[]".equals(obj)
				|| "null".equals(obj)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入是否是中文
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isChinese(String str) {
		String strPattern = "[\u0391-\uFFE5]*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(str.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为手机号码
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		Pattern pattern = Pattern.compile("([1]{1})([0-9]{10})");
		Matcher m = pattern.matcher(phone.trim());
		if (m.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断给定字符串是否空白串。<br>
	 * 空白串是指由空格、制表符、回车符、换行符组成的字符串<br>
	 * 
	 * @param
	 * @return boolean
	 */
	public static boolean isBlank(String data) {
		if (data == null || "".equals(data))
			return true;

		for (int i = 0; i < data.length(); i++) {
			char c = data.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static String replace(String str, String oldSubStr, String newSubStr) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		int j = 0;
		int len = oldSubStr.length();
		while ((i = str.indexOf(oldSubStr, j)) != -1) {
			sb.append(str.substring(j, i));
			sb.append(newSubStr);
			j = i + len;
		}
		sb.append(str.substring(j));
		return sb.toString();
	}

	/**
	 * 
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCellphone(String str) {
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * str 非空判断
	 */
	public static boolean isEmpty(String str) {
		if (null == str)
			return true;
		str = str.trim();
		if (null == str || "".equals(str) || " ".equals(str) || str.isEmpty()
				|| "null".endsWith(str)) {
			return true;
		}
		return false;
	}

	/***
	 * 
	 * 判断是否为null
	 * **/
	public static boolean notNull(Object obj) {
		if (null != obj && obj != "") {
			return true;
		}
		return false;
	}

	public static boolean dataIsNull(String data) {
		if (null == data || "".equals(data) || " ".equals(data.trim())
				|| "[]".equals(data)) {
			return true;
		}
		return false;
	}

	public static String getUTF8StringFromGBKString(String gbkStr) {
		try {
			return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new InternalError();
		}
	}

	public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
		int n = gbkStr.length();
		byte[] utfBytes = new byte[3 * n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int m = gbkStr.charAt(i);
			if (m < 128 && m >= 0) {
				utfBytes[k++] = (byte) m;
				continue;
			}
			utfBytes[k++] = (byte) (0xe0 | (m >> 12));
			utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
			utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
		}
		if (k < utfBytes.length) {
			byte[] tmp = new byte[k];
			System.arraycopy(utfBytes, 0, tmp, 0, k);
			return tmp;
		}
		return utfBytes;
	}

//	/**
//	 * 判断某一个字符串是否包含特殊字符
//	 * 
//	 * @param str
//	 *            要判断的字符串
//	 * @back
//	 */
//	public static boolean isContainSpecStr(String str) {
//		Pattern p = Pattern.compile(CommonUtils.SPEC_STRING_REGEX);
//		Matcher m = p.matcher(str);
//		back m.matches();
//	}

//	/**
//	 * 复制文字到系统剪贴板
//	 * 
//	 * @param context
//	 *            Activity 上下文
//	 * @param str
//	 *            要复制的内容
//	 */
//	@SuppressLint("NewApi")
//	public static void copyStr(Context context, String str) {
//		ClipboardManager cmb = (ClipboardManager) context
//				.getSystemService(Context.CLIPBOARD_SERVICE);
//		cmb.setText(str);
//		Toast.makeText(context, "复制成功", 3000).show();
//	}

	/**
	 * 指定字符串出现的次数
	 * 
	 * @param oneStr
	 *            要查询的字符串
	 * @param totalStr
	 *            全部字符串
	 * @return
	 */
	public static int strContainsTotal(String oneStr, String totalStr) {
		if (StringHelper.isEmpty(oneStr) || StringHelper.isEmpty(totalStr)) {
			return -1;
		}
		int count = 0;
		int onelenght = oneStr.length();
		try {
			if (totalStr.contains(oneStr)) {
				for (int i = 0; i < totalStr.length(); i++) {
					if (i + onelenght > totalStr.length())
						return count;
					// System.out.println(totalStr.substring(i, i + onelenght));
					if (totalStr.substring(i, i + onelenght).contains(oneStr)) {
						count++;
					}
				}
			} else {
				return -1;
			}
		} catch (StringIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 过虑特殊字符串
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String SpecStringFilter(String str)
			throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[` ~　!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 验证字符串是否为11位的手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		if (StringHelper.isEmpty(mobiles)) {
			return false;
		}
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 设置textview中不同位置下的字体样式
	 * 
	 * @param textStr
	 *            要变换的字符串
	 * @param colorId
	 *            颜色id
	 * @param style
	 *            样式
	 * @param startIndex
	 *            开始截取位置
	 * @param endIndex
	 *            结束截取位置
	 * @param tv
	 *            textview
	 */
	public static void setTextStyle(String textStr, int colorId, int style,
			int startIndex, int endIndex, TextView tv) {
		try {
			SpannableString sp = new SpannableString(textStr);
			sp.setSpan(new ForegroundColorSpan(colorId), startIndex, endIndex,
					style);
			tv.setText(sp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setTextStyle(String leftStr, String rightStr,
			String leftColor, String rightColor, TextView textview) {
		try {
			textview.setText(Html.fromHtml("<font color='" + leftColor + "'>"
					+ leftStr + "</font>" + "<font color='" + rightColor + "'>"
					+ rightStr + "</font>"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图片上画字
	 * 
	 * @param bitmap
	 * @param text
	 * @param textColor
	 * @param textSize
	 * @return
	 */
	public static Bitmap drawTextAtBitmap(Bitmap bitmap, String text,
			String textColor, float textSize) {
		int x = bitmap.getWidth();
		int y = bitmap.getHeight();
		// 创建一个和原图同样大小的位图
		Bitmap newbit = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newbit);
		Paint paint = new Paint();
		// 在原始位置0，0插入原图
		canvas.drawBitmap(bitmap, 0, 0, paint);
		paint.setColor(Color.parseColor(textColor));
		paint.setTextAlign(Align.CENTER);
		paint.setAntiAlias(true);
		paint.setTextSize(textSize);
		// 在原图指定位置写上字
		canvas.drawText(text, 10, y - 24, paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		// 存储
		canvas.restore();
		return newbit;
	}

	/**
	 * 图片上画字
	 * 
	 * @param bitmap
	 * @param text
	 * @param textColor
	 * @param textSize
	 * @return
	 */
	public static Bitmap drawTextAtBitmap2(Bitmap bitmap, String text,
			String textColor, int width, int height, float textSize) {
		int x = bitmap.getWidth();
		int y = bitmap.getHeight();
		// 创建一个和原图同样大小的位图
		Bitmap newbit = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(newbit);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// 在原始位置0，0插入原图
		canvas.drawBitmap(bitmap, 0, 0, paint);
		Rect targetRect = new Rect(0, 0, width, height);
		paint.setColor(Color.CYAN);
		paint.setTextSize(30);
		paint.setStrokeWidth(1);
		canvas.drawRect(targetRect, paint);
		paint.setColor(Color.parseColor(textColor));
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		// 转载请注明出处：http://blog.csdn.net/hursing
		int baseline = targetRect.top
				+ (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top)
				/ 2 - fontMetrics.top;
		// 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
		paint.setTextAlign(Align.CENTER);
		paint.setTextSize(textSize);
		canvas.drawText(text, targetRect.centerX(), baseline, paint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		// 存储
		canvas.restore();
		return newbit;
	}

	private static int[] widthAndHeight = new int[2];

	/**
	 * 初始化
	 * 
	 * @param context
	 * @param
	 * @param drawable
	 * @param id
	 * @return
	 */
	public static Bitmap initDrawable(Context context, TextView textview,
			Drawable drawable, int id) {
		Bitmap imgMarker = BitmapFactory.decodeResource(context.getResources(),
				id);
		drawable = new BitmapDrawable(imgMarker);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		widthAndHeight[0] = imgMarker.getWidth();
		widthAndHeight[1] = imgMarker.getHeight();
		FontMetrics fontMetrics = textview.getPaint().getFontMetrics();
		// imgMarker.
		return imgMarker;
	}

	/**
	 * 这个是可以同时设置字体和有创建字体的图片
	 * 
	 * @param context
	 * @param tv
	 * @param textContext
	 * @param imgTextContent
	 * @param imgId
	 */
	public static void setTextViewData(final Context context,
			final TextView tv, final String textContext,
			final float textViewSize, final String imgTextContent,
			final int imgId) {
		FontMetrics fontMetrics = tv.getPaint().getFontMetrics();
		final String html = "<img src='" + imgId + "'/>";
		ImageGetter imgGetter = new ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
				// TODO Auto-generated method stub
				int id = Integer.parseInt(source);
				Drawable d = context.getResources().getDrawable(id);
				Bitmap bitmap = BitmapFactory.decodeResource(
						context.getResources(), id);
				widthAndHeight[0] = bitmap.getWidth();
				widthAndHeight[1] = bitmap.getHeight();
				d = createDrawable(context, tv, bitmap, textContext, imgId,
						textViewSize);
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				return d;
			}
		};
		CharSequence charSequence = Html.fromHtml(textContext + html,
				imgGetter, null);
		tv.setText(charSequence);
	}

	/**
	 * 创建带有字体的图片 必须先初始化一下initDrawable方法
	 * 
	 * @param letter
	 * @return
	 */
	public static Drawable createDrawable(Context context, TextView img,
			Bitmap imgMarker, String letter, int id, float textSize) {
		Bitmap imgTemp = Bitmap.createBitmap(widthAndHeight[0],
				widthAndHeight[1], Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(imgTemp);
		Paint paint = new Paint(); // 建立画笔
		paint.setDither(true);
		paint.setFilterBitmap(true);
		Rect src = new Rect(0, 0, widthAndHeight[0], widthAndHeight[1]);
		Rect dst = new Rect(0, 0, widthAndHeight[0], widthAndHeight[1]);
		canvas.drawBitmap(imgMarker, src, dst, paint);
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
				| Paint.DEV_KERN_TEXT_FLAG);
		textPaint.setTextSize(textSize);
		// textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 采用默认的宽度
		textPaint.setAntiAlias(true);
		textPaint.setColor(Color.WHITE);
		textPaint.setTextAlign(Align.CENTER);
		canvas.drawText(letter, widthAndHeight[0] / 2 - 5,
				widthAndHeight[1] / 2 + 5, textPaint);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return (Drawable) new BitmapDrawable(context.getResources(), imgTemp);
	}

	public static void setTextView(SpannableString msp, Drawable drawable) {
		// Drawable da = createDrawable("两个");
		// da.setBounds(0, 0, drawable.getIntrinsicWidth(),
		// drawable.getIntrinsicHeight());
		// ImageSpan span = new ImageSpan(da);
		// msp.setSpan(span, 12, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// img.setText(msp);
	}
}
