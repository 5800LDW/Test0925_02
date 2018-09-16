package kld.com.huizhan.util.ui;

/**
 * Created by LDW10000000 on 22/11/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.ldw.xyz.util.ResHelper;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kld.com.huizhan.R;

/**
 * Created by LDW10000000 on 03/12/2016.
 */

public class ProgressUtil {

    public interface OnFinishListener {
        void onFinish();
    }


//    private static OnFinishListener mListener;
    private static final int Count = 30;//转动次数
    private static int i = -1;
//    private static SweetAlertDialog pDialog;

    public static SweetAlertDialog show2(final Context context, String info, final SweetAlertDialog dialog, OnFinishListener listener) {
        if (info == null) {
            info = context.getResources().getString(R.string.loading);
        }

        if (context != null && context instanceof Activity && ((Activity) context).isFinishing() != true && ((Activity) context).isDestroyed() != true) {
            if (dialog != null) {
                dialog.setTitleText(info).changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
                dialog.show();
                dialog.showCancelButton(false);
                dialog.setCancelable(false);
                i = -1;
                CountDownTimer ct = new CountDownTimer(800 * Count, 800) {
                    public void onTick(long millisUntilFinished) {
                        // you can change the progress bar color by ProgressHelper every 800 millis
                        i++;
                        switch (i) {
                            case 0:
                                dialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.blue_btn_bg_color));
                                break;
                            case 1:
                                dialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_deep_teal_50));
                                break;
                            case 2:
                                dialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.success_stroke_color));
                                break;
                            case 3:
                                dialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_deep_teal_20));
                                break;
                            case 4:
                                dialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_blue_grey_80));
                                break;
                            case 5:
                                dialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.warning_stroke_color));
                                break;
                            case 6:
                                dialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.success_stroke_color));
                                break;
                        }
                    }

                    public void onFinish() {
                        i = -1;
                        if(listener!=null){
                            listener.onFinish();
                        }

                    }
                };
                ct.start();
            }
        }

        return dialog;

    }


    public static SweetAlertDialog show(final Context context, String info, CountDownTimer mCountDownTimer, @Nullable OnFinishListener listener) {
//        if (listener != null) {
//            mListener = listener;
//        }

        if (info == null) {
            info = context.getResources().getString(R.string.loading);
        }

        SweetAlertDialog  pDialog ;

//        if(pDialog==null){
//            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE).setTitleText(info);
//        }
//        else{
//            pDialog.setTitleText(info);
//        }

//        if(context!=null && context instanceof Activity &&  ((Activity)context).getParent()!=null){
//            pDialog.show();
//        }

        if (context != null && context instanceof Activity && ((Activity) context).isFinishing() != true && ((Activity) context).isDestroyed() != true) {

            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE).setTitleText(info);

            pDialog.show();

            pDialog.setCancelable(false);


            i = -1;
            mCountDownTimer = new CountDownTimer(800 * Count, 800) {
                public void onTick(long millisUntilFinished) {
                    // you can change the progress bar color by ProgressHelper every 800 millis
                    i++;
                    switch (i) {
                        case 0:
                            pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.blue_btn_bg_color));
                            break;
                        case 1:
                            pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_deep_teal_50));
                            break;
                        case 2:
                            pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.success_stroke_color));
                            break;
                        case 3:
                            pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_deep_teal_20));
                            break;
                        case 4:
                            pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.material_blue_grey_80));
                            break;
                        case 5:
                            pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.warning_stroke_color));
                            break;
                        case 6:
                            pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.success_stroke_color));
                            break;
                    }

                }

                public void onFinish() {
                    i = -1;
//                String r = "";
//                if (r.equals("1")) {
//                    pDialog.setTitleText("注册成功!")
//                            .setConfirmText("OK")
//                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//
//
//                } else {
//                    pDialog.setTitleText("注册失败!")
//                            .setContentText("康利达物联科技股份有限公司" + "\n" + "联系项目部" + "\n" + "电话:4006-322-138")
//                            .setConfirmText("确定").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        }
//                    }).changeAlertType(SweetAlertDialog.ERROR_TYPE);
//                }

                    if(listener!=null){
                        listener.onFinish();
                    }
                }
            };
            mCountDownTimer.start();

            return pDialog;
        }

        return new SweetAlertDialog(context.getApplicationContext(), SweetAlertDialog.PROGRESS_TYPE).setTitleText(info);

    }


    public static void onlyMessageDialog(Context context, String message) {
        new SweetAlertDialog(context)
                .setTitleText(message)
                .showContentText(false)
                .setConfirmText(ResHelper.getString(context, R.string.alright))
                .show();
    }


    public static void updateFailed(SweetAlertDialog dialog) {
        dialog.setTitleText("更新失败!请重新下载!")
                .setConfirmText("好的")
                .showCancelButton(false)
                .showContentText(false)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
    }


    public static void updateSucceed(SweetAlertDialog d, Handler handler) {
        d.setTitleText("更新成功")
                .setConfirmText("好的")
                .showCancelButton(false)
                .showContentText(false)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (d != null) {
                    d.dismiss();
                }

            }
        }, 2000);
    }

    public static void upLoadSucceed(SweetAlertDialog d, Handler handler) {
        d.setTitleText("提交成功")
                .setConfirmText("好的")
                .showCancelButton(false)
                .showContentText(false)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (d != null) {
                    d.dismiss();
                }

            }
        }, 2000);
    }

    public static void saveSucceed(SweetAlertDialog d, Handler handler) {
        d.setTitleText("保存成功")
                .setConfirmText("好的")
                .showCancelButton(false)
                .showContentText(false)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (d != null) {
                    d.dismiss();
                }

            }
        }, 2000);
    }


    public static void errorDialog(SweetAlertDialog dialog, String info) {
        dialog.setTitleText("错误提示")
                .setConfirmText("好的")
                .setContentText(info)
                .showCancelButton(false)
                .showContentText(true)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
    }


    public interface OnReminderDialogListenter {
        void cancel(Dialog dialog);

        void confirm(Dialog dialog);
    }

    public static Dialog reminderDialog(Context context, String content, OnReminderDialogListenter listenter) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示")
                .setContentText(content)
                .setCancelText("取消")
                .setConfirmText("是的")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        listenter.cancel(sDialog);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        listenter.confirm(sDialog);
                    }
                });

        dialog.show();
        return dialog;

    }

    public static Dialog reminderDialog2(Context context, String title, String content, OnReminderDialogListenter listenter) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(content)
                .setCancelText("取消")
                .setConfirmText("是的")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        listenter.cancel(sDialog);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        listenter.confirm(sDialog);
                    }
                });
        dialog.show();

        return dialog;

    }

    public static Dialog reminderDialog3(Context context, String content) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("提示")
                .setContentText(content)
                .showCancelButton(false)
                .setConfirmText("好的")
                .showCancelButton(true);
        dialog.show();
        return dialog;

    }



    public interface OnConfirmSaveListener {
        void confirm(Dialog dialog);
    }

    public static Dialog confirmSaveCommonUseDialog(Context context, OnConfirmSaveListener listener) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("确认保存?")
//                        .setContentText("Won't be able to recover this file!")
                .setConfirmText("是的")
                .showContentText(false)
                .setCancelText("取消")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        listener.confirm(sDialog);
                    }
                });
        dialog.show();
        return dialog;
    }


    private static SweetAlertDialog getDialog(Context context, OnConfirmListener listener, String title) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setConfirmText("是的")
                .showContentText(false)
                .setCancelText("取消")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        listener.confirm(sDialog);
                        if (sDialog != null) {
                            sDialog.dismiss();
                        }
                    }
                });
        dialog.show();
        return  dialog;

    }

    public interface OnConfirmListener {
        void confirm(Dialog dialog);
    }
    public static Dialog confirmDialog(Context context, OnConfirmListener listener, int index) {
        SweetAlertDialog dialog = null;
        String title ;
        switch (index) {
            case 0:
                title = "确认退出?";
                getDialog(context, listener, title);
                break;
            case 1:

                title = "确认提交?";
                getDialog(context, listener, title);
                break;

            case 2:
                title = "确认删除?";
                getDialog(context, listener, title);
                break;

            case 3:

                title = "确认全部删除?";
                getDialog(context, listener, title);
                break;


        }


        return dialog;
    }


}