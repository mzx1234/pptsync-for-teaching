package com.newppt.android.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.dboption.AllOption;
import com.newppt.android.db.dboption.BiJiPPTOption;
import com.newppt.android.db.dboption.CourseOption;
import com.newppt.android.db.dboption.YuanbanPPTOption;
import com.newppt.android.entity.MyPath;
import com.newppt.android.logical.ApplyListAdapter;
import com.newppt.android.logical.FileInfo;
import com.newppt.android.logical.PPTListAdapt;
import com.newppt1.android_v5.R;

import android.R.raw;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class YuanBanTagFragment extends Fragment implements
		OnItemClickListener, OnItemLongClickListener, OnItemSelectedListener,
		android.view.View.OnClickListener{

	private ListView _xiazaiListView;
	private Spinner _spinner;
	private ArrayAdapter<String> _spinnerAdapter;
	private List<String> _spinnerList = new ArrayList<String>();
	
	List<Object> _list;
	private PPTListAdapt _adapter;
	MyDBHelper _dbHelper;
	String _path;
	
	private EditText _selectEdit;
	private ImageView _selectImage;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.tablist, container, false);

		iniView(inflater, rootView);

		return rootView;
	}

	private void iniView(LayoutInflater inflater, View rootView) {
		_xiazaiListView = (ListView) rootView.findViewById(R.id.pptlist);
		_spinner = (Spinner) rootView.findViewById(R.id.chooseCourse_inBijipptlist);
		_selectEdit = (EditText) rootView.findViewById(R.id.selectedit);
		_selectImage = (ImageView) rootView.findViewById(R.id.selectimge);
		try {
			_path = MyPath.savePptFilePath;
			_dbHelper = new MyDBHelper(getActivity(), "androidppt.db3", null, 1);
			// System.out.println("----mzxxxxxxx3");
			AllOption allOption = new AllOption(_dbHelper);
			// System.out.println("----mzxxxxxxx4");
			Cursor cursor = allOption.selectYuanbanPPT();
			// System.out.println("----mzxxxxxxx5");
			_list = FileInfo.getPPTList(cursor);
			// System.out.println("----mzxxxxxxx2");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		_selectImage.setOnClickListener(this);

		_adapter = new PPTListAdapt(inflater, _list);
		_xiazaiListView.setAdapter(_adapter);

		_xiazaiListView.setOnItemClickListener(this);
		_xiazaiListView.setOnItemLongClickListener(this);
		
		initSpainner();
		_spinner.setOnItemSelectedListener(this);
	}
	
	
	/**
	 * 初始化Spainner
	 */
	private void initSpainner() {
		_spinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, _spinnerList);
		_spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		_spinnerList.add("全部");
		CourseOption cOption = new CourseOption(_dbHelper);				       									
		Cursor cursor = cOption.selectCourse();
		while (cursor.moveToNext()) {				
			String cname = cursor.getString(1);			
			_spinnerList.add(cname);			
		}
		_spinner.setAdapter(_spinnerAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

		// final String filePath = _path + _adapter.getItem(position);
		final String filePath = (String) _adapter.getItem(position);

		if (filePath != null) {
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			File file = new File(filePath);
			intent.setDataAndType(Uri.fromFile(file), "text/*");
			startActivity(intent);
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		final int index = position;

		Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle("删除对话框");
		dialog.setIcon(R.drawable.delete);
		dialog.setMessage("确定要删除么？");

		dialog.setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// final String filePath = _path + _adapter.getItem(index);
				final String filePath = (String) _adapter.getItem(index);
				if (FileInfo.DeleteFolder(filePath)) {
					Toast.makeText(getActivity(), "删除成功", 2).show();

					YuanbanPPTOption yuanbanPPTOption = new YuanbanPPTOption(
							_dbHelper);
					yuanbanPPTOption.deleteYuanbanPPT(filePath);
					_list.remove(index);

					// 判断是否该PPT文件所属学科是否只有一门
					if (_list.get(index - 1) instanceof String
							&& _list.size() == index) {
						_list.remove(index - 1);
					}

					else {
						if (_list.get(index - 1) instanceof String
								&& _list.get(index) instanceof String)
							_list.remove(index - 1);
					}

					_adapter.notifyDataSetChanged();
					_xiazaiListView.setAdapter(_adapter);
				} else {
					Toast.makeText(getActivity(), "删除失败", 2).show();
				}
			}
		});

		dialog.setNegativeButton("取消", null);

		dialog.create();
		dialog.show();

		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		if( _spinnerAdapter.getItem(position).equals("全部")) {
			AllOption allOption = new AllOption(_dbHelper);
			Cursor cursor = allOption.selectYuanbanPPT();
			try {
				_list = FileInfo.getPPTList(cursor);
				_adapter.setList(_list);
				_adapter.notifyDataSetChanged();
				_xiazaiListView.setAdapter(_adapter);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		AllOption allOption = new AllOption(_dbHelper);
        String cname = _spinnerAdapter.getItem(position);
		Cursor cursor = allOption.selectYuanbanPPT(cname);
		System.out.println("---"+cursor.getCount());
		try {		
	 
			_list = FileInfo.getPPTList(cursor);
			_adapter.setList(_list);
			_adapter.notifyDataSetChanged();
			_xiazaiListView.setAdapter(_adapter);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AllOption allOption = new AllOption(_dbHelper);
		String key = _selectEdit.getText().toString();
		System.out.println("---"+key);
		Cursor cursor = allOption.selectYuanbanPPTKey(key);
		try {
			_list = FileInfo.getPPTList(cursor);
			_adapter.setList(_list);
			_adapter.notifyDataSetChanged();
			_xiazaiListView.setAdapter(_adapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
