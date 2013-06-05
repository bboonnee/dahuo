package com.tuifi.dahuo.model;

import java.io.Serializable;
import java.util.ArrayList;


public class Region implements Serializable {
	/**
	 * yibo 2011.10
	 */
	private static final long serialVersionUID = 1L;
	ArrayList <String> regionlist = new ArrayList<String>(); 
	public  String REGION_ID;	
	public  String REGION_CODE;
	public  String REGION_NAME;
	public  String PARENT_ID;
	public  String REGION_LEVEL;
	public  String REGION_ORDER;
	public  String REGION_NAME_EN;
	public  String REGION_SHORTNAME_EN;	
	
	public String getREGION_ID() {		
		return REGION_ID;
	}

	public void setREGION_ID(String REGION_ID) {
		this.REGION_ID = REGION_ID;
	}

	public String getREGION_CODE() {
		return REGION_CODE;
	}

	public void setREGION_CODE(String REGION_CODE) {
		this.REGION_CODE = REGION_CODE;
	}
	public String getREGION_NAME() {
		return REGION_NAME;
	}

	public void setREGION_NAME(String REGION_NAME) {
		this.REGION_NAME = REGION_NAME;
	}
	
	public String getPARENT_ID() {
		return PARENT_ID;
	}

	public void setPARENT_ID(String PARENT_ID) {
		this.PARENT_ID = PARENT_ID;
	}
	

	public String getREGION_LEVEL() {
		return REGION_LEVEL;
	}

	public void setREGION_LEVEL(String REGION_LEVEL) {
		this.REGION_LEVEL = REGION_LEVEL;
	}

	public String getREGION_ORDER() {
		return REGION_ORDER;
	}

	public void setREGION_ORDER(String REGION_ORDER) {
		this.REGION_ORDER = REGION_ORDER;
	}

	
	public String getREGION_NAME_EN() {
		return REGION_NAME_EN;
	}

	public void setREGION_NAME_EN(String REGION_NAME_EN) {
		this.REGION_NAME_EN = REGION_NAME_EN;
	}
	
	public String getREGION_SHORTNAME_EN() {
		return REGION_SHORTNAME_EN;
	}

	public void setREGION_SHORTNAME_EN(String REGION_SHORTNAME_EN) {
		this.REGION_SHORTNAME_EN = REGION_SHORTNAME_EN;
	}	
	
	
}
