package com.newppt.android.ui;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class NewImage extends ImageView {

	float _mX;
	float _mY;
	private Path _path;
	public Paint _paint = null;
	final int VIEW_WIDTH = 320;
	final int VIEW_HEIGHT = 480;
	// ����һ���ڴ��е�ͼƬ����ͼƬ����Ϊ������
	public Bitmap _bitmapDraw = null;
	// ����cacheBitmap�ϵ�Canvas����
	public Canvas _canvas = null;
	private int _penSize = 6;
	public List<DrawPath> _paths = null;
	private List<DrawPath> _redoPaths = null;
	private DrawPath _dPath = null;

	public NewImage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		innitCanvas();
		innitPaint();

		_paths = new ArrayList<DrawPath>();
		_redoPaths = new ArrayList<DrawPath>();

	}

	public NewImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		innitCanvas();
		innitPaint();

		_paths = new ArrayList<DrawPath>();
		_redoPaths = new ArrayList<DrawPath>();

	}

	public NewImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	// ��ʼ��Canvas
	private void innitCanvas() {
		_bitmapDraw = Bitmap.createBitmap(VIEW_WIDTH, VIEW_HEIGHT,
				Config.ARGB_8888);
		_bitmapDraw.eraseColor(Color.TRANSPARENT);
		_canvas = new Canvas();
		_canvas.setBitmap(_bitmapDraw);
	}

	// ��ʼ��Paint
	private void innitPaint() {
		_path = new Path();
		// ����cacheCanvas������Ƶ��ڴ��е�cacheBitmap��
		// _canvas.setBitmap(_bitmapDraw);
		// ���û��ʵ���ɫ
		_paint = new Paint(Paint.DITHER_FLAG);
		_paint.setColor(Color.RED);

		// ���û��ʷ��?
		_paint.setStyle(Paint.Style.STROKE);

		// �����۽Ǵ�Բ��
		_paint.setStrokeJoin(Paint.Join.ROUND);
		// ������ʼ�ͽ�β�ʼ�����ΪԲ��
		_paint.setStrokeCap(Paint.Cap.ROUND);
		// �����?
		_paint.setAntiAlias(true);
		_paint.setDither(true);
		// ���û��ʿ��?
		_paint.setStrokeWidth(_penSize);
	}

	public void setPaint() {
		// ���û��ʵ���ɫ
		_paint = new Paint(Paint.DITHER_FLAG);
		_paint.setColor(Color.RED);

		// ���û��ʷ��?
		_paint.setStyle(Paint.Style.STROKE);

		// �����۽Ǵ�Բ��
		_paint.setStrokeJoin(Paint.Join.ROUND);
		// ������ʼ�ͽ�β�ʼ�����ΪԲ��
		_paint.setStrokeCap(Paint.Cap.ROUND);
		// �����?
		_paint.setAntiAlias(true);
		_paint.setDither(true);
		// ���û��ʿ��?
		_paint.setStrokeWidth(_penSize);
	}

	// ����Canvas
	public void clear() {
		_paths.clear();
		_redoPaths.clear();

		// ˢ�½���
		invalidate();
	}

	// ������Ƥ��
	public void initEraserPaint() {
		_paint.setStrokeWidth(30);
		_paint.setColor(Color.WHITE);
		_paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
	}

	// �������?
	public void undo() {
		if (!_paths.isEmpty() && _paths.size() > 0) {
			int location = _paths.size() - 1;
			_redoPaths.add(_paths.get(location));
			_paths.remove(location);
			// innitCanvas();
			Iterator<DrawPath> iter = _paths.iterator();
			while (iter.hasNext()) {
				DrawPath dp = iter.next();
				// dp.paint.setColor(dp.color);
				// dp.paint.setMaskFilter(dp.mf);
				// dp.paint.setStrokeWidth(dp.strokeWidth);
				_canvas.drawPath(dp.path, dp.paint);
			}
			invalidate();
		}
	}

	// ��������
	public void redo() {
		if (!_redoPaths.isEmpty() && _redoPaths.size() > 0) {
			// innitCanvas();
			if (!_paths.isEmpty() && _paths.size() > 0) {
				Iterator<DrawPath> iter = _paths.iterator();
				while (iter.hasNext()) {
					DrawPath dp = iter.next();
					// dp.paint.setColor(dp.color);
					// dp.paint.setMaskFilter(dp.mf);
					// dp.paint.setStrokeWidth(dp.strokeWidth);
					_canvas.drawPath(dp.path, dp.paint);
				}
			}
			int pre = _redoPaths.size() - 1;
			DrawPath dp = _redoPaths.get(pre);
			dp.paint.setColor(dp.color);
			dp.paint.setMaskFilter(dp.mf);
			dp.paint.setStrokeWidth(dp.strokeWidth);
			_canvas.drawPath(dp.path, dp.paint);
			_paths.add(dp);
			_redoPaths.remove(pre);
			invalidate();
		}
	}

	// ��յ���?
	public void clearMemery(Bitmap bitmap) {
		// ���ʱ����?
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}

	public byte[] Bitmap2Bytes(Bitmap bitmap) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
		return outStream.toByteArray();
	}

	// // ����ͼƬ
	public void save(byte[] buffer) {

		// try
		// {
		// Bitmap temp = getBmp();
		// File file = new File(filePath);
		// if (!file.isDirectory())
		// {
		// file.mkdirs();
		// }
		// FileOutputStream fos = new FileOutputStream(filePath + picCount +
		// ".png");
		// // ��bitmapת����jpg��ʽ
		// temp.compress(CompressFormat.JPEG, 100, fos);
		// fos.close();
		// // ����ͼƬ�ڴ�
		// clearMemery(temp);
		//
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
	}

	// ��ȡҪ�����bitmap
	public Bitmap getBmp() {
		Bitmap mCombinedBmp = Bitmap.createBitmap(_bitmapDraw.getWidth(),
				_bitmapDraw.getHeight(), Bitmap.Config.ARGB_8888);
		mCombinedBmp.eraseColor(Color.TRANSPARENT);
		Canvas canvas = new Canvas(mCombinedBmp);
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(_bitmapDraw, 0, 0, null);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return mCombinedBmp;
	}

	// �������λ�ã���һ����?
	public void touchStart(float x, float y) {
		_path.moveTo(x, y);
		_mX = x;
		_mY = y;
		System.out.println("----------x=" + x + "  " + "y=" + y);
	}

	// �����ƶ�ƫ����λ�ã���һ���㣩
	public void touchMove(float x, float y) {
		float dx = Math.abs(x - _mX);
		float dy = Math.abs(y - _mY);
		if (dx != 0 && dy != 0) {
			// �����������м��?x+mX)/2��������ߣ������߸�Բ��?
			_path.quadTo(_mX, _mY, (x + _mX) / 2, (y + _mY) / 2);
		}
		_mX = x;
		_mY = y;
	}

	// ���ʻ��߽���ʱ�Ĵ��?������·��ѹ��1/2�󱣴浽��ʱͼƬ��
	public void touchUP() {
		_path.lineTo(_mX, _mY);
		_canvas.drawPath(_path, _paint);
		_paths.add(_dPath);
		_path = null;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			_dPath = new DrawPath();
			_path = new Path();
			_dPath.paint = _paint;
			_dPath.path = _path;
			_dPath.color = _paint.getColor();
			_dPath.mf = _paint.getMaskFilter();
			_dPath.strokeWidth = _paint.getStrokeWidth();
			touchStart(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			touchMove(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			touchUP();
			invalidate();
			System.out.println("----->dafaf");
			break;
		}
		return true;

	}

	@Override
	public void onDraw(Canvas canvas) {
		Paint bmpPaint = new Paint();
		// ��cacheBitmap���Ƶ���View�����?
		canvas.drawBitmap(_bitmapDraw, 0, 0, bmpPaint); // ��
		// ����path����
		if (_path != null) {
			canvas.drawPath(_path, _paint);

		}
	}
	
	public void drawNoteBitmap(Bitmap bitmap) {
		_bitmapDraw = bitmap;
		invalidate();
	}

	// ���滭�ʺ�·��
	public class DrawPath {
		private Path path;// ·��
		private Paint paint;// ����
		private int color; // ��ɫ
		private MaskFilter mf; // Ч��
		private float strokeWidth;// ��С
	}

}
