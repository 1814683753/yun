package com.le.yun.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.le.yun.entity.ResponseMessage;
import com.le.yun.entity.User;
import com.le.yun.service.FileManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @author ：hjl
 * @date ：2021/2/14 20:28
 * @description：
 * @modified By：
 */
@Service
@Slf4j
public class FileManageServiceImpl implements FileManageService {
    /**
     * 处理文件长传
     *
     * @param file
     * @return
     */
    @Override
    public ResponseMessage upload(MultipartFile file) {
        try {
            ImportParams params = new ImportParams();
            // 表格标题行数,默认0
            params.setTitleRows(0);
            // 表头行数,默认1
            params.setHeadRows(1);
            List<User> list  = ExcelImportUtil.importExcel(
                    file.getInputStream(),
                    User.class, params);
            log.info("list={}", JSON.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(0);
            params.setHeadRows(1);
            List<User> list  = ExcelImportUtil.importExcel(
                    new File("D:\\study\\测试导入.xlsx"),
                    User.class, params);
            log.info("list={}", JSON.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
