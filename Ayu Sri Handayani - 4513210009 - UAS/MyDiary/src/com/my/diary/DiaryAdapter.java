package com.my.diary;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DiaryAdapter extends BaseAdapter {
	private Activity activity;
	private ArrayList<Diary> data_diary = new ArrayList<Diary>();
	private static LayoutInflater inflater = null;
	
	public DiaryAdapter(Activity a, ArrayList<Diary> d) {
		activity = a;
		data_diary = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		public int getCount() {
		return data_diary.size();
		}
		public Object getItem(int position) {
		return data_diary.get(position);
		}
		public long getItemId(int position) {
		return position;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
		vi = inflater.inflate(R.layout.activity_list_item, null);
		TextView idD = (TextView) vi.findViewById(R.id.idD);
		TextView judul = (TextView) vi.findViewById(R.id.judul);
		
		Diary daftar_diary = data_diary.get(position);
		idD.setText(daftar_diary.getidD());
		judul.setText(daftar_diary.getjudul());
		
		return vi;
		}
}
