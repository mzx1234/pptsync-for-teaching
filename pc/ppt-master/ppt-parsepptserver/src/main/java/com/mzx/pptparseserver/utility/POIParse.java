package com.mzx.pptparseserver.utility;



import com.mzx.pptcommon.constant.SystemConstant;
import com.mzx.pptcommon.exception.PPTshowException;
import com.mzx.pptcommon.utility.SerializeUtil;
import com.mzx.pptparseserver.constant.PptTypeConstant;
import org.apache.poi.hslf.usermodel.*;
import org.apache.poi.xslf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * POI解析ppt文件工具类
 * Created by zison on 2015/12/31.
 */
public class POIParse {

    private Logger logger = LoggerFactory.getLogger(getClass());

    List<HSLFSlide> pptSlideList;
    List<XSLFSlide> pptxSlideList;
    /**
     * ppt页面大小
     */
    private Dimension pgsize = null;
    /**
     * ppt文件页数
     */
    private int len;

    /**
     * ppt文件类型，包括ppt和pptx
     */
    private PptTypeConstant.PPTType pptType ;


    /**
     * 通过文件路径解析ppt文件成Slide类型
     * @param inputStream
     */
    private  void getPPTSlides(InputStream inputStream) {
        logger.info("getPPTSlides function start");
        HSLFSlideShow pptSlideShow = null;
        try {
            pptSlideShow = new HSLFSlideShow(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件不存在或者文件不是.ppt文件");
            throw new PPTshowException(SystemConstant.ResponseStatusCode.ABNORMAL.getCode(),
                    "文件不存在或者文件不是ppt文件");
        }
        pgsize = pptSlideShow.getPageSize();
        pptSlideList = pptSlideShow.getSlides();

        //改字体，解决乱码问题
        for(HSLFSlide slide : pptSlideList) {
            for(List<HSLFTextParagraph> hslfTextParagraphList : slide.getTextParagraphs()) {
                for(HSLFTextParagraph hslfTextParagraph : hslfTextParagraphList) {
                    for(HSLFTextRun textRun : hslfTextParagraph.getTextRuns()) {
                        textRun.setFontIndex(1);
                        textRun.setFontFamily("宋体");
                    }
                }
            }
        }


        pptType = PptTypeConstant.PPTType.PPT;
        len = pptSlideList.size();


        logger.info("getPPTSlides function is finish");
    }


    /**
     * 通过文件路径解析pptx文件成Slide类型
     * @param inputStream
     */
    private void getPPTXSlides(InputStream inputStream) {
        logger.info("getPPTXSlides function start");
        XMLSlideShow pptxSlideShow = null;
        try {
            pptxSlideShow = new XMLSlideShow(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件不存在或者文件不是pptx文件");
            throw new PPTshowException(SystemConstant.ResponseStatusCode.ABNORMAL.getCode(),
                    "文件不存在或者文件不是.pptx文件");
        }
        pgsize = pptxSlideShow.getPageSize();
        pptxSlideList = pptxSlideShow.getSlides();

        //改字体，解决乱码问题
        for(XSLFSlide xslfSlide : pptxSlideList ) {
            for (XSLFShape shape : xslfSlide.getShapes()) {
                if (shape instanceof XSLFTextShape) {
                    for (XSLFTextParagraph paragraph : ((XSLFTextShape) shape)) {
                        List<XSLFTextRun> truns = paragraph.getTextRuns();
                        for (XSLFTextRun trun : truns) {
                            trun.setFontFamily("宋体");
                        }
                    }
                }
            }
        }
        pptType = PptTypeConstant.PPTType.PPTX;
        len = pptxSlideList.size();

        logger.info("getPPTXSlides function is finish");
    }



    /**
     * 判断文件类型
     * @param fileName
     * @return
     */
    private PptTypeConstant.PPTType isPPT(String fileName) {
        if(fileName.endsWith("ppt"))
            return PptTypeConstant.PPTType.PPT;
        else if(fileName.endsWith("pptx"))
            return PptTypeConstant.PPTType.PPTX;
        else
            return PptTypeConstant.PPTType.NOTPPT;
    }


    /**
     * 将ppt文件解析为img
     * @param inputStream
     * @return
     */
    private Map<byte[],byte[]> parsePPTAndImg(InputStream inputStream) {
        getPPTSlides(inputStream);
        Map<byte[],byte[]> imgMap = new HashMap<byte[],byte[]>();
        for(int cur = 0 ; cur < pptSlideList.size(); cur++) {
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D jpgGraphics = img.createGraphics();
            jpgGraphics.setPaint(Color.white);
            jpgGraphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            pptSlideList.get(cur).draw(jpgGraphics);
            imgMap.put(((cur+1)+"").getBytes(), SerializeUtil.serializeImg(img));
        }
        return imgMap;
    }

    /**
     * 将pptx文件解析为img
     * @param inputStream
     * @return
     */
    private Map<byte[],byte[]> parsePPTXAndImg(InputStream inputStream) {
        getPPTXSlides(inputStream);
        Map<byte[],byte[]> imgMap = new HashMap<byte[],byte[]>();
        for(int cur = 0 ; cur < pptxSlideList.size(); cur++) {
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D jpgGraphics = img.createGraphics();
            jpgGraphics.setPaint(Color.white);
            jpgGraphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            pptxSlideList.get(cur).draw(jpgGraphics);
            //TODO 重启之后redis filed字段总是从1开始，还没找明原因，在此先将就
            imgMap.put(((cur+1)+"").getBytes(), SerializeUtil.serializeImg(img));
        }
        return imgMap;
    }


    /**
     * 将文件解析为img
     * @param fileName
     * @param inputStream
     * @return
     */
    public Map<byte[],byte[]> parsePPTToImg(String fileName, InputStream inputStream) {
        pptType = isPPT(fileName);

        switch (pptType) {
            case PPT:
                return parsePPTAndImg(inputStream);
            case PPTX:
                return parsePPTXAndImg(inputStream);
            default:
                return null;

        }
    }



}
