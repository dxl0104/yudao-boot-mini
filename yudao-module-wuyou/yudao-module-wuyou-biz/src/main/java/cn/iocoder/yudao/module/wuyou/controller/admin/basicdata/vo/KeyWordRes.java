package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyWordRes {
    @JsonProperty("Data")
    private WuyouKeyWordResVO Data;
}
