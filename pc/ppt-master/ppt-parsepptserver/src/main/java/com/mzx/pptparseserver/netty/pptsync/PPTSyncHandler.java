package com.mzx.pptparseserver.netty.pptsync;

import com.mzx.pptcommon.entity.PPTInfo;
import com.mzx.pptparseserver.application.GlobalApplication;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zison on 2016/4/30.
 */

public class PPTSyncHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        channels.add(ctx.channel());
        logger.info("client numbers:"+channels.size());
        if(GlobalApplication.CurPPTInfo != null) {
            PPTInfo pptInfo = new PPTInfo(GlobalApplication.CurPPTInfo);
//            pptInfo.setImgBytes(null);
            ctx.channel().writeAndFlush(pptInfo);
        }

    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PPTInfo pptInfo = new PPTInfo(GlobalApplication.CurPPTInfo);
        ctx.channel().writeAndFlush(pptInfo);
    }
}



