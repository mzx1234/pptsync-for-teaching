package com.mzx.pptparseserver.netty.broadcastppt;

import io.netty.buffer.ByteBuf;

/**
 * Created by zison on 2016/5/29.
 */
public class ByteBufToBytes {

    public byte[] read(ByteBuf datas) {
        byte[] bytes = new byte[datas.readableBytes()];
        datas.readBytes(bytes);
        return bytes;
    }
}