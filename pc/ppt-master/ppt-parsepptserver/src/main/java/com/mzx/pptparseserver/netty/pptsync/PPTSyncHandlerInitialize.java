package com.mzx.pptparseserver.netty.pptsync;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Created by zison on 2016/4/30.
 */
public class PPTSyncHandlerInitialize extends ChannelInitializer{
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
        channelPipeline.addLast(new ObjectEncoder());
        channelPipeline.addLast(new PPTSyncHandler());
    }
}
