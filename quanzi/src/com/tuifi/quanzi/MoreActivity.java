package com.tuifi.quanzi;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.tuifi.quanzi.logic.IWeiboActivity;
import com.tuifi.quanzi.logic.MainService;
import com.tuifi.quanzi.util.FrameActivity;
import com.tuifi.quanzi.util.MyActivity;

public class MoreActivity extends FrameActivity implements IWeiboActivity {
	final String[] arr = { "����", "�˺Ź���", "�������", "����", "����°汾" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		initActivity();
		MainService.allActivity.add(this);
		ListView morelist = (ListView) findViewById(R.id.morelist);
		// ����һ������

		// �������װArrayAdapter
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, arr);
		// ΪListView����Adapter
		morelist.setAdapter(arrayAdapter);
		morelist.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> lv, View view, int pos,
					long id) {
				directForm(pos);
			}
		});
	}

	private void initActivity() {
		initTopbar();
		// ��activity������
		// bnotheract.setEnabled(false);
		// bnotherlayout.setSelected(true);
		//
		bnback.setVisibility(View.GONE);
		bnnewmsg.setVisibility(View.GONE);
		bnrefresh.setVisibility(View.GONE);
		title.setText("����");
	}

	private void directForm(int pos) {
		switch (pos) {
		case 0:
			break;
		case 1:
			break;

		case 2:
			break;

		case 3:
			break;

		case 4:
			break;

		case 5:
			break;

		default:
			break;

		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object... param) {
		// TODO Auto-generated method stub

	}

}