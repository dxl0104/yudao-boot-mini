package cn.iocoder.yudao.module.wuyou.controller.admin.keyword;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.KeywordPageReqVO;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.KeywordRespVO;
import cn.iocoder.yudao.module.wuyou.controller.admin.keyword.vo.KeywordSaveReqVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.keyword.KeywordDO;
import cn.iocoder.yudao.module.wuyou.service.keyword.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 无忧侵权词")
@RestController
@RequestMapping("/wuyou/keyword")
@Validated
public class KeywordController {

    @Resource
    private KeywordService keywordService;

    @PostMapping("/create")
    @Operation(summary = "创建无忧侵权词")
    @PreAuthorize("@ss.hasPermission('wuyou:keyword:create')")
    public CommonResult<Long> createKeyword(@Valid @RequestBody KeywordSaveReqVO createReqVO) {
        return success(keywordService.createKeyword(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新无忧侵权词")
    @PreAuthorize("@ss.hasPermission('wuyou:keyword:update')")
    public CommonResult<Boolean> updateKeyword(@Valid @RequestBody KeywordSaveReqVO updateReqVO) {
        keywordService.updateKeyword(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除无忧侵权词")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:keyword:delete')")
    public CommonResult<Boolean> deleteKeyword(@RequestParam("id") Long id) {
        keywordService.deleteKeyword(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得无忧侵权词")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:keyword:query')")
    public CommonResult<KeywordRespVO> getKeyword(@RequestParam("id") Long id) {
        KeywordDO keyword = keywordService.getKeyword(id);
        return success(BeanUtils.toBean(keyword, KeywordRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得无忧侵权词分页")
    @PreAuthorize("@ss.hasPermission('wuyou:keyword:query')")
    public CommonResult<PageResult<KeywordRespVO>> getKeywordPage(@Valid KeywordPageReqVO pageReqVO) {
        PageResult<KeywordDO> pageResult = keywordService.getKeywordPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, KeywordRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出无忧侵权词 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:keyword:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportKeywordExcel(@Valid KeywordPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<KeywordDO> list = keywordService.getKeywordPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "无忧侵权词.xls", "数据", KeywordRespVO.class,
                        BeanUtils.toBean(list, KeywordRespVO.class));
    }

}