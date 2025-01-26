package cn.iocoder.yudao.module.wuyou.service.basicdata;

import cn.hutool.core.util.IdUtil;
import cn.iocoder.yudao.framework.common.pojo.CookieDatedRes;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.infra.dal.dataobject.file.FileConfigDO;
import cn.iocoder.yudao.module.infra.dal.mysql.file.FileConfigMapper;
import cn.iocoder.yudao.module.infra.framework.file.core.client.FileClient;
import cn.iocoder.yudao.module.infra.framework.file.core.client.FileClientFactory;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata.BasicDataDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.keyword.KeywordDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.violateproduct.ViolateProductDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.basicdata.BasicDataMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.keyword.KeywordMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.producturl.ProductUrlMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.violateproduct.ViolateProductMapper;
import cn.iocoder.yudao.module.wuyou.utils.ErpUtils;
import cn.iocoder.yudao.module.wuyou.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.cache.CacheUtils.buildAsyncReloadingCache;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.FILE_CONFIG_NOT_EXISTS;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.BASIC_DATA_NOT_EXISTS;
import static cn.iocoder.yudao.module.wuyou.utils.ErpUtils.reGetCookie;


/**
 * 无忧基础数据 Service 实现类
 *
 * @author admin234
 */
@Service
@Validated
public class BasicDataServiceImpl implements BasicDataService {

    @Resource
    private BasicDataMapper basicDataMapper;

    @Resource
    private ProductUrlMapper productUrlMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private FileConfigMapper fileConfigMapper;

    @Resource
    private FileClientFactory fileClientFactory;

    private static final Long CACHE_MASTER_ID = 0L;

    @Resource
    private KeywordMapper keywordMapper;

    @Resource
    private ViolateProductMapper violateProductMapper;

    /**
     * {@link FileClient} 缓存，通过它异步刷新 fileClientFactory
     */
    @Getter
    private final LoadingCache<Long, FileClient> clientCache = buildAsyncReloadingCache(Duration.ofSeconds(10L),
            new CacheLoader<Long, FileClient>() {

                @Override
                public FileClient load(Long id) {
                    FileConfigDO config = Objects.equals(CACHE_MASTER_ID, id) ?
                            fileConfigMapper.selectByMaster() : fileConfigMapper.selectById(id);
                    if (config != null) {
                        fileClientFactory.createOrUpdateFileClient(config.getId(), config.getStorage(), config.getConfig());
                    }
                    return fileClientFactory.getFileClient(null == config ? id : config.getId());
                }

            });

    @Override
    public Long createBasicData(BasicDataSaveReqVO createReqVO) {
        // 插入
        BasicDataDO basicData = BeanUtils.toBean(createReqVO, BasicDataDO.class);
        basicDataMapper.insert(basicData);
        String url = basicData.getUrl();
        ProductUrlDO productUrlDO = productUrlMapper.selectOne(ProductUrlDO::getUrl, url);
        productUrlDO.setProcessFlag(1);
        productUrlMapper.updateById(productUrlDO);
        // 返回
        return basicData.getId();
    }

    @Override
    public void updateBasicData(BasicDataSaveReqVO updateReqVO) {
        // 校验存在
        validateBasicDataExists(updateReqVO.getId());
        // 更新
        BasicDataDO updateObj = BeanUtils.toBean(updateReqVO, BasicDataDO.class);
        basicDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteBasicData(Long id) {
        // 校验存在
        validateBasicDataExists(id);
        // 删除
        basicDataMapper.deleteById(id);
    }

    private void validateBasicDataExists(Long id) {
        if (basicDataMapper.selectById(id) == null) {
            throw exception(BASIC_DATA_NOT_EXISTS);
        }
    }

    @Override
    public BasicDataDO getBasicData(Long id) {
        return basicDataMapper.selectById(id);
    }

    @Override
    public PageResult<BasicDataDO> getBasicDataPage(BasicDataPageReqVO pageReqVO) {
        return basicDataMapper.selectPage(pageReqVO);
    }

    @Override
    public Boolean importRes(BasicDataImportReqNewVO basicDataImportReqVO) {
        Boolean flag = false;
//        // 校验存在
        validateFileConfigExists(new Long(28));

        // 文件内容转成 byte 数组
        byte[] fileContent = basicDataImportReqVO.getJson().getBytes(StandardCharsets.UTF_8);
        String uploadUrl = "";
        try {
            // 生成唯一的文件名（以 .txt 结尾）
            String fileName = IdUtil.fastSimpleUUID() + ".txt";
            uploadUrl = getFileClient(new Long(28)).upload(fileContent, fileName, "text/plain");
        } catch (Exception e) {
            return flag;
        }
        //存储数据
        BasicDataDO basicDataDO = new BasicDataDO();

        basicDataDO.setDelivery(new BigDecimal(basicDataImportReqVO.getDelivery()));
        //解析数据
        ObjectMapper objectMapper = new ObjectMapper();
        // 将字符串转换为 JsonNode 对象
        try {
            JsonNode jsonNode = objectMapper.readTree(basicDataImportReqVO.getJson());
            // 获取 offerId 字段
            JsonNode descriptionNode = jsonNode.path("Description");
            String offerId = descriptionNode.path("offerId").asText();
            basicDataDO.setOfferId(offerId);
            // 获取ean字段
            JsonNode showofferActions = jsonNode.path("showoffer.actions");
            JsonNode offer = showofferActions.path("offer");
            JsonNode seo = offer.path("seo");
            String gtin = seo.path("gtin").asText();
            if (!gtin.equals("null")) {
                basicDataDO.setEan(gtin);
            }
            //获取title字段
            JsonNode allegroShowofferStickyMenu = jsonNode.path("allegro.showoffer.stickyMenu");
            JsonNode offer1 = allegroShowofferStickyMenu.path("offer");
            String name = offer1.path("name").asText();
            basicDataDO.setTitle(name);
            //获取Product ID (EAN/UPC/ISBN/ISSN/Allegro Product ID)
            JsonNode product = offer1.path("product");
            String productId = product.path("id").asText();
            basicDataDO.setProductId(productId);
            //获取Quantity字段
            JsonNode stock = offer1.path("stock");
            String available = stock.path("available").asText();
            basicDataDO.setQuantity(available);
            //获取price价格
            JsonNode sellingMode = offer1.path("sellingMode");
            JsonNode buyNow = sellingMode.path("buyNow");
            JsonNode price = buyNow.path("price");
            JsonNode sale = price.path("sale");
            String amount = sale.path("amount").asText();
            basicDataDO.setPrice(amount);
            //获取货币类型
            String currency = sale.path("currency").asText();
            basicDataDO.setCurrency(currency);
            //获取主要商品类型
            // 获取 breadcrumbs 数组中的项
            JsonNode breadcrumbs = offer1.path("breadcrumbs");

// 获取并设置 MainCategory1
            JsonNode itemAtIndex1 = breadcrumbs.get(1);
            String mainCategory1 = itemAtIndex1.path("name").asText();
            basicDataDO.setMainCategory1(new String(mainCategory1.getBytes(), StandardCharsets.UTF_8)); // 确保是 UTF-8 编码

// 获取并设置 MainCategory2
            JsonNode itemAtIndex2 = breadcrumbs.get(2);
            String mainCategory2 = itemAtIndex2.path("name").asText();
            basicDataDO.setMainCategory2(new String(mainCategory2.getBytes(), StandardCharsets.UTF_8)); // 确保是 UTF-8 编码

// 获取并设置 MainCategory3
            JsonNode itemAtIndex3 = breadcrumbs.get(3);
            String mainCategory3 = itemAtIndex3.path("name").asText();
            basicDataDO.setMainCategory3(new String(mainCategory3.getBytes(), StandardCharsets.UTF_8)); // 确保是 UTF-8 编码
            //获取images
            JsonNode images = offer1.path("images");
            JsonNode imagesIndex1 = images.get(0);
            int size = images.size();
            StringBuffer stringBuffer = new StringBuffer();

            for (int i = 0; i < size; i++) {
                String url = images.get(i).path("url").asText();

                // 判断是否是最后一个元素，如果是，直接拼接 url，否则加上分隔符 "|"
                if (i < size - 1) {
                    stringBuffer.append(url).append("|");
                } else {
                    stringBuffer.append(url);
                }
            }
            basicDataDO.setImgUrl(stringBuffer.toString());

            String imgUrl = imagesIndex1.path("url").asText();
            //设置商品主图
            basicDataDO.setMainUrl(imgUrl);
            //Offer Description
            StringBuilder offerDescriptionBegin = new StringBuilder("{\"sections\":[");
            // 获取 standardizedDescription 和 sections
            JsonNode standardizedDescription = descriptionNode.path("standardizedDescription");
            JsonNode standardizedDescriptionItem = standardizedDescription.path("sections");
            for (int i = 0; i < standardizedDescriptionItem.size(); i++) {
                if (i > 0) offerDescriptionBegin.append(",");  // 处理 sections 数组中的多个对象
                offerDescriptionBegin.append("{\"items\":[");

                JsonNode jsonNode1 = standardizedDescriptionItem.get(i);
                JsonNode items = jsonNode1.path("items");

                for (int j = 0; j < items.size(); j++) {
                    if (j > 0) offerDescriptionBegin.append(",");  // 处理 items 数组中的多个对象
                    JsonNode itemNode = items.get(j);
                    offerDescriptionBegin.append("{");

                    // 将 itemNode 转换为 Map
                    Map<String, Object> item = objectMapper.convertValue(itemNode, LinkedHashMap.class);

                    // 遍历 Map
                    for (Map.Entry<String, Object> entry : item.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        if (!key.equals("size") && !key.equals("thumbnails") && !key.equals("alt"))
                        {
                            //转移特殊字符 /n /t /&
                            String valueStr = StringEscapeUtils.escapeJson(String.valueOf(value));
                            offerDescriptionBegin.append("\"" + key + "\":\"" + valueStr + "\",");
                        }
                    }

                    // 删除最后一个逗号
                    if (offerDescriptionBegin.charAt(offerDescriptionBegin.length() - 1) == ',') {
                        offerDescriptionBegin.deleteCharAt(offerDescriptionBegin.length() - 1);
                    }

                    offerDescriptionBegin.append("}");
                }

                offerDescriptionBegin.append("]}");
            }

            if (offerDescriptionBegin.charAt(offerDescriptionBegin.length() - 1) == ',') {
                offerDescriptionBegin.deleteCharAt(offerDescriptionBegin.length() - 1);
            }

            offerDescriptionBegin.append("]}");
            basicDataDO.setOfferDescription(offerDescriptionBegin.toString());
            //上传到oss 中

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String offerDesc = uploadOss(basicDataDO.getOfferDescription());
        basicDataDO.setOfferDescription(offerDesc);
        String imgUrl = uploadOss(basicDataDO.getImgUrl());
        basicDataDO.setImgUrl(imgUrl);
        basicDataDO.setUrl(basicDataImportReqVO.getUrl());
        basicDataDO.setDataJson(uploadUrl);

        List<KeywordDO> keywordList = keywordMapper.selectList(new QueryWrapper<KeywordDO>().eq("deleted", 0));
        for (KeywordDO keywordDO : keywordList) {
            if (basicDataDO.getTitle().contains(keywordDO.getInfringementKeyword())){
                ViolateProductDO violateProductDO = new ViolateProductDO();
                BeanUtils.copyProperties(basicDataDO,violateProductDO);
                violateProductDO.setViolateWord(keywordDO.getInfringementKeyword());
                violateProductDO.setViolateId(keywordDO.getId());
                violateProductMapper.insert(violateProductDO);
                flag = true;
                return flag;
            }
        }

        basicDataMapper.insert(basicDataDO);
        flag = true;

        return flag;
    }

    public String uploadOss(String data) {
//        validateFileConfigExists(new Long(28));
        // 文件内容转成 byte 数组
        byte[] fileContent = data.getBytes(StandardCharsets.UTF_8);
        String uploadUrl = "";

        // 生成唯一的文件名（以 .txt 结尾）
        String fileName = IdUtil.fastSimpleUUID() + ".txt";
        try {
            uploadUrl = getFileClient(new Long(28)).upload(fileContent, fileName, "text/plain");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadUrl;
    }

    public FileClient getFileClient(Long id) {
        return clientCache.getUnchecked(id);
    }

    private FileConfigDO validateFileConfigExists(Long id) {
        FileConfigDO config = fileConfigMapper.selectById(id);
        if (config == null) {
            throw exception(FILE_CONFIG_NOT_EXISTS);
        }
        return config;
    }

    @Override
    public BasicDataImportVO importById(List<String> ids) {
        String cookie;
        String url = "https://allegro.pl/oferta/apple-ipad-10-9-10-gen-64gb-wifi-blue-niebieski-17111724334"; // 这里可以从 ids 中提取 URL

        Long userId = SecurityFrameworkUtils.getLoginUserId();
        String userIdStr = String.valueOf(userId);

        // 获取 cookie
        cookie = getCookie(userIdStr);

        // 验证 cookie 是否有效
        if (isCookieExpired(cookie, url)) {
            // cookie 过期，重新获取并设置
            cookie = refreshCookie(userIdStr, userId);
        }
        // 进行接下来的导入操作
        if (cookie != null) {
            BasicDataImportVO basicDataImportVO = performImport(ids, cookie);
            return basicDataImportVO;
        }
        return null;
    }

    // 获取缓存中的 cookie，若没有则返回 null
    private String getCookie(String userIdStr) {
        Object exist = redisUtil.get(userIdStr);
        if (exist != null) {
            return (String) exist;
        }
        return null;
    }

    // 判断 cookie 是否过期
    private boolean isCookieExpired(String cookie, String url) {
        if (cookie == null) {
            return true; // 如果 cookie 为 null，说明需要重新获取
        }

        CookieDatedRes cookieDatedRes = ErpUtils.verifyDuplicate(url, cookie);
        return !cookieDatedRes.isSuccess() && cookieDatedRes.getResponseError().getMessage().contains("过期");
    }

    // 重新获取并设置 cookie
    private String refreshCookie(String userIdStr, Long userId) {
        AdminUserRespDTO user = adminUserApi.getUser(userId);
        String newCookie = reGetCookie(user.getErpName(), user.getErpPwd());
        redisUtil.set(userIdStr, newCookie, 60 * 60 * 48);  // 设置 Redis 缓存 3 小时
        return newCookie;
    }

    // 处理导入操作
    private BasicDataImportVO performImport(List<String> ids, String cookie) {
        Integer successCount = 0;
        Integer errorCount = 0;
        BasicDataImportVO basicDataImportVO = new BasicDataImportVO();
        ArrayList<ImportSuccessRes> importSuccessResList = new ArrayList<>();
        ArrayList<ImportErrorRes> importErrorResList = new ArrayList<>();
        // 这里是导入操作的实现逻辑
        List<BasicDataDO> list = basicDataMapper.selectList(BasicDataDO::getId, ids);
        for (BasicDataDO basicDataDO : list) {
            CookieDatedRes cookieDatedRes = ErpUtils.saveProduct(basicDataDO.getDataJson(), basicDataDO.getUrl(), 1, cookie);
            //保存成功
            if (cookieDatedRes.isSuccess()) {
                successCount++;
                ImportSuccessRes successRes = new ImportSuccessRes();
                successRes.setId(basicDataDO.getId());
                successRes.setUrl(basicDataDO.getUrl());
                importSuccessResList.add(successRes);
            } else {
                errorCount++;
                ImportErrorRes importErrorRes = new ImportErrorRes();
                importErrorRes.setId(basicDataDO.getId());
                importErrorRes.setUrl(basicDataDO.getUrl());
                importErrorRes.setCookieDatedRes(cookieDatedRes);
                importErrorResList.add(importErrorRes);
            }
        }
        basicDataImportVO.setSuccessCount(successCount);
        basicDataImportVO.setErrorCount(errorCount);
        basicDataImportVO.setTotalCount(list.size());
        basicDataImportVO.setSuccessResList(importSuccessResList);
        basicDataImportVO.setErrorDetailList(importErrorResList);
        return basicDataImportVO;
    }

}