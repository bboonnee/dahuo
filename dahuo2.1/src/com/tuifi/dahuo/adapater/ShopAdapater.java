package com.tuifi.dahuo.adapater;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuifi.dahuo.R;
import com.tuifi.dahuo.model.Msg;

public class ShopAdapater extends BaseAdapter {
	private static final String LOG = "ShopAdapater";
    private LayoutInflater mLayoutInflater;

    private OnClickListener mOnClickListener;
    private Context mContext;
	
	public ShopAdapater(Context context) {
        mContext = context;               
    }
	

	public List<Msg> adaptList = new ArrayList<Msg>();

	public List<Msg> getadaptList() {
		return adaptList;
	}

	public void setadaptList(List<Msg> adaptList) {
		Log.i(LOG, "setadaptList");
		this.adaptList = adaptList;
	}

	public int getCount() {
		// Log.i(LOG, "getCount" + adaptList.size());
		return adaptList.size();
	}

	public Object getItem(int position) {
		Log.i(LOG, "getItem" + position);
		return adaptList.get(position);
	}

	public long getItemId(int position) {
		Log.i(LOG, "getItemId" + position);
		return position;
	}

	public void removeAll() {
		Log.i(LOG, "removeAll");
		adaptList.clear();
		notifyDataSetChanged();
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		// Log.i(LOG, "getView");			
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.contact, null);
		ContactHolder oh = new ContactHolder();
		oh.chclass = (TextView) convertView
				.findViewById(R.id.contact_class);
		oh.chlocatoin = (TextView) convertView
				.findViewById(R.id.contact_location);
		oh.chmobile = (TextView) convertView
				.findViewById(R.id.contact_mobile);
		oh.chname = (TextView) convertView.findViewById(R.id.contact_name);
		oh.chusericon = (ImageView) convertView
				.findViewById(R.id.contact_icon);

		Msg oi = adaptList.get(position);

		if (oi != null) {
			convertView.setTag(oi.getId());
			try {
				// oh.chclass.setText(oi.get());
				oh.chlocatoin.setText(oi.getstartadd());
				oh.chmobile.setText(oi.getendadd());
				oh.chname.setText(oi.getremark());
				oh.chclass.setText(oi.getdeadline());
				//if (oi.getlogo() != null)
					//oh.chusericon.setImageDrawable(oi.getlogo());
				/*
				 * Drawable cachedImage = asyncImageLoader.loadDrawable(
				 * oi.getimageurl(), oh.chusericon, new ImageCallback() {
				 * public void imageLoaded(Drawable imageDrawable, ImageView
				 * imageView, String imageUrl) {
				 * imageView.setImageDrawable(imageDrawable); } }); if
				 * (cachedImage == null) {
				 * oh.chusericon.setImageResource(R.drawable.usericon); }
				 * else { oh.chusericon.setImageDrawable(cachedImage); }
				 */

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return convertView;
	}

	// ////
			public class ContactHolder {
				public ImageView chusericon;
				public TextView chname;
				public TextView chclass;
				public TextView chmobile;
				public TextView chlocatoin;

			}
}
