package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Row {
    @JsonProperty("Id")
    private int Id;

    @JsonProperty("SellerId")
    private int SellerId;

    @JsonProperty("Platform")
    private int Platform;

    @JsonProperty("InfringementKeyword")
    private String InfringementKeyword;

    @JsonProperty("Operator")
    private String Operator;

    @JsonProperty("OperatorTime")
    private String OperatorTime;
}
