package cn.iocoder.yudao.module.wuyou.service.basicdata;

import cn.hutool.core.io.resource.ResourceUtil;
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
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.basicdata.BasicDataMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.producturl.ProductUrlMapper;
import cn.iocoder.yudao.module.wuyou.utils.ErpUtils;
import cn.iocoder.yudao.module.wuyou.utils.RedisUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        boolean flag = false;
        // 校验存在
        validateFileConfigExists(28L);

        // 文件内容转成 byte 数组
        byte[] fileContent = basicDataImportReqVO.getJson().getBytes(StandardCharsets.UTF_8);
        String uploadUrl = "";
        try {
            // 生成唯一的文件名（以 .txt 结尾）
            String fileName = IdUtil.fastSimpleUUID() + ".txt";
            uploadUrl = getFileClient(28L).upload(fileContent, fileName, "text/plain");
        } catch (Exception e) {
            return flag;
        }
        //存储数据，将数据转化为JSON的格式
        BasicDataDO basicDataDO = new BasicDataDO();
        basicDataDO.setDataJson(uploadUrl);
        basicDataDO.setUrl(basicDataImportReqVO.getUrl());
        basicDataMapper.insert(basicDataDO);
        flag = true;

        return flag;
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
            if (cookieDatedRes.isSuccess()){
                successCount++;
                ImportSuccessRes successRes = new ImportSuccessRes();
                successRes.setId(basicDataDO.getId());
                successRes.setUrl(basicDataDO.getUrl());
                importSuccessResList.add(successRes);
            }
            else {
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