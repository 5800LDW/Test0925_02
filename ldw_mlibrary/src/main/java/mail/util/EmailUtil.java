package mail.util;

import android.util.Log;

import java.io.File;

import mail.MailSenderInfo;
import mail.SimpleMailSender;

/**
 * Created by LDW10000000 on 15/03/2017.
 */

public class EmailUtil {
    public static String TAG = "Email";

    public static void sendMessage(final String Subject,final String msg ){
        /*****************************************************/
        Log.i(TAG, "is Sending Email...");
        // 这个类主要是设置邮件
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                MailSenderInfo mailInfo = new MailSenderInfo();

//                mailInfo.setMailServerHost("smtp.163.com");
//                mailInfo.setMailServerPort("25");
//                mailInfo.setValidate(true);
//                mailInfo.setUserName("LDW5800@163.com");
//                mailInfo.setPassword("5800shouquanma");// 您的邮箱密码
//                mailInfo.setFromAddress("LDW5800@163.com");
//                mailInfo.setToAddress("15089815098@163.com");
                mailInfo = configuration(mailInfo);

                mailInfo.setSubject(Subject);
                mailInfo.setContent(msg);
                // 这个类主要来发送邮件
                SimpleMailSender sms = new SimpleMailSender();
                // TODO: 07/02/2018
                boolean isSuccess = sms.sendTextMail(mailInfo);// 发送文体格式
                // sms.sendHtmlMail(mailInfo);//发送html格式
                if (isSuccess) {
                    Log.i(TAG, "=============Send a success=============");
                } else {
                    Log.i(TAG, "=============Send failure=============");
                }
            }
        }).start();
    }


    public interface OnSendEmailListener{
        void onSucceed();
        void onFailed();

    }



    public static void sendMessageWithAttachments(final String Subject,final String msg ,File file ,OnSendEmailListener listener){
        /*****************************************************/
        Log.i(TAG, "is Sending Email...");
        // 这个类主要是设置邮件
        new Thread(new Runnable() {
            @Override

            public void run() {
                // TODO Auto-generated method stub
                MailSenderInfo mailInfo = new MailSenderInfo();

//                mailInfo.setMailServerHost("smtp.163.com");
//                mailInfo.setMailServerPort("25");
//                mailInfo.setValidate(true);
//                mailInfo.setUserName("LDW5800@163.com");
//                mailInfo.setPassword("5800shouquanma");// 您的邮箱密码
//                mailInfo.setFromAddress("LDW5800@163.com");
//                mailInfo.setToAddress("15089815098@163.com");
                mailInfo = configuration(mailInfo);

                mailInfo.setSubject(Subject);
                mailInfo.setContent(msg);
                // 这个类主要来发送邮件
                SimpleMailSender sms = new SimpleMailSender();
                // TODO: 07/02/2018
                boolean isSuccess = sms.sendEmailWithAttachments(mailInfo,file);// 发送文体格式
                // sms.sendHtmlMail(mailInfo);//发送html格式
                if (isSuccess) {
                    listener.onSucceed();
                    Log.i(TAG, "=============Send a success=============");
                } else {
                    listener.onFailed();
                    Log.i(TAG, "=============Send failure=============");
                }

            }
        }).start();
    }

    private static MailSenderInfo configuration(MailSenderInfo mailInfo){

        return mailInfo;
    }






    public static void sendMessage(final String UserName,
                                   final String Password,
                                   final String FromAddress,
                                   final String ToAddress,
                                   final String Subject,
                                   final String msg){
        /*****************************************************/
        Log.i(TAG, "is Sending Email...");
        // 这个类主要是设置邮件
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                MailSenderInfo mailInfo = new MailSenderInfo();
                mailInfo.setMailServerHost("smtp.163.com");
                mailInfo.setMailServerPort("25");
                mailInfo.setValidate(true);
                mailInfo.setUserName(UserName);
                mailInfo.setPassword(Password);// 您的邮箱密码
                mailInfo.setFromAddress(FromAddress);
                mailInfo.setToAddress(ToAddress);
                mailInfo.setSubject(Subject);
                mailInfo.setContent(msg);
                // 这个类主要来发送邮件
                SimpleMailSender sms = new SimpleMailSender();
                boolean isSuccess = sms.sendTextMail(mailInfo);// 发送文体格式
                // sms.sendHtmlMail(mailInfo);//发送html格式
                if (isSuccess) {
                    Log.i(TAG, "=============Send a success=============");
                } else {
                    Log.i(TAG, "=============Send failure=============");
                }
            }
        }).start();
    }





















}

























