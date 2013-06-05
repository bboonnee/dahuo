package com.tuifi.quanzi.logic;

import java.util.Map;

public class Task {
	private int taskID;// 任务编号
	private Map taskParam;// 任务参数
	public static final int TASK_CLEAR_NOTIFY = 10;// 获取用户详细信息
	public static final int LOAD_QUANZI_LIST = 11;// 获取圈子详细信息
	public static final int LOAD_USER_LIST = 12;// 获取用户详细信息
	public static final int REFRESH_USER_INFO_LIST = 13;// 获取用户详细信息
	public static final int LOAD_HUODONG_LIST = 14;// 获取用户详细信息
	public static final int LOAD_MSG_LIST = 15;// 获取用户详细信息
	public static final int LOAD_MSG_CHAT = 16;// 获取用户详细信息
	public static final int REFRESH_USER_LIST = 17;// 获取用户详细信息
	public static final int REFRESH_USER_ICON = 18;// 获取用户ICON
	public static final int LOAD_AFFAIRS = 19;// 获取用户ICON	
	public Task(int id, Map param) {
		this.taskID = id;
		this.taskParam = param;
	}

	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public Map getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(Map taskParam) {
		this.taskParam = taskParam;
	}
}
