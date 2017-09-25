/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.antonioleiva.mvpexample.app.main;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Fragment;
import android.app.SharedElementCallback;
import android.app.TaskStackBuilder;
import android.app.assist.AssistContent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StyleRes;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.antonioleiva.mvpexample.app.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class RecordStartActivity extends Activity {

    private ListView listView;
    private ProgressBar progressBar;
    private MainPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main01);
        listView = (ListView) findViewById(R.id.list);
//        listView.setOnItemClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
//        presenter = new MainPresenterImpl(this, new FindItemsInteractorImpl());

        EditText editText = (EditText) findViewById(R.id.edittext);

    }

    public RecordStartActivity() {
        super();
        Log.e("TAG","RecordStartActivity()");
    }

    @Override
    public Intent getIntent() {
        Log.e("TAG","getIntent()");
        return super.getIntent();

    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        Log.e("TAG","setIntent()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.e("TAG","onCreate()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("TAG","onRestoreInstanceState()");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.e("TAG","onRestoreInstanceState()");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.e("TAG","onPostCreate()");
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        Log.e("TAG","onPostCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG","onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("TAG","onRestart()");
    }

    @Override
    public void onStateNotSaved() {
        super.onStateNotSaved();
        Log.e("TAG","onStateNotSaved()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG","onResume()");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e("TAG","onPostResume()");
    }


    @Override
    public void startLocalVoiceInteraction(Bundle privateOptions) {
        super.startLocalVoiceInteraction(privateOptions);
        Log.e("TAG","startLocalVoiceInteraction()");
    }

    @Override
    public void onLocalVoiceInteractionStarted() {
        super.onLocalVoiceInteractionStarted();
        Log.e("TAG","onLocalVoiceInteractionStarted()");
    }

    @Override
    public void onLocalVoiceInteractionStopped() {
        super.onLocalVoiceInteractionStopped();
        Log.e("TAG","onLocalVoiceInteractionStopped()");
    }

    @Override
    public void stopLocalVoiceInteraction() {
        super.stopLocalVoiceInteraction();
        Log.e("TAG","stopLocalVoiceInteraction()");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("TAG","onNewIntent()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("TAG","onSaveInstanceState()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e("TAG","onSaveInstanceState()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG","onPause()");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.e("TAG","onUserLeaveHint()");
    }

    @Override
    public void onProvideAssistData(Bundle data) {
        super.onProvideAssistData(data);
        Log.e("TAG","onProvideAssistData()");
    }

    @Override
    public void onProvideAssistContent(AssistContent outContent) {
        super.onProvideAssistContent(outContent);
        Log.e("TAG","onProvideAssistContent()");
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId);
        Log.e("TAG","onProvideKeyboardShortcuts()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG","onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG","onDestroy()");
    }

    @Override
    public void reportFullyDrawn() {
        super.reportFullyDrawn();
        Log.e("TAG","reportFullyDrawn()");
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);
        Log.e("TAG","onMultiWindowModeChanged()");
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        Log.e("TAG","onPictureInPictureModeChanged()");
    }

    @Override
    public void enterPictureInPictureMode() {
        super.enterPictureInPictureMode();
        Log.e("TAG","enterPictureInPictureMode()");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("TAG","onConfigurationChanged()");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.e("TAG","onLowMemory()");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e("TAG","onTrimMemory()");
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.e("TAG","onAttachFragment()");
    }

    @Override
    public void startManagingCursor(Cursor c) {
        super.startManagingCursor(c);
        Log.e("TAG","startManagingCursor()");
    }

    @Override
    public void stopManagingCursor(Cursor c) {
        super.stopManagingCursor(c);
        Log.e("TAG","stopManagingCursor()");
    }

    @Override
    public void setActionBar(@Nullable Toolbar toolbar) {
        super.setActionBar(toolbar);
        Log.e("TAG","setActionBar()");
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        Log.e("TAG","setContentView()");
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        Log.e("TAG","setContentView()");
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        Log.e("TAG","setContentView()");
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
        Log.e("TAG","addContentView()");
    }

    @Override
    public void setContentTransitionManager(TransitionManager tm) {
        super.setContentTransitionManager(tm);
        Log.e("TAG","setContentTransitionManager()");
    }

    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
        Log.e("TAG","setFinishOnTouchOutside()");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("TAG","onBackPressed()");
    }


    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.e("TAG","onUserInteraction()");
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        Log.e("TAG","onWindowAttributesChanged()");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.e("TAG","onContentChanged()");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e("TAG","onWindowFocusChanged()");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e("TAG","onAttachedToWindow()");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e("TAG","onDetachedFromWindow()");
    }


    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        Log.e("TAG","onPanelClosed()");
    }

    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
        Log.e("TAG","invalidateOptionsMenu()");
    }

    @Override
    public void onCreateNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onCreateNavigateUpTaskStack(builder);
        Log.e("TAG","onCreateNavigateUpTaskStack()");
    }

    @Override
    public void onPrepareNavigateUpTaskStack(TaskStackBuilder builder) {
        super.onPrepareNavigateUpTaskStack(builder);
        Log.e("TAG","onPrepareNavigateUpTaskStack()");
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        Log.e("TAG","onOptionsMenuClosed()");
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.e("TAG","onCreateContextMenu()");
    }

    @Override
    public void registerForContextMenu(View view) {
        super.registerForContextMenu(view);
        Log.e("TAG","registerForContextMenu()");
    }

    @Override
    public void unregisterForContextMenu(View view) {
        super.unregisterForContextMenu(view);
        Log.e("TAG","unregisterForContextMenu()");
    }

    @Override
    public void openContextMenu(View view) {
        super.openContextMenu(view);
        Log.e("TAG","openContextMenu()");
    }

    @Override
    public void closeContextMenu() {
        super.closeContextMenu();
        Log.e("TAG","closeContextMenu()");
    }

    @Override
    public void onContextMenuClosed(Menu menu) {
        super.onContextMenuClosed(menu);
        Log.e("TAG","onContextMenuClosed()");
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        Log.e("TAG","onPrepareDialog()");
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        super.onPrepareDialog(id, dialog, args);
        Log.e("TAG","onPrepareDialog()");
    }

    @Override
    public void startSearch(@Nullable String initialQuery, boolean selectInitialQuery, @Nullable Bundle appSearchData, boolean globalSearch) {
        super.startSearch(initialQuery, selectInitialQuery, appSearchData, globalSearch);
        Log.e("TAG","startSearch()");
    }

    @Override
    public void triggerSearch(String query, @Nullable Bundle appSearchData) {
        super.triggerSearch(query, appSearchData);
        Log.e("TAG","triggerSearch()");
    }

    @Override
    public void takeKeyEvents(boolean get) {
        super.takeKeyEvents(get);
        Log.e("TAG","takeKeyEvents()");
    }

    @Override
    public void setTheme(int resid) {
        super.setTheme(resid);
        Log.e("TAG","setTheme()");
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, @StyleRes int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);
        Log.e("TAG","onApplyThemeResource()");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("TAG","onRequestPermissionsResult()");
    }

    @Override
    public void startActivityForResult(@RequiresPermission Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        Log.e("TAG","startActivityForResult()");
    }

    @Override
    public void startActivityForResult(@RequiresPermission Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        Log.e("TAG","startActivityForResult()");
    }

    @Override
    public void startIntentSenderForResult(IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
        Log.e("TAG","startIntentSenderForResult()");
    }

    @Override
    public void startIntentSenderForResult(IntentSender intent, int requestCode, @Nullable Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        Log.e("TAG","startIntentSenderForResult()");
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        Log.e("TAG","startActivity()");
    }

    @Override
    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
        Log.e("TAG","startActivity()");
    }

    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
        Log.e("TAG","startActivities()");
    }

    @Override
    public void startActivities(Intent[] intents, @Nullable Bundle options) {
        super.startActivities(intents, options);
        Log.e("TAG","startActivities()");
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
        Log.e("TAG","overridePendingTransition()");
    }


    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        Log.e("TAG","setVisible()");
    }

    @Override
    public void recreate() {
        super.recreate();
        Log.e("TAG","recreate()");
    }

    @Override
    public void finish() {
        super.finish();
        Log.e("TAG","finish()");
    }

    @Override
    public void finishAffinity() {
        super.finishAffinity();
        Log.e("TAG","finishAffinity()");
    }

    @Override
    public void finishFromChild(Activity child) {
        super.finishFromChild(child);
        Log.e("TAG","finishFromChild()");
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        Log.e("TAG","finishAfterTransition()");
    }

    @Override
    public void finishActivity(int requestCode) {
        super.finishActivity(requestCode);
        Log.e("TAG","finishActivity()");
    }

    @Override
    public void finishActivityFromChild(@NonNull Activity child, int requestCode) {
        super.finishActivityFromChild(child, requestCode);
        Log.e("TAG","finishActivityFromChild()");
    }

    @Override
    public void finishAndRemoveTask() {
        super.finishAndRemoveTask();
        Log.e("TAG","finishAndRemoveTask()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG","onActivityResult()");
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        Log.e("TAG","onActivityReenter()");
    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        Log.e("TAG","onTitleChanged()");
    }

    @Override
    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        super.onChildTitleChanged(childActivity, title);
        Log.e("TAG","onChildTitleChanged()");
    }

    @Override
    public void setTaskDescription(ActivityManager.TaskDescription taskDescription) {
        super.setTaskDescription(taskDescription);
        Log.e("TAG","setTaskDescription()");
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        super.dump(prefix, fd, writer, args);
        Log.e("TAG","dump()");
    }

    @Override
    public void onVisibleBehindCanceled() {
        super.onVisibleBehindCanceled();
        Log.e("TAG","onVisibleBehindCanceled()");
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        Log.e("TAG","onEnterAnimationComplete()");
    }

    @Override
    public void setImmersive(boolean i) {
        super.setImmersive(i);
        Log.e("TAG","setImmersive()");
    }

    @Override
    public void setVrModeEnabled(boolean enabled, @NonNull ComponentName requestedComponent) throws PackageManager.NameNotFoundException {
        super.setVrModeEnabled(enabled, requestedComponent);
        Log.e("TAG","setVrModeEnabled()");
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        Log.e("TAG","onActionModeStarted()");
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        Log.e("TAG","onActionModeFinished()");
    }

    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
        Log.e("TAG","setEnterSharedElementCallback()");
    }

    @Override
    public void setExitSharedElementCallback(SharedElementCallback callback) {
        super.setExitSharedElementCallback(callback);
        Log.e("TAG","setExitSharedElementCallback()");
    }

    @Override
    public void postponeEnterTransition() {
        super.postponeEnterTransition();
        Log.e("TAG","postponeEnterTransition()");
    }

    @Override
    public void startPostponedEnterTransition() {
        super.startPostponedEnterTransition();
        Log.e("TAG","startPostponedEnterTransition()");
    }

    @Override
    public void startLockTask() {
        super.startLockTask();
        Log.e("TAG","startLockTask()");
    }

    @Override
    public void stopLockTask() {
        super.stopLockTask();
        Log.e("TAG","stopLockTask()");
    }

    @Override
    public void showLockTaskEscapeMessage() {
        super.showLockTaskEscapeMessage();
        Log.e("TAG","showLockTaskEscapeMessage()");
    }


//    @Override protected void onResume() {
//        super.onResume();
//        presenter.onResume();
//    }
//
//    @Override public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    @Override protected void onDestroy() {
//        presenter.onDestroy();
//        super.onDestroy();
//    }
//
//    @Override public void showProgress() {
//        progressBar.setVisibility(View.VISIBLE);
//        listView.setVisibility(View.INVISIBLE);
//    }
//
//    @Override public void hideProgress() {
//        progressBar.setVisibility(View.INVISIBLE);
//        listView.setVisibility(View.VISIBLE);
//    }
//
//    @Override public void setItems(List<String> items) {
//        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
//    }
//
//    @Override public void showMessage(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
//    }
//
//    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        presenter.onItemClicked(position);
//    }
}
