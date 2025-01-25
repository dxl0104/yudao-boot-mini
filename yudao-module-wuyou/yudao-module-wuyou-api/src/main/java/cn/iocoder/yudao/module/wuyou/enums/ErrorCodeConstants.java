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
    ErrorCode TASK_NOT_EXISTS = new ErrorCode(10009, "采集任务不存在");
    ErrorCode TASK_PAGE_DETAIL_NOT_EXISTS = new ErrorCode(10010, "页数据采集状态不存在");
    ErrorCode DEVICE_NOT_EXISTS = new ErrorCode(10011, "采集器信息不存在");
    ErrorCode SOURCE_URL_NOT_EXISTS = new ErrorCode(10012, "列表链接不存在");
    ErrorCode TASK_PROCESS_NOT_EXISTS = new ErrorCode(10013, "任务进度不存在");
    ErrorCode DATA_INTO_REPEAT = new ErrorCode(10014, "数据重复插入");
    ErrorCode DATA_INTO_ERROR = new ErrorCode(10015, "数据插入异常");
    ErrorCode VIOLATE_PRODUCT_NOT_EXISTS = new ErrorCode(10016, "侵权商品不存在");
    ErrorCode CATEGORY_NOT_EXISTS = new ErrorCode(10016, "数据类别不存在");
    ErrorCode CATEGORY_EXITS_CHILDREN = new ErrorCode(10017, "存在存在子数据类别，无法删除");
    ErrorCode CATEGORY_PARENT_NOT_EXITS = new ErrorCode(10018,"父级数据类别不存在");
    ErrorCode CATEGORY_PARENT_ERROR = new ErrorCode(10019, "不能设置自己为父数据类别");
    ErrorCode CATEGORY_ZH_NAME_DUPLICATE = new ErrorCode(10020, "已经存在该中文名称的数据类别");
    ErrorCode CATEGORY_PARENT_IS_CHILD = new ErrorCode(10021, "不能设置自己的子Category为父Category");
}
