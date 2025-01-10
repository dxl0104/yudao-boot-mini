package cn.iocoder.yudao.module.wuyou.service.basicdata;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.BasicDataPageReqVO;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.BasicDataSaveReqVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata.BasicDataDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.basicdata.BasicDataMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.producturl.ProductUrlMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.BASIC_DATA_NOT_EXISTS;


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

}