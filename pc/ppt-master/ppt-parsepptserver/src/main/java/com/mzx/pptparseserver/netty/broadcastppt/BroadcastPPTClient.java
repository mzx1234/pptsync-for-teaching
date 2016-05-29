package com.mzx.pptparseserver.netty.broadcastppt;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

import java.net.InetSocketAddress;


/**
 * Created by zison on 2016/5/28.
 */
public class BroadcastPPTClient {
    private final Bootstrap bootstrap;
    private final EventLoopGroup group;

    private InetSocketAddress address = new InetSocketAddress(8989);

    public BroadcastPPTClient() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)  //1
                .channel(NioDatagramChannel.class)
//                .option(ChannelOption.SO_BROADCAST, true)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(60240))
        .handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast(new PPTInfoDecoder());  //2
                pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
//                        pipeline.addLast(new MyObjectDecoder());
                pipeline.addLast(new PPTInfoHandler());
            }
        }).localAddress(address);
    }

    public Channel bind() {
        return bootstrap.bind().syncUninterruptibly().channel();  //3
    }

    public static void main(String []args) {

        BroadcastPPTClient client = new BroadcastPPTClient();
        Channel channel = client.bind();
        System.out.println("LogEventMonitor running");

        try {
            channel.closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
