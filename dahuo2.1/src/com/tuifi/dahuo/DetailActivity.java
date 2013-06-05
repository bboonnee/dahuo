package com.tuifi.dahuo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.tuifi.dahuo.controller.MsgController;
import com.tuifi.dahuo.model.Msg;

public class DetailActivity extends Activity {
	public String msgid;
	public Msg currentmsg;
	private EditText info_cargoname,info_weight,info_from,info_to,info_deadline,info_contact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initForms();
		//
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			msgid = extras.getString("id");		
			for(int i =0;i<MsgController.MsgList.size();i++)
			{
				if(MsgController.MsgList.get(i).getId().equals(msgid))
				{
					currentmsg = MsgController.MsgList.get(i);
					break;
				}
			}
			
		}
		if(currentmsg!=null)
		{
			setValues();
		}
	}
	
	public void initForms()
	{
		info_cargoname = (EditText) findViewById(R.id.info_cargoname);
		info_weight = (EditText) findViewById(R.id.info_weight);
		info_from = (EditText) findViewById(R.id.info_from);
		info_to = (EditText) findViewById(R.id.info_to);
		info_deadline = (EditText) findViewById(R.id.info_deadline);
		info_contact = (EditText) findViewById(R.id.info_contact);
		
		findViewById(R.id.btncall).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						
					}
				});		
	}
	public void setValues()
	{
		info_cargoname.setText(currentmsg.getremark());
		info_weight.setText(currentmsg.getweight());
		info_from.setText(currentmsg.getstartadd());
		info_to.setText(currentmsg.getendadd());
		info_deadline.setText(currentmsg.getdeadline());
		info_contact.setText(currentmsg.getuid());		
	}

}
