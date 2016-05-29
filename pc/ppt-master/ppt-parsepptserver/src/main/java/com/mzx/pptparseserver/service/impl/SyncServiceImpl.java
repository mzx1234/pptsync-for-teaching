package com.mzx.pptparseserver.service.impl;

import com.mzx.pptcommon.entity.PPTInfo;
import com.mzx.pptparseserver.application.GlobalApplication;
import com.mzx.pptparseserver.netty.broadcastppt.BroadcastPPTServer;
import com.mzx.pptparseserver.netty.pptsync.PPTSyncHandler;
import com.mzx.pptparseserver.service.GetPPTPageService;
import com.mzx.pptparseserver.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zison on 2016/4/30.
 */
@Service
public class SyncServiceImpl implements SyncService {

    @Autowired
    private GetPPTPageService getPPTPageService;

//    @Autowired
//    private BroadcastPPTServer broadcastPPTServer;

    public void pptSync(String fileName, int cur) {
        byte[] bytes= getPPTPageService.getPPTPage(fileName, cur);
//        PPTInfo pptInfo = new PPTInfo();
//        pptInfo.setFileName(fileName);
//        pptInfo.setCurPage(cur);
//        pptInfo.setImgBytes(bytes);

        GlobalApplication.CurPPTInfo.setCurPage(cur);
        GlobalApplication.CurPPTInfo.setImgBytes(bytes);
        PPTInfo pptInfo = new PPTInfo(GlobalApplication.CurPPTInfo);
        pptInfo.setImgBytes(null);

        PPTSyncHandler.channels.writeAndFlush(pptInfo);
//        broadcastPPTServer.broadcastPPTInfo(pptInfo);
    }
}
