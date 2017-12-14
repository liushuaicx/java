package com.kaishengit.crm.controller;

import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.entity.User;
import com.kaishengit.crm.exception.ServiceException;
import com.kaishengit.crm.service.UserService;
import com.kaishengit.web.result.AjaxResult;
import com.kaishengit.web.result.DataTablesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘帅
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String list() {
        return "user/list";
    }

    @GetMapping("/list.json")
    @ResponseBody
    public DataTablesResult<User> list(Integer draw , Integer start, Integer length, Integer deptId,
                                       HttpServletRequest request) {
        String keyword = request.getParameter("search[value]");
        Map<String,Object> queryParam = new HashMap<>();
        queryParam.put("start",start);
        queryParam.put("length",length);
        queryParam.put("userName",keyword);
        queryParam.put("deptId",deptId);

        List<User> userList = userService.pageForUser(queryParam);
        Long total = userService.userCountByDeptId(deptId);
        return new DataTablesResult<>(draw,total.intValue(),userList);
    }

    @GetMapping("/dept.json")
    @ResponseBody
    public List<Dept> deptList() {
        List<Dept> deptList = userService.finAllDept();
        return deptList;
    }

    @PostMapping("/dept/add")
    @ResponseBody
    public AjaxResult addDept(String deptName) {

        try {
            userService.addDept(deptName);
            return AjaxResult.success();
        }catch (ServiceException ex) {
            return AjaxResult.error(ex.getMessage());
        }
    }

    @PostMapping("/addUser")
    @ResponseBody
    public AjaxResult addUser(String userName,String mobile,String password,Integer[] deptId) {

        try {
            userService.addUser(userName, mobile, password, deptId);
            return AjaxResult.success();
        }catch (ServiceException ex) {
            return AjaxResult.error(ex.getMessage());
        }
    }

    @PostMapping("/{id:\\d+}/delete")
    @ResponseBody
    public AjaxResult deleteUser(@PathVariable Integer id) {

        userService.deleteUserById(id);
        return AjaxResult.success();
    }

}
