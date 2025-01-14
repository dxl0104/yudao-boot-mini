package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.CookieResponse;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.common.util.wuyou.LoginUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.KeyWordQueryVO;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.KeywordPageReqVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata.BasicDataDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.keyword.KeywordDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.keyword.KeywordMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.producturl.ProductUrlMapper;
import cn.iocoder.yudao.module.wuyou.service.basicdata.BasicDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.GET_CAGETORY_NOT_EXISTS;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.PRODUCT_NOT_EXISTS;

@Tag(name = "管理后台 - 无忧基础数据")
@RestController
@RequestMapping("/wuyou/basic-data")
@Validated
@Slf4j
public class BasicDataController {

    @Resource
    private BasicDataService basicDataService;

    @Resource
    private ProductUrlMapper productUrlMapper;

    @Resource
    private KeywordMapper keywordMapper;

    @PostMapping("/create")
    @Operation(summary = "创建无忧基础数据")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:create')")
    public CommonResult<Long> createBasicData(@Valid @RequestBody BasicDataSaveReqVO createReqVO) {
        return success(basicDataService.createBasicData(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新无忧基础数据")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:update')")
    public CommonResult<Boolean> updateBasicData(@Valid @RequestBody BasicDataSaveReqVO updateReqVO) {
        basicDataService.updateBasicData(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除无忧基础数据")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:delete')")
    public CommonResult<Boolean> deleteBasicData(@RequestParam("id") Long id) {
        basicDataService.deleteBasicData(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得无忧基础数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:query')")
    public CommonResult<BasicDataRespVO> getBasicData(@RequestParam("id") Long id) {
        BasicDataDO basicData = basicDataService.getBasicData(id);
        return success(BeanUtils.toBean(basicData, BasicDataRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得无忧基础数据分页")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:query')")
    public CommonResult<PageResult<BasicDataRespVO>> getBasicDataPage(@Valid BasicDataPageReqVO pageReqVO) {
        PageResult<BasicDataDO> pageResult = basicDataService.getBasicDataPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BasicDataRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出无忧基础数据 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBasicDataExcel(@Valid BasicDataPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BasicDataDO> list = basicDataService.getBasicDataPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "无忧基础数据.xls", "数据", BasicDataRespVO.class,
                BeanUtils.toBean(list, BasicDataRespVO.class));
    }

    @PostMapping("/verifyDuplicate")
    public CommonResult Verify(@RequestBody Map<String, Object> params) {
        String sourceUrl = params.get("sourceUrl").toString();
        return success(VerifyDuplicate(sourceUrl));
    }

    @PostMapping("/saveProduct")
    public String Save(@RequestBody BasicDataReqVO basicDataReqVO) {
        return SaveProduct(basicDataReqVO.getHtml(), basicDataReqVO.getSourceUrl(), basicDataReqVO.getSourcePlatform(),basicDataReqVO.getCookie());
    }

    @PostMapping("/getOneProductDetail")
    public CommonResult getOneProductDetail(@RequestBody BasicDataRqeCategoryVO basicDataRqeCategoryVO) {
        log.info("返回商品详情数据{}", getOneProduct(basicDataRqeCategoryVO.getSourceUrl(), basicDataRqeCategoryVO.getCookie()));
        return getOneProduct(basicDataRqeCategoryVO.getSourceUrl(), basicDataRqeCategoryVO.getCookie());
    }

    @PostMapping("/getCategoryData")
    public CommonResult<String> getCategoryData(@RequestBody BasicDataRqeCategoryVO basicDataRqeCategoryVO) {
        log.info("返回数据{}", getCategoryData(basicDataRqeCategoryVO.getSourceUrl(), basicDataRqeCategoryVO.getP(), null));
        return getCategoryData(basicDataRqeCategoryVO.getSourceUrl(), basicDataRqeCategoryVO.getP(), null);
    }

    public CommonResult<String> getCategoryData(String sourceUrl, Integer page, String cookie) {
        try {
            String url = sourceUrl + "&p=" + page;
            // 创建 URL 对象
            URL targetUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            // 设置请求头，模拟 cURL 请求头
            connection.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            connection.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
            connection.setRequestProperty("content-type", "application/json");
            if (cookie != null) {
                connection.setRequestProperty("cookie", cookie);
            } else {
                //cookie必须有 没有的话无法请求
                connection.setRequestProperty("cookie", "_cmuid=90205531-ec74-4b4c-8a95-49986067b911; _gcl_au=1.1.640494160.1735788908; gdpr_permission_given=1; __gfp_64b=-TURNEDOFF; OptOutOnRequest=groups=googleAnalytics:1,googleAdvertisingProducts:1,tikTok:1,allegroAdsNetwork:1,facebook:1; _fbp=fb.1.1735788926019.197344872; _meta_facebookTag_sync=1735788926019; _meta_googleGtag_ga_library_loaded=1735788926021; _ga=GA1.1.1252726110.1735788926; _ga_G64531DSC4=GS1.1.1735788926.1.0.1735789133.60.0.0; _meta_googleGtag_ga=GA1.1.1252726110.1735788926; wdctx=v5.05bN7cUkT7u3zONiGSqLI-1RC8ruYfFr5TEmMeip6Q9d_yhFL0IKH-_L4IeY-VJKkvlq1hwYTQsCWoOPkGj7quE64XcUzEUDwm6WpjRGbN54yn41UaDkvMM2RS8GihhEARm8-nRQhLx0CYTqe2uBsMeZjkei9jKInSRNESvQNG1p9iw_fEQyRQyfYaSEg3ZzODaKi433QjcQbj9c9H0J8JxehecjYruSGMvACZ_XPQuX0g.WvQZlFTNThC2v1IX4P5CBQ.Qo00EB08O6o; _meta_googleGtag_session_id=1736407579; _meta_googleGtag_ga_session_count=6; datadome=tgYyX0n_Sn9IZRhwwwhHWJmwOxS6zwVB87pbbrKgmTNCA7gczky8is0Y1g68xu_4PwDScDECCbV929Pp2WyXKHk0~a9MfhstxSgmzYyYxFJNj9M03w0DlFAD1eMVqDoW; __gads=ID=102a949be5e9a3cb:T=1736407609:RT=1736407609:S=ALNI_MaSn4fsiA2vQnFlGMtRJYDSjLre-w; __gpi=UID=00000fb1ef8e0f7b:T=1736407609:RT=1736407609:S=ALNI_Ma7uTrhokPf16I2fHBMl7pkaIznug; __eoi=ID=7fe629eab8c63dff:T=1736407609:RT=1736407609:S=AA-AfjZF5NyOFdVyXeHerD4D4iWW");
            }
            connection.setRequestProperty("dpr", "1");
            connection.setRequestProperty("priority", "u=1, i");
            connection.setRequestProperty("referer", "https://allegro.pl/");
            connection.setRequestProperty("sec-ch-device-memory", "8");
            connection.setRequestProperty("sec-ch-prefers-color-scheme", "light");
            connection.setRequestProperty("sec-ch-prefers-reduced-motion", "no-preference");
            connection.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
            connection.setRequestProperty("sec-ch-ua-arch", "\"x86\"");
            connection.setRequestProperty("sec-ch-ua-full-version-list", "\"Google Chrome\";v=\"131.0.6778.205\", \"Chromium\";v=\"131.0.6778.205\", \"Not_A Brand\";v=\"24.0.0.0\"");
            connection.setRequestProperty("sec-ch-ua-mobile", "?0");
            connection.setRequestProperty("sec-ch-ua-model", "\"\"");
            connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
            connection.setRequestProperty("sec-ch-viewport-height", "919");
            connection.setRequestProperty("sec-fetch-dest", "empty");
            connection.setRequestProperty("sec-fetch-mode", "cors");
            connection.setRequestProperty("sec-fetch-site", "same-origin");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.6778.205 Safari/537.36");
            connection.setRequestProperty("viewport-width", "798");
            connection.setRequestProperty("x-requested-with", "XMLHttpRequest");

            // 获取响应码
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode != 200) {
                return error(GET_CAGETORY_NOT_EXISTS);
            }

            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return success(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CommonResult<String> getOneProduct(String sourceUrl, String cookie) {
        try {
            // 创建 URL 对象
            URL targetUrl = new URL(sourceUrl);
            HttpURLConnection connection = (HttpURLConnection) targetUrl.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            // 设置请求头，模拟 cURL 请求头
            connection.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
            connection.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
            connection.setRequestProperty("content-type", "application/json");
            //cookie必须有 没有的话无法请求
            if (cookie != null && "".equals(cookie)) {
                connection.setRequestProperty("cookie", cookie);
            } else {
                connection.setRequestProperty("cookie", "_cmuid=90205531-ec74-4b4c-8a95-49986067b911; _gcl_au=1.1.640494160.1735788908; gdpr_permission_given=1; __gfp_64b=-TURNEDOFF; OptOutOnRequest=groups=googleAnalytics:1,googleAdvertisingProducts:1,tikTok:1,allegroAdsNetwork:1,facebook:1; _fbp=fb.1.1735788926019.197344872; _meta_facebookTag_sync=1735788926019; _meta_googleGtag_ga_library_loaded=1735788926021; _ga=GA1.1.1252726110.1735788926; _ga_G64531DSC4=GS1.1.1735788926.1.0.1735789133.60.0.0; _meta_googleGtag_ga=GA1.1.1252726110.1735788926; _meta_googleGtag_ga_session_count=5; _meta_googleGtag_session_id=1736322260; wdctx=v5.Imv6Pch56tZ9ZxjmE-nzTloXk04e0xXtNG4qaDuxDG-TKsN_hT_cdSRwD-1azQNPXnexzOHCKV-j5Dgl5BKqYkxsBaMwyW68-IPli-MES5HRhyTBdcpHti-h-PFLACvyh4SIY1YJl3C2Lua6NwImZHeb-0rEcv17pvF1ujEQOgUOyKTZvwRADJzkK8_fAU6X3azsb8YCqqxIXN3FZyoUZkj4NKzWRsH2jUKxw5jiDy0.WvQZlFTNThC2v1IX4P5CBQ.V7IfZOTqukk; datadome=6r4OatSL98E4HTsnniwC72b~tlgFgBjjfazW~5Rvz823f7uwe_DGaTOJqlurKcg2FMUAIFClmQXF~R6SWqo6C9zU~zYUOK8IhixDqvoA3zAQ5eWfz574GfFjJzrz5N1O");
            }
            connection.setRequestProperty("dpr", "1");
            connection.setRequestProperty("priority", "u=1, i");
            connection.setRequestProperty("referer", "https://allegro.pl/");
            connection.setRequestProperty("sec-ch-device-memory", "8");
            connection.setRequestProperty("sec-ch-prefers-color-scheme", "light");
            connection.setRequestProperty("sec-ch-prefers-reduced-motion", "no-preference");
            connection.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
            connection.setRequestProperty("sec-ch-ua-arch", "\"x86\"");
            connection.setRequestProperty("sec-ch-ua-full-version-list", "\"Google Chrome\";v=\"131.0.6778.205\", \"Chromium\";v=\"131.0.6778.205\", \"Not_A Brand\";v=\"24.0.0.0\"");
            connection.setRequestProperty("sec-ch-ua-mobile", "?0");
            connection.setRequestProperty("sec-ch-ua-model", "\"\"");
            connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
            connection.setRequestProperty("sec-ch-viewport-height", "919");
            connection.setRequestProperty("sec-fetch-dest", "empty");
            connection.setRequestProperty("sec-fetch-mode", "cors");
            connection.setRequestProperty("sec-fetch-site", "same-origin");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.6778.205 Safari/537.36");
            connection.setRequestProperty("viewport-width", "798");
            connection.setRequestProperty("x-requested-with", "XMLHttpRequest");

            // 获取响应码
            int responseCode = connection.getResponseCode();
            System.out.println("商品详情 Response Code: " + responseCode);

            if (responseCode != 200) {
                return error(GET_CAGETORY_NOT_EXISTS);
            }

            // 读取响应内容
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 输出返回的内容
            System.out.println(response.toString());
            return success(response.toString());

        } catch (Exception e) {
//            e.printStackTrace();
            //删除404的数据
//            ProductUrlDO productUrlDO = productUrlMapper.selectOne("url", sourceUrl);
//            if (productUrlDO!=null){
//                productUrlMapper.deleteById(productUrlDO);
//            }
            return error(PRODUCT_NOT_EXISTS);
        }
    }

    public String SaveProduct(String html, String sourceUrl, Integer sourcePlatform,String cookie) {
        try {
            // URL地址
            String url = "https://www.51selling.com/collect/CollectBox/SaveProduct";


            // 请求的JSON数据
            String postData = "{\"Html\":\"" + html + "\",\"sourceUrl\":\"" + sourceUrl + "\",\"SourcePlatform\":\"" + sourcePlatform + "\"}";

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
            System.out.println("Response Code: " + responseCode);

            // 可选：读取响应内容（如果需要）
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String VerifyDuplicate(String sourceUrl) {
        try {
            // URL地址
            String url = "https://www.51selling.com/collect/CollectBox/VerifyDuplicate";

            // Cookie信息
            String cookie = "SessID=cviktd0txmsprh1lsrivoo44; usrid=4+EcjTy+fLo=; tk=f76b4cd9-9230-43c7-8100-bdd432de61fc";

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
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/query")
    @Operation(summary = "查询无忧侵权词分页数据")
    public String query(@RequestBody KeyWordQueryVO keyWordQueryVO) {
        try {
            // 请求URL
            URL url = new URL("https://www.51selling.com/sale/ShelvesInfringementKeyword");

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为 POST
            connection.setRequestMethod("POST");
            // 设置请求头
            connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            connection.setRequestProperty("Cookie", "SessID=xe2gyencjg134o0z3anbhyan; Hm_lvt_97745fd517881ba15b518da92c105831=1736093172,1736570997,1736579851,1736605293; HMACCOUNT=0D20F43D5F4808BE; Hm_lpvt_97745fd517881ba15b518da92c105831=1736613877; usrid=4+EcjTy+fLo=; tk=126453f7-d644-42c8-a835-40aad9f0b258");
            connection.setRequestProperty("Origin", "https://www.51selling.com");
            connection.setRequestProperty("Referer", "https://www.51selling.com/sale/shelvesinfringementkeyword");
            connection.setRequestProperty("Sec-CH-UA", "\"Microsoft Edge\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
            connection.setRequestProperty("Sec-CH-UA-Mobile", "?0");
            connection.setRequestProperty("Sec-CH-UA-Platform", "\"Windows\"");
            connection.setRequestProperty("Sec-Fetch-Dest", "empty");
            connection.setRequestProperty("Sec-Fetch-Mode", "cors");
            connection.setRequestProperty("Sec-Fetch-Site", "same-origin");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            // 允许输入输出流
            connection.setDoOutput(true);

            // 请求体数据
            String data = "pageIndex=" + keyWordQueryVO.getPageIndex() + "&pageSize=" + keyWordQueryVO.getPageSize() + "&orderBy=Id+desc&search=%E2%80%A0%E2%80%A1%E2%80%A0%E2%80%A1%E4%BE%B5%E6%9D%83%E5%85%B3%E9%94%AE%E8%AF%8D%E2%80%A0%E6%B7%BB%E5%8A%A0%E6%97%B6%E9%97%B4%E2%80%A1%E2%80%A0%E2%80%A1%E2%80%A0";

            // 写入请求体
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = data.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 获取响应码
            int statusCode = connection.getResponseCode();
            System.out.println("Response Code: " + statusCode);

            // 获取响应内容
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                System.out.println("Response Body: " + response.toString());
                // 关闭连接
                connection.disconnect();
                return response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/saveKey")
    @Operation(summary = "查询无忧侵权词分页数据")
    public String saveKeyWord() {
        String targetUrl = "https://www.51selling.com/sale/ShelvesInfringementKeyword/Edit";
        String cookie = "SessID=xe2gyencjg134o0z3anbhyan; Hm_lvt_97745fd517881ba15b518da92c105831=1736093172,1736570997,1736579851,1736605293; HMACCOUNT=0D20F43D5F4808BE; Hm_lpvt_97745fd517881ba15b518da92c105831=1736615808; usrid=nYas788ClX4=; tk=ba2f775b-4088-4df1-b0e9-209b10a908e4";
        String referer = "https://www.51selling.com/sale/ShelvesInfringementKeyword/Edit?ids=0";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36 Edg/131.0.0.0";

        try {
            // 分页循环请求
            for (int i = 1; i <= 119; i++) {
                // 设置分页参数
                KeywordPageReqVO keywordPageReqVO = new KeywordPageReqVO();
                keywordPageReqVO.setPageSize(100);
                keywordPageReqVO.setPageNo(i);

                // 获取分页数据
                PageResult<KeywordDO> keywordDOPageResult = keywordMapper.selectPage(keywordPageReqVO);
                List<KeywordDO> list = keywordDOPageResult.getList();

                // 拼接数据
                String data = "Platform=0&InfringementKeyword=" +
                        list.stream()
                                .map(keywordDO -> keywordDO.getInfringementKeyword())
                                .collect(Collectors.joining("%0A")) + // 拼接关键字并使用 %0A 分隔
                        "&Id=0";

                // 创建 URL 和 HttpURLConnection
                URL url = new URL(targetUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // 设置请求方法和请求头
                connection.setRequestMethod("POST");
                connection.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
                connection.setRequestProperty("accept-language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
                connection.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
                connection.setRequestProperty("cookie", cookie);
                connection.setRequestProperty("origin", "https://www.51selling.com");
                connection.setRequestProperty("referer", referer);
                connection.setRequestProperty("sec-ch-ua", "\"Microsoft Edge\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
                connection.setRequestProperty("sec-ch-ua-mobile", "?0");
                connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
                connection.setRequestProperty("sec-fetch-dest", "empty");
                connection.setRequestProperty("sec-fetch-mode", "cors");
                connection.setRequestProperty("sec-fetch-site", "same-origin");
                connection.setRequestProperty("user-agent", userAgent);
                connection.setRequestProperty("x-requested-with", "XMLHttpRequest");

                // 启用输出流
                connection.setDoOutput(true);

                // 写入请求参数
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = data.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // 获取响应代码
                int responseCode = connection.getResponseCode();
                log.info("Response Code: " + responseCode);

                // 读取响应内容
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    // 输出响应内容
                    log.info("Response Body: " + response.toString());
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while processing the request", e);
            return "Error occurred: " + e.getMessage();
        }
        return "Success";
    }


}