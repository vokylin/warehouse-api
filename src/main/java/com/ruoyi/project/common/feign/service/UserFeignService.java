//package com.ruoyi.project.common.feign.service;
//
//import com.ruoyi.project.common.entity.ExamineTaskDTO;
//import com.ruoyi.project.common.feign.fallback.UserFeignFallbackProxy;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * 质检作业服务
// *
// * @author REM
// * @date 2023/04/28
// */
//@FeignClient(name = "user-service", path = "user-service", fallbackFactory = UserFeignFallbackProxy.class)
//public interface UserFeignService {
//
//    /**
//     * 推送质检作业通知
//     */
//    @RequestMapping(value = "/wms/examine/addTask", method = RequestMethod.POST)
//    void examineWorkNotice(ExamineTaskDTO examineTaskDTO) throws Exception;
//
//}
