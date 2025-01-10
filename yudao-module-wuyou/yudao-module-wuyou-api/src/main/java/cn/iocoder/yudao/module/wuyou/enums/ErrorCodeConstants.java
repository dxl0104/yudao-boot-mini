package cn.iocoder.yudao.module.wuyou.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode BASIC_DATA_NOT_EXISTS = new ErrorCode(10002, "无忧基础数据不存在");
    ErrorCode PRODUCT_URL_NOT_EXISTS = new ErrorCode(10003, "商品url列表不存在");
}
