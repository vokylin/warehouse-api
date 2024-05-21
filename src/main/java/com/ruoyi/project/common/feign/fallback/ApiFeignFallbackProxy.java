//package com.ruoyi.project.common.feign.fallback;
//
//import com.ruoyi.common.utils.StringUtils;
//import com.ruoyi.project.common.entity.BaseNoticeDTO;
//import com.ruoyi.project.common.entity.DeliveryNoticeDTO;
//import com.ruoyi.project.common.entity.WmsCancelDTO;
//import com.ruoyi.project.common.feign.service.ApiFeignService;
//import feign.hystrix.FallbackFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class ApiFeignFallbackProxy implements FallbackFactory<ApiFeignService> {
//
//    @Override
//    public ApiFeignService create(Throwable throwable) {
//        String msg = throwable == null ? "" : throwable.getMessage();
//        if (!StringUtils.isEmpty(msg)) {
//            log.info("the third system error");
//        }
//        return new ApiFeignService() {
//            @Override
//            public void receiveFinished(BaseNoticeDTO baseNoticeDTO) throws Exception {
//                log.info("Feign调用失败 receiveFinished");
//                throw new Exception("API接口收货完成调用失败");
//            }
//
//            @Override
//            public void deliveryFinished(DeliveryNoticeDTO deliveryNoticeDTO) throws Exception {
//                log.info("Feign调用失败 deliveryFinished");
//                throw new Exception("API接口发货完成调用失败");
//            }
//
//            @Override
//            public void cancelNotice(WmsCancelDTO wmsCancelDTO) throws Exception {
//                log.info("Feign调用失败 cancelNotice");
//                throw new Exception("API作废接口调用失败");
//            }
//        };
//    }
//}
