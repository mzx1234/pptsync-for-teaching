package com.newppt.android.entity;

import android.graphics.Bitmap;

public class JpgBitmapMessage {

	private Bitmap pptjpgBitmap;
	private Bitmap notejpgBitmap;
	public Bitmap getPptjpg() {
		return pptjpgBitmap;
	}
	public void setPptjpg(Bitmap pptjpgBitmap) {
		this.pptjpgBitmap = pptjpgBitmap;
	}
	public Bitmap getNotejpg() {
		return notejpgBitmap;
	}
	public void setNotejpg(Bitmap notejpgBitmap) {
		this.notejpgBitmap = notejpgBitmap;
	}
	
}
