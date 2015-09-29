package com.example.truong.quytchat.model.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.truong.quytchat.R;
import com.example.truong.quytchat.model.Chat;

public class ChatAdapter extends BaseAdapter {

	private Context mContext;
	private int layoutID;
	private ArrayList<Chat> lstData;
	private ViewHolder holder;

	public ChatAdapter(Context mContext, ArrayList<Chat> lstData) {
		super();
		this.mContext = mContext;
		this.lstData = lstData;
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

	public void addAll(ArrayList<Chat> lstData) {
		this.lstData.addAll(lstData);
		notifyDataSetChanged();
	}

	public void add(Chat chat) {
		this.lstData.add(chat);
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View view, ViewGroup group) {
		if (view == null) {
			layoutID = lstData.get(position).isFlag() ? R.layout.chat_item_sent : R.layout.chat_item_rcv;
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

		public void init(Chat item) {
			title.setText("" + item.getMessage());
		}

	}
}
