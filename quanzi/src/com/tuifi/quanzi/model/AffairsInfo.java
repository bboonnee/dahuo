package com.tuifi.quanzi.model;

import java.io.Serializable;


public class AffairsInfo  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String ID="id";
	public static final String AID="aid";
	public static final String NAME="name";
	public static final String DEADTIME="deadtime";
	public static final String CRAWLTIME="crawltime";
	public static final String CONTENT="content";	
	public static final String URL="url";
	public static final String LIKENUM="likenum";	
	public static final String SOURCESITE="sourcesite";
	public static final String QUANZI="quanzi";
		


	
	
	private String id;
	private String aid;
	private String name;
	//private String state;
	private String deadtime;
	private String crawltime;
	private String content;
	private String url;
	private String likenum;
	private String sourcesite;
	private String quanzi;

	public AffairsInfo(String aid,String name,  String deadtime, 
			String crawltime, String content,String url, String likenum, String sourcesite, String quanzi) {
		this.aid = aid;
		this.name = name;
		this.deadtime = deadtime;
		this.crawltime = crawltime;
		this.content = content;
		this.url = url;
		this.likenum = likenum;
		this.sourcesite = sourcesite;
		this.quanzi = quanzi;
		
	}
	
	public AffairsInfo() {

	}

	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}
	public String getaid() {
		return aid;
	}

	public void setaid(String aid) {
		this.aid = aid;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getdeadtime() {
		return deadtime;
	}

	public void setdeadtime(String deadtime) {
		this.deadtime = deadtime;
	}

	public String getcrawltime() {
		return crawltime;
	}

	public void setcrawltime(String crawltime) {
		this.crawltime = crawltime;
	}

	public String getcontent() {
		return content;
	}

	public void setcontent(String content) {
		this.content = content;
	}
	public String geturl() {
		return url;
	}

	public void seturl(String url) {
		this.url = url;
	}

	public String getlikenum() {
		return likenum;
	}

	public void setlikenum(String likenum) {
		this.likenum = likenum;
	}

	public String getsourcesite() {
		return sourcesite;
	}

	public void setsourcesite(String sourcesite) {
		this.sourcesite = sourcesite;
	}
	public String getquanzi() {
		return quanzi;
	}

	public void setquanzi(String quanzi) {
		this.quanzi = quanzi;
	}

	

}
