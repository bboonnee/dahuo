package com.tuifi.dahuo.model;

import java.io.Serializable;
import java.util.ArrayList;
import android.graphics.drawable.Drawable;

public class User implements Serializable {
	/**
	 * yibo 2011.10
	 */
	private static final long serialVersionUID = 1L;
	public static final String UID = "uid";
	public static final String UNAME = "uname";
	public static final String ACCOUNT = "account";
	public static final String EMAIL = "email";
	public static final String MOBILE = "mobile";
	public static final String PASSWORD = "password";
	public static final String CTIME = "ctime";
	public static final String CONTACTID = "contactId";
	public static final String USERICON = "userIcon";
	public static final String IMAGEURL = "imageurl";

	public static final String GENDER = "gender";
	public static final String CLASSNO = "classno";
	public static final String CITY = "city";
	public static final String COMPANY = "company";
	public static final String TITLE = "title";

	public static final String WEIGHT = "weight";
	public static final String STUDENTNO = "studentno";
	public static final String PERMISSIONS = "permission";
	public static final String STARTYEAR = "startyear";
	
	public String id;
	public String name;
	public String mobile;
	public String password;
	public String regdate;
	public String lastlogin;
	public String carno;
	public String province;
	public String city;
	public String address;
	public String idcard;
	public String authed;
	public String avatar;
	public String account;
	public String type;
	public String contact;
	public String telephone;
	public String result;

}
