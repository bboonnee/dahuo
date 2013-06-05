package com.tuifi.quanzi.model;

import java.io.Serializable;
import java.util.ArrayList;
import android.graphics.drawable.Drawable;


public class User implements Serializable {
	/**
	 * yibo 2011.10
	 */
	private static final long serialVersionUID = 1L;
	public static final String UID="uid";	
	public static final String UNAME="uname";	
	public static final String EMAIL="email";
	public static final String MOBILE="mobile";	
	public static final String PASSWORD="password";
	public static final String CTIME="ctime";	
	public static final String CONTACTID="contactId";
	public static final String USERICON="userIcon";
	public static final String IMAGEURL="imageurl";
	


	ArrayList <String> userlist = new ArrayList<String>(); 
	public  String uid;	
	public  String uname;	
	public  String email;
	public  String mobile;
	public  String password;
	public  String ctime;
	public  String contactId;
	public  String imageurl;
	private Drawable userIcon;
	
	public String getuid() {		
		return uid;
	}

	public void setuid(String uid) {
		this.uid = uid;
	}

	public String getuname() {
		return uname;
	}

	public void setuname(String uname) {
		this.uname = uname;
	}
	
	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}
	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getpassword() {
		return password;
	}

	public void setpassword(String password) {
		this.password = password;
	}

	
	public String getctime() {
		return ctime;
	}

	public void setctime(String ctime) {
		this.ctime = ctime;
	}
	
	public String getcontactId() {
		return contactId;
	}

	public void setcontactId(String contactId) {
		this.contactId = contactId;
	}
    // «∑Ò”–Õº∆¨imageurl
	public String getimageurl() {
		return imageurl;
	}

	public void setimageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public Drawable getUserIcon() {
	return userIcon;
	}

	public void setUserIcon(Drawable userIcon) {
	this.userIcon = userIcon;
	}
	
}
