package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import lombok.Data;

import java.util.List;

@Data
public class BasicDataImportVO {
    //总次数
    private Integer totalCount;
    //成功导入的产品次数
    private Integer successCount;
    //失败导入的产品次数
    private Integer errorCount;
    //成功导入的产品信息
    private List<ImportSuccessRes> successResList;
    //失败导入的产品信息以及erp报错信息
    private List<ImportErrorRes> errorDetailList;
}
