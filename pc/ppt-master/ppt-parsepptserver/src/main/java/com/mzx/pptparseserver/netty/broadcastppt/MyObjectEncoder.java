package com.mzx.pptparseserver.netty.broadcastppt;

import com.mzx.pptcommon.entity.PPTInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by zison on 2016/5/29.
 */
public class MyObjectEncoder extends MessageToByteEncoder<PPTInfo> {
    @Override
    protected void encode(ChannelHandlerContext ctx, PPTInfo msg, ByteBuf out) throws Exception {
        byte[] datas = ByteObjConverter.objectToByte(msg);
        out.writeBytes(datas);
        ctx.flush();
    }
}
