package com.mzx.pptparseserver.netty.fileload;

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
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
        ch.pipeline().addLast(new ObjectEncoder());
        channelPipeline.addLast(new FileLoadHandler("D://abc.pptx"));
    }
}
