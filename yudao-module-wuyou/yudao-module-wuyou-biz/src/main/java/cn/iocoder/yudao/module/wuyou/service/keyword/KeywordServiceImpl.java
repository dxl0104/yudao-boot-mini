package cn.iocoder.yudao.module.wuyou.service.keyword;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.KeywordPageReqVO;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.KeywordSaveReqVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.keyword.KeywordDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.keyword.KeywordMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.wuyou.enums.ErrorCodeConstants.KEYWORD_NOT_EXISTS;

/**
 * 无忧侵权词 Service 实现类
 *
 * @author admin234
 */
@Service
@Validated
@Slf4j
public class KeywordServiceImpl implements KeywordService {

    @Resource
    private KeywordMapper keywordMapper;

    @Override
    public Long createKeyword(KeywordSaveReqVO createReqVO) {
        // 插入
        KeywordDO keyword = BeanUtils.toBean(createReqVO, KeywordDO.class);
        try {
            keywordMapper.insert(keyword);
        }catch (Exception e){
            log.info("侵权词重复{}",keyword.getInfringementKeyword());
        }

        // 返回
        return keyword.getId();
    }

    @Override
    public void updateKeyword(KeywordSaveReqVO updateReqVO) {
        // 校验存在
        validateKeywordExists(updateReqVO.getId());
        // 更新
        KeywordDO updateObj = BeanUtils.toBean(updateReqVO, KeywordDO.class);
        keywordMapper.updateById(updateObj);
    }

    @Override
    public void deleteKeyword(Long id) {
        // 校验存在
        validateKeywordExists(id);
        // 删除
        keywordMapper.deleteById(id);
    }

    private void validateKeywordExists(Long id) {
        if (keywordMapper.selectById(id) == null) {
            throw exception(KEYWORD_NOT_EXISTS);
        }
    }

    @Override
    public KeywordDO getKeyword(Long id) {
        return keywordMapper.selectById(id);
    }

    @Override
    public PageResult<KeywordDO> getKeywordPage(KeywordPageReqVO pageReqVO) {
        return keywordMapper.selectPage(pageReqVO);
    }

}