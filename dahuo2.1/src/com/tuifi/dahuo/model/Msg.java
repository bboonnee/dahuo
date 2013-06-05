package com.tuifi.dahuo.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


public class Msg implements Serializable {
	/**
	 * yibo 2011.10
	 */
	private static final long serialVersionUID = 1L;
	public static final String UID="uid";	
	public static final String UNAME="uname";	
	public static final String ACCOUNT="account";
	public static final String EMAIL="email";
	public static final String MOBILE="mobile";	
	public static final String PASSWORD="password";
	public static final String CTIME="ctime";	
	public static final String CONTACTID="contactId";
	public static final String USERICON="userIcon";
	public static final String IMAGEURL="imageurl";
	
	
	public static final String GENDER="gender";	
	public static final String CLASSNO="classno";
	public static final String CITY="city";
	public static final String COMPANY="company";	
	public static final String TITLE="title";
	
	public static final String WEIGHT="weight";
	public static final String STUDENTNO="studentno";
	public static final String PERMISSIONS="permission";
	public static final String STARTYEAR="startyear";
	


	ArrayList <String> userlist = new ArrayList<String>(); 
	public  String id;	
	public  String uid;
	public  String startadd;
	public  String starttime;
	public  String endadd;
	public  String endtime;
	public  String deadline;
	public  String sendtime;
	public  String msgtype;	
	public  String cartype;
	public  String middlecity;
	public  String twoway;
	public  String remark;
	public  String weight;	
	public  String price;
	public  String carlength;
	public  String carno;		
	
	public String getId() {		
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getuid() {
		return uid;
	}

	public void setuid(String uid) {
		this.uid = uid;
	}
	public String getstartadd() {
		return startadd;
	}

	public void setstartadd(String startadd) {
		this.startadd = startadd;
	}
	
	public String getstarttime() {
		return starttime;
	}

	public void setstarttime(String starttime) {
		this.starttime = starttime;
	}
	

	public String getendadd() {
		return endadd;
	}

	public void setendadd(String endadd) {
		this.endadd = endadd;
	}

	public String getendtime() {
		return endtime;
	}

	public void setendtime(String endtime) {
		this.endtime = endtime;
	}

	
	public String getdeadline() {
		return deadline;
	}

	public void setdeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public String getsendtime() {
		return sendtime;
	}

	public void setsendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getmsgtype() {
		return msgtype;
	}

	public void setmsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	//
	public void setcartype(String cartype) {
		this.cartype = cartype;
	}

	public String getcartype() {
		return cartype;
	}
	public void setmiddlecity(String middlecity) {
		this.middlecity = middlecity;
	}

	public String getmiddlecity() {
		return middlecity;
	}
	public void settwoway(String twoway) {
		this.twoway = twoway;
	}

	public String gettwoway() {
		return twoway;
	}
	public void setremark(String remark) {
		this.remark = remark;
	}

	public String getremark() {
		return remark;
	}
	public void setprice(String price) {
		this.price = price;
	}

	public String getprice() {
		return price;
	}
	public void setcarlength(String carlength) {
		this.carlength = carlength;
	}

	public String getcarlength() {
		return carlength;
	}	
	public void setweight(String weight) {
		
		this.weight = weight;
	}

	public String getweight() {
		return weight;
	}	
	public void setcarno(String carno) {
		this.carno = carno;
	}

	public String getcarno() {
		return carno;
	}
	public String toJSON(){

	    JSONObject jsonObject= new JSONObject();
	    try {
	        jsonObject.put("id", getId());
	        jsonObject.put("uid", getuid());
	        jsonObject.put("startadd", getstartadd());
	        jsonObject.put("starttime", getstarttime());
	        jsonObject.put("endadd", getendadd());
	        jsonObject.put("endtime", getendtime());
	        jsonObject.put("deadline", getdeadline());
	        jsonObject.put("sendtime", getsendtime());
	        jsonObject.put("msgtype", getmsgtype());
	        jsonObject.put("cartype", getcartype());
	        jsonObject.put("middlecity", getmiddlecity());
	        jsonObject.put("twoway", gettwoway());
	        jsonObject.put("remark", getremark());
	        jsonObject.put("weight", getweight());
	        jsonObject.put("price", getprice());
	        jsonObject.put("carlength", getcarlength());
	        jsonObject.put("carno", getcarno());

	        return jsonObject.toString();
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        return "";
	    }

	}
	
}
