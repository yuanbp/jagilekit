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
                    <label class="layui-form-label">登录名
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="loginname" name="loginname" value="${user.loginname}" <#if operational == 'modify'>lay-verify="required"<#else>lay-verify="required|checkExist"</#if> placeholder="请输入" autocomplete="on" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">密码</label>
                    <div class="layui-input-inline">
                        <input type="password" id="pwd" name="pwd" value="${user.pwd}" lay-verify="required|min6" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">确认密码</label>
                    <div class="layui-input-inline">
                        <input type="password" id="confirmPwd" name="confirmPwd" value="${user.pwd}" lay-verify="required|regPwd|min6" placeholder="请输入" autocomplete="off" class="layui-input">
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
                        <input type="text" id="mobile" name="mobile" value="${user.mobile}" <#if operational == 'modify'>lay-verify="required|phone"<#else>lay-verify="required|phone|checkExist"</#if> placeholder="请输入" autocomplete="on" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item" style="margin-left: 100px;">
                    <label class="layui-form-label">邮箱</label>
                    <div class="layui-input-inline">
                        <input type="text" id="email" name="email" value="${user.email}" <#if operational == 'modify'>lay-verify="required|email"<#else>lay-verify="required|email|checkExist"</#if>placeholder="请输入" autocomplete="on" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item" align="right">
                    <input type="submit" id="submitbtn" class="layui-btn" lay-submit="" lay-filter="" value="提交" style="margin-right: 25%;">
                </div>
                <input type="hidden" name="id" value="${user.id}">
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
            let status = '${status}';
            let operational = '${operational}';
            if(operational != null && operational != ''){
                if(operational == 'modify'){
                    $('#loginname').prop('disabled','disabled');
                    $('#mobile').prop('disabled','disabled');
                    $('#email').prop('disabled','disabled');
                    $('#loginname').prop('class','layui-input layui-disabled');
                    $('#mobile').prop('class','layui-input layui-disabled');
                    $('#email').prop('class','layui-input layui-disabled');
                }
            }
            if(status != null && status != ''){
                switch (status){
                    case 'success':
                        layer.confirm('操作成功', {
                            btn: ['确定'],icon:1
                        }, function(e){
                            layer.close(e);
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        });
                        break;
                    case 'fail':
                        layer.confirm('操作失败', {
                            btn: ['确定'],icon:2
                        }, function(e){
                            layer.close(e);
                        });
                        break;
                    default:
                        break;
                }
            }
        });

        let formVerify = form.verify({
            regPwd: function (value) {
                //获取密码
                var pwd = $("#pwd").val();
                if (pwd != value) {
                    return '两次输入的密码不一致';
                }
            },
            min6: (value)=>{
                if(value.length < 6){
                    return "长度不得小于6个字符";
                }
            },
            checkExist: (value, obj)=>{
                let name = obj.name;
                let valueType = '';
                let tipMsg = '';
                if(name == 'loginName'){
                    valueType='nameLogin';
                    tipMsg = '登陆名';
                }
                if(name == 'mobile'){
                    valueType='mobileLogin';
                    tipMsg = '电话号码';
                }
                if(name == 'email'){
                    valueType='emailLogin';
                    tipMsg = '邮箱';
                }
                $.ajax({
                    url: '/checkExist',
                    data: {'checkValue': value,'valueType': valueType},
                    type: 'POST',
                    dataType: 'text',
                    async: false,
                    cache: false,
                    success: function (data) {
                        data = JSON.parse(data);
                        if (data == 'yes') {
                            tipMsg = tipMsg + '已被使用'
                        }else {
                            tipMsg = null;
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        layer.alert('校验过程中出现异常', {icon: 2});
                    }
                });
                if(tipMsg != null && tipMsg != ''){
                    return tipMsg;
                }
            }
        });
    });
</script>
</body>
</html>