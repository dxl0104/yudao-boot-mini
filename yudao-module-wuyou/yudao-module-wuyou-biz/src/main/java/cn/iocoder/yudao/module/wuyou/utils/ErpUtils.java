package cn.iocoder.yudao.module.wuyou.utils;

import cn.iocoder.yudao.framework.common.pojo.CookieDatedRes;
import cn.iocoder.yudao.framework.common.pojo.CookieResponse;
import cn.iocoder.yudao.framework.common.util.wuyou.LoginUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

@Component
public class ErpUtils {

    public static CookieDatedRes verifyDuplicate(String sourceUrl, String cookie) {
        try {
            // URL地址
            String url = "https://www.51selling.com/collect/CollectBox/VerifyDuplicate";


            // 请求的JSON数据
            String postData = "{\"SourceUrl\":\"" + sourceUrl + "\"}";

            // 设置连接
            URL targetUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();

            // 设置请求方法为 POST
            connection.setRequestMethod("POST");

            // 允许输出数据
            connection.setDoOutput(true);

            // 设置请求头
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
            connection.setRequestProperty("cookie", cookie);
            connection.setRequestProperty("origin", "chrome-extension://njaodcoemplngimnhkahckmoabggpolh");
            connection.setRequestProperty("priority", "u=1, i");
            connection.setRequestProperty("sec-fetch-dest", "empty");
            connection.setRequestProperty("sec-fetch-mode", "cors");
            connection.setRequestProperty("sec-fetch-site", "none");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");

            // 发送POST请求数据
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 获取响应码
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw exception(GET_CAGETORY_NOT_EXISTS);
            }
            // 可选：读取响应内容（如果需要）
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            CookieDatedRes cookieDatedRes = JSON.toJavaObject(JSON.parseObject(response.toString()), CookieDatedRes.class);
            return cookieDatedRes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * cookie过期
     * 重新获取cookie
     *
     * @param erpName
     * @param pwd
     * @return
     */
    public static String reGetCookie(String erpName, String pwd) {
        if (StringUtils.isBlank(erpName) || StringUtils.isBlank(pwd)) {
            error(ERP_AMOUNT_NOT_EXIST);
        }
        CookieResponse response = LoginUtils.getCookie(erpName, pwd);
        if (response.getIsSuccess() == false) {
            error(PWD_ERROR);
        } else if (response.getIsSuccess()) {
            return response.getCookies();
        }
        return null;
    }

    public static CookieDatedRes saveProduct(String html, String sourceUrl, Integer sourcePlatform, String cookie) {
        try {
            // URL地址
            String url = "https://www.51selling.com/collect/CollectBox/SaveProduct";

            String encodedURI = URLEncoder.encode(html, StandardCharsets.UTF_8.name());
            // Step 3: Base64 编码
            String base64Encoded = Base64.getEncoder().encodeToString(encodedURI.getBytes(StandardCharsets.UTF_8));

            // 请求的JSON数据
            String postData = "{\"Html\":\"" + base64Encoded + "\",\"SouceUrl\":\"" + sourceUrl + "\",\"SourcePlatform\":\"" + sourcePlatform + "\"}";

            // 设置连接
            URL targetUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();

            // 设置请求方法为 POST
            connection.setRequestMethod("POST");

            // 允许输出数据
            connection.setDoOutput(true);
            // 设置请求头
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
            connection.setRequestProperty("cookie", cookie);
            connection.setRequestProperty("origin", "chrome-extension://njaodcoemplngimnhkahckmoabggpolh");
            connection.setRequestProperty("priority", "u=1, i");
            connection.setRequestProperty("sec-fetch-dest", "empty");
            connection.setRequestProperty("sec-fetch-mode", "cors");
            connection.setRequestProperty("sec-fetch-site", "none");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");

            // 发送POST请求数据
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = postData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 获取响应码
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw exception(IMPORT_ERROR);
            }

            // 可选：读取响应内容（如果需要）
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            CookieDatedRes cookieDatedRes = JSON.toJavaObject(JSON.parseObject(response.toString()), CookieDatedRes.class);
            return cookieDatedRes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
