package com.sparrowcode.mail;

import java.util.ArrayList;
import java.util.List;

public class MailMessage {

	// #ST fields
	private String from = null;
	private List<String> to = new ArrayList<String>();
	private List<String> cc = new ArrayList<String>();
	private List<String> bcc = new ArrayList<String>();
	private String subject = null;
	private String content = null;
	private List<String> attachFileNames = new ArrayList<String>();
	private List<String> imageFileNames = new ArrayList<String>();

	// #ED fields

	// #ST properties
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		if (to != null) {
			this.to = to;
		}
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		if (cc != null) {
			this.cc = cc;
		}
	}

	public List<String> getBcc() {
		return bcc;
	}

	public void setBcc(List<String> bcc) {
		if (bcc != null) {
			this.bcc = bcc;
		}
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(List<String> attachFileNames) {
		if (attachFileNames != null) {
			this.attachFileNames = attachFileNames;
		}
	}

	public List<String> getImageFileNames() {
		return imageFileNames;
	}

	public void setImageFileNames(List<String> imageFileNames) {
		if (imageFileNames != null) {
			this.imageFileNames = imageFileNames;
		}
	}

	// #ED properties

	// #ST constructor
	public MailMessage() {
		super();
	}

	public MailMessage(String from, String to, String cc, String bcc, String subject, String content) {
		super();
		if (from != null)
			this.from = from;
		if (to != null)
			this.to.add(to);
		if (cc != null)
			this.cc.add(cc);
		if (bcc != null)
			this.bcc.add(bcc);
		if (subject != null)
			this.subject = subject;
		if (content != null)
			this.content = content;
	}

	// #ED constructor

	// #ST methods
	public void addTo(String to) {
		this.to.add(to);
	}

	public void addAttachFileName(String fullName) {
		this.attachFileNames.add(fullName);
	}

	// #ED methods

}