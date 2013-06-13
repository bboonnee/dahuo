package com.tuifi.dahuo.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


public class Shop implements Serializable {
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
	public  String name;//企业名称
	public  String contactname; //联系人
	public  String address;//公司地址
	public  String province;
	public  String city;
	public  String region;// 公司所在地
	public  String mobile;
	public  String telephone;
	public  String scope;//公司内容
	public  String email;
	public  String qq;
	public  String weixin;
	public  String logo;
	public  String authed;
	public  String description;//公司简介
	public  String type;//公司性质 0 配货信息部
	public  String zhizha;//公司营业执照
	public  String faren;//公司法人代表
	public  String createtime;//公司成立时间
	public  String writetime;//写入信息时间
	public  String daishouhuokuan;//是否代收货款
	public  String latitude;
	public  String longtitude;
	public  String registered; //是否注册激活
	public  String lastlogin; //上次登录时间
	
	
	
	public String toJSON(){

	    JSONObject jsonObject= new JSONObject();
	    try {
	        jsonObject.put("id", id);
	        

	        return jsonObject.toString();
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        return "";
	    }

	}
	
}
