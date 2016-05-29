package com.mzx.pptparseserver.netty.pptsync;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by zison on 2016/4/30.
 */
public class PPTSyncServer implements InitializingBean{

    private int port = 8082;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void startServer() throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new PPTSyncHandlerInitialize());

            // Start the server.
            ChannelFuture f = serverBootstrap.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();

        }finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void afterPropertiesSet() throws Exception {
        new Thread() {
            @Override
            public void run() {
                try {
                    startServer();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
