package cn.iocoder.yudao.module.wuyou.service.sourceurl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.wuyou.controller.admin.sourceurl.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.sourceurl.SourceUrlDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.wuyou.dal.mysql.sourceurl.SourceUrlMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.*;

/**
 * 列表链接 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SourceUrlServiceImpl implements SourceUrlService {

    @Resource
    private SourceUrlMapper sourceUrlMapper;

    @Override
    public Long createSourceUrl(SourceUrlSaveReqVO createReqVO) {
        // 插入
        SourceUrlDO sourceUrl = BeanUtils.toBean(createReqVO, SourceUrlDO.class);
        sourceUrlMapper.insert(sourceUrl);
        // 返回
        return sourceUrl.getId();
    }

    @Override
    public void updateSourceUrl(SourceUrlSaveReqVO updateReqVO) {
        // 校验存在
        validateSourceUrlExists(updateReqVO.getId());
        // 更新
        SourceUrlDO updateObj = BeanUtils.toBean(updateReqVO, SourceUrlDO.class);
        sourceUrlMapper.updateById(updateObj);
    }

    @Override
    public void deleteSourceUrl(Long id) {
        // 校验存在
        validateSourceUrlExists(id);
        // 删除
        sourceUrlMapper.deleteById(id);
    }

    private void validateSourceUrlExists(Long id) {
        if (sourceUrlMapper.selectById(id) == null) {
            throw exception(SOURCE_URL_NOT_EXISTS);
        }
    }

    @Override
    public SourceUrlDO getSourceUrl(Long id) {
        return sourceUrlMapper.selectById(id);
    }

    @Override
    public PageResult<SourceUrlDO> getSourceUrlPage(SourceUrlPageReqVO pageReqVO) {
        return sourceUrlMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SourceUrlDO> getSourceUrlList() {
        return sourceUrlMapper.selectList(SourceUrlDO::getConvertTask,0);
    }
}