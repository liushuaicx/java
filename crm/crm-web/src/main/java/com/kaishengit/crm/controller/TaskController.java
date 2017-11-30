package com.kaishengit.crm.controller;

import com.kaishengit.crm.auth.ShiroUtils;
import com.kaishengit.crm.controller.exception.ForbiddenException;
import com.kaishengit.crm.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Task;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 待办事项
 * @author 刘帅
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/list")
    public String taskList (Model model) {

        //获得当前登录对象User
        User user = ShiroUtils.getCurrentUser();
        List<Task> taskList = taskService.findTaskByUserId(user.getId());

        model.addAttribute("taskList",taskList);

        return "task/list";
    }

    @GetMapping("/done/{id:\\d+}")
    public String done(HttpSession session, @PathVariable Integer id , Byte done) {

        checkTask(session, id);
        //更改状态
        taskService.updateStates(id,done);

        return "redirect:/task/list";
    }

    @GetMapping("/unDone/{id:\\d+}")
    public String unDone(HttpSession session, @PathVariable Integer id , Byte done) {
        checkTask(session,id);

        //更改为未读
        taskService.updateStatesToUnDone(id,done);
        return "redirect:/task/list";
    }
    @GetMapping("/1ist")
    public String unDoneList(Model model) {
        //获得当前登录对象User
        User user = ShiroUtils.getCurrentUser();
        List<Task> taskList = taskService.findTaskByUserIdAndUnDone(user.getId());

        model.addAttribute("taskList",taskList);

        return "task/undone";
    }
    /**
     * 创建新的 代办事项
     * @return
     */
    @GetMapping("/new/{id:\\d+}")
    public String newTask(@PathVariable(required = false) Integer id) {
        return "task/new";
    }

    @PostMapping("/new/{id:\\d+}")
    public String newTask(Integer userId,String title,String finishTime, String remindTime,@PathVariable(required = false) Integer id) {

        User user = ShiroUtils.getCurrentUser();

        Task task = new Task();
        task.setUserId(user.getId());
        task.setTitle(title);
        task.setFinishTime(finishTime);
        task.setRemindTime(remindTime);
        task.setCustId(id);
        taskService.newTask(task);

        return "redirect:/task/list";
    }

    @GetMapping("/{id:\\d+}/del")
    public String taskDel(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {

        taskService.taskDel(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/task/list";
    }


    /**
     *
     * @param session
     * @param id TaskId
     * @return
     */
    private Task checkTask(HttpSession session, @PathVariable Integer id) {

        User user = ShiroUtils.getCurrentUser();
        Task task = taskService.findById(id);
        if (task == null) {
            throw new NotFoundException("不存在");
        }
        if (!task.getUserId().equals(user.getId())) {

            throw new ForbiddenException("权限不足");
        }
        return task;
    }

}
