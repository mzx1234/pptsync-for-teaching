package com.mzx.pptparseserver.netty.fileload;

import com.mzx.pptcommon.entity.EchoFile;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by zison on 2016/4/30.
 */
public class FileLoadHandler extends ChannelInboundHandlerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private int dataLength = 10240;
    public RandomAccessFile randomAccessFile;
    private int sumCountpackage = 0;
    private String filePath;

    public FileLoadHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded");
        try {
            File file=new File(filePath);

            randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(0);

            if ((randomAccessFile.length() % dataLength) == 0) {
                sumCountpackage = (int) (randomAccessFile.length() / dataLength);
            } else {
                sumCountpackage = (int) (randomAccessFile.length() / dataLength) + 1;
            }
            byte[] bytes = new byte[dataLength];

            logger.info("file long:"+randomAccessFile.length());
            if (randomAccessFile.read(bytes) != -1) {
                EchoFile msgFile = new EchoFile();
                msgFile.setSumCountPackage(sumCountpackage);
                msgFile.setCountPackage(1);
                msgFile.setBytes(bytes);
                msgFile.setFile_md5(file.getName());
                ctx.writeAndFlush(msgFile);
            } else {
                System.out.println("finish");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof EchoFile) {
            EchoFile msgEchoFile = (EchoFile) msg;
            int countPackage = msgEchoFile.getCountPackage();
            randomAccessFile.seek(countPackage * dataLength - dataLength);
            int byteLength = 0;
            // 剩余的文件长度
            long remainderFileCount = randomAccessFile.length()
                    - randomAccessFile.getFilePointer();

            logger.info("剩余文件长度:" + remainderFileCount);

            if (remainderFileCount < dataLength) {
                logger.info("小于固定长度：" + remainderFileCount);
                byteLength = (int) remainderFileCount;

            } else {
                byteLength = dataLength;
            }
            byte[] bytes = new byte[byteLength];
            if (randomAccessFile.read(bytes) != -1 && remainderFileCount > 0) {

                msgEchoFile.setCountPackage(countPackage);

                msgEchoFile.setBytes(bytes);

                ctx.writeAndFlush(msgEchoFile);
            } else {
                randomAccessFile.close();
                ctx.close();
                System.out.println("文件已经读完--------" + remainderFileCount);
            }

        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();;
        ctx.channel().close();

    }
}
