package com.tuifi.quanzi.model;

import java.io.Serializable;
import java.util.ArrayList;



public class NotifyInfo  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NOTIFY_ID="notify_id";
	public static final String TYPE="type";
	public static final String SEND_UID="send_uid";
	public static final String RECEIVE_UID="receive_uid";
	public static final String NOTIFY_MSG="notify_msg";
	public static final String IS_READ="is_read";
	public static final String QZ_ID="qz_id";
	public static final String HUODONG_ID="huodong_id";	
	public static final String CTIME="ctime";


	//ArrayList <String> msglist = new ArrayList<String>(); 
	public  String notify_id;
	public  String type;// ”√ªßid
	public  String send_uid;
	public  String receive_uid;
	public  String notify_msg;
	public  String is_read;
	public  String huodong_id;
	public  String ctime;
	
	public String getnotify_id() {		
		return notify_id;
	}

	public void setnotify_id(String notify_id) {
		this.notify_id = notify_id;
	}

	public String gettype() {
		return type;
	}

	public void settype(String type) {
		this.type = type;
	}

	public String getSenduid() {
		return send_uid;
	}

	public void setSenduid(String send_uid) {
		this.send_uid = send_uid;
	}

	public String getReceiveuid() {
		return receive_uid;
	}

	public void setReceiveuid(String receive_uid) {
		this.receive_uid = receive_uid;
	}

	public String getNotifymsg() {
		return notify_msg;
	}

	public void setNotifymsg(String notify_msg) {
		this.notify_msg = notify_msg;
	}

	public String getIsread() {
		return is_read;
	}

	public void setIsread(String is_read) {
		this.is_read = is_read;
	}

	//
	public String getHuodongid() {
		return huodong_id;
	}

	public void setHuodongid(String huodong_id) {
		this.huodong_id = huodong_id;
	}

	public String getctime() {
		return ctime;
	}

	public void setctime(String ctime) {
		this.ctime = ctime;
	}
}
