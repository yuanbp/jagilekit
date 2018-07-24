<#include "common/_layout.ftl">

<@layout page_tab="confirmPwd_index">

<div class="layui-col-md6">
    <div class="layui-card">
        <div class="layui-card-header"></div>
        <div class="layui-card-body">
            <form id="confirmPwdForm" class="layui-form layui-form-pane" lay-filter="confirmPwdForm" action="/confirmPwd">
                <div class="layui-form-item">
                    <label class="layui-form-label">当前密码</label>
                    <div class="layui-input-inline">
                        <input type="password" id="pwd" name="pwd" value="${user.pwd}" lay-verify="required|min6" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item" align="right">
                    <input type="submit" id="submitbtn" class="layui-btn" lay-submit="" lay-filter="checkPwd" value="提交" style="margin-right: 25%;">
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    layui.use(['layer', 'jquery', 'form', 'element'], function () {
        let $ = layui.jquery
                , form = layui.form
                , layer = layui.layer
                , element = layui.element;

        $(() => {
            $('div .layui-form-item').attr('style', 'margin-left: 10%');
            form.verify({
                min6: (value) => {
                    if (value.length < 6) {
                        return "长度不得小于6个字符";
                    }
                }
            });

            form.on('submit(checkPwd)', function (data) {
                $.ajax({
                    url: '/checkPwd',
                    data: data.field,
                    type: 'POST',
                    dataType: 'text',
                    async: false,
                    cache: false,
                    success: function (result) {
                        result = JSON.parse(result);
                        if (result == 'yes') {
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.openRegistry();
                        } else {
                            layer.alert('校验失败', {icon: 2});
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        layer.alert('校验过程中出现异常', {icon: 2});
                    }
                });
                return false;
            });
        });

    });
</script>
</@layout>