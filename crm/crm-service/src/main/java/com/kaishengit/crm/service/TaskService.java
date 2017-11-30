package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Task;

import java.util.List;

/**
 * @author 刘帅
 */
public interface TaskService {


    /**
     * 无条件查询
     * @param id
     * @return
     */
    List<Task> findTaskByUserId(Integer id);

    /**
     * 查询带CustName
     * @param id
     * @return
     */
    List<Task> findTaskWithCustNameByUserId(Integer id);

    /**
     * 查询带SaleName
     * @param id
     * @return
     */
    List<Task> findTaskWithSaleNameByUserId(Integer id);

    /**
     * 通过id查找Task对象
     * @param id
     * @return
     */
    Task findById(Integer id);

    /**
     * 更改状态为done
     * @param id
     * @param done
     */
    void updateStates(Integer id, Byte done);

    /**
     * 更改状态为UnDone
     * @param id
     * @param done
     */
    void updateStatesToUnDone(Integer id, Byte done);
    /**
     * 新增代办事项
     * @param task
     */
    void newTask(Task task);


    /**
     * 删除待办事项
     * @param id
     */
    void taskDel(Integer id);

    /**
     * 查询未完成列表
     * @param userId
     * @return
     */
    List<Task> findTaskByUserIdAndUnDone(Integer userId);
}
