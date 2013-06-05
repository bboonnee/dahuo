package com.tuifi.quanzi.model;

import java.io.Serializable;
import java.util.ArrayList;


public class UserInfos implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String USERINFOID="useinfoid";
	public static final String UID="uid";
	public static final String INFOID="infoid";
	public static final String DATASTR="datastr";
	public static final String INFONAME="infoname";
	public static final String INFODATA="infodata";
	public static final String INFOLEVEL="infolevel";
	public static final String CTIME="ctime";



	ArrayList <String> infolist = new ArrayList<String>(); 
	public  String uid;
	public  String useinfoid;// ”√ªßid
	public  String infoid;
	public  String datastr;
	public  String infoname;
	public  String infodata;
	public  String infolevel;
	public  String ctime;
	
	public String getuid() {		
		return uid;
	}

	public void setuid(String uid) {
		this.uid = uid;
	}

	public String getuseinfoid() {
		return useinfoid;
	}

	public void setuseinfoid(String useinfoid) {
		this.useinfoid = useinfoid;
	}

	public String getinfoid() {
		return infoid;
	}

	public void setinfoid(String infoid) {
		this.infoid = infoid;
	}

	public String getdatastr() {
		return datastr;
	}

	public void setdatastr(String datastr) {
		this.datastr = datastr;
	}

	public String getinfoname() {
		return infoname;
	}

	public void setinfoname(String infoname) {
		this.infoname = infoname;
	}

	public String getinfodata() {
		return infodata;
	}

	public void setinfodata(String infodata) {
		this.infodata = infodata;
	}

	//
	public String getinfolevel() {
		return infolevel;
	}

	public void setinfolevel(String infolevel) {
		this.infolevel = infolevel;
	}

	
	public String getctime() {
		return ctime;
	}

	public void setctime(String ctime) {
		this.ctime = ctime;
	}
	
}
