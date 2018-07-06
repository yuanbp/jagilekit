<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <title>JAgileKit</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="/static/images/java.ico"/>

    <link rel="stylesheet" href="/static/xadmin/css/xadmin.css">

    <style>
        #verify {
            width: 60%;
        }
    </style>
</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up">

    <div class="message">JAgileKit-管理登录</div>
    <div id="darkbannerwrap"></div>

    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <ul class="layui-tab-title">
            <li id="nameLogin" class="layui-this">账户登录</li>
            <li id="emailLogin">邮箱登录</li>
            <li id="phoneLogin">手机登录</li>
        </ul>
        <div class="layui-tab-content" style="height: 100px;">
            <div class="layui-tab-item layui-show">

            </div>
            <div class="layui-tab-item">

            </div>
            <div class="layui-tab-item">

            </div>

            <form id="loginForm" method="post" class="layui-form">
                <input id="loginName" name="loginName" placeholder="用户名" type="text" value="${loginName}" lay-verify="required" class="layui-input">
                <hr id="loginNameHr" class="hr15">
                <input id="pwd" name="pwd" placeholder="密码" type="password" value="${pwd}" lay-verify="required" class="layui-input">
                <hr class="hr15">
                <input id="verify" name="verify" placeholder="验证码" type="text" lay-verify="required" class="layui-input" style="float:left; display:inline">
                <img alt="验证码" onclick="this.src='/defaultKaptcha?d='+new Date()*1" src="/defaultKaptcha" style="float:left; display:inline; margin: 5px"/>
                <hr class="hr15">
                <input value="登录" id="loginButton" lay-submit lay-filter="login" style="width:45%;" type="submit">
                <input value="注册" id="registerButton" style="width:45%;margin-left: 8%;background-color: #01AAED;" type="button">
                <input type="hidden" id="loginType" name="loginType" value="${loginType}">
            </form>

        </div>
    </div>


    <hr class="hr20">
    <hr class="hr20">
    <hr class="hr20">

</div>

<script src="/static/jquery/jquery-3.3.1.min.js"></script>
<script src="/static/layui/layui.js" charset="utf-8"></script>
<script type="text/javascript" src="/static/xadmin/js/xadmin.js"></script>

<script>
    layui.use(['layer', 'jquery', 'form'], function () {
        let $ = layui.jquery
                , form = layui.form
                , layer = layui.layer;

        $('#loginForm').attr('action', '/do_Login');
        $('#loginType').val('nameLogin');

        $(() => {
            let phoneReg = /(\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;

            let status = '${status}';
            let msg = '${msg}';
            if (null != msg && undefined != msg && '' != msg) {
                layer.alert(msg, {
                    skin: 'layui-layer-lan',
                    icon: 5
                    , closeBtn: 0
                    , anim: 6
                });
            }

            let pageForm = document.querySelector('#loginForm');
            let loginName= document.querySelector('#loginName');
            let loginNameHr = document.querySelector('#loginNameHr');
            $('#nameLogin').click(() => {
                $('#loginName').removeAttr("placeholder");
                $('#loginName').attr("placeholder","用户名");
                $('#loginName').removeAttr("lay-verify");
                $('#loginName').attr("lay-verify","required");
                $('#loginType').val('nameLogin');
            });

            $('#emailLogin').click(() => {
                $('#loginName').removeAttr("placeholder");
                $('#loginName').attr("placeholder","邮箱");
                $('#loginName').removeAttr("lay-verify");
                $('#loginName').attr("lay-verify","required|email");
                $('#loginType').val('emailLogin');
            });

            $('#phoneLogin').click(() => {
                $('#loginName').removeAttr("placeholder");
                $('#loginName').attr("placeholder","手机号");
                $('#loginName').removeAttr("lay-verify");
                $('#loginName').attr("lay-verify","required|phone");
                $('#loginType').val('mobileLogin');
            });

            $('#loginButton').click(() => {

            });

            $('#registerButton').bind('click',()=>{
                layer.open({
                    type: 2,
                    skin: 'layui-layer-lan', //样式类名
                    closeBtn: 0, //不显示关闭按钮
                    anim: 2,
                    shadeClose: true, //开启遮罩关闭
                    title:'用户注册',
                    area:['30%','70%'],
                    content: '/intoRegistry'
                });
            });
        });
    });
</script>
</body>