package cn.iocoder.yudao.module.wuyou.controller.admin.violateproduct;

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

import cn.iocoder.yudao.module.wuyou.controller.admin.violateproduct.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.violateproduct.ViolateProductDO;
import cn.iocoder.yudao.module.wuyou.service.violateproduct.ViolateProductService;

@Tag(name = "管理后台 - 侵权商品")
@RestController
@RequestMapping("/wuyou/violate-product")
@Validated
public class ViolateProductController {

    @Resource
    private ViolateProductService violateProductService;

    @PostMapping("/create")
    @Operation(summary = "创建侵权商品")
    @PreAuthorize("@ss.hasPermission('wuyou:violate-product:create')")
    public CommonResult<Long> createViolateProduct(@Valid @RequestBody ViolateProductSaveReqVO createReqVO) {
        return success(violateProductService.createViolateProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新侵权商品")
    @PreAuthorize("@ss.hasPermission('wuyou:violate-product:update')")
    public CommonResult<Boolean> updateViolateProduct(@Valid @RequestBody ViolateProductSaveReqVO updateReqVO) {
        violateProductService.updateViolateProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除侵权商品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:violate-product:delete')")
    public CommonResult<Boolean> deleteViolateProduct(@RequestParam("id") Long id) {
        violateProductService.deleteViolateProduct(id);
        return success(true);
    }

    @DeleteMapping("/recover")
    @Operation(summary = "恢复侵权商品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:violate-product:recover')")
    public CommonResult<Boolean> recoverViolateProduct(@RequestParam("id") Long id) {
        violateProductService.recoverViolateProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得侵权商品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:violate-product:query')")
    public CommonResult<ViolateProductRespVO> getViolateProduct(@RequestParam("id") Long id) {
        ViolateProductDO violateProduct = violateProductService.getViolateProduct(id);
        return success(BeanUtils.toBean(violateProduct, ViolateProductRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得侵权商品分页")
    @PreAuthorize("@ss.hasPermission('wuyou:violate-product:query')")
    public CommonResult<PageResult<ViolateProductRespVO>> getViolateProductPage(@Valid ViolateProductPageReqVO pageReqVO) {
        PageResult<ViolateProductDO> pageResult = violateProductService.getViolateProductPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ViolateProductRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出侵权商品 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:violate-product:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportViolateProductExcel(@Valid ViolateProductPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ViolateProductDO> list = violateProductService.getViolateProductPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "侵权商品.xls", "数据", ViolateProductRespVO.class,
                        BeanUtils.toBean(list, ViolateProductRespVO.class));
    }

}