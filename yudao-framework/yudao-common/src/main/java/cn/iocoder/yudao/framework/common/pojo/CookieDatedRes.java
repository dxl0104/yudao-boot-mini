package cn.iocoder.yudao.framework.common.pojo;


import com.alibaba.fastjson.annotation.JSONField;

public class CookieDatedRes {
    @JSONField(name = "IsSuccess")
    private boolean isSuccess;

    @JSONField(name = "Data")
    private String data;

    @JSONField(name = "ResponseError")
    private ResponseError responseError;

    @JSONField(name = "CheckedKey")
    private String checkedKey;

    @JSONField(name = "Message")
    private String message;

    @JSONField(name = "Url")
    private String url;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResponseError getResponseError() {
        return responseError;
    }

    public void setResponseError(ResponseError responseError) {
        this.responseError = responseError;
    }

    public String getCheckedKey() {
        return checkedKey;
    }

    public void setCheckedKey(String checkedKey) {
        this.checkedKey = checkedKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
