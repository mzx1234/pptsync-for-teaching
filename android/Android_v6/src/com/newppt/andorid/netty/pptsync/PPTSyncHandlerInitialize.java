package com.newppt.andorid.netty.pptsync;

import android.os.Handler;

import com.newppt.android.db.dbhelper.MyDBHelper;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class PPTSyncHandlerInitialize extends ChannelInitializer{
	
	private Handler handler;
	MyDBHelper _dbHelper;
	private String cno;
	
	public PPTSyncHandlerInitialize(Handler handler, MyDBHelper dbHelper, String cno) {
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this._dbHelper = dbHelper;
		this.cno = cno;
	}


	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        channelPipeline.addLast(new ObjectEncoder());
        channelPipeline.addLast(new PPTSyncHandler(handler, _dbHelper, cno));
	}

}
