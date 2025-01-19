package cn.iocoder.yudao.module.wuyou.controller.admin.sourceurl;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.wuyou.controller.admin.sourceurl.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.sourceurl.SourceUrlDO;
import cn.iocoder.yudao.module.wuyou.service.sourceurl.SourceUrlService;

@Tag(name = "管理后台 - 列表链接")
@RestController
@RequestMapping("/wuyou/source-url")
@Validated
public class SourceUrlController {

    @Resource
    private SourceUrlService sourceUrlService;

    @PostMapping("/create")
    @Operation(summary = "创建列表链接")
    @PreAuthorize("@ss.hasPermission('wuyou:source-url:create')")
    public CommonResult<Long> createSourceUrl(@Valid @RequestBody SourceUrlSaveReqVO createReqVO) {
        return success(sourceUrlService.createSourceUrl(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新列表链接")
    @PreAuthorize("@ss.hasPermission('wuyou:source-url:update')")
    public CommonResult<Boolean> updateSourceUrl(@Valid @RequestBody SourceUrlSaveReqVO updateReqVO) {
        sourceUrlService.updateSourceUrl(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除列表链接")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:source-url:delete')")
    public CommonResult<Boolean> deleteSourceUrl(@RequestParam("id") Long id) {
        sourceUrlService.deleteSourceUrl(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得列表链接")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:source-url:query')")
    public CommonResult<SourceUrlRespVO> getSourceUrl(@RequestParam("id") Long id) {
        SourceUrlDO sourceUrl = sourceUrlService.getSourceUrl(id);
        return success(BeanUtils.toBean(sourceUrl, SourceUrlRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得列表链接分页")
    @PreAuthorize("@ss.hasPermission('wuyou:source-url:query')")
    public CommonResult<PageResult<SourceUrlRespVO>> getSourceUrlPage(@Valid SourceUrlPageReqVO pageReqVO) {
        PageResult<SourceUrlDO> pageResult = sourceUrlService.getSourceUrlPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SourceUrlRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出列表链接 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:source-url:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSourceUrlExcel(@Valid SourceUrlPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SourceUrlDO> list = sourceUrlService.getSourceUrlPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "列表链接.xls", "数据", SourceUrlRespVO.class,
                        BeanUtils.toBean(list, SourceUrlRespVO.class));
    }

}