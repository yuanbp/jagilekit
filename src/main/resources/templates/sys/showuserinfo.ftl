<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <title>JAgileKit 快速开发平台</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="/static/images/java.ico"/>

    <link rel="stylesheet" href="/static/toast/css/toast.style.min.css">
    <link rel="stylesheet" href="/static/layui/css/layui.css"/>
</head>
<body class=layui-layout-body">

<div class="layui-col-md6">
    <div class="layui-card">
        <div class="layui-card-header"></div>
        <div class="layui-card-body">
            <form id="registryForm" class="layui-form layui-form-pane" action="/registryUser">
                <div class="layui-form-item">
                    <label class="layui-form-label">登录名</label>
                    <div class="layui-input-inline">
                        <input type="text" id="loginname" name="loginname" value="${user.loginname}" lay-verify="required|checkExist" placeholder="请输入" autocomplete="on" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">真实姓名</label>
                    <div class="layui-input-inline">
                        <input type="text" id="username" name="username" value="${user.username}" lay-verify="required" placeholder="请输入" autocomplete="on" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">手机号</label>
                    <div class="layui-input-inline">
                        <input type="text" id="mobile" name="mobile" value="${user.mobile}" lay-verify="required|phone|checkExist" placeholder="请输入" autocomplete="on" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-left: 100px;">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-inline">
                        <input type="text" id="email" name="email" value="${user.email}" lay-verify="required|email|checkExist" placeholder="请输入" autocomplete="on" class="layui-input">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script src="/static/layui/layui.js"></script>
<script>
    layui.use(['layer', 'jquery', 'form', 'element'], function () {
        let $ = layui.jquery
                , form = layui.form
                , layer = layui.layer
                , element = layui.element;

        $(() => {
            $('div .layui-form-item').attr('style', 'margin-left: 10%');
            $('input').attr('readonly','readonly');
        });
    });
</script>
</body>
</html>