package com.mzx.pptparseserver.Filter;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by zison on 2016/4/21.
 */
public class CheckFileTypeFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        MultipartFile file = (MultipartFile) request.getAttribute("file");

        if(file != null && !isPPTFile(file)) {
            response.sendRedirect("WEB-INF/views/error/notpptfile.jsp");
            return;
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    private FileItem getFileItem(HttpServletRequest request) throws Exception{
        DiskFileItemFactory factory = new DiskFileItemFactory();

        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items =  upload.parseRequest(request);
        return items.get(0);


    }


    //上传的文件是否ppt文件
    private boolean isPPTFile(MultipartFile file) {
        if(file.getOriginalFilename().endsWith("ppt") || file.getOriginalFilename().endsWith("pptx")) {
            return true;
        }

        return false;
    }

    public void destroy() {

    }
}
