package com.newppt.android.ui;

import java.util.List;
import java.util.ListIterator;

import com.newppt.android.data.AnimUtils2;
import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.dboption.PPTPageOption;
import com.newppt.android.logical.FileInfo;
import com.newppt.android.logical.JpgPathInfo;


import com.newppt1.android_v5.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

public class BiJiPPTView extends Activity implements OnGestureListener {

	// �����¼���һЩ��������
	private int _count = 0;
	private long _firClick;
	private long _secClick;
	private boolean _scaleTip = true;
	private float _x;
	private float _y;

	// �ؼ�
	ImageView _pptImageView = null;
	ImageView _noteImageView = null;
	ImageView _shadowImageView = null;
	GestureDetector _detector;

	final int FLIP_DISTANCE = 50;
	final private int FLIP_DISTANCE2 = 20;

	List<JpgPathInfo> _infos = null;
	PPTPageOption pptPageOption;
	MyDBHelper _dbHelper;
	

	ListIterator<JpgPathInfo> _iter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pptview);

		iniView();
	}

	private void iniView() {
		_detector = new GestureDetector(this, this);

		_pptImageView = (ImageView) findViewById(R.id.image);
		_noteImageView = (ImageView) findViewById(R.id.image2);
		_shadowImageView = (ImageView) findViewById(R.id.image3);

		Intent intent = this.getIntent();

		String path1 = intent.getStringExtra("path");

		System.out.println("----1" + path1);
		
		_dbHelper = new MyDBHelper(this, "androidppt.db3", null, 1);
		pptPageOption = new PPTPageOption(_dbHelper);
		Cursor cursor = pptPageOption.selectBiJiPPTPage(path1);
		
		_infos = FileInfo.getPPTAndNote(cursor);
		_iter = _infos.listIterator();

		JpgPathInfo info = _iter.next();
		Bitmap pptBitmap = BitmapFactory.decodeFile(info.getPptJpg());
		_pptImageView.setImageBitmap(pptBitmap);
		_shadowImageView.setImageBitmap(pptBitmap);

		if (info.getNoteJpg() != null) {
			Bitmap noteBitmap = BitmapFactory.decodeFile(info.getNoteJpg());
			_noteImageView.setImageBitmap(noteBitmap);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			_count++;
			if (_count == 1) {
				_firClick = System.currentTimeMillis();
				_x = event.getX();
				_y = event.getY();
			} else if (_count == 2) {
				_secClick = System.currentTimeMillis();
				float mx = event.getX();
				float my = event.getY();
				if (_secClick - _firClick < 700
						&& Math.abs(mx - _x) < FLIP_DISTANCE2
						&& Math.abs(my - _y) < FLIP_DISTANCE2) {
					// ˫���¼�
					if (_scaleTip) {
						_x = mx;
						_y = my;

						AnimUtils2 animUtils2 = new AnimUtils2();
						animUtils2.imageZoomOut(_pptImageView, 200, _x, _y);
						animUtils2.imageZoomOut(_noteImageView, 200, _x, _y);

						_scaleTip = false;

					}

					else {
						AnimUtils2 animUtils2 = new AnimUtils2();
						animUtils2.imageZoomIn(_pptImageView, 200, _x, _y);
						animUtils2.imageZoomIn(_noteImageView, 200, _x, _y);

						_scaleTip = true;
					}
				}

				_count = 0;
				_firClick = 0;
				_secClick = 0;

			}
			// return true;

		}
		return _detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		// �����һ�������¼���X������ڵڶ��������¼���X���곬��FLIP_DISTANCE
		// Ҳ�������ƴ������󻬡�
		System.out.println("-----kkkk");
		AnimUtils2 animUtils2 = new AnimUtils2();
		if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
			// Ϊflipper�����л��ĵĶ���Ч��
			System.out.println("-----5yy");
			if (!_iter.hasNext()) {
				Toast.makeText(this, "���һ��", 2).show();
				return true;
			}

			JpgPathInfo info = _iter.next();
			Bitmap pptBitmap = BitmapFactory.decodeFile(info.getPptJpg());
			_pptImageView.setImageBitmap(pptBitmap);
			_shadowImageView.setImageBitmap(pptBitmap);
			
//			animUtils2.layoutLeftOut(_pptImageView, 200);						
//			animUtils2.drawViewIn(_shadowImageView, 400);
			
			animUtils2.left_Out(this, _pptImageView);
			

			if (info.getNoteJpg() != null) {
				Bitmap noteBitmap = BitmapFactory.decodeFile(info.getNoteJpg());
				_noteImageView.setImageBitmap(noteBitmap);
//				animUtils2.drawViewIn(_noteImageView, 400);
			} else {
				_noteImageView.setImageBitmap(null);
			}

			return true;
		}
		// ����ڶ��������¼���X������ڵ�һ�������¼���X���곬��FLIP_DISTANCE
		// Ҳ�������ƴ������󻬡�
		else if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
			// Ϊflipper�����л��ĵĶ���Ч��
			if (!_iter.hasPrevious()) {
				Toast.makeText(this, "������ҳ", 2).show();
				return true;
			}

			JpgPathInfo info = _iter.previous();
			Bitmap pptBitmap = BitmapFactory.decodeFile(info.getPptJpg());
			_pptImageView.setImageBitmap(pptBitmap);
			_shadowImageView.setImageBitmap(pptBitmap);
//			animUtils2.layoutLeftIn(_pptImageView, 400);
			
//			animUtils2.drawViewOut(_pptImageView, 200);						
//			animUtils2.layoutLeftIn(_shadowImageView, 400);			
			
			animUtils2.right_Out(this, _pptImageView);
//			animUtils2.left_in(this, _pptImageView);
			
			if (info.getNoteJpg() != null) {
				Bitmap noteBitmap = BitmapFactory.decodeFile(info.getNoteJpg());
				_noteImageView.setImageBitmap(noteBitmap);
//				animUtils2.layoutLeftIn(_noteImageView, 400);
//				animUtils2.left_in(this, _noteImageView);
			} else {
				_noteImageView.setImageBitmap(null);
			}

			return true;
		}
		return false;
	}

	private long exitTime = 0;

	// �˳���ʾ
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ�η���ѡ��ҳ��",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				this.finish();
				System.exit(0);
				
				
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}

