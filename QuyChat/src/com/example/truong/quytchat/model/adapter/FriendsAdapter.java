package com.example.truong.quytchat.model.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.truong.quytchat.R;
import com.example.truong.quytchat.model.Account;

public class FriendsAdapter extends BaseAdapter {

	private Context mContext;
	private int layoutID;
	private ArrayList<Account> lstData;
	private ViewHolder holder;

	public FriendsAdapter(Context mContext, int layoutID, ArrayList<Account> lstData) {
		super();
		this.mContext = mContext;
		this.layoutID = layoutID;
		this.lstData = lstData;
	}
	
	public ArrayList<Account> getData(){
		return lstData;
	}
	
	@Override
	public int getCount() {
		return lstData.size();
	}

	@Override
	public Object getItem(int position) {
		return lstData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addAll(ArrayList<Account> lstData) {
		this.lstData.addAll(lstData);
		notifyDataSetChanged();
	}
	
	public void clear(){
		this.lstData.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(layoutID, group, false);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.init(lstData.get(position));
		return view;
	}

	private class ViewHolder {
		private TextView title;
		
		public ViewHolder(View view) {
			title = (TextView) view.findViewById(R.id.title);
		}

		public void init(Account item) {
			title.setText(""+item.getUsername());
			if(item.isOnline()){
				title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_online, 0, R.drawable.arrow, 0);
			} else{
				title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_offline, 0, R.drawable.arrow, 0);
			}
		}

	}

}
