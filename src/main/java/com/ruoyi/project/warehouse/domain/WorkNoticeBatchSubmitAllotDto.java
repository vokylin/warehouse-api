package com.ruoyi.project.warehouse.domain;

import lombok.Data;

/**
 * 作业通知批量入库提交对象
 *
 * @author REM
 * @date 2023/05/02
 */
@Data
public class WorkNoticeBatchSubmitAllotDto {

    public String[] ids;

    public String workNoticeId;
}
