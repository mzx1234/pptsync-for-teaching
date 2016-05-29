package com.mzx.pptsyncshow.main;

import com.mzx.pptparseserver.utility.POIParse;
import com.mzx.pptsyncshow.BaseTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

/**
 * Created by zison on 2016/4/20.
 */
public class MainTest extends BaseTest {

    @Test
    public void mainTest() throws Exception{
        new ClassPathXmlApplicationContext(
                "application-context.xml");


        POIParse pOIParse = (POIParse) getBean("pOIParse");
        System.out.println("======================");
        System.out.println("ppt service start...");
        System.out.println("======================");

    }
}
