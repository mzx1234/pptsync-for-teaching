package com.mzx.pptparseserver.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by zison on 2016/5/3.
 */
public class FileLoadClient {

    public void connect() throws Exception {

         String host = "127.0.0.1";
         int port = 8083;

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new FileLoadHandlerInitializer());
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String []args) {
        try {
            new FileLoadClient().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
