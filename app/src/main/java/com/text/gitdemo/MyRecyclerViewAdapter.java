package com.text.gitdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.text.gitdemo.data.ResponseUser;

import java.util.List;

/**
 * Created by mengyuanyuan on 2017/7/3.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
	private List<ResponseUser> data;


	public MyRecyclerViewAdapter(List<ResponseUser> data) {
		this.data = data;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview,parent,false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.setName(data.get(position).getName())
				.setId(data.get(position).getId());
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		private TextView mNameTv;
		private TextView mIdTv;
		public ViewHolder(View v) {
			super(v);
			mNameTv = (TextView) v.findViewById(R.id.name);
			mIdTv = (TextView) v.findViewById(R.id.id);
		}
		public ViewHolder setName(String name){
			mNameTv.setText(name);
			return this;
		}
		public ViewHolder setId(String id){
			mIdTv.setText(id);
			return this;
		}
	}
}
