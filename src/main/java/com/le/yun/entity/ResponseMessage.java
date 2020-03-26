package com.le.yun.entity;

import com.le.yun.constant.Constants;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ：hjl
 * @date ：2019/12/3 17:20
 * @description：响应结果实体类
 * @modified By：
 */
@Data
public class ResponseMessage implements Serializable {


    private static final long serialVersionUID = 1153125791509666757L;

    public ResponseMessage(Integer code, Object message, String result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ResponseMessage() {
    }

    /**
     * 成功的结果
     */
    public final static ResponseMessage SUCCESS = new ResponseMessage(Constants.SUCCESS_CODE,"",Constants.SUCCESS);
    /**
     * 成功的结果
     */
    public final static ResponseMessage FAILED = new ResponseMessage(Constants.ERROR_CODE,"",Constants.FAILED);

    /**
     * 信息编码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private Object message;
    /**
     * 处理结果
     */
    private String result;
}
