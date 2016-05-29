package com.mzx.pptparseserver.netty.client;

import com.mzx.pptcommon.entity.EchoFile;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Created by zison on 2016/5/3.
 */
public class FileLoadHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String file_dir = "E:";
    private int dataLength=1024;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof EchoFile) {
            EchoFile ef = (EchoFile) msg;
            int SumCountPackage=ef.getSumCountPackage();
            int countPackage=ef.getCountPackage();
            byte[] bytes = ef.getBytes();
            String md5 = ef.getFile_md5();//文件名

            String path = file_dir + File.separator + md5;
            File file = new File(path);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(countPackage * dataLength - dataLength);
            randomAccessFile.write(bytes);
            logger.info("zongbaoshu ："+ef.getSumCountPackage());
            logger.info("shoudaodi" + countPackage + "bao");
            logger.info("benbaozisu:"+bytes.length);
            countPackage=countPackage+1;

            if (countPackage<=SumCountPackage) {
                ef.setCountPackage(countPackage);
                ef.setBytes(null);
                ctx.writeAndFlush(ef);
                randomAccessFile.close();
            } else {
                randomAccessFile.close();
                ctx.close();
            }
        }
    }
}
