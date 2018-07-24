<#include "common/_layout.ftl">
<@layout page_tab="menu_inde">
<div class="layui-card">
    <div class="layui-card-header">查询条件</div>
    <div class="layui-card-body">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="number" lay-verify="required|number" autocomplete="off" class="layui-input">
                </div>
            </div>
        </div>
    </div>
</div>
<div class="layui-card">
    <div class="layui-card-header">结果列表</div>
    <div class="layui-card-body">
        <table class="layui-table" id="test">
            <thead>
            <tr>
                <th>名称</th>
                <th>类型</th>
                <th>权限标识</th>
                <th>连接地址</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
                <#list permissions as item>
                <tr>
                    <td>${item.menuName}</td>
                    <td>
                        <#if "item.type == '0'">
                            菜单
                        <#elseIf "item.type == '1'">
                            按钮
                        <#elseIf "item.type == '2'">
                            权限标识
                        </#if>
                    </td>
                    <td>${item.permission}</td>
                    <td>${item.menuUrl}</td>
                    <td></td>
                </tr>
                </#list>
            </tbody>
        </table>
        <div id="pageElem" name="pageElem"></div>
    </div>
</div>
</@layout>