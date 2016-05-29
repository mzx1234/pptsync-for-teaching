package com.mzx.pptparseserver.netty.broadcastppt;

import com.mzx.pptcommon.entity.PPTInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zison on 2016/5/28.
 */
public class PPTInfoHandler extends SimpleChannelInboundHandler<PPTInfo> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PPTInfo msg) throws Exception {
        System.out.println(msg);
    }
}
