package com.newppt.android.entity;

public class HMessage {

	DMT msgDmt;
	JpgBitmapMessage jpgBitmapMessage;
	public JpgBitmapMessage getJpgBitmapMessage() {
		return jpgBitmapMessage;
	}

	public void setJpgBitmapMessage(JpgBitmapMessage jpgBitmapMessage) {
		this.jpgBitmapMessage = jpgBitmapMessage;
	}

	byte[] buffer;

	public DMT getMsgDmt() {
		return msgDmt;
	}

	public void setMsgDmt(DMT msgDmt) {
		this.msgDmt = msgDmt;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

}
