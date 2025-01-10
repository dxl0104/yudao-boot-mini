package cn.iocoder.yudao.module.wuyou.controller.admin.producturl;

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

import cn.iocoder.yudao.module.wuyou.controller.admin.producturl.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import cn.iocoder.yudao.module.wuyou.service.producturl.ProductUrlService;

@Tag(name = "管理后台 - 商品url列表")
@RestController
@RequestMapping("/wuyou/product-url")
@Validated
public class ProductUrlController {

    @Resource
    private ProductUrlService productUrlService;

    @PostMapping("/create")
    @Operation(summary = "创建商品url列表")
    @PreAuthorize("@ss.hasPermission('wuyou:product-url:create')")
    public CommonResult<Long> createProductUrl(@Valid @RequestBody ProductUrlSaveReqVO createReqVO) {
        return success(productUrlService.createProductUrl(createReqVO));
    }

    @PostMapping("/createBatch")
    @Operation(summary = "批量创建商品url列表")
    @PreAuthorize("@ss.hasPermission('wuyou:product-url:create')")
    public CommonResult<Long> createBatchProductUrl(@Valid @RequestBody ProductUrlBatchSaveReqVO createReqVO) {
        return success(productUrlService.createBatchProductUrl(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新商品url列表")
    @PreAuthorize("@ss.hasPermission('wuyou:product-url:update')")
    public CommonResult<Boolean> updateProductUrl(@Valid @RequestBody ProductUrlSaveReqVO updateReqVO) {
        productUrlService.updateProductUrl(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商品url列表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:product-url:delete')")
    public CommonResult<Boolean> deleteProductUrl(@RequestParam("id") Long id) {
        productUrlService.deleteProductUrl(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得商品url列表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:product-url:query')")
    public CommonResult<ProductUrlRespVO> getProductUrl(@RequestParam("id") Long id) {
        ProductUrlDO productUrl = productUrlService.getProductUrl(id);
        return success(BeanUtils.toBean(productUrl, ProductUrlRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得商品url列表分页")
    @PreAuthorize("@ss.hasPermission('wuyou:product-url:query')")
    public CommonResult<PageResult<ProductUrlRespVO>> getProductUrlPage(@Valid ProductUrlPageReqVO pageReqVO) {
        PageResult<ProductUrlDO> pageResult = productUrlService.getProductUrlPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProductUrlRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出商品url列表 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:product-url:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProductUrlExcel(@Valid ProductUrlPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProductUrlDO> list = productUrlService.getProductUrlPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "商品url列表.xls", "数据", ProductUrlRespVO.class,
                        BeanUtils.toBean(list, ProductUrlRespVO.class));
    }

}