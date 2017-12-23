<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!-- 左侧菜单栏 -->
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

        <!-- 菜单 -->
        <ul class="sidebar-menu">
            <li class="header">系统功能</li>

            <li class="${param.menu == 'home' ? 'active' : ''}"><a href="/home"><i class="fa fa-home"></i> <span>首页</span></a></li>

            <li class="treeview ${fn:startsWith(param.menu, 'base_') ? 'active' : ''}">
                <a href="/scenic/">
                    <i class="fa fa-address-book-o"></i> <span>基本信息管理</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'base_scenic'? 'active' : ''}"><a href="/scenic/"><i class="fa fa-circle-o"></i> 景区信息</a></li>
                    <li class="${param.menu == 'base_ticket'? 'active' : ''}"><a href="/ticket/"><i class="fa fa-circle-o"></i> 售票点信息</a></li>
                </ul>
            </li>
            <!-- 工作记录 -->
            <li class="treeview ${fn:startsWith(param.menu, 'sale_') ? 'active' : ''}">
                <a href="#">
                    <i class="fa fa-bars"></i> <span>综合办公</span>
                    <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
                </a>
                <ul class="treeview-menu">
                    <li class="${param.menu == 'sale_my'? 'active' : ''}"><a href="/"><i class="fa fa-circle-o"></i> 电子公告</a></li>
                    <li class="${param.menu == 'sale_my'? 'active' : ''}"><a href="/"><i class="fa fa-circle-o"></i> 消息中心</a></li>
                    <li class="${param.menu == 'sale_my'? 'active' : ''}"><a href="/"><i class="fa fa-circle-o"></i> 规章制度</a></li>
                </ul>
            </li>
            <!-- 待办事项 -->
            <li class="${param.menu == 'task' ? 'active' : ''}"><a href="/"><i class="fa fa-calendar"></i> <span>待办事项</span></a></li>
            <!-- 统计报表 -->

            <li class="${param.menu == 'charts_list'? 'active' : ''}"><a href="/"><i class="fa fa-pie-chart"></i><span> 统计报表</span></a></li>


            <shiro:hasRole name="超级管理员">
            <li class="header">系统管理</li>
            <!-- 部门员工管理 -->
            <li class="${param.menu == 'list' ? 'active' : ''}"><a href="/account/list"><i class="fa fa-users"></i> <span>用户管理</span></a></li>
            </shiro:hasRole>

        </ul>
    </section>
    <!-- /.sidebar -->
</aside>