package com.mzx.pptparseserver.netty.broadcastppt;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.serialization.ClassResolver;

import java.util.List;

/**
 * Created by zison on 2016/5/29.
 */
public class PPTInfoDecoder2 extends LengthFieldBasedFrameDecoder {
    private final ClassResolver classResolver;

    public PPTInfoDecoder2(int maxObjectSize, ClassResolver classResolver) {
        super(maxObjectSize, 0, 4, 0, 4);
        this.classResolver = classResolver;
    }

    public PPTInfoDecoder2(ClassResolver classResolver) {
        this(1048576, classResolver);
    }

//    @Override
//    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
//        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
//    }
}
