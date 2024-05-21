//package com.ruoyi.project.common.feign.fallback;
//
//import com.ruoyi.common.utils.StringUtils;
//import com.ruoyi.project.common.feign.service.OrderFeignService;
//import com.ruoyi.project.warehouse.domain.FeedstockFormPrintResult;
//import feign.hystrix.FallbackFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class OrderFeignFallbackProxy implements FallbackFactory<OrderFeignService> {
//    @Override
//    public OrderFeignService create(Throwable throwable) {
//        String msg = throwable == null ? "" : throwable.getMessage();
//        if (!StringUtils.isEmpty(msg)) {
//            log.info("the third system error");
//        }
//        return new OrderFeignService() {
//            @Override
//            public FeedstockFormPrintResult getFeedstockPrintInfo(String id) {
//                log.info("Feign调用失败! getFeedstockPrintInfo");
//                return null;
//            }
//        };
//    }
//}
