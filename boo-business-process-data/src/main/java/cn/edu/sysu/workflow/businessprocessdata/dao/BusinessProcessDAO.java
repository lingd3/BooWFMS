package cn.edu.sysu.workflow.businessprocessdata.dao;

import cn.edu.sysu.workflow.common.entity.BusinessProcess;

import java.util.List;

/**
 * 业务流程数据库操作
 *
 * @author Skye
 * Created on 2020/1/1
 */
public interface BusinessProcessDAO {

    /**
     * 创建业务流程
     *
     * @param businessProcess <ul>
     *                        <li>businessProcessId</li>
     *                        <li>businessProcessName</li>
     *                        <li>mainBusinessObjectName</li>
     *                        <li>creatorId</li>
     *                        <li>launchCount</li>
     *                        <li>successCount</li>
     *                        <li>averageCost</li>
     *                        <li>status</li>
     *                        <li>lastLaunchTimestamp</li>
     *                        </ul>
     * @return 新增的数据量
     */
    int save(BusinessProcess businessProcess);

    /**
     * 更新业务流程，不允许更新业务流程Id
     *
     * @param businessProcess <ul>
     *                        <li>businessProcessId</li>
     *                        <li>businessProcessName</li>
     *                        <li>mainBusinessObjectName</li>
     *                        <li>creatorId</li>
     *                        <li>launchCount</li>
     *                        <li>successCount</li>
     *                        <li>averageCost</li>
     *                        <li>status</li>
     *                        <li>lastLaunchTimestamp</li>
     *                        </ul>
     * @return 修改的数据量
     */
    int update(BusinessProcess businessProcess);

    /**
     * 根据业务流程Id查找业务流程
     *
     * @param businessProcessId
     * @return
     */
    BusinessProcess findOne(String businessProcessId);

    /**
     * 根据创建者ID查找业务流程列表
     *
     * @param creatorId
     * @return
     */
    List<BusinessProcess> findBusinessProcessesByCreatorId(String creatorId);

    /**
     * 根据组织名查找业务流程列表
     *
     * @param organization
     * @return
     */
    List<BusinessProcess> findBusinessProcessesByOrganization(String organization);

    /**
     * 根据创建者ID和业务流程名称检查是否存在业务流程
     *
     * @param creatorId
     * @param processName
     * @return
     */
    Boolean checkBusinessProcessByCreatorIdAndProcessName(String creatorId, String processName);

}
