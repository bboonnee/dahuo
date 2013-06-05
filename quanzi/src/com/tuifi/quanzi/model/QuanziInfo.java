package com.tuifi.quanzi.model;

import java.io.Serializable;

import android.graphics.drawable.Drawable;


public class QuanziInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID="id";
	public static final String NAME="name";
	public static final String FATHERID="fatherid";
	public static final String FATHERNAME="fathername";
	public static final String DESCRIPTION="description";
	public static final String AUTHORITY="authority";
	public static final String TYPE="type";	
	public static final String DETAIL="detail";	
	public static final String CTIME="ctime";
	public static final String CUID="cuid";
	public static final String FTIME="ftime";
	public static final String USERNUM="usernum";
	public static final String ICON="icon";


	public  String id;
	public  String name;
	public  String fatherid;
	public  String description;
	public  String authority;
	public  String type;
	public  String detail;
	public  String ctime;
	public  String cuid;
	public  String ftime;
	public  String fathername;
	public  String usernum;
	private Drawable icon;
	
	public String getID() {		
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}

	public String gettype() {
		return type;
	}
	public void settype(String type) {
		this.type = type;
	}

	public String getfatherid() {
		return fatherid;
	}

	public void setfatherid(String fatherid) {
		this.fatherid = fatherid;
	}

	public String getdescription() {
		return description;
	}

	public void setdescription(String description) {
		this.description = description;
	}

	public String getauthority() {
		return authority;
	}

	public void setauthority(String authority) {
		this.authority = authority;
	}

	public String getdetail() {
		return detail;
	}

	public void setdetail(String detail) {
		this.detail = detail;
	}

	public String getftime() {
		return ftime;
	}

	public void setftime(String ftime) {
		this.ftime= ftime;
	}
	public String getctime() {
		return ctime;
	}

	public void setctime(String ctime) {
		this.ctime = ctime;
	}
	public String getcuid() {
		return cuid;
	}

	public void setcuid(String cuid) {
		this.cuid = cuid;
	}	
	
	public String getfathername() {
		return fathername;
	}

	public void setfathername(String fathername) {
		this.fathername = fathername;
	}
	
	public String getusernum() {
		return usernum;
	}

	public void setusernum(String usernum) {
		this.usernum = usernum;
	}
	public Drawable getIcon() {
	return icon;
	}

	public void setIcon(Drawable icon) {
	this.icon = icon;
	}
}
