package com.newppt.andorid.netty.pptsync;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.mzx.pptcommon.entity.PPTInfo;
import com.newppt.android.application.GlobalApplication;
import com.newppt.android.data.SaveJpgThread;
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

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PPTSyncHandler extends ChannelInboundHandlerAdapter{
	
	private Handler handler;
	MyDBHelper _dbHelper;
	private String cno;
	volatile boolean threadStopTip = false;
	public boolean connectSucceedTip = false;
	
	public PPTSyncHandler(Handler handler, MyDBHelper dbHelper, String cno) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this._dbHelper = dbHelper;
		this.cno = cno;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		PPTInfo pptInfo = (PPTInfo) msg;
		Channel channel = ctx.channel();
		System.out.println(pptInfo);
		if( isCheckExist(pptInfo)) {
			if(isExistAndHandlerPPTIndo(pptInfo)) {
				return;
			}
			else {
				channel.writeAndFlush(pptInfo);
				return;
			}
		}
		handlerPPTInfo(pptInfo);
	}
	
	

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
	
	private boolean isExistAndHandlerPPTIndo(PPTInfo info) {
		AllOption allOption = new AllOption(_dbHelper);
		Cursor cursor = allOption.selectFilePPTPage(info.getFileName(),
				info.getCurPage());
		if(cursor.moveToNext()) {
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

			DMT msgDmt = new DMT();
			msgDmt.setCurrentPage(info.getCurPage());
			msgDmt.setFilename(info.getFileName());
			msgDmt.setPage(info.getPageNum());
			
			HMessage hMessage = new HMessage();
			hMessage.setMsgDmt(msgDmt);
			hMessage.setJpgBitmapMessage(jpMessage);

			Message msg = new Message();
			msg.what = 0x111;
			msg.obj = hMessage;
			handler.sendMessage(msg);
			return true;
		}
		else {					
			return false;
		}
	}
	
	/**
	 * 是否需要检测数据库是否存在，如果PPTInfo中ImgBytes字段为null，返回true
	 * @param info
	 * @return
	 */
	private boolean isCheckExist(PPTInfo info) {
		return (info.getImgBytes() == null);
	}

	
	/**
	 * 
	 * @param pptInfo
	 */
	private void handlerPPTInfo(PPTInfo pptInfo) {
		// TODO Auto-generated method stub
		DMT msgDmt = new DMT();
		msgDmt.setCurrentPage(pptInfo.getCurPage());
		msgDmt.setFilename(pptInfo.getFileName());
		msgDmt.setPage(pptInfo.getPageNum());
		
		GlobalApplication.dmt = msgDmt;
		
		Message msg = new Message();
		msg.what = 0x123;
		HMessage hMessage = new HMessage();
		hMessage.setBuffer(pptInfo.getImgBytes());
		hMessage.setMsgDmt(msgDmt);
		
		msg.obj = hMessage;
		handler.sendMessage(msg);
		
		saveJPG(pptInfo);
	}

	private void saveJPG(PPTInfo msgDmt) {
		// save jpg
		String rootPath = MyPath.rootPath + msgDmt.getFileName();
		String savePath = rootPath + MyPath.pptJpg;

		FileInfo.CreateFile(rootPath);
		FileInfo.CreateFile(savePath);
		String filePath = savePath + "/ppt-" + msgDmt.getCurPage()
				+ ".jpg";

		BiJiPPT biJiPPT = new BiJiPPT(rootPath, msgDmt.getFileName(), cno);
		BiJiPPTOption biJiPPTOption = new BiJiPPTOption(_dbHelper);
		biJiPPTOption.checkAndInserBijiPPT(biJiPPT);

		new SaveJpgThread(msgDmt.getImgBytes(), filePath).start();
		PPTPageOption pptPageOption = new PPTPageOption(_dbHelper);
		PPTPage pptPage = new PPTPage(filePath, rootPath, null,
				msgDmt.getCurPage());
		pptPageOption.checkAndInserBijiPPTPage(pptPage);
	}
}
