package com.sparrowcode.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

@SuppressWarnings("all")
public class MailSender {

	// #ST fields
	// user's name and password
	private String username;
	private String password;
	// other settings
	private String defaultEncoding = "UTF-8";
	private Properties javaMailProperties;

	// #ED fields

	// #ST properties

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public Properties getJavaMailProperties() {
		return javaMailProperties;
	}

	public void setJavaMailProperties(Properties javaMailProperties) {
		this.javaMailProperties = javaMailProperties;
	}

	// #ED properties

	// #ST public methods

	/**
	 * send a text email.
	 * 
	 * @param mailMessage
	 *            email entity.
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @version v1.0
	 * @date 2016年1月15日
	 * @author HWS
	 */
	public void sendText(MailMessage mailMessage) throws MessagingException, UnsupportedEncodingException {
		// create authenticator an session
		MailAuthenticator authenticator = new MailAuthenticator(this.username, this.password);
		Session session = Session.getDefaultInstance(javaMailProperties, authenticator);
		// according to the session, create a email model(MimeMessage), then set
		// email information about the address part .
		Message message = new MimeMessage(session);
		addDestinations(message, mailMessage);
		// set mail information and content.
		addContent(message, mailMessage, false);
		// send
		Transport.send(message);
	}

	/**
	 * send a html format email,the message content should be html.
	 * 
	 * @param mailMessage
	 *            email entity.
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @version v1.0
	 * @date 2016年1月15日
	 * @author HWS
	 */
	public void sendHtml(MailMessage mailMessage) throws MessagingException, UnsupportedEncodingException {
		// create authenticator an session
		MailAuthenticator authenticator = new MailAuthenticator(this.username, this.password);
		Session session = Session.getDefaultInstance(this.javaMailProperties, authenticator);
		// according to the session, create a email model, then set email
		// information.
		Message message = new MimeMessage(session);
		addDestinations(message, mailMessage);
		// set mail information and content.
		addContent(message, mailMessage, true);
		// send email.
		Transport.send(message);
	}

	// TODO 有问题，需修改
	/**
	 * send a email with image's data nested.<br>
	 * <b>This method has some bug, do not use it! We will fixed it latter.</b>
	 * 
	 * @param mailMessage
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @version v1.0
	 * @date 2016年1月15日
	 * @author HWS
	 */
	@Deprecated
	public void sendHtmlImage(MailMessage mailMessage) throws MessagingException, UnsupportedEncodingException {
		// create authenticator an session
		MailAuthenticator authenticator = new MailAuthenticator(this.username, this.password);
		Session session = Session.getDefaultInstance(this.javaMailProperties, authenticator);
		// according to the session, create a email model, then set email
		// information.
		Message message = new MimeMessage(session);
		addDestinations(message, mailMessage);

		Multipart multipart = new MimeMultipart("related");

		// first part (the html)
		BodyPart messageBodyPart = new MimeBodyPart();
		String htmlText = "<H1>Hello</H1>11111<img src=\"cid:image\">";
		messageBodyPart.setContent(htmlText, "text/html;charset=UTF-8");
		// add it
		multipart.addBodyPart(messageBodyPart);

		// second part (the image)
		messageBodyPart = new MimeBodyPart();
		DataSource fds = new FileDataSource("C:/Users/Administrator/Desktop/1.png");
		messageBodyPart.setDataHandler(new DataHandler(fds));
		messageBodyPart.setHeader("Content-ID", "image");

		// add image to the multipart
		multipart.addBodyPart(messageBodyPart);

		// put everything together
		// set mail information and content.
		message.setSubject(mailMessage.getSubject());
		message.setSentDate(new Date());
		message.setContent(multipart);

		// send email
		Transport.send(message);
	}

	/**
	 * Send a html format email with attachments. We recommend you to use this
	 * method.
	 * 
	 * @param mailMessage
	 *            mail entity.
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @version v1.0
	 * @date 2016年1月15日
	 * @author HWS
	 */
	public void sendHtmlAttach(MailMessage mailMessage) throws MessagingException, UnsupportedEncodingException {
		// create authenticator an session
		MailAuthenticator authenticator = new MailAuthenticator(this.username, this.password);
		Session session = Session.getDefaultInstance(this.javaMailProperties, authenticator);
		// according to the session, create a email model, then set email
		// information.
		Message message = new MimeMessage(session);
		addDestinations(message, mailMessage);
		// create email carrier, including the part of html and the part of
		// attachments.
		Multipart multipart = new MimeMultipart();
		// create the part of html,then add it to the Multipart.
		BodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(mailMessage.getContent(), "text/html; charset=" + this.defaultEncoding);
		multipart.addBodyPart(htmlPart);
		// the part of attachment
		addAttachments(multipart, mailMessage.getAttachFileNames());
		// set mail content.
		message.setSubject(mailMessage.getSubject());
		message.setSentDate(new Date());
		message.setContent(multipart);
		// send email.
		Transport.send(message);
	}

	// #ED public methods

	// #ST private methods
	/**
	 * build a mail's content.
	 * 
	 * @param message
	 * @param mailMessage
	 * @param isHtml
	 * @throws MessagingException
	 * @version v1.0
	 * @date 2016年1月15日
	 * @author HWS
	 */
	private void addContent(Message message, MailMessage mailMessage, boolean isHtml) throws MessagingException {
		message.setSubject(mailMessage.getSubject());
		message.setSentDate(new Date());
		if (isHtml) {
			message.setContent(mailMessage.getContent(), "text/html; charset=" + this.defaultEncoding);
		} else {
			message.setText(mailMessage.getContent());
		}
	}

	/**
	 * add email's destinations.
	 * 
	 * @param message
	 * @param mailMessage
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @version v1.0
	 * @date 2016年1月15日
	 * @author HWS
	 */
	private void addDestinations(Message message, MailMessage mailMessage) throws MessagingException, UnsupportedEncodingException {
		// set sender address and name.
		Address from = new InternetAddress(mailMessage.getFrom());
		((InternetAddress) from).setPersonal(((InternetAddress) from).getPersonal(), "UTF-8");
		message.setFrom(from);
		// set destinations, including to,cc,bcc.
		addAddress(message, Message.RecipientType.TO, mailMessage.getTo());
		addAddress(message, Message.RecipientType.CC, mailMessage.getCc());
		addAddress(message, Message.RecipientType.BCC, mailMessage.getBcc());
	}

	/**
	 * add diffrent destinations.
	 * 
	 * @param message
	 * @param type
	 * @param addresses
	 * @throws MessagingException
	 * @version v1.0
	 * @date 2016年1月15日
	 * @author HWS
	 */
	private void addAddress(Message message, RecipientType type, List<String> addresses) throws MessagingException {
		Address[] adds = new Address[addresses.size()];
		for (int i = 0; i < addresses.size(); i++) {
			Address add = new InternetAddress(addresses.get(i));
			adds[i] = add;
		}
		message.setRecipients(type, adds);
	}

	/**
	 * add attachments to the email.
	 * 
	 * @param multipart
	 * @param attachFileNames
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 * @version v1.0
	 * @date 2016年1月15日
	 * @author HWS
	 */
	private void addAttachments(Multipart multipart, List<String> attachFileNames) throws MessagingException, UnsupportedEncodingException {
		for (int i = 0; i < attachFileNames.size(); i++) {
			BodyPart attachmentPart = new MimeBodyPart();
			String filename = attachFileNames.get(i);
			String shortName = getShortName(filename);
			DataSource source = new FileDataSource(filename);
			attachmentPart.setDataHandler(new DataHandler(source));
			attachmentPart.setFileName(MimeUtility.encodeText(shortName));
			multipart.addBodyPart(attachmentPart);
		}
	}

	public static String getShortName(String fullName) {
		String middle;
		String[] split = fullName.split("\\\\");
		if (split == null || split.length == 0) {
			return null;
		}
		middle = split[split.length - 1];
		split = middle.split("/");
		if (split == null && split.length == 0) {
			return null;
		}
		return split[split.length - 1];
	}
	// #ED private methods
}