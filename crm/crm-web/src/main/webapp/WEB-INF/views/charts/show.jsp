<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>凯盛软件CRM-客户级别统计</title>
    <%@ include file="../include/css.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<!-- Site wrapper -->
<div class="wrapper">
    <%@include file="../include/header.jsp"%>
    <!-- =============================================== -->

    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="charts_list"/>
    </jsp:include>
    <!-- 右侧内容部分 -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box">
                <div class="box-header with-border">
                    <h3 class="box-title">客户级别数量统计</h3>
                </div>
                <div class="box-body">
                    <div id="bar" style="height: 300px;width: 100%"></div>
                </div>
            </div>
            <%--折线图--%>
            <div class="box-header with-border">
                <h3 class="box-title">销售进度</h3>
            </div>
            <div class="box-body">
                <div id="line" style="height: 300px;width: 100%"></div>
            </div>
        </section>


            <%--漏斗图--%>
            <div class="box-header with-border">
                <h3 class="box-title">销售进度</h3>
            </div>
            <div class="box-body">
                <div id="funnel" style="height: 300px;width: 50%"></div>
            </div>



        <!-- /.content -->

    </div>
    <!-- /.content-wrapper -->

    <%@ include file="../include/footer.jsp"%>

</div>
<!-- ./wrapper -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/charts/echarts.min.js"></script>
<script src="/static/plugins/layer/layer.js"></script>
<script>
    $(function () {
        var bar = echarts.init(document.getElementById("bar"));

        var option = {
            title: {
                text: "客户级别数量统计",
                left: 'center'
            },
            tooltip: {},
            legend: {
                data: ['人数'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                data: []
            },
            yAxis: {},
            series: {
                name: "人数",
                type: 'bar',
                data:[]
            }
        }
        bar.setOption(option);

        $.get("/charts/lever.json").done(function (resp) {

            if (resp.state = 'success') {

                var nameArray = [];
                var valueArray = [];

                var dataArray = resp.data;
                for (var i= 0 ; i < dataArray.length ; i++ ) {
                    var obj = dataArray[i];
                    nameArray.push(obj.LEVEL);
                    valueArray.push(obj.count);
                }

                bar.setOption({
                    xAxis:{
                        data:nameArray
                    },
                    series:{
                        data:valueArray
                    }
                })
            }else {
                layer.msg(resp.message)
            }

        }).error(function () {
            layer.msg("数据加载异常");
        });

        //漏斗图
        var funnel = echarts.init(document.getElementById("funnel"));

        option = {

            tooltip: {
                trigger: 'item'
            },
            toolbox: {
                feature: {
                    dataView: {readOnly: false},
                    restore: {},
                    saveAsImage: {}
                }
            },
            legend: {
                data: []
            },
            calculable: true,
            series: [
                {
                    name:'漏斗图',
                    type:'funnel',
                    left: '10%',
                    top: 60,
                    //x2: 80,
                    bottom: 60,
                    width: '80%',
                    // height: {totalHeight} - y - y2,

                    sort: 'descending',
                    gap: 2,
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        },
                        emphasis: {
                            textStyle: {
                                fontSize: 20
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            length: 10,
                            lineStyle: {
                                width: 1,
                                type: 'solid'
                            }
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    },
                    data: [ ]
                }
            ]
        };
        funnel.setOption(option);

        $.get("/charts/progress.json").done(function (resp) {

            if (resp.state == 'success') {

                var nameArray = [];
                var Array = [];

                var dataArray = resp.data;
                for (var i= 0 ; i < dataArray.length ; i++ ) {
                    var obj = dataArray[i];
                    Array.push({name:obj.progress,value:obj.count});
                    nameArray.push(obj.progress);
                }
                funnel.setOption({
                    legend: {
                        data: nameArray
                    },
                    series: [{
                        data: Array
                    }]
                })
            }else {
                layer.msg(resp.message)
            }

        }).error(function () {
            layer.msg("加载漏斗图失败")
        })

        //折线图
        var line = echarts.init(document.getElementById("line"));

        var option = {
            title: {
                text: "客户数量统计",
                left: 'center'
            },
            tooltip: {},
            legend: {
                data: ['人数'],
                left: 'right'
            },
            xAxis: {
                type: 'category',
                data: []
            },
            yAxis: {},
            series: {
                name: "人数",
                type: 'line',
                data:[]
            }
        }
        line.setOption(option);
        $.get("/charts/count.json").done(function (resp) {
            if (resp.state = 'success') {
                var nameArray = [];
                var valueArray = [];

                var dataArray = resp.data;
                for (var i= 0 ; i < dataArray.length ; i++ ) {
                    var obj = dataArray[i];
                    nameArray.push(obj.month+'月份');
                    valueArray.push(obj.count);
                }

                line.setOption({
                    xAxis:{
                        data:nameArray
                    },
                    series:{
                        data:valueArray
                    }
                })

            }else {
                layer.msg(resp.message)
            }


        }).error(function () {
            layer.msg("加载客户数据失败");
        })

    });
</script>

</body>
</html>
