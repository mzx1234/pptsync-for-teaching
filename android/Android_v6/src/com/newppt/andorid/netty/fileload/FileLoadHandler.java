package com.newppt.andorid.netty.fileload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.os.Handler;
import android.os.Message;

import com.mzx.pptcommon.entity.EchoFile;
import com.mzx.pptcommon.entity.PPTFileInfo;
import com.newppt.android.db.dbhelper.MyDBHelper;
import com.newppt.android.db.dboption.YuanbanPPTOption;
import com.newppt.android.db.tableentity.YuanBanPPT;
import com.newppt.android.entity.MyPath;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zison on 2016/4/30.
 */
public class FileLoadHandler extends ChannelInboundHandlerAdapter {
	
	 private String file_dir = MyPath.savePptFilePath;
	 private int dataLength=10240;
	 
	 private String cno;
	 private Handler handler;
	 private MyDBHelper _dbHelper;
	 
	 public FileLoadHandler(String cno, Handler handler, MyDBHelper dbHelper) {
		// TODO Auto-generated constructor stub
		 this.cno = cno;
		 this.handler = handler;
		 this._dbHelper = dbHelper;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		 if (msg instanceof EchoFile) {
	            handlerMsg(ctx, msg);
	     }
		 
	}


	private void handlerMsg(ChannelHandlerContext ctx, Object msg)
			throws FileNotFoundException, IOException {
		EchoFile ef = (EchoFile) msg;
		int SumCountPackage=ef.getSumCountPackage();
		int countPackage=ef.getCountPackage();
		byte[] bytes = ef.getBytes();
		String md5 = ef.getFile_md5();//文件名
		
		YuanbanPPTOption yuanbanPPTOption = new YuanbanPPTOption(_dbHelper);
		String savePath = file_dir + md5;
		if (yuanbanPPTOption.isExistYuanbanPPT(savePath)) {
						Message handlerMsg = new Message();
						handlerMsg.what = 0x127;
						handlerMsg.obj = "本文件已存在";
						handler.sendMessage(handlerMsg);
						ctx.close();
						return ;
		}
		String path = file_dir + File.separator + md5;
		File file = new File(path);
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
		randomAccessFile.seek(countPackage * dataLength - dataLength);
		randomAccessFile.write(bytes);
		System.out.println("-----总包数 ："+ef.getSumCountPackage());
		System.out.println("-----收到第" + countPackage + "包");
		System.out.println("------本包字节数:"+bytes.length);
		countPackage=countPackage+1;
		if (countPackage<=SumCountPackage) {
			System.out.println("------if");
		    ef.setCountPackage(countPackage);
		    ef.setBytes(null);
		    ctx.writeAndFlush(ef);
		    randomAccessFile.close();
		} else {
			System.out.println("------else");
		    randomAccessFile.close();
		    ctx.close();
		    
		    Message handlerMsg = new Message();
			handlerMsg.what = 0x125;
			handlerMsg.obj = "下载结束";
			handler.sendMessage(handlerMsg);
			
			YuanBanPPT yuanBanPPT = new YuanBanPPT(savePath, md5, cno);
			yuanbanPPTOption.inserYuanbanPPT(yuanBanPPT);
		}
		
		
	}
	
	



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Message handlerMsg = new Message();
		handlerMsg.what = 0x126;
		handlerMsg.obj = "下载失败";
        ctx.channel().close();
    }
}
