package com.newppt.android.data;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.dboption.AllOption;
import com.newppt.android.db.dboption.BiJiPPTOption;
import com.newppt.android.db.dboption.PPTPageOption;
import com.newppt.android.db.tableentity.BiJiPPT;
import com.newppt.android.db.tableentity.PPTPage;
import com.newppt.android.entity.DMT;
import com.newppt.android.entity.HMessage;
import com.newppt.android.entity.JpgBitmapMessage;
import com.newppt.android.entity.MyPath;
import com.newppt.android.logical.FileInfo;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/**
 * @author mzx
 *
 */
public class ClientSocket extends Thread {
	private String ip;
	private int port = 8888;
	private String cno;
	private Socket socket;
	private Handler handler;
	public Handler reHandler;
	InputStream is;
	ObjectInputStream objectInputStream = null;
	ObjectOutputStream objectOutputStream;
	DataOutputStream os;
	String fileName = null;
	MyDBHelper _dbHelper;
	int page;
	int currentPage;
	final int timeOut = 10 * 1000;
	volatile boolean threadStopTip = false;

	public boolean connectSucceedTip = false;
	

	public ClientSocket(String ip, String cno, MyDBHelper dbHelper,
			Handler handler) {
		this.ip = ip;
		this.cno = cno;
		this._dbHelper = dbHelper;
		this.handler = handler;
		fileName = "";
		page = 0;
		currentPage = 0;

	}

	@Override
	public void run() {
		
		System.out.println("----------run");
		
		// TODO Auto-generated method stub
		try {
			// socket = new Socket(ip, port);
			socket = new Socket();
			// System.out.println("------1-4-0");
			socket.connect(new InetSocketAddress("192.168.191.5", port), timeOut);
			// System.out.println("------1-3-0");
//			socket.setSoTimeout(timeOut);
			// System.out.println("------1-2-0");
			is = socket.getInputStream();
			// System.out.println("------1-1-0");
			connectSucceedTip = true;
			new Thread() {
				public void run() {
					try {
						while (true) {
							// sleep(1000);
							if (threadStopTip) {
								break;
							}
							readAndSendMessage(socket);
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			}.start();
		} catch (SocketTimeoutException e) {
			// TODO: handle exception

			Message msg = new Message();
			if (!socket.isClosed() && socket.isConnected()) {
				System.out.println("------1-1-3-读取超时");
				msg.obj = "读取超时";
			}

			else {
				System.out.println("------1-1-1777-连接超时");
				msg.obj = "连接超时,请确认IP地址是否正确";

			}
			msg.what = 0x110;
			handler.sendMessage(msg);

			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("------1-1-2-连接超时");

			Message msg = new Message();
			msg.what = 0x110;
			msg.obj = "ip地址不正确";
			handler.sendMessage(msg);

			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void setStopTip() {
		threadStopTip = true;
	}

	private void readAndSendMessage(Socket s) throws Exception {
		// System.out.println("-----currentpage-----" + currentPage);
		objectInputStream = new ObjectInputStream(new BufferedInputStream(
				s.getInputStream()));
		DMT msgDmt = (DMT) objectInputStream.readObject();

		objectOutputStream = new ObjectOutputStream(s.getOutputStream());
		objectOutputStream.writeObject(msgDmt);

		os = new DataOutputStream(socket.getOutputStream());
		os.writeUTF("ok");

		if (currentPage != msgDmt.getCurrentPage()
				|| msgDmt.getCurrentPage() == msgDmt.getCurrentPage()
				&& !fileName.equals(msgDmt.getFilename())) {
			currentPage = msgDmt.getCurrentPage();

			fileName = msgDmt.getFilename();

			AllOption allOption = new AllOption(_dbHelper);
			Cursor cursor = allOption.selectFilePPTPage(msgDmt.getFilename(),
					msgDmt.getCurrentPage());
			if (!cursor.moveToNext()) {
				SendFileClient sendFileClient = new SendFileClient(ip, cno,
						_dbHelper, handler, "SVG");

				// ImageBytes imgebytes = new ImageBytes(ip,"SVG");

				Message msg = new Message();
				msg.what = 0x123;
				byte[] buffer = sendFileClient.getByte();
				// byte[] buffer = imgebytes.getByte();

				HMessage hMessage = new HMessage();
				hMessage.setBuffer(buffer);
				hMessage.setMsgDmt(msgDmt);
				msg.obj = hMessage;
				handler.sendMessage(msg);

				// save jpg
				String rootPath = MyPath.rootPath + msgDmt.getFilename();
				String savePath = rootPath + MyPath.pptJpg;

				FileInfo.CreateFile(rootPath);
				FileInfo.CreateFile(savePath);
				String filePath = savePath + "/ppt-" + msgDmt.getCurrentPage()
						+ ".jpg";

				BiJiPPT biJiPPT = new BiJiPPT(rootPath, fileName, cno);
				BiJiPPTOption biJiPPTOption = new BiJiPPTOption(_dbHelper);
				biJiPPTOption.checkAndInserBijiPPT(biJiPPT);

				new SaveJpgThread(buffer, filePath).start();
				PPTPageOption pptPageOption = new PPTPageOption(_dbHelper);
				PPTPage pptPage = new PPTPage(filePath, rootPath, null,
						currentPage);
				pptPageOption.checkAndInserBijiPPTPage(pptPage);
			} else {

				String pptjpgPath = cursor.getString(0);
				BitmapFactory.Options options = new BitmapFactory.Options();
				Bitmap pptBitmap = BitmapFactory
						.decodeFile(pptjpgPath, options);
				Bitmap noteBitmap = null;
				String notejpgPath = cursor.getString(1);

				if (notejpgPath != null) {
					noteBitmap = BitmapFactory.decodeFile(notejpgPath, options);
				}
				JpgBitmapMessage jpMessage = new JpgBitmapMessage();
				jpMessage.setPptjpg(pptBitmap);
				jpMessage.setNotejpg(noteBitmap);

				HMessage hMessage = new HMessage();
				hMessage.setMsgDmt(msgDmt);
				hMessage.setJpgBitmapMessage(jpMessage);

				Message msg = new Message();
				msg.what = 0x111;
				msg.obj = hMessage;
				handler.sendMessage(msg);
			}

		}

	}

	public String getFileName() {
		return fileName;
	}
	
//	public ClientSocket(){}
//	
//	public static void main(String []args) {
//		new ClientSocket().start();
//	}
	
}
