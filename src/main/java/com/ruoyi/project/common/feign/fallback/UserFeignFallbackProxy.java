//package com.ruoyi.project.common.feign.fallback;
//
//import com.ruoyi.common.utils.StringUtils;
//import com.ruoyi.project.common.entity.ExamineTaskDTO;
//import com.ruoyi.project.common.feign.service.UserFeignService;
//import lombok.extern.slf4j.Slf4j;
//import feign.hystrix.FallbackFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class UserFeignFallbackProxy implements FallbackFactory<UserFeignService> {
//    @Override
//    public UserFeignService create(Throwable throwable) {
//        String msg = throwable == null ? "" : throwable.getMessage();
//        if (!StringUtils.isEmpty(msg)) {
//            log.info("the third system error");
//        }
//        return new UserFeignService() {
//            @Override
//            public void examineWorkNotice(ExamineTaskDTO examineTaskDTO) throws Exception {
//                log.info("Feign调用失败! examineWorkNotice");
//                new Exception("推送质检作业通知失败");
//            }
//        };
//    }
//}
