package cn.iocoder.yudao.module.wuyou.controller.admin.collect;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.wuyou.controller.admin.basicdata.vo.BasicDataImportReqNewVO;
import cn.iocoder.yudao.module.wuyou.controller.admin.collect.vo.CollectData;
import cn.iocoder.yudao.module.wuyou.controller.admin.collect.vo.TaskPageResVO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.device.DeviceDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.producturl.ProductUrlDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.task.TaskDO;
import cn.iocoder.yudao.module.wuyou.dal.dataobject.taskpagedetail.TaskPageDetailDO;
import cn.iocoder.yudao.module.wuyou.dal.mysql.device.DeviceMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.producturl.ProductUrlMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.task.TaskMapper;
import cn.iocoder.yudao.module.wuyou.dal.mysql.taskpagedetail.TaskPageDetailMapper;
import cn.iocoder.yudao.module.wuyou.service.basicdata.BasicDataService;
import cn.iocoder.yudao.module.wuyou.utils.IpUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.error;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 采集器请求接口")
@RestController
@RequestMapping("/collect")
@Validated
@Slf4j
public class CollectController {

    @Resource
    private TaskMapper taskMapper;

    @Resource
    private TaskPageDetailMapper taskPageDetailMapper;

    @Resource
    private ProductUrlMapper productUrlMapper;

    @Resource
    private DeviceMapper deviceMapper;

    @Resource
    private BasicDataService basicDataService;

    @Resource
    private IpUtils ipUtils;

    @PostMapping("/getData")
    @Operation(summary = "获取需要采集的数据")
    @PermitAll
    public CommonResult<List<TaskPageResVO>> getCollectData(HttpServletRequest request) {
        //获取ip
        String ipAddress = ipUtils.getClientIp(request);
        DeviceDO deviceDO = deviceMapper.selectOne(DeviceDO::getIpAddress, ipAddress);
       if (deviceDO==null){
           return error(500, "采集器不存在");
       }
        // 找到可以进行分配的任务
        List<TaskDO> taskDOList = taskMapper.selectList(new QueryWrapper<TaskDO>().in("status", 0, 1).orderByAsc("create_time"));
       if (taskDOList.isEmpty()){
           return error(500, "没有可分配的任务");
       }

        // 用来存储最终的分页数据
        List<TaskPageDetailDO> taskPageDetailDOS = new ArrayList<>();
        int requiredCount = 50;  // 剩余需要的条数

        // 遍历 taskDOList，直到收集到 50 条数据
        for (TaskDO task : taskDOList) {
            if (task.getTaskType() == 0) { // 采列表任务
                Long taskId = task.getId();
                // 根据 taskId 去分页详情表查询，限制查询剩余所需条数的数据
                List<TaskPageDetailDO> pageDetailList = taskPageDetailMapper.selectList(
                        new QueryWrapper<TaskPageDetailDO>()
                                .eq("task_id", taskId)
                                .isNull("device_id")
                                .isNull("assigned_at")
                                .last("LIMIT " + requiredCount)  // 根据剩余需要的条数限制
                );

                if (!pageDetailList.isEmpty()) {
                    taskPageDetailDOS.addAll(pageDetailList);
                    requiredCount -= pageDetailList.size(); // 更新剩余需要的条数
                }

                // 如果已经收集到 50 条数据，退出循环
                if (requiredCount <= 0) {
                    break;
                }
            }
            else if (task.getTaskType() ==1){
                //请求商品的详情任务
                String detailIds = task.getDetailIds();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    List<Long> detailIdsList = objectMapper.readValue(detailIds, new TypeReference<List<Long>>(){});
                    // 使用 detailIdsArray
                    List<ProductUrlDO> list = productUrlMapper.selectList(new QueryWrapper<ProductUrlDO>().in("id", detailIdsList));
                    List<String> collect = list.stream().map(ProductUrlDO::getUrl).collect(Collectors.toList());
                    TaskPageResVO taskPageResVO = new TaskPageResVO();
                    //详情任务
                    taskPageResVO.setCategory(1);
                    taskPageResVO.setUrlList(collect);
                    List<TaskPageResVO> taskPageResVOS = new ArrayList<>();
                    taskPageResVOS.add(taskPageResVO);
                    for (ProductUrlDO productUrlDO : list) {
                        productUrlDO.setDeviceId(deviceDO.getId());
                        productUrlDO.setAssignedAt(LocalDateTime.now());
                        //设置为采集中
                        productUrlDO.setProcessFlag(1);
                    }
                    productUrlMapper.updateBatch(list);
                    //详情任务 设置为已分配
                    task.setStatus(2);
                    taskMapper.updateById(task);
                    return success(taskPageResVOS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (TaskPageDetailDO taskPageDetailDO : taskPageDetailDOS) {
            taskPageDetailDO.setDeviceId(deviceDO.getId());
            //设置为采集中
            taskPageDetailDO.setStatus(1);
            taskPageDetailDO.setAssignedAt(LocalDateTime.now());
        }
        taskPageDetailMapper.updateBatch(taskPageDetailDOS);
        //构造返回的数据
        List<TaskPageResVO> taskPageResVOS = processTaskPageDetails(taskPageDetailDOS);
        //采集器设置为采集中
        deviceDO.setStatus(1);
        deviceMapper.updateById(deviceDO);
        //任务的状态由定时任务进行更改
        return success(taskPageResVOS);
    }

    @PostMapping("/saveData")
    @Operation(summary = "保存数据")
    @PermitAll
    public CommonResult saveData(@RequestBody CollectData collectData) {
        Integer type = collectData.getType();
        //  先收集page的数据 要先修改pageDetail的记录  要修改进度表
        if (type == 0) {
            String categoryUrl = collectData.getUrl();
            Integer currentPage = collectData.getCurrentPage();

            TaskPageDetailDO taskPageDetailDO = taskPageDetailMapper.selectOne(new QueryWrapper<TaskPageDetailDO>().eq("url", categoryUrl).eq("page_num", currentPage));
            //设置为采集完成
            taskPageDetailDO.setStatus(2);
            taskPageDetailMapper.updateById(taskPageDetailDO);

            //插入链接数据  url重复的 数据库会进行过滤
            List<String> productStr = collectData.getProductList();
            ArrayList<ProductUrlDO> productList = new ArrayList<>();
            for (String item : productStr) {
                ProductUrlDO productUrlDO = new ProductUrlDO();
                productUrlDO.setUrl(item);
                productUrlDO.setCategoryUrl(categoryUrl);
                productList.add(productUrlDO);
            }
            Boolean aBoolean = productUrlMapper.insertBatch(productList);
            if (aBoolean){
                //生成一个商品详情任务
                TaskDO taskDO = new TaskDO();
                taskDO.setTaskType(1);
                taskDO.setUrl(categoryUrl);
                List<Long> collect = productList.stream().map(ProductUrlDO::getId).collect(Collectors.toList());
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String jsonString = objectMapper.writeValueAsString(collect);
                    taskDO.setDetailIds(jsonString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                taskMapper.insert(taskDO);
            }
            return success(aBoolean);
        }
        if (type == 1){
            String url = collectData.getUrl();
            String dataJson= collectData.getDataJson();
            BasicDataImportReqNewVO basicDataImportReqNewVO = new BasicDataImportReqNewVO();
            basicDataImportReqNewVO.setUrl(url);
            basicDataImportReqNewVO.setJson(dataJson);
            Boolean res = basicDataService.importRes(basicDataImportReqNewVO);
            //需要将这条数据设置为已采集
            ProductUrlDO productUrlDO = productUrlMapper.selectOne("url", url);
            productUrlDO.setProcessFlag(2);
            productUrlMapper.updateById(productUrlDO);
            return success(res);
        }
        return null;
    }

    public List<TaskPageResVO> processTaskPageDetails(List<TaskPageDetailDO> taskPageDetailDOS) {
        // 按url分组，并计算每个url的最小页数和最大页数
        List<TaskPageResVO> urlPageMap = taskPageDetailDOS.stream()
                .collect(Collectors.groupingBy(TaskPageDetailDO::getUrl))  // 按url分组
                .entrySet().stream()
                .map(entry -> {
                    String url = entry.getKey();
                    List<TaskPageDetailDO> pageDetails = entry.getValue();

                    // 获取最小和最大页数
                    int minPageNum = pageDetails.stream()
                            .min(Comparator.comparingInt(TaskPageDetailDO::getPageNum))
                            .get()
                            .getPageNum();
                    int maxPageNum = pageDetails.stream()
                            .max(Comparator.comparingInt(TaskPageDetailDO::getPageNum))
                            .get()
                            .getPageNum();

                    // 创建一个新的TaskPageResVO，存储最小和最大页数
                    TaskPageResVO aggregatedDetail = new TaskPageResVO();
                    aggregatedDetail.setUrl(url);
                    aggregatedDetail.setCategory(0);
                    aggregatedDetail.setMinPageNum(minPageNum);  // 设置最小页数
                    aggregatedDetail.setMaxPageNum(maxPageNum);  // 设置最大页数

                    return aggregatedDetail;
                })
                .collect(Collectors.toList());

        return urlPageMap;
    }


}


