package com.newppt.andorid.netty.fileload;

import android.os.Handler;

import com.newppt.android.db.dbhelper.MyDBHelper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by zison on 2016/4/30.
 */
public class FileLoadHandlerInitialize extends ChannelInitializer {
	
	private String cno;
	 private Handler handler;
	 private MyDBHelper _dbHelper;
	 
	 public FileLoadHandlerInitialize(String cno, Handler handler, MyDBHelper dbHelper) {
		// TODO Auto-generated constructor stub
		 this.cno = cno;
		 this.handler = handler;
		 this._dbHelper = dbHelper;
	}
	
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
        ch.pipeline().addLast(new ObjectEncoder());
        channelPipeline.addLast(new FileLoadHandler(cno, handler, _dbHelper));
    }
}
