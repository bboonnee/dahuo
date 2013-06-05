package com.tuifi.quanzi.model;

import java.io.Serializable;
import java.util.ArrayList;


public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String UID="uid";
	public static final String UNUM="unum";
	public static final String UNAME="uname";
	public static final String UENAME="uename";
	public static final String EMAIL="email";
	public static final String MOBILE="mobile";
	public static final String GRADE="grade";
	public static final String PROJECT="project";
	public static final String CLASSNUM="classnum";
	public static final String PASSWORD="password";
	public static final String SEX="sex";
	public static final String COUNTRY="country";
	public static final String LOCATION="location";
	public static final String BIRTHDAY="birthday";
	public static final String ADDRESS="address";
	public static final String COMPANY="company";
	public static final String TITLE="title";
	public static final String INDUSTRY="industry";
	public static final String HOBBY="hobby";
	public static final String CARNUMBER="carnumber";
	public static final String DESCRIPTION="description";
	public static final String CTIME="ctime";
	public static final String IMEI="imei";


	ArrayList <String> infolist = new ArrayList<String>(); 
	public  String uid;
	public  String unum;// 用户id
	public  String uname;
	public  String uename;
	public  String email;
	public  String mobile;
	public  String grade;
	public  String project;
	public  String classnum;
	public  String password;
	public  String sex;// 用户id
	public  String country;
	public  String location;
	public  String birthday;
	public  String address;
	public  String company;
	public  String title;
	public  String industry;
	public  String hobby;
	public  String carnumber;
	public  String description;
	public  String ctime;
	public  String weibouid;
	public  String weibouserKey;
	public  String weibouserSecret;
	public  String imei;	
	
	public String getuid() {		
		return uid;
	}

	public void setuid(String uid) {
		this.uid = uid;
	}

	public String getunum() {
		return unum;
	}

	public void setunum(String unum) {
		this.unum = unum;
	}

	public String getuname() {
		return uname;
	}

	public void setuname(String uname) {
		this.uname = uname;
	}

	public String getuename() {
		return uename;
	}

	public void setuename(String uename) {
		this.uename = uename;
	}

	public String getemail() {
		return email;
	}

	public void setemail(String email) {
		this.email = email;
	}

	public String getgrade() {
		return grade;
	}

	public void setgrade(String grade) {
		this.grade = grade;
	}

	//
	public String getClassNumber() {
		return classnum;
	}

	public void setClassNumber(String classnum) {
		this.classnum = classnum;
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

	public String getsex() {
		return sex;
	}

	public void setsex(String sex) {
		this.sex = sex;
	}

	public String getcountry() {
		return country;
	}

	public void setcountry(String country) {
		this.country = country;
	}

	public String getbirthday() {
		return birthday;
	}

	public void setbirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getaddress() {
		return address;
	}

	public void setaddress(String address) {
		this.address = address;
	}

	public String getcompany() {
		return company;
	}

	public void setcompany(String company) {
		this.company = company;
	}

	public String gettitle() {
		return title;
	}

	public void settitle(String title) {
		this.title = title;
	}

	public String getindustry() {
		return industry;
	}

	public void setindustry(String industry) {
		this.industry = industry;
	}

	public String gethobby() {
		return hobby;
	}

	public void sethobby(String hobby) {
		this.hobby = hobby;
	}

	public String getcarnumber() {
		return carnumber;
	}

	public void setcarnumber(String carnumber) {
		this.carnumber = carnumber;
	}

	public String getdescription() {
		return description;
	}

	public void setdescription(String description) {
		this.description = description;
	}

	public String getctime() {
		return ctime;
	}

	public void setctime(String ctime) {
		this.ctime = ctime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getproject() {
		return project;
	}

	public void setproject(String project) {
		this.project = project;
	}
	public String getIMEI() {
		return imei;
	}

	public void setIMEI(String imei) {
		this.imei = imei;
	}	
	///
	public String getweibouid() {
		return weibouid;
	}

	public void setweibouid(String weibouid) {
		this.weibouid = weibouid;
	}

	public String getweibouserKey() {
		return weibouserKey;
	}

	public void setweibouserKey(String weibouserKey) {
		this.weibouserKey = weibouserKey;
	}

	public String getweibouserSecret() {
		return weibouserSecret;
	}

	public void setweibouserSecret(String weibouserSecret) {
		this.weibouserSecret = weibouserSecret;
	}	

}
