package cn.iocoder.yudao.module.wuyou.controller.admin.basicdata;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;


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

import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.*;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.basicdata.BasicDataDO;
import cn.iocoder.yudao.module.wuyou.service.basicdata.BasicDataService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "管理后台 - 无忧基础数据")
@RestController
@RequestMapping("/wuyou/basic-data")
@Validated
public class BasicDataController {

    @Resource
    private BasicDataService basicDataService;

    @PostMapping("/create")
    @Operation(summary = "创建无忧基础数据")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:create')")
    public CommonResult<Long> createBasicData(@Valid @RequestBody BasicDataSaveReqVO createReqVO) {
        return success(basicDataService.createBasicData(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新无忧基础数据")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:update')")
    public CommonResult<Boolean> updateBasicData(@Valid @RequestBody BasicDataSaveReqVO updateReqVO) {
        basicDataService.updateBasicData(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除无忧基础数据")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:delete')")
    public CommonResult<Boolean> deleteBasicData(@RequestParam("id") Long id) {
        basicDataService.deleteBasicData(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得无忧基础数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:query')")
    public CommonResult<BasicDataRespVO> getBasicData(@RequestParam("id") Long id) {
        BasicDataDO basicData = basicDataService.getBasicData(id);
        return success(BeanUtils.toBean(basicData, BasicDataRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得无忧基础数据分页")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:query')")
    public CommonResult<PageResult<BasicDataRespVO>> getBasicDataPage(@Valid BasicDataPageReqVO pageReqVO) {
        PageResult<BasicDataDO> pageResult = basicDataService.getBasicDataPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, BasicDataRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出无忧基础数据 Excel")
    @PreAuthorize("@ss.hasPermission('wuyou:basic-data:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportBasicDataExcel(@Valid BasicDataPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<BasicDataDO> list = basicDataService.getBasicDataPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "无忧基础数据.xls", "数据", BasicDataRespVO.class,
                        BeanUtils.toBean(list, BasicDataRespVO.class));
    }

}