package com.newppt.android.data;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;

import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.dboption.YuanbanPPTOption;
import com.newppt.android.db.tableentity.YuanBanPPT;
import com.newppt.android.entity.DMT;
import com.newppt.android.entity.MyPath;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

public class SendFileClient extends Thread {
//	private Socket s;
	String ip;
	int port = 8889;
	String cno;
	Handler handler;
	MyDBHelper _dbHelper;
	String typeString;
	final int timeOut = 5000;
	

	public SendFileClient(String ip, String cno ,MyDBHelper dbHelper ,Handler handler, String type) {
		// TODO Auto-generated constructor stub
		this.ip = ip;
		this.cno = cno;
		this.handler = handler;
		this.typeString = type;
		this._dbHelper = dbHelper;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		loadFile();
	}

	/**
	 * 下载文件函数
	 */
	
	private void loadFile() {
		try {
//			Socket s = new Socket(ip, port);
			
			Socket s = new Socket();
//			 System.out.println("------1-4-0");
			s.connect(new InetSocketAddress(ip, port), timeOut);
//			 System.out.println("------1-3-0");
			
			
			byte[] buf = new byte[100];
			DataOutputStream os = null;
			os = new DataOutputStream(s.getOutputStream());
			os.writeUTF(typeString);
			System.out.println("-----kk----11" );
			
			s.setSoTimeout(timeOut);
			
			InputStream is = s.getInputStream();
			// ���մ��������ļ���
			int len = is.read(buf);
			String fileName = new String(buf, 0, len,"GBK");
			
			System.out.println(fileName);
			String savePath = MyPath.savePptFilePath + fileName;
			
			YuanbanPPTOption yuanbanPPTOption = new YuanbanPPTOption(_dbHelper);
			
			if (yuanbanPPTOption.isExistYuanbanPPT(savePath)) {
				
	//			System.out.println("-----�ļ��Ѵ���1");
				Message msg = new Message();
				msg.what = 0x127;
				msg.obj = "���ļ��Ѵ���";
				handler.sendMessage(msg);
				return ;
			}

			// ���մ��������ļ�
			FileOutputStream fos = new FileOutputStream(savePath);
	//		System.out.println("-----gg-----11");
			int data;
			byte by[] = new byte[1024];
	//		System.out.println("----uu-----22");
			
			if (typeString.equals("PPT")) {
				Message msg = new Message();
				msg.what = 0x124;
				msg.obj = "��ʼ����";
				handler.sendMessage(msg);
			}

			while (-1 != (data = is.read(by))) {
				// System.out.println("----1");
				fos.write(by, 0, data);
				
			}
	//		System.out.println("---------33");
			fos.close();
			is.close();
			s.close();
			if (typeString.equals("PPT")) {
	//			System.out.println("-----�ļ��Ѵ���2");
				Message msg = new Message();
				msg.what = 0x125;
				msg.obj = "���ؽ���";
				handler.sendMessage(msg);
	//			System.out.println("---------44");
				
				YuanBanPPT yuanBanPPT = new YuanBanPPT(savePath, fileName, cno);
	//			System.out.println("---------55");
				yuanbanPPTOption.inserYuanbanPPT(yuanBanPPT);
	//			System.out.println("---------66");
			}			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Message msg = new Message();
//			System.out.println("-----00000");
			msg.what = 0x126;
			msg.obj = "����ʧ��";
			handler.sendMessage(msg);
			
			e.printStackTrace();
		} 
	}

	public byte[] getByte() throws Exception {
		
		System.out.println("---10010");
		Socket s = new Socket(ip, port);
		System.out.println("---10011");
		DataOutputStream os = null;
		os = new DataOutputStream(s.getOutputStream());
		os.writeUTF(typeString);

		InputStream in = s.getInputStream();
		System.out.println("---------1sss" + "\n");

//		byte[] buf = new byte[100];
//		int len = in.read(buf);
//		String fileName = new String(buf, 0, len);
//		System.out.println(fileName);

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] byffer = new byte[1024];
		int length = -1;
		while ((length = in.read(byffer)) != -1) {
			byteArrayOutputStream.write(byffer, 0, length);

		}
		
		byteArrayOutputStream.close();
		in.close();
		return byteArrayOutputStream.toByteArray();
	}

}
