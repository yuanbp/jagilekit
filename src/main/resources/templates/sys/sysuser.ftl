<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <title>Sysuser List</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="/static/images/java.ico" />

    <#include "../common/common-style.ftl" />
</head>
<body>
<div style="padding: 20px; background-color: #F2F2F2;">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">用户列表</div>
                <div class="layui-card-body">
                    <table class="layui-table" id="test">
                        <thead>
                        <tr>
                            <th id="headid">id</th>
                            <th>name</th>
                            <th>age</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list users as user>
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.age}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                    <div id="pageElem" name="pageElem"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<#include "../common/common-script.ftl"/>
<script>
    layui.use(['laypage', 'layer', 'jquery', 'form'], function () {
        let $ = layui.jquery
                , laypage = layui.laypage
                , form = layui.form
                , layer = layui.layer;

        laypage.render({
            elem: 'pageElem'
            , count: 100
            , layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
            , jump: function (obj) {
                console.log(obj)
            }
        });

        $(() => {

        });
    });

</script>

</body>
</html>