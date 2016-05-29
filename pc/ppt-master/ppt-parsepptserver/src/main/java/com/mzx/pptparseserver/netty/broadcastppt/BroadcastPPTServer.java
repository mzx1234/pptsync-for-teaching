package com.mzx.pptparseserver.netty.broadcastppt;

import com.mzx.pptcommon.entity.PPTInfo;
import com.mzx.pptparseserver.Main.Main;
import com.mzx.pptparseserver.utility.RedisUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by zison on 2016/5/27.
 */
//@Component
public class BroadcastPPTServer {

    private Channel ch = null;

    public void startServer() throws Exception {
        Bootstrap bootstrap = null;
        EventLoopGroup group = null;
        try {
            bootstrap = new Bootstrap();
            group = new NioEventLoopGroup();
            bootstrap.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new PPTInfoEncoder());
                            pipeline.addLast(new ObjectEncoder());

                        }
                    });

            ch = bootstrap.bind(0).syncUninterruptibly().channel();
        }finally {
//            group.shutdownGracefully();
        }
    }

    public Channel getChannel() {
        return ch;
    }

    public void broadcastPPTInfo(PPTInfo pptInfo) {
        ch.writeAndFlush(pptInfo);
    }

    public static void main(String []args) {

        PPTInfo info = new PPTInfo();
        info.setFileName("mzx.pptx");
        info.setCurPage(2);
        info.setPageNum(7);
        RedisUtil redisUtil = (RedisUtil) getBean("redisUtil");
        info.setImgBytes(redisUtil.hGetBytes("java.ppt", 1 + ""));
//        info.setImgBytes("dddddd".getBytes());

        BroadcastPPTServer server = new BroadcastPPTServer();
        try {
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.broadcastPPTInfo(info);
    }

    public static Object getBean(String name) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "application-context.xml");
        return context.getBean(name);
    }

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    startServer();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
}
