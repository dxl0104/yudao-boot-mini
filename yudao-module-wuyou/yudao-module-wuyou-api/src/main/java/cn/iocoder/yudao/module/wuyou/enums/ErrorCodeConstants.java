package cn.iocoder.yudao.module.wuyou.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode GET_CAGETORY_NOT_EXISTS = new ErrorCode(10001, "查询不到数据");
    ErrorCode BASIC_DATA_NOT_EXISTS = new ErrorCode(10002, "无忧基础数据不存在");
    ErrorCode PRODUCT_URL_NOT_EXISTS = new ErrorCode(10003, "商品url列表不存在");
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(10004, "商品404");
    ErrorCode KEYWORD_NOT_EXISTS = new ErrorCode(10005, "无忧侵权词不存在");
    ErrorCode ERP_AMOUNT_NOT_EXIST = new ErrorCode(10006, "请先设置erp账号和密码");
    ErrorCode PWD_ERROR = new ErrorCode(10007, "密码错误，总共五次机会");
    ErrorCode IMPORT_ERROR = new ErrorCode(10008, "导入失败");
}
