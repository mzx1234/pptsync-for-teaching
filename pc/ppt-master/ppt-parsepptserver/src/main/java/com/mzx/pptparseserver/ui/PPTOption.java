package com.mzx.pptparseserver.ui;

import com.mzx.pptcommon.entity.PPTInfo;
import com.mzx.pptparseserver.Main.Main;
import com.mzx.pptparseserver.application.GlobalApplication;
import com.mzx.pptparseserver.service.GetPPTPageService;
import com.mzx.pptparseserver.service.SyncService;
import com.mzx.pptparseserver.service.ParseToImgService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


/**
 * PPT相关操作工具类
 * Created by zison on 2016/1/9.
 */
public class PPTOption {

//    @Autowired
//    private ParseToImgService parseToImgService;
//    @Autowired
//    private GetPPTPageService getPPTPageService;
//    @Autowired
//    private PPTSyncServer pptSyncServer;

    /**
     * 加载操作
     * @param path ppt文件路径
     * @return
     */
    public byte[] load(String path, String fileName) throws Exception {
        ParseToImgService parseToImgService = (ParseToImgService) Main.getBean("parseToImgServiceImpl");
        GetPPTPageService getPPTPageService = (GetPPTPageService) Main.getBean("getPPTPageServiceImpl");
        InputStream inputStream = new FileInputStream(new File(path));
        long pageNum = parseToImgService.parseToImg(fileName, inputStream);
        byte[] imgByte = getPPTPageService.getPPTPage(fileName, 0);
        PPTInfo info = new PPTInfo();
        info.setCurPage(0);
        info.setFileName(fileName);
        info.setImgBytes(imgByte);
        GlobalApplication.CurPPTInfo = info;
        return imgByte;
    }

    /**
     * 切换页数
     * @param cur 切换页码
     * @return
     */
    public  byte[] swichPage(int cur)  {
        GetPPTPageService getPPTPageService = (GetPPTPageService) Main.getBean("getPPTPageServiceImpl");
        SyncService pptSyncService = (SyncService) Main.getBean("syncServiceImpl");
        pptSyncService.pptSync(GlobalApplication.CurPPTInfo.getFileName(), cur);
        return getPPTPageService.getPPTPage(GlobalApplication.CurPPTInfo.getFileName(), cur);
    }
}
