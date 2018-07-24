<#include "common/_layout.ftl">
<@layout page_tab="main_index">
<#--<@shiro.hasPermission name="admin:add"></@shiro.hasPermission>-->
<#--<@shiro.hasRole name="basic"></@shiro.hasRole>-->
    <@shiro.authenticated>
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <div class="layui-logo">JAgileKit 快速开发平台</div>
            <!-- 头部区域（可配合layui已有的水平导航） -->
            <ul class="layui-nav layui-layout-left">

            </ul>
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item">
                    <a href="javascript:;">其它功能</a>
                    <dl class="layui-nav-child">
                        <dd><a href="">邮件管理</a></dd>
                        <dd><a href="">消息管理</a></dd>
                        <dd><a href="">授权管理</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <span id="userInfoSp">${loginName}</span> [用户信息]
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:showUserInfo();">基本资料</a></dd>
                        <dd><a href="javascript:confirmPwd();">资料修改</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item"><a href="/doLogout">注销</a></li>
            </ul>
        </div>

        <div class="layui-side layui-bg-black">
            <div class="layui-side-scroll">
                <!-- 左侧导航区域（可配合layui已有的垂直导航） -->

            </div>
        </div>

    <#--<div class="layui-body" id="container">
        <!-- 顶部切换卡 &ndash;&gt;
        <div class="layui-tab layui-tab-brief" lay-filter="top-tab" lay-allowClose="true" style="margin: 0;">
            <ul class="layui-tab-title">
                <i id="closeAll" class="layui-icon layui-icon-close" style="font-size: 25px; color: red;float: right;margin-top: 7px;margin-right: 7px;cursor:pointer;" title="关闭所有TAB"></i>
            </ul>
            <div id="tabContent" class="layui-tab-content" style="padding: 0;"></div>
        </div>
    </div>-->

        <div class="layui-tab" lay-filter="demo" lay-allowclose="true" style="margin-left: 200px;">
            <ul class="layui-tab-title">
            </ul>
            <ul class="rightmenu" style="display: none;position: absolute;background-color: #009E94;">
                <li data-type="closethis"><a href="javascript:;" style="cursor:hand"><font color="#4b0082">X 关闭当前</font></a></li>
                <li data-type="closeall"><a href="javascript:;" style="cursor:hand"><font color="#4b0082">X 关闭所有</font></a></li>
            </ul>
            <div class="layui-tab-content">
            </div>
        </div>

        <div class="layui-footer" align="center">
            <!-- 底部固定区域 -->
            © JAgileKit
        </div>
    </div>

    <script type="text/javascript" src="/static/navtab/laynavtab.js"></script>
    <script>
        //JavaScript代码区域
        layui.use(['layer', 'jquery', 'form', 'element'], function () {
            let $ = layui.jquery
                    , form = layui.form
                    , layer = layui.layer
                    , element = layui.element;

            $(() => {
                $.ajax({
                    url: '/userInfo',
                    type: 'post',
                    data: {'param': 'none'},
                    dataType: 'json',
                    success: (data) => {
                        let jsonObj = JSON.parse(data);
                        let jsonStr = JSON.stringify(jsonObj);
                        $('#userInfoSp').text(jsonObj.loginName);
                    },
                    error: (jqXHR, textStatus, errorThrown) => {
                        layer.alert('获取登陆用户名异常.', {
                            skin: 'layui-layer-lan',
                            icon: 5
                            , closeBtn: 0
                            , anim: 6
                        });
                    }
                });

                $.ajax({
                    url: '/menu/findMenuByLogined',
                    type: 'post',
                    data: {'param': 'none'},
                    dataType: 'json',
                    success: (data) => {
                        element.init();
                        let jsonObj = JSON.parse(data);
                        let jsonStr = JSON.stringify(jsonObj);
                        let menuTree = makeMenuTree(jsonObj);
                        handleMenuTree(menuTree);
                        $(menuTree[0].firstChild).prepend(defaultMenuItem());
                        $('div [class="layui-side-scroll"]').append(menuTree);
                        element.render();
                    },
                    error: (jqXHR, textStatus, errorThrown) => {
                        layer.alert('获取菜单数据异常.', {
                            skin: 'layui-layer-lan',
                            icon: 5
                            , closeBtn: 0
                            , anim: 6
                        });
                    }
                });
            });

            function makeMenuTree(roots) {
                function makeNode(node) {
                    let $i = $('<i>');
                    $i.attr('class', node.icon);
                    $i.attr('style', 'margin-right:10px;');
                    let $a = $("<a>").text(node.menuname || "").prepend($i);
                    $a.attr('href', 'javascript:;');
                    if (node.level != '0') {
                        $a.attr('data-url', node.menuurl);
                        $a.attr('data-id', node.menuid);
                        $a.attr('data-title', node.menuname);
                        $a.attr('tab-item', '');
                        $a.attr('lay-filter', 'menu-item');
                        $a.attr('kit-target', '');
                        $a.attr('class', 'site-demo-active');
                    }
                    let $li = $("<li>").append($a);
                    $li.attr('mid', 'tab' + node.menuid);
                    $li.attr('funurl', node.menuurl);
                    if (node.level == 0) {
                        $li.attr('class', 'layui-nav-item');
                    }
                    if (node.nodes && node.nodes.length) {
                        $li.append(makeNodeList(node.nodes));
                    }
                    return $li;
                }

                function makeNodeList(nodes) {
                    let maxUl = $("<ul id='menuSideBar'>");
                    maxUl.attr('class', 'layui-nav-child');
                    return nodes
                            .map(child => makeNode(child))
                            .reduce(($ul, $li) => {
                                return $ul.append($li);
                            }, maxUl);
                }

                return makeNodeList(roots);
            }

            function defaultMenuItem() {
                let $li = $("<li>");
                $li.attr('class', 'layui-nav-item');
                let $i = $('<i>');
                $i.attr('class', 'layui-icon layui-icon-home');
                $i.attr('style', 'margin-right:10px;');
                let $a = $("<a>").text('后台首页').prepend($i);
                $a.attr('href', 'javascript:;');
                $a.attr('data-url', '/welcome');
                $a.attr('data-id', 'default-index-id');
                $a.attr('data-title', 'welcome');
                $a.attr('tab-item', '');
                $a.attr('lay-filter', 'menu-item');
                $li.append($a);
                return $li;
            }

            function handleMenuTree(menuTree) {
                $(menuTree).removeAttr('class');
                $(menuTree).removeAttr('lay-filter');
                $(menuTree).attr('class', 'layui-nav layui-nav-tree');
                $(menuTree).attr('lay-filter', 'menu');
            }
        });

        function showUserInfo() {
            layer.open({
                type: 2,
                skin: 'layui-layer-lan', //样式类名
                closeBtn: 1, //不显示关闭按钮
                anim: 2,
                shadeClose: true, //开启遮罩关闭
                title: '用户信息',
                area: ['30%', '50%'],
                content: '/showUserInfo'
            });
        }

        function confirmPwd() {
            layer.open({
                type: 2,
                skin: 'layui-layer-lan', //样式类名
                closeBtn: 1, //不显示关闭按钮
                anim: 2,
                shadeClose: true, //开启遮罩关闭
                title: '用户信息',
                area: ['30%', '30%'],
                content: '/confirmPwd'
            });
        }

        function openRegistry() {
            layer.open({
                type: 2,
                skin: 'layui-layer-lan', //样式类名
                closeBtn: 1, //不显示关闭按钮
                anim: 2,
                shadeClose: true, //开启遮罩关闭
                title: '资料修改',
                area: ['30%', '70%'],
                content: '/intoRegistry?operational=modify'
            });
        }

    </script>

    </@shiro.authenticated>

</@layout>