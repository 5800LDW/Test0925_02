package mail;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 简单邮件（不带附件的邮件）发送器
 */
public class SimpleMailSender
{
	/**
	 * 以文本格式发送邮件
	 * @param mailInfo 待发送的邮件的信息
	 */
	public boolean sendTextMail(MailSenderInfo mailInfo)
	{
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate())
		{
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
		try
		{
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO,to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		}
		catch (MessagingException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}




	/**
	 * 以HTML格式发送邮件
	 * @param mailInfo 待发送的邮件信息
	 */
	public static boolean sendHtmlMail(MailSenderInfo mailInfo)
	{
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		//如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate())
		{
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipient(Message.RecipientType.TO,to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}


	/**
	 *
	 * 发送一个邮件,带附件的;
	 *
	 * @param mailInfo
	 * @param f file对象,附件的绝对路径的地址
	 * @return
	 */
	public static boolean sendEmailWithAttachments(MailSenderInfo mailInfo,File f)
	{
		// 判断是否需要身份认证
		MyAuthenticator auth = null;
		Properties props = mailInfo.getProperties();
		if (mailInfo.isValidate())
		{
			// 如果需要身份认证，则创建一个密码验证器
			auth = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(props,auth);
		try
		{
			// 根据session创建一个邮件消息
			Message message = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			message.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			message.setRecipient(Message.RecipientType.TO,to);
			// 设置邮件消息的主题
			message.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			message.setSentDate(new Date());


			try{
				Multipart mpRoot = new MimeMultipart();
				//MimeBodyPart(用于信件内容/附件)
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setContent(mailInfo.getContent(), "text/html;charset=utf-8");


//				mimeBodyPart.attachFile(new File("mail.txt"));
//				mimeBodyPart.setHeader("Content-Type", "text/plain; charset=\"us-ascii\"; name=\"mail.txt\"");


				//添加到MimeMultipart对象中
				mpRoot.addBodyPart(mimeBodyPart);
				ArrayList<File> attachments = new ArrayList<>();
				attachments.add(f);
//				attachments.add(new File("/storage/emulated/0/MyCrash/001.log"));
				for (int i = 0; i < attachments.size(); i++) {
					File file = (File) attachments.get(i);
					String fname = file.getName();
					//创建FileDAtaSource(用于添加附件)
					FileDataSource fds = new FileDataSource(file);
					MimeBodyPart mpAttachments = new MimeBodyPart();
					// 字符流形式装入文件
					mpAttachments.setDataHandler(new DataHandler(fds));
					// 设置附件文件名
					mpAttachments.setFileName(fname);
					mpRoot.addBodyPart(mpAttachments);
//todo
				}
				message.setContent(mpRoot);


				/**设置mailcap支持多种格式**/
				MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
				mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
				mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
				mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
				mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
				mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
				CommandMap.setDefaultCommandMap(mc);



			}catch (Exception e){
				Log.e("TAGTAG",""+e.toString());
			}

			// 发送邮件
			Transport.send(message);
			return true;
		}
		catch (MessagingException ex)
		{
			ex.printStackTrace();
		}
		return false;
	}




//	/**
//	 * 以文本格式发送邮件
//	 * @param mailInfo 待发送的邮件的信息
//	 */
//	public static boolean sendTextMailWtihAccessory(MailSenderInfo mailInfo)
//	{
//		// 判断是否需要身份认证
//		MyAuthenticator authenticator = null;
//		Properties pro = mailInfo.getProperties();
//		if (mailInfo.isValidate())
//		{
//			// 如果需要身份认证，则创建一个密码验证器
//			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
//		}
//		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
//		Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
//		try
//		{
//			// 根据session创建一个邮件消息
//			Message mailMessage = new MimeMessage(sendMailSession);
//			// 创建邮件发送者地址
//			Address from = new InternetAddress(mailInfo.getFromAddress());
//			// 设置邮件消息的发送者
//			mailMessage.setFrom(from);
//			// 创建邮件的接收者地址，并设置到邮件消息中
//			Address to = new InternetAddress(mailInfo.getToAddress());
//			mailMessage.setRecipient(Message.RecipientType.TO,to);
//			// 设置邮件消息的主题
//			mailMessage.setSubject(mailInfo.getSubject());
//
//			Vector<File> files = new Vector<>();
//			files.add(new File("/storage/emulated/0/MyCrash/001.log"));
//			try{
//				MimeMultipart multipart = new MimeMultipart();
//				//MimeBodyPart(用于信件内容/附件)
//				BodyPart bodyPart = new MimeBodyPart();
//				bodyPart.setContent("1234567890", "text/html;charset=utf-8");
//				//添加到MimeMultipart对象中
//				multipart.addBodyPart(bodyPart);
//				for (int i = 0; i < files.size(); i++) {
//					File file = (File) files.elementAt(i);
//                    Log.e("TAGTAG","file = "+file);
//					String fname = file.getName();
//					Log.e("TAGTAG","fname = "+fname);
//					//创建FileDAtaSource(用于添加附件)
//					FileDataSource fds = new FileDataSource(file);
//					BodyPart fileBodyPart = new MimeBodyPart();
//					// 字符流形式装入文件
//					fileBodyPart.setDataHandler(new DataHandler(fds));
//					// 设置附件文件名
//					fileBodyPart.setFileName(fname);
//					multipart.addBodyPart(fileBodyPart);
////todo
//				}
//				mailMessage.setContent(multipart);
//			}catch (Exception e){
//				Log.e("TAGTAG",""+e.toString());
//			}
//
//			// 设置邮件消息发送的时间
//			mailMessage.setSentDate(new Date());
////			// 设置邮件消息的主要内容
//			String mailContent = mailInfo.getContent();
//			mailMessage.setText(mailContent);
//
//			// 存储邮件信息
//			mailMessage.saveChanges();
//
//			// 发送邮件
//			Transport.send(mailMessage);
//			return true;
//		}
//		catch (MessagingException ex)
//		{
//			ex.printStackTrace();
//		}
//		return false;
//	}

//	public  static void test(String content,Vector<File> files,Message message) throws Exception{
//		//创建 Mimemultipart添加内容(可包含多个附件)
//		MimeMultipart multipart = new MimeMultipart();
//		//MimeBodyPart(用于信件内容/附件)
//		BodyPart bodyPart = new MimeBodyPart();
//		bodyPart.setContent(content.toString(), "text/html;charset=utf-8");
//		//添加到MimeMultipart对象中
//		multipart.addBodyPart(bodyPart);
//		for (int i = 0; i < files.size(); i++) {
//			File file = (File) files.elementAt(i);
//			String fname = file.getName();
//			//创建FileDAtaSource(用于添加附件)
//			FileDataSource fds = new FileDataSource(file);
//			BodyPart fileBodyPart = new MimeBodyPart();
//			// 字符流形式装入文件
//			fileBodyPart.setDataHandler(new DataHandler(fds));
//			// 设置附件文件名
//			fileBodyPart.setFileName(fname);
//			multipart.addBodyPart(fileBodyPart);
//			message.setContent(multipart);
//		}
//	}

}