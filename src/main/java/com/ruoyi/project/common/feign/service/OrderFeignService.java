//package com.ruoyi.project.common.feign.service;
//
//import com.ruoyi.project.common.feign.fallback.OrderFeignFallbackProxy;
//import com.ruoyi.project.warehouse.domain.FeedstockFormPrintResult;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@FeignClient(name = "order-service", path = "order-service", fallbackFactory = OrderFeignFallbackProxy.class)
//public interface OrderFeignService {
//    /**
//     * 进料验收单打印信息
//     *
//     * @return
//     */
//    @RequestMapping(value = "/acceptance/feedstock/print/{id}", method = RequestMethod.GET)
//    FeedstockFormPrintResult getFeedstockPrintInfo(@PathVariable("id") String id);
//
//}
