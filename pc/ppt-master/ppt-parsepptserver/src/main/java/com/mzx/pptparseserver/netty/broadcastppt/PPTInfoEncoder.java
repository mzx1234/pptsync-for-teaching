package com.mzx.pptparseserver.netty.broadcastppt;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by zison on 2016/5/27.
 */
public class PPTInfoEncoder extends MessageToMessageEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        msg.retain();
        out.add(new DatagramPacket(msg, new InetSocketAddress("255.255.255.255", 8989)));
        ctx.flush();
    }

}
