package com.le.yun.controller;

import com.le.yun.constant.Constants;
import com.le.yun.entity.ResponseMessage;
import com.le.yun.service.FileManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：hjl
 * @date ：2021/2/13 20:59
 * @description： 文件管理相关controller
 * @modified By：
 */
@Controller
@RequestMapping("/file/")
@Slf4j
public class FileController {

    @Autowired
    private FileManageService fileManageService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseMessage upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseMessage<String>(Constants.ERROR_CODE, "上传失败，请选择文件", "", Boolean.FALSE);
        }
        String fileName = file.getOriginalFilename();
        String filePath = "/Users/itinypocket/workspace/temp/";
        return fileManageService.upload(file);
    }


}
