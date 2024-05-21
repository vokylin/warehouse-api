package com.ruoyi.framework.web.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.project.system.domain.Company;
import com.ruoyi.project.system.domain.StorageUnitType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
@Data
public class BaseTreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private String id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 节点编码
     */
    private String code;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BaseTreeSelect> children;

    public BaseTreeSelect() {

    }

    public BaseTreeSelect(Company company) {
        this.id = company.getId();
        this.label = company.getName();
        this.children = company.getChildren().stream().map(BaseTreeSelect::new).collect(Collectors.toList());
    }

    public BaseTreeSelect(StorageUnitType storageUnitType) {
        this.id = storageUnitType.getId();
        this.label = storageUnitType.getName();
        this.code = storageUnitType.getCode();
        this.children = storageUnitType.getChildren().stream().map(BaseTreeSelect::new).collect(Collectors.toList());
    }


}
