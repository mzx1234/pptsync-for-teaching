package com.newppt.android.ui;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.dboption.AllOption;
import com.newppt.android.entity.MyPath;
import com.newppt.android.logical.ApplyListAdapter;
import com.newppt.android.logical.FileInfo;
import com.newppt.android.logical.PPTListAdapt;
import com.newppt1.android_v5.R;

public class TongbuTagFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener {
	
	private ListView _tongbuListview;
	private List<String> _pptList = new ArrayList<String>();
	private List<String> _timeList = new ArrayList<String>();
	
//	List<Object> _list;
	
	private ApplyListAdapter _adapter;
//	private PPTListAdapt _adapter;
	String _path;
	MyDBHelper _dbHelper;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.tablist, container, false);
		
		iniView(inflater, rootView);
		
		return rootView;
	}


	private void iniView(LayoutInflater inflater, View rootView) {
		_tongbuListview = (ListView) rootView.findViewById(R.id.pptlist);
		
		try {
			_path = MyPath.rootPath;
			Map<String, Object> map = FileInfo.loadPPTInfo(_path);
			_pptList = (List<String>) map.get("pptList");
			_timeList = (List<String>) map.get("timeList");
			
//			_dbHelper = new MyDBHelper(getActivity(), "androidppt.db3", null, 1);
//			AllOption allOption = new AllOption(_dbHelper);
//			Cursor cursor = allOption.selectBijiPPT();
//			_list = FileInfo.getPPTList(cursor);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_adapter = new ApplyListAdapter(inflater, _pptList, _timeList);
		
//		_adapter = new PPTListAdapt(inflater, _list);
		_tongbuListview.setAdapter(_adapter);

		_tongbuListview.setOnItemClickListener(this);
		_tongbuListview.setOnItemLongClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		final String filePath = _path + _adapter.getItem(position);

		Intent intent = new Intent(getActivity(), PPTView.class);
		intent.putExtra("path", filePath);
		startActivity(intent);
		
		
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		 
		final int index = position;
		
		Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle("ɾ���Ի���");
		dialog.setIcon(R.drawable.delete);
		dialog.setMessage("ȷ��Ҫɾ��ô��");
		
		dialog.setPositiveButton("ȷ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				final String filePath = _path + _adapter.getItem(index);
				if (FileInfo.DeleteFolder(filePath)) {
					Toast.makeText(getActivity(), "ɾ���ɹ�", 2).show();
					_pptList.remove(index);
					_adapter.notifyDataSetChanged();
					_tongbuListview.setAdapter(_adapter);
				} else {
					Toast.makeText(getActivity(), "ɾ��ʧ��", 2).show();
				}
			}
		});
				
		dialog.setNegativeButton("ȡ��",null);
		
		dialog.create();
		dialog.show();

		return true;
	}
}
