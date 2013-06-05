package com.tuifi.dahuo.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.tuifi.dahuo.model.Region;
import com.tuifi.dahuo.model.User;

public class RegionController extends ModelController implements Serializable {
	/**
	 * yibo
	 */
	private static final long serialVersionUID = 1L;
	public static String LOG = "UserController";

	// search
	public static List<Region> getResionList(Resources resources) {
		// 每次loadlist的时候即出发了修改button的事件
		Log.d(LOG, "getResionList");

		List<Region> list = new ArrayList<Region>();
		JSONArray data;

		try {
			String jsonstr = readCityJsonfile(resources);
			data = fileArray(jsonstr);
			// 清空
			for (int i = 0; i < data.length(); i++) {
				JSONObject d = data.getJSONObject(i);
				Region w = convertJSONObj(d);
				list.add(w);
			}
		} catch (Exception e) {
			// DialogUtil.showDialog(this, "服务器响应异常，请稍后再试！", false);
			e.printStackTrace();
			Log.w(LOG, e.toString());
			return null;
		}
		return list;
	}

	public static List<Region> getProvinceRegion(List<Region> regionlist)
	{
		List<Region> list = new ArrayList<Region>();
		for (int i = 0; i < regionlist.size(); i++) {
			
			Region w = regionlist.get(i);
			if(w.PARENT_ID.equals("1"))
			list.add(w);
		}
		return list;
	}
	public static List<Region> getRegionlist(List<Region> regionlist,String parentid)
	{
		List<Region> list = new ArrayList<Region>();
		for (int i = 0; i < regionlist.size(); i++) {			
			Region w = regionlist.get(i);
			if(w.PARENT_ID.equals(parentid))
			list.add(w);
		}
		return list;
	}
	public static Region convertJSONObj(JSONObject d) {
		try {
			if (d != null) {
				String REGION_ID = d.getString("REGION_ID");
				String REGION_CODE = d.getString("REGION_CODE");
				String REGION_NAME = d.getString("REGION_NAME");
				String PARENT_ID = d.getString("PARENT_ID");
				String REGION_LEVEL = d.getString("REGION_LEVEL");
				String REGION_ORDER = d.getString("REGION_ORDER");
				String REGION_NAME_EN = d.getString("REGION_NAME_EN");
				String REGION_SHORTNAME_EN = d.getString("REGION_SHORTNAME_EN");

				Region w = new Region();
				w.setREGION_ID(REGION_ID);
				w.setREGION_CODE(REGION_CODE);
				w.setREGION_NAME(REGION_NAME);
				w.setPARENT_ID(PARENT_ID);
				w.setREGION_LEVEL(REGION_LEVEL);
				w.setREGION_ORDER(REGION_ORDER);
				w.setREGION_NAME_EN(REGION_NAME_EN);
				w.setREGION_SHORTNAME_EN(REGION_SHORTNAME_EN);

				return w;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String readCityJsonfile(Resources resources) {
		String fileName = "region.json"; // 文件名字

		String res = "";

		try {

			InputStream in = resources.getAssets().open(fileName);

			// \Test\assets\yan.txt这里有这样的文件存在

			int length = in.available();

			byte[] buffer = new byte[length];

			in.read(buffer);

			res = EncodingUtils.getString(buffer, "UTF-8");

		} catch (Exception e) {

			e.printStackTrace();

		}
		return res;
	}
}
