package cn.iocoder.yudao.framework.common.util.wuyou;

import cn.iocoder.yudao.framework.common.pojo.CookieResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取无忧cookie
 */
public class LoginUtils {
    public static CookieResponse getLoginUser(String userName, String pwd) {
        try {
            // 设置目标 URL
            String targetUrl = "http://38.147.189.30:5000/signin";
            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 POST
            connection.setRequestMethod("POST");

            // 设置请求头，必要时可以添加更多头信息
            connection.setRequestProperty("Content-Type", "application/json");

            // 允许输入输出流
            connection.setDoOutput(true);

            // 构造 JSON 数据
            String jsonInputString = "{\"username\": \"" + userName + "\", \"password\": \"" + pwd + "\"}";

            // 向服务器发送 POST 数据
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length); // 写入请求体
            }
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 读取服务器响应内容
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                ObjectMapper objectMapper = new ObjectMapper();
                CookieResponse cookieResponse = objectMapper.readValue(response.toString(), CookieResponse.class);
                return cookieResponse;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
