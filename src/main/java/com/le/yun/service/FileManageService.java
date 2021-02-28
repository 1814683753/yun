package com.le.yun.service;

import com.le.yun.entity.ResponseMessage;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：hjl
 * @date ：2021/2/14 20:23
 * @description：文件管理相关服务
 * @modified By：
 */
public interface FileManageService {

    /**
     * 处理文件长传
     * @param file
     * @return
     */
    ResponseMessage upload(MultipartFile file);

}
