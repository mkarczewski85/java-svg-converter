package com.test.uploadapi;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
public class FileController {

    @Autowired
    FileService service;

    @GetMapping("/stream/{fileName:.+}")
    public void getAsByteStream(@PathVariable String fileName, HttpServletResponse response) throws Exception {
        InputStream in = service.getInputStream();
        response.setContentType("image/svg+xml");
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/thumbnail/{fileName:.+}")
    public void getAsPNGThumbnail(@PathVariable String fileName, HttpServletResponse response) throws Exception {
        InputStream thumbnail = service.getThumbnail();
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(thumbnail, response.getOutputStream());
    }

}
