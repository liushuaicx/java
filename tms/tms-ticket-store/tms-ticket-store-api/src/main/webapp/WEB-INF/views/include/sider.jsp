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

            <li class="${param.menu == 'handle' ? 'active' : ''}"><a href="/store/handle"><i class="fa fa-shopping-basket"></i> <span>年票办理</span></a></li>

            <li class="${param.menu == 'payment' ? 'active' : ''}"><a href="/store/payment"><i class="fa fa-usd"></i> <span>年票续费</span></a></li>

            <li class="${param.menu == 'guashi' ? 'active' : ''}"><a href="/store/guashi"><i class="fa fa-yelp"></i> <span>年票挂失</span></a></li>

            <li class="${param.menu == 'restore' ? 'active' : ''}"><a href="/store/restore"><i class="fa fa-smile-o"></i> <span>年票解挂</span></a></li>

            <li class="${param.menu == 'replace' ? 'active' : ''}"><a href="/store/replace"><i class="fa fa-umbrella"></i> <span>年票补办</span></a></li>


        </ul>
    </section>
    <!-- /.sidebar -->
</aside>