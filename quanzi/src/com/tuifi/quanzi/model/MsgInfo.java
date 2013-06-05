package com.tuifi.quanzi.model;

import java.io.Serializable;
import java.util.ArrayList;


public class MsgInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID="id";
	public static final String CONTENT="content";
	public static final String SENDUID="senduid";
	public static final String RECEIVEUID="receiveuid";
	public static final String QZID="qzid";
	public static final String HDID="hdid";	
	public static final String CTIME="ctime";
	public static final String TYPE="type";



	ArrayList <String> infolist = new ArrayList<String>(); 
	public  String id;
	public  String content;
	public  String senduid;
	public  String receiveuid;
	public  String qzid;
	public  String hdid;	
	public  String ctime;
	public  String type;
	
	
	public String getid() {		
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getcontent() {
		return content;
	}

	public void setcontent(String content) {
		this.content = content;
	}

	public String getsenduid() {
		return senduid;
	}

	public void setsenduid(String senduid) {
		this.senduid = senduid;
	}

	public String getreceiveuid() {
		return receiveuid;
	}

	public void setreceiveuid(String receiveuid) {
		this.receiveuid = receiveuid;
	}

	public String getqzid() {
		return qzid;
	}

	public void setqzid(String qzid) {
		this.qzid = qzid;
	}

	public String gethdid() {
		return hdid;
	}

	public void sethdid(String hdid) {
		this.hdid = hdid;
	}

	public String getctime() {
		return ctime;
	}

	public void setctime(String ctime) {
		this.ctime = ctime;
	}
	public String gettype() {
		return type;
	}

	public void settype(String type) {
		this.type = type;
	}
	
}
