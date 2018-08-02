package com.sparrowcode.mail;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SparrowMailTest {

	private static String username = "497721267@qq.com";
	private static String password = "vinsonhu19911109";

	// ************************自定义封装方式API方式
	@Test
	public void sendText() throws UnsupportedEncodingException, MessagingException {

		MailMessage mailMessage = new MailMessage("lalahu<497721267@qq.com>", "hws_work@163.com", null, "497721267@qq.com", "测试数据", "测试数据数据数据、。。。。");

		MailSender mailSender = new MailSender();

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.host", "smtp.qq.com");
		javaMailProperties.put("mail.smtp.port", "25");
		javaMailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		mailSender.sendText(mailMessage);
	}

	@Test
	public void sendHtml() throws MessagingException, UnsupportedEncodingException {

		MailMessage mailMessage = new MailMessage("lalahu<497721267@qq.com>", "hws_work@163.com", null, "497721267@qq.com", "测试数据", "<a href='http://www.baidu.com'>测试数据数据数据</a>。。。。");

		MailSender mailSender = new MailSender();

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.host", "smtp.qq.com");
		javaMailProperties.put("mail.smtp.port", "25");
		javaMailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		mailSender.sendHtml(mailMessage);
	}

	@Test
	public void sendHtmlImage() throws MessagingException {
		MailSender mailSender = new MailSender();

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.host", "smtp.qq.com");
		javaMailProperties.put("mail.smtp.port", "25");
		javaMailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		// mailSender.sendHtmlImage();
	}

	@Test
	public void SendHtmlAttachment() throws MessagingException, UnsupportedEncodingException {
		MailMessage mailMessage = new MailMessage("lalahu<497721267@qq.com>", "hws_work@163.com", null, "497721267@qq.com", "测试数据", "<a href='http://www.baidu.com'>测试数据数据数据</a>。。。。");
		mailMessage.addAttachFileName("C:\\Users\\Zero\\Desktop\\HWS_Frame_Spring.docx");

		MailSender mailSender = new MailSender();

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.host", "smtp.qq.com");
		javaMailProperties.put("mail.smtp.port", "25");
		javaMailProperties.put("mail.debug", "true");

		mailSender.setJavaMailProperties(javaMailProperties);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		mailSender.sendHtmlAttach(mailMessage);
	}

	// ************************自定义封装Spring管理方式
	@Test
	public void SpringSendText() throws MessagingException, UnsupportedEncodingException {
		ApplicationContext actx = new ClassPathXmlApplicationContext("spring-mail.xml");
		MailSender mailSender = (MailSender) actx.getBean("mailSender");
		MailMessage mailMessage = (MailMessage) actx.getBean("mailMessage");

		mailMessage.addTo("hws_work@163.com");
		mailMessage.setSubject("spring封装测试");
		mailMessage.setContent("spring封装测试邮件。。。");

		mailSender.sendHtml(mailMessage);



	}

}
