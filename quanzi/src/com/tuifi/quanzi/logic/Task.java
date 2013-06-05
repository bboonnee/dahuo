package com.tuifi.quanzi.logic;

import java.util.Map;

public class Task {
	private int taskID;// ������
	private Map taskParam;// �������
	public static final int TASK_CLEAR_NOTIFY = 10;// ��ȡ�û���ϸ��Ϣ
	public static final int LOAD_QUANZI_LIST = 11;// ��ȡȦ����ϸ��Ϣ
	public static final int LOAD_USER_LIST = 12;// ��ȡ�û���ϸ��Ϣ
	public static final int REFRESH_USER_INFO_LIST = 13;// ��ȡ�û���ϸ��Ϣ
	public static final int LOAD_HUODONG_LIST = 14;// ��ȡ�û���ϸ��Ϣ
	public static final int LOAD_MSG_LIST = 15;// ��ȡ�û���ϸ��Ϣ
	public static final int LOAD_MSG_CHAT = 16;// ��ȡ�û���ϸ��Ϣ
	public static final int REFRESH_USER_LIST = 17;// ��ȡ�û���ϸ��Ϣ
	public static final int REFRESH_USER_ICON = 18;// ��ȡ�û�ICON
	public static final int LOAD_AFFAIRS = 19;// ��ȡ�û�ICON	
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
