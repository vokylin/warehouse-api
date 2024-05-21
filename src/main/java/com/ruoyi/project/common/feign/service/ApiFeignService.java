//package com.ruoyi.project.common.feign.service;
//
//import com.ruoyi.project.common.entity.BaseDetailDTO;
//import com.ruoyi.project.common.entity.BaseNoticeDTO;
//import com.ruoyi.project.common.entity.DeliveryNoticeDTO;
//import com.ruoyi.project.common.entity.WmsCancelDTO;
//import com.ruoyi.project.common.feign.fallback.ApiFeignFallbackProxy;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * 管理中心装服务
// *
// * @author REM
// * @date 2023/04/07
// */
//@FeignClient(value = "api-service", path = "api-service", fallbackFactory = ApiFeignFallbackProxy.class)
//public interface ApiFeignService {
//
//    /**
//     * 收货完成调用
//     */
//    @RequestMapping(value = "/wms/callBack/receiveFinished", method = RequestMethod.POST)
//    void receiveFinished(BaseNoticeDTO baseNoticeDTO) throws Exception;
//
//
//    /**
//     * 发货完成调用
//     */
//    @RequestMapping(value = "/wms/callBack/deliveryFinished", method = RequestMethod.POST)
//    void deliveryFinished(DeliveryNoticeDTO deliveryNoticeDTO) throws Exception;
//
//
//    /**
//     * 收货/发货作废调用
//     */
//    @RequestMapping(value = "/wms/callBack/cancelNotice", method = RequestMethod.POST)
//    void cancelNotice(WmsCancelDTO wmsCancelDTO) throws Exception;
//
//
//}
