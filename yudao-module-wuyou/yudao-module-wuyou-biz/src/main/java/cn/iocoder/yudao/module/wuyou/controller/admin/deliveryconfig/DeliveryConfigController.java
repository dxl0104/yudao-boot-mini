package cn.iocoder.yudao.module.wuyou.controller.admin.deliveryconfig;

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

import cn.iocoder.yudao.module.wuyou.controller.admin.deliveryconfig.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.deliveryconfig.DeliveryConfigDO;
import cn.iocoder.yudao.module.wuyou.service.deliveryconfig.DeliveryConfigService;

@Tag(name = "管理后台 - 快递费归档")
@RestController
@RequestMapping("/wuyou/delivery-config")
@Validated
public class DeliveryConfigController {

    @Resource
    private DeliveryConfigService deliveryConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建快递费归档")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-config:create')")
    public CommonResult<Long> createDeliveryConfig(@Valid @RequestBody DeliveryConfigSaveReqVO createReqVO) {
        return success(deliveryConfigService.createDeliveryConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新快递费归档")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-config:update')")
    public CommonResult<Boolean> updateDeliveryConfig(@Valid @RequestBody DeliveryConfigSaveReqVO updateReqVO) {
        deliveryConfigService.updateDeliveryConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除快递费归档")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-config:delete')")
    public CommonResult<Boolean> deleteDeliveryConfig(@RequestParam("id") Long id) {
        deliveryConfigService.deleteDeliveryConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得快递费归档")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-config:query')")
    public CommonResult<DeliveryConfigRespVO> getDeliveryConfig(@RequestParam("id") Long id) {
        DeliveryConfigDO deliveryConfig = deliveryConfigService.getDeliveryConfig(id);
        return success(BeanUtils.toBean(deliveryConfig, DeliveryConfigRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得快递费归档分页")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-config:query')")
    public CommonResult<PageResult<DeliveryConfigRespVO>> getDeliveryConfigPage(@Valid DeliveryConfigPageReqVO pageReqVO) {
        PageResult<DeliveryConfigDO> pageResult = deliveryConfigService.getDeliveryConfigPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DeliveryConfigRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出快递费归档 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:delivery-config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDeliveryConfigExcel(@Valid DeliveryConfigPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DeliveryConfigDO> list = deliveryConfigService.getDeliveryConfigPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "快递费归档.xls", "数据", DeliveryConfigRespVO.class,
                        BeanUtils.toBean(list, DeliveryConfigRespVO.class));
    }

}