package com.ruoyi.project.system.service.impl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.project.system.domain.SysDictData;
import com.ruoyi.project.system.domain.WmsDeliverySureDict;
import com.ruoyi.project.system.mapper.WmsDeliverySureDictMapper;
import com.ruoyi.project.system.service.ISysDictTypeService;
import com.ruoyi.project.system.service.IWmsDeliverySureDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出库确认项Service业务层处理
 *
 * @author Rem
 * @date 2023-04-12
 */
@Service
public class WmsDeliverySureDictServiceImpl implements IWmsDeliverySureDictService {
    @Autowired
    private WmsDeliverySureDictMapper wmsDeliverySureDictMapper;
    @Autowired
    private ISysDictTypeService dictTypeService;

    /**
     * 查询出库确认项
     *
     * @param id 出库确认项主键
     * @return 出库确认项
     */
    @Override
    public WmsDeliverySureDict selectWmsDeliverySureDictById(String id) {
        return wmsDeliverySureDictMapper.selectWmsDeliverySureDictById(id);
    }

    /**
     * 查询出库确认项列表
     *
     * @param wmsDeliverySureDict 出库确认项
     * @return 出库确认项
     */
    @Override
    public List<WmsDeliverySureDict> selectWmsDeliverySureDictList(WmsDeliverySureDict wmsDeliverySureDict) {
        return wmsDeliverySureDictMapper.selectWmsDeliverySureDictList(wmsDeliverySureDict);
    }

    /**
     * 新增出库确认项
     *
     * @param wmsDeliverySureDict 出库确认项
     * @return 结果
     */
    @Override
    public int insertWmsDeliverySureDict(WmsDeliverySureDict wmsDeliverySureDict) {
        wmsDeliverySureDict.setId(IdUtils.fastSimpleUUID());
        wmsDeliverySureDict.setCompanyId(ServletUtils.getCompanyId());
        wmsDeliverySureDict.setCreateTime(DateUtils.getNowDate());
        return wmsDeliverySureDictMapper.insertWmsDeliverySureDict(wmsDeliverySureDict);
    }

    /**
     * 修改出库确认项
     *
     * @param wmsDeliverySureDict 出库确认项
     * @return 结果
     */
    @Override
    public int updateWmsDeliverySureDict(WmsDeliverySureDict wmsDeliverySureDict) {
        wmsDeliverySureDict.setUpdateTime(DateUtils.getNowDate());
        return wmsDeliverySureDictMapper.updateWmsDeliverySureDict(wmsDeliverySureDict);
    }

    /**
     * 批量删除出库确认项
     *
     * @param ids 需要删除的出库确认项主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliverySureDictByIds(String[] ids) {
        return wmsDeliverySureDictMapper.deleteWmsDeliverySureDictByIds(ids);
    }

    /**
     * 删除出库确认项信息
     *
     * @param id 出库确认项主键
     * @return 结果
     */
    @Override
    public int deleteWmsDeliverySureDictById(String id) {
        return wmsDeliverySureDictMapper.deleteWmsDeliverySureDictById(id);
    }

    @Override
    public List<WmsDeliverySureDict> getListByBusinessType(WmsDeliverySureDict wmsDeliverySureDict) {
        // 获取字典中需要检查的单据类型
        List<SysDictData> sysDictData = dictTypeService.selectDictDataByType(Constants.DELIVERY_CHECK_BILL_TYPE);
        if (sysDictData.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> list = sysDictData.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
        String businessType = wmsDeliverySureDict.getBusinessType();
        if (list.contains(businessType)) {
            return wmsDeliverySureDictMapper.selectWmsDeliverySureDictList(wmsDeliverySureDict);
        }
        return new ArrayList<>();
    }
}
