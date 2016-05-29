package com.mzx.pptparseserver.netty.broadcastppt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;

import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by zison on 2016/5/28.
 */
public class PPTInfoDecoder extends MessageToMessageDecoder<DatagramPacket> {
    private final ClassResolver classResolver = ClassResolvers.cacheDisabled(null);

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        ByteBuf frame = msg.content();


//        ObjectInputStream is = new ObjectInputStream(new ByteBufInputStream(frame));
//        Object result = is.readObject();
//        is.close();
        frame.retain();
        out.add(frame);
    }
}
