package com.elivoa.aliprint.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * MailUtil
 * 
 * @TODO Move config to config files.
 */
public class MailUtil {
	// TODO Move Email config to resource.properties
	private static final String SenderEmailAddr = "feedback@arnetminer.org";
	private static final String SMTPUserName = "feedback@arnetminer.org";
	private static final String SMTPPassword = "backfeed";
	private static final String SMTPServerName = "61.135.129.15";
	private static final String TransprotType = "smtp";
	private static Properties props;
	
	private MailUtil(){
	}
	
	static{
		MailUtil.props = new Properties();
		
		MailUtil.props.put("mail.smtp.host", MailUtil.SMTPServerName);
		MailUtil.props.put("mail.smtp.auth", "true");
	}
		
	public static void sendMail(String emailAddr, String mailTitle, String mailConcent){
		Session s = Session.getInstance(MailUtil.props, null);
		s.setDebug(false);
		
		Message message = new MimeMessage(s);
		try{
			Address from = new InternetAddress(MailUtil.SenderEmailAddr);
			message.setFrom(from);
			Address to = new InternetAddress(emailAddr);
			message.setRecipient(Message.RecipientType.TO, to);
			
			message.setSubject(mailTitle);
			Multipart mainPart = new MimeMultipart();
			BodyPart html = new MimeBodyPart();
			html.setContent(mailConcent, "text/html;charset=gbk");
			mainPart.addBodyPart(html);
			message.setContent(mainPart);
			message.setSentDate(new Date());
			message.saveChanges();
			
			Transport transport = s.getTransport(MailUtil.TransprotType);
			transport.connect(MailUtil.SMTPServerName, MailUtil.SMTPUserName, MailUtil.SMTPPassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			System.out.println("发送邮件，邮件地址："+emailAddr);
			System.out.println("标题："+mailTitle);
			System.out.println("内容："+mailConcent);
			System.out.println("成功！");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
