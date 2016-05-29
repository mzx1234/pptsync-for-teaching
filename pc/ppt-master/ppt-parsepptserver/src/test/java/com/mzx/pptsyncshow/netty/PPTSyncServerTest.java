package com.mzx.pptsyncshow.netty;

import com.mzx.pptparseserver.netty.pptsync.PPTSyncServer;
import com.mzx.pptsyncshow.BaseTest;
import org.testng.annotations.Test;

/**
 * Created by zison on 2016/4/30.
 */
public class PPTSyncServerTest extends BaseTest {

    @Test
    public void pptSyncServerTest() throws Exception{
        try {
            new PPTSyncServer().startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread.sleep(100 *1000);
    }
}
