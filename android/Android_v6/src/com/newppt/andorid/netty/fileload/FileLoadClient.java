package com.newppt.andorid.netty.fileload;


import android.os.Handler;

import com.newppt.andorid.netty.pptsync.PPTSyncClient;
import com.newppt.android.db.dbhelper.MyDBHelper;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * Created by zison on 2016/4/30.
 */
public class FileLoadClient {

	private int port = 8083;
    private String host = "192.168.191.5";
    
    private String cno;
	 private Handler handler;
	 private MyDBHelper _dbHelper;
	 
	 public FileLoadClient(String ip, String cno, Handler handler, MyDBHelper dbHelper) {
		// TODO Auto-generated constructor stub
		 this.host = ip;
		 this.cno = cno;
		 this.handler = handler;
		 this._dbHelper = dbHelper;
	}

    private void startClient() throws Exception {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new FileLoadHandlerInitialize(cno, handler, _dbHelper));

            ChannelFuture f = bootstrap.connect(host, port).sync();

            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
    
    public void start() {
    	new Thread() {
    		@Override
    		public void run() {
    			// TODO Auto-generated method stub
    			try {
					startClient();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}.start();
    }
    

//    public static void main(String []args) {	
//        try {
//            new FileLoadClient().start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
