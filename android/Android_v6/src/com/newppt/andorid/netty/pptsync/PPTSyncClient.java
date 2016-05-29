package com.newppt.andorid.netty.pptsync;

import android.os.Handler;

import com.newppt.android.db.dbhelper.MyDBHelper;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class PPTSyncClient {

	private int port = 8082;
    private String host = "192.168.191.5";
    
	private Handler handler;
	MyDBHelper _dbHelper;
	private String cno;
	private ChannelFuture future;
	
	public PPTSyncClient(String ip, Handler handler, MyDBHelper dbHelper, String cno) {
		// TODO Auto-generated constructor stub
		this.host = ip;
		this.handler = handler;
		this._dbHelper = dbHelper;
		this.cno = cno;
	}

    public void startClient() throws Exception {

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new PPTSyncHandlerInitialize(handler, _dbHelper, cno));

            future = bootstrap.connect(host, port).sync();

            future.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
        }
    }
    
    /**
     * 返回是否连上服务端
     * @return
     */
    public boolean isConnect() {
    	if(future == null) {
    		return false;
    	}
    	return future.channel().isActive();
    }
    
    /**
     * 关闭Channel，关闭成功返回true，否则返回false
     * @return
     */
    public boolean closeChannel() {
    	if(future == null) {
    		return true;
    	}
    	ChannelFuture f = future.channel().close();
    	try {
			f.channel().closeFuture().sync();
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(isConnect()) {
    		return false;
    	}
    	return true;
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

    public static void main(String []args) {	
        try {
//            new PPTSyncClient().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
