package cn.iocoder.yudao.module.wuyou.controller.admin.deliveryempty;

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

import cn.iocoder.yudao.module.wuyou.controller.admin.deliveryempty.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryempty.DeliveryEmptyDO;
import cn.iocoder.yudao.module.wuyou.service.deliveryempty.DeliveryEmptyService;

@Tag(name = "管理后台 - 快递为零商品")
@RestController
@RequestMapping("/wuyou/delivery-empty")
@Validated
public class DeliveryEmptyController {

    @Resource
    private DeliveryEmptyService deliveryEmptyService;

    @PostMapping("/create")
    @Operation(summary = "创建快递为零商品")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-empty:create')")
    public CommonResult<Long> createDeliveryEmpty(@Valid @RequestBody DeliveryEmptySaveReqVO createReqVO) {
        return success(deliveryEmptyService.createDeliveryEmpty(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新快递为零商品")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-empty:update')")
    public CommonResult<Boolean> updateDeliveryEmpty(@Valid @RequestBody DeliveryEmptySaveReqVO updateReqVO) {
        deliveryEmptyService.updateDeliveryEmpty(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除快递为零商品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-empty:delete')")
    public CommonResult<Boolean> deleteDeliveryEmpty(@RequestParam("id") Long id) {
        deliveryEmptyService.deleteDeliveryEmpty(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得快递为零商品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-empty:query')")
    public CommonResult<DeliveryEmptyRespVO> getDeliveryEmpty(@RequestParam("id") Long id) {
        DeliveryEmptyDO deliveryEmpty = deliveryEmptyService.getDeliveryEmpty(id);
        return success(BeanUtils.toBean(deliveryEmpty, DeliveryEmptyRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得快递为零商品分页")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-empty:query')")
    public CommonResult<PageResult<DeliveryEmptyRespVO>> getDeliveryEmptyPage(@Valid DeliveryEmptyPageReqVO pageReqVO) {
        PageResult<DeliveryEmptyDO> pageResult = deliveryEmptyService.getDeliveryEmptyPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DeliveryEmptyRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出快递为零商品 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-empty:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDeliveryEmptyExcel(@Valid DeliveryEmptyPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeliveryEmptyDO> list = deliveryEmptyService.getDeliveryEmptyPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "快递为零商品.xls", "数据", DeliveryEmptyRespVO.class,
                        BeanUtils.toBean(list, DeliveryEmptyRespVO.class));
    }

}