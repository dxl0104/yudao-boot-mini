package cn.iocoder.yudao.module.wuyou.service.basicdata;

import cn.iocoder.yudao.framework.common.pojo.CookieDatedRes;
import cn.iocoder.yudao.framework.common.pojo.CookieResponse;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.common.util.wuyou.LoginUtils;
import cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.BasicDataPageReqVO;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.BasicDataSaveReqVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata.BasicDataDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.basicdata.BasicDataMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.producturl.ProductUrlMapper;
import cn.iocoder.yudao.module.wuyou.utils.ErpUtils;
import cn.iocoder.yudao.module.wuyou.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;

import javax.annotation.Resource;
import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;
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
    public void importByIdS(List<String> ids) {
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
            performImport(ids,cookie);
        }
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
        String newCookie = reGetCookie(user.getErpName(), user.getEprPwd());
        redisUtil.set(userIdStr, newCookie, 60 * 60 * 3);  // 设置 Redis 缓存 3 小时
        return newCookie;
    }

    // 处理导入操作
    private CookieDatedRes performImport(List<String> ids,String cookie) {
        Integer successCount = 0;
        // 这里是导入操作的实现逻辑
        List<BasicDataDO> list = basicDataMapper.selectList(BasicDataDO::getId, ids);
        for (BasicDataDO basicDataDO : list) {
            CookieDatedRes cookieDatedRes = ErpUtils.saveProduct(basicDataDO.getDataJson(), basicDataDO.getUrl(), 1, cookie);
        }
        return null;
    }

}