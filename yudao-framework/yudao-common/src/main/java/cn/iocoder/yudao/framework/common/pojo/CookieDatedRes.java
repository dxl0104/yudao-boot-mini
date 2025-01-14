package cn.iocoder.yudao.framework.common.pojo;



public class CookieDatedRes {
    private boolean IsSuccess;
    private String Data;
    private ResponseError ResponseError;
    private String CheckedKey;
    private String Message;
    private String Url;

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public cn.iocoder.yudao.framework.common.pojo.ResponseError getResponseError() {
        return ResponseError;
    }

    public void setResponseError(cn.iocoder.yudao.framework.common.pojo.ResponseError responseError) {
        ResponseError = responseError;
    }

    public String getCheckedKey() {
        return CheckedKey;
    }

    public void setCheckedKey(String checkedKey) {
        CheckedKey = checkedKey;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
