package com.newppt.android.ui;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.newppt.android.data.GetIpThread;
import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.dboption.AllOption;
import com.newppt.android.db.dboption.CourseOption;
import com.newppt.android.db.tableentity.Course;
import com.newppt.android.entity.MyPath;
import com.newppt.android.logical.FileInfo;
import com.newppt1.android_v5.R;



public class Login extends Activity {

	// private Button ok;
	private ImageView _enter;
	private Button _iPButton;
	private Button _addSubject;

	private ImageView _gotoImageView;
	private EditText _ipEditView;
	private String _ipString;
	private Handler _handler;
	private ImageView _clearEdit;
	
	private Spinner _spinner;
	private List<String> _list = new ArrayList<String>();
	private ArrayAdapter<String> _adapter;
	private Map<String, String> _map = new HashMap<String, String>();
	
	
	MyDBHelper _dbHelper;
	String _selectedCourse;

	// ip��ַ��֤������ʽ
	Pattern pattern = Pattern
			.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");

	public Login() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
        _dbHelper = new MyDBHelper(this, "androidppt.db3", null, 1);
        
		_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, _list);
		_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		iniView();
			
		iniCourse();
	

	}


	private void iniCourse() {

		
	      
		
		Course course[] = new Course[3];
		course[0] = new Course("1", "���������");
		course[1] = new Course("2", "���ݿ�");
		course[2] = new Course("3", "�������");
		CourseOption cOption = new CourseOption(_dbHelper);
		cOption.checkAndInserCourse(course);
		       									
		Cursor cursor = cOption.selectCourse();
		
		
		while (cursor.moveToNext()) {	
			String cno = cursor.getString(0);
			String cname = cursor.getString(1);
			System.out.println("----"+cname);
			_map.put(cname, cno);
			_list.add(cname);			
		}


		_spinner.setAdapter(_adapter);
		
	}

	private void iniView() {
		// �½��ļ���
		FileInfo.CreateFile(MyPath.rootPath);
		FileInfo.CreateFile(MyPath.savePptFilePath);

		_ipEditView = (EditText) this.findViewById(R.id.ip);
		// ok=(Button)this.findViewById(R.id.ok);

		_enter = (ImageView) findViewById(R.id.ok);
		_gotoImageView = (ImageView) this.findViewById(R.id.golist);
		_clearEdit = (ImageView) findViewById(R.id.clearTip);
		_iPButton = (Button) this.findViewById(R.id.getIP);
		_spinner = (Spinner) this.findViewById(R.id.chooseCourse);
		_addSubject = (Button) findViewById(R.id.addsubject);

		
		optionEvent();
		
	}
	
	
   //�����¼�����
	private void optionEvent() {

		_gotoImageView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this, PLFragmentActivity.class);
				startActivity(intent);

			}
		});

		_iPButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new GetIpThread(_handler).start();
			}
		});

		_enter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String ipStr = _ipEditView.getText().toString();

				Matcher matcher = pattern.matcher(ipStr);
				if (!matcher.matches()) {
					Toast.makeText(Login.this, "IP��ַ��ʽ����", 2).show();
					return;
				}

				if (ipStr == null || ipStr.equals("")) {
					Toast.makeText(Login.this, "IP��ַΪ��", 2).show();
					return;
				}
//				String portString = "8888";
				String cno = _map.get(_selectedCourse);
				Intent intent = new Intent(Login.this, SvgView.class);
				intent.putExtra("ip", ipStr);
//				intent.putExtra("port", portString);
				intent.putExtra("cno", cno);
				startActivity(intent);
				

			}
		});

		_clearEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_ipEditView.setText("");
			}
		});

		_handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 0x123)
					_ipString = (String) msg.obj;
				_ipEditView.setText(_ipString);
			}
		};
		
		_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				_selectedCourse = _adapter.getItem(position);				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				_selectedCourse = _adapter.getItem(0);		
			}
			
			
		});
		_spinner.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("dsffasfa");
				int position = _spinner.getSelectedItemPosition();
				Builder dialog = new AlertDialog.Builder(Login.this);
				dialog.setTitle("ɾ����Ŀ");
				dialog.setIcon(R.drawable.delete);
				dialog.setMessage("ȷ��Ҫɾ��ô��");
				dialog.setPositiveButton("ȷ��", new DeleteSucjectOnclik(position));
				dialog.setNegativeButton("ȡ��", null);
				dialog.create();
				dialog.show();
				return true;
			}
			
			class DeleteSucjectOnclik implements android.content.DialogInterface.OnClickListener{

				private int positon;
				
				public DeleteSucjectOnclik(int position) {
					this.positon = position;
				}
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					CourseOption courseOption = new CourseOption(_dbHelper);
					String cname = _list.get(positon);
					courseOption.deleteCourse(cname);
					_list.remove(positon);
					_adapter.notifyDataSetChanged();
					_spinner.setAdapter(_adapter);
				}
				
			}
		});
		

		
		_addSubject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder dialog = new AlertDialog.Builder(Login.this);
				dialog.setTitle("��ӿ�Ŀ");
				View view =  getLayoutInflater().inflate(R.layout.addcourse, null);
				EditText editText = (EditText) view.findViewById(R.id.mysubject1);
				dialog.setView(view);
				
				dialog.setPositiveButton("ȷ��", new AddSubjectDialogOnclick(editText));		
				dialog.setNegativeButton("ȡ��",null);
							               
				
				dialog.create();
				dialog.show();
			}
			
			class AddSubjectDialogOnclick implements android.content.DialogInterface.OnClickListener{

				private EditText editText;
				
				public AddSubjectDialogOnclick(EditText edit) {
					this.editText = edit;
				}
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub		
					
					String cname = editText.getText().toString();
	//				System.out.println("---"+editText.getText());
					if(cname == null || cname.equals("")) {
						Toast.makeText(Login.this, "����Ϊ��", Toast.LENGTH_SHORT).show();
						return;
					}
					AllOption allOption = new AllOption(_dbHelper);
					if(allOption.insertCourse(cname)) {
						Toast.makeText(Login.this, "��ӳɹ�", Toast.LENGTH_SHORT).show();
						_list.add(cname);
						_adapter.notifyDataSetChanged();
						_spinner.setAdapter(_adapter);						
					}
					else {
						Toast.makeText(Login.this, "��ѧ���Ѵ��ڣ����ʧ��", Toast.LENGTH_SHORT).show();
						editText.setText("");						
					}
					
				}
				
			}
			
		});
		
		
		

	}
		
		
	

	private long exitTime = 0;

	// �˳���ʾ
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);			
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		// �˳�����ʱ�ر�MyDatabaseHelper���SQLiteDatabase
		if (_dbHelper != null)
		{
			_dbHelper.close();
		}
	}
	
	
}

