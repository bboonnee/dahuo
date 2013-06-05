package com.tuifi.dahuo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tuifi.dahuo.controller.RegionController;
import com.tuifi.dahuo.model.Region;
import com.tuifi.dahuo.tools.constActivity;

public class CityActivity extends constActivity {
	private static final String LOG = "CityActivity";
	private Spinner spinnerprovince;
	private Spinner spinnercity;
	private Spinner spinnerxian;
	public  ArrayList<Region> currentRegion;
	String add, type;
	boolean isInit = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_city);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			add = extras.getString("add");
			type = extras.getString("type");
			if (type.equals("postStartAdd")) {
				currentRegion = ApplicationMap.postStartAdd ;
			} else if (type.equals("postEndAdd")) {
				currentRegion = ApplicationMap.postEndAdd ;
			} else if (type.equals("SearchStartAdd")) {
				currentRegion = ApplicationMap.SearchStartAdd ;
			} else if (type.equals("SearchEndAdd")) {
				currentRegion = ApplicationMap.SearchEndAdd ;
			}
		}
		// actionBar.setDisplayHomeAsUpEnabled(true);

		initSpinner();
		//初始化spinner位置
		if(currentRegion!=null)
		{
			try{
				spinnerprovince.setSelection(provinceList.indexOf(currentRegion.get(0)));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			 
		}
		

		findViewById(R.id.btnSelectCity).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {						
						int pindex = spinnerprovince.getSelectedItemPosition();
						int cindex = spinnercity.getSelectedItemPosition();
						int xindex = spinnerxian.getSelectedItemPosition();
						Log.d(LOG, "" + pindex + cindex + xindex);
						Region pcode = provinceList.get(pindex);
						Region ccode = cityList.get(cindex);
						Region xcode = xianList.get(xindex);
						//将p,c,x 分布存在数组的0，1，2号位置						
						if (type.equals("postStartAdd")) {
							ApplicationMap.postStartAdd = new ArrayList<Region>();
							ApplicationMap.postStartAdd.add(pcode);
							ApplicationMap.postStartAdd.add(ccode);
							ApplicationMap.postStartAdd.add(xcode);
						} else if (type.equals("postEndAdd")) {
							ApplicationMap.postEndAdd = new ArrayList<Region>();
							ApplicationMap.postEndAdd.add(pcode);
							ApplicationMap.postEndAdd.add(ccode);
							ApplicationMap.postEndAdd.add(xcode);
						} else if (type.equals("SearchStartAdd")) {
							ApplicationMap.SearchStartAdd = new ArrayList<Region>();
							ApplicationMap.SearchStartAdd.add(pcode);
							ApplicationMap.SearchStartAdd.add(ccode);
							ApplicationMap.SearchStartAdd.add(xcode);
						} else if (type.equals("SearchEndAdd")) {
							ApplicationMap.SearchEndAdd = new ArrayList<Region>();
							ApplicationMap.SearchEndAdd.add(pcode);
							ApplicationMap.SearchEndAdd.add(ccode);
							ApplicationMap.SearchEndAdd.add(xcode);
						}
						finish();
					}
				});
	}

	public void initSpinner() {
		spinnerprovince = (Spinner) findViewById(R.id.spinnerProvince);
		RegionAdapter provinceAdapter = new RegionAdapter(this, provinceList);
		spinnerprovince.setAdapter(provinceAdapter);
		spinnerprovince
				.setOnItemSelectedListener(new ProvinceSelectedListener());
		spinnercity = (Spinner) findViewById(R.id.spinnerCity);
		spinnercity.setOnItemSelectedListener(new CitySelectedListener());
		spinnerxian = (Spinner) findViewById(R.id.spinnerXian);		
		//
	}

	class ProvinceSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			int pos = spinnerprovince.getSelectedItemPosition();
			Region r = provinceList.get(pos);
			String paid = r.getREGION_ID();
			cityList = RegionController.getRegionlist(ApplicationMap.regionList, paid);
			RegionAdapter cityAdapter = new RegionAdapter(parent.getContext(),
					cityList);
			spinnercity.setAdapter(cityAdapter);
			//初始化
			if(currentRegion!=null)
			{
				try{
					spinnercity.setSelection(cityList.indexOf(currentRegion.get(1)));
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				 
			}
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	class CitySelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			if (cityList != null) {
				int pos = spinnercity.getSelectedItemPosition();
				String paid = cityList.get(pos).getREGION_ID();
				xianList = RegionController.getRegionlist(ApplicationMap.regionList, paid);
				RegionAdapter xianAdapter = new RegionAdapter(
						parent.getContext(), xianList);
				spinnerxian.setAdapter(xianAdapter);
				//初始化
				if(currentRegion!=null)
				{
					try{
						spinnerxian.setSelection(xianList.indexOf(currentRegion.get(2)));
					}catch(Exception e)
					{
						e.printStackTrace();
					}					 
				}
							
			}

		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	public class RegionAdapter extends BaseAdapter {
		private List<Region> mList;
		private Context mContext;

		public RegionAdapter(Context pContext, List<Region> pList) {
			this.mContext = pContext;
			this.mList = pList;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mList.get(position);

		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
			convertView = _LayoutInflater.inflate(R.layout.item, null);
			if (convertView != null) {
				TextView _TextView1 = (TextView) convertView
						.findViewById(R.id.region);
				_TextView1.setText(mList.get(position).getREGION_NAME());
			}
			return convertView;

		}

	}
	

}
