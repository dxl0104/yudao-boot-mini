package cn.iocoder.yudao.module.wuyou.controller.admin.discountrules;

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

import cn.iocoder.yudao.module.wuyou.controller.admin.discountrules.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.discountrules.DiscountRulesDO;
import cn.iocoder.yudao.module.wuyou.service.discountrules.DiscountRulesService;

@Tag(name = "管理后台 - 折扣规则")
@RestController
@RequestMapping("/wuyou/discount-rules")
@Validated
public class DiscountRulesController {

    @Resource
    private DiscountRulesService discountRulesService;

    @PostMapping("/create")
    @Operation(summary = "创建折扣规则")
    @PreAuthorize("@ss.hasPermission('wuyou:discount-rules:create')")
    public CommonResult<Long> createDiscountRules(@Valid @RequestBody DiscountRulesSaveReqVO createReqVO) {
        return success(discountRulesService.createDiscountRules(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新折扣规则")
    @PreAuthorize("@ss.hasPermission('wuyou:discount-rules:update')")
    public CommonResult<Boolean> updateDiscountRules(@Valid @RequestBody DiscountRulesSaveReqVO updateReqVO) {
        discountRulesService.updateDiscountRules(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除折扣规则")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:discount-rules:delete')")
    public CommonResult<Boolean> deleteDiscountRules(@RequestParam("id") Long id) {
        discountRulesService.deleteDiscountRules(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得折扣规则")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:discount-rules:query')")
    public CommonResult<DiscountRulesRespVO> getDiscountRules(@RequestParam("id") Long id) {
        DiscountRulesDO discountRules = discountRulesService.getDiscountRules(id);
        return success(BeanUtils.toBean(discountRules, DiscountRulesRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得折扣规则分页")
    @PreAuthorize("@ss.hasPermission('wuyou:discount-rules:query')")
    public CommonResult<PageResult<DiscountRulesRespVO>> getDiscountRulesPage(@Valid DiscountRulesPageReqVO pageReqVO) {
        PageResult<DiscountRulesDO> pageResult = discountRulesService.getDiscountRulesPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DiscountRulesRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出折扣规则 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:discount-rules:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDiscountRulesExcel(@Valid DiscountRulesPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DiscountRulesDO> list = discountRulesService.getDiscountRulesPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "折扣规则.xls", "数据", DiscountRulesRespVO.class,
                        BeanUtils.toBean(list, DiscountRulesRespVO.class));
    }

}