
package com.tuifi.quanzi.model;

import android.content.Context;
import android.database.DataSetObserver;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.tuifi.quanzi.R;

public class ChatMsgViewAdapter extends BaseAdapter {
    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();

    private ArrayList<ChatMsgEntity> coll;

    private Context ctx;

    public ChatMsgViewAdapter(Context context, ArrayList<ChatMsgEntity> coll) {
        ctx = context;
        this.coll = coll;
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled(int arg0) {
        return false;
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v(TAG, "getView>>>>>>>");
        ChatMsgEntity entity = coll.get(position);
        int itemLayout = entity.getLayoutID();

        LinearLayout layout = new LinearLayout(ctx);
        LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vi.inflate(itemLayout, layout, true);

        TextView tvName = (TextView) layout.findViewById(R.id.messagedetail_row_name);
        tvName.setText(entity.getName());

        TextView tvDate = (TextView) layout.findViewById(R.id.messagedetail_row_date);
        tvDate.setText(entity.getDate());

        TextView tvText = (TextView) layout.findViewById(R.id.messagedetail_row_text);
        tvText.setText(entity.getText());
        return layout;
    }

    public int getViewTypeCount() {
        return coll.size();
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isEmpty() {
        return false;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
}
