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

<#include "common/common-style.ftl" />
</head>
<body class=layui-layout-body">
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

    <div class="layui-body">
        <!-- 顶部切换卡 -->
        <div class="layui-tab layui-tab-brief" lay-filter="top-tab" lay-allowClose="true" style="margin: 0;">
            <ul class="layui-tab-title">
                <i id="closeAll" class="layui-icon layui-icon-close" style="font-size: 25px; color: red;float: right;margin-top: 7px;margin-right: 7px;cursor:pointer;" title="关闭所有TAB"></i>
            </ul>
            <div class="layui-tab-content" style="padding: 0;"></div>
        </div>
    </div>

    <div class="layui-footer" align="center">
        <!-- 底部固定区域 -->
        © JAgileKit
    </div>
</div>

    <#include "common/common-script.ftl"/>
<script type="text/javascript" src="/static/toast/js/toast.script.js"></script>
<script type="text/javascript" src="/static/toast/js/notification.js"></script>
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
                url: '/findMenuByLogined',
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
                    $a.attr('tab-item', '');
                    $a.attr('lay-filter', 'menu-item');
                }
                let $li = $("<li>").append($a);
                if (node.level == 0) {
                    $li.attr('class', 'layui-nav-item');
                }
                if (node.nodes && node.nodes.length) {
                    $li.append(makeNodeList(node.nodes));
                }
                return $li;
            }

            function makeNodeList(nodes) {
                let maxUl = $("<ul>");
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
            $a.attr('data-url', '');
            $a.attr('data-id', 'default-index-id');
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

        // iframe切换动画
        function ani(id) {
            $("[iframe-id=" + id + "]").hide();
            $("[iframe-id=" + id + "]").fadeIn(127);
        }

        var tabfilter = 'top-tab';
        var tab = $('.layui-tab[lay-filter=' + tabfilter + ']').eq(0);
        var tabcontent = tab.children('.layui-tab-content').eq(0);
        var tabtitle = tab.children('.layui-tab-title').eq(0);

        /**
         * iframe自适应
         */
        $(window).resize(function () {
            //设置顶部切换卡容器度
            tabcontent.height($(this).height() - 60 - 41 - 44); //头部高度 顶部切换卡高度 底部高度
            //设置顶部切换卡容器内每个iframe高度
            tabcontent.find('iframe').each(function () {
                $(this).height(tabcontent.height());
            });
        }).resize();

        // 监听Tab切换，以改变地址hash值
        element.on('tab(' + tabfilter + ')', function () {
            try {
                var id = this.getAttribute('lay-id');
            } catch (e) {
                var id = null;
            }
            if (id != null) {
                location.hash = tabfilter + '=' + id;
                ani(id);
            }
        });

        element.on('nav(menu)', (elem) => {
            let isLink = elem.attr("lay-filter");
            if (isLink == 'menu-item') {
                openTab(elem);
            }
        });

        //点击菜单打开选项卡
        function openTab(elem) {
            var id = $(elem).attr('data-id');
            var title = $(elem).text();
            var url = $(elem).attr('data-url');

            var iframes = $("[lay-id=" + id + "]").length;
            if (iframes == 0) {
                // 不存在的情况
                var iframe = '<iframe';
                iframe += ' src="' + url + '" iframe-id="' + id + '"';
                iframe += ' style="width: 100%; height: ' + tabcontent.height() + 'px; border: 0px;"';
                iframe += '></iframe>';
                //顶部切换卡新增一个卡片
                element.tabAdd(tabfilter, {'title': title, 'content': iframe, 'id': id});
                ani(id);
            }
            // 添加记录
            location.hash = tabfilter + '=' + id;
            // 改变tab
            element.tabChange(tabfilter, id);
        }

        // 点击左侧链接的时候
        $('[tab-item]').bind('click', function () {
            var id = $(this).attr('data-id');
            var title = $(this).text();
            var url = $(this).attr('data-url');

            var iframes = $("[lay-id=" + id + "]").length;
            if (iframes == 0) {
                // 不存在的情况
                var iframe = '<iframe';
                iframe += ' src="' + url + '" iframe-id="' + id + '"';
                iframe += ' style="width: 100%; height: ' + tabcontent.height() + 'px; border: 0px;"';
                iframe += '></iframe>';
                //顶部切换卡新增一个卡片
                element.tabAdd(tabfilter, {'title': title, 'content': iframe, 'id': id});
                ani(id);
            }
            // 添加记录
            location.hash = tabfilter + '=' + id;
            // 改变tab
            element.tabChange(tabfilter, id);
        });

        // 顶部导航选择时
        $('[top-bar]').bind('click', function () {
            var id = $(this).attr('top-id');
            var lefts = $("[left-bar][left-id='" + id + "']").length;
            if (lefts != 0) {
                // top-bar有对应的left-bar的情况下
                $("[left-bar]").hide();
                $("[left-bar][left-id='" + id + "']").fadeIn(500);
            }
        });

        /**
         * 初始化点击侧边栏导航
         */
        var layid = location.hash.replace(/^#top-tab=/, '');
        // layui-this
        if (layid) {
            $('.layui-side-scroll').find('[data-id=' + layid + ']').eq(0).click();    // 根据传入的ID跳转
        } else {
            $('.layui-side-scroll').find('[data-url][data-id]').eq(0).click();    // 点击第一个
        }

        $('#closeAll').bind('click', () => {
            var tabtitle = $(".layui-tab-title li");
            var ids = new Array();
            $.each(tabtitle, function (i) {
                ids[i] = $(this).attr("lay-id");
            });
            tabDeleteAll(ids);
        });

        function tabDelete(id) {
            console.log("删除的TabID：" + id);
            element.tabDelete("top-tab", id);//删除
        }

        function tabDeleteAll(ids) {
            $.each(ids, function (i, item) {
                element.tabDelete("top-tab", item);
            })
        }
    });

    function showUserInfo(){
        layer.open({
            type: 2,
            skin: 'layui-layer-lan', //样式类名
            closeBtn: 1, //不显示关闭按钮
            anim: 2,
            shadeClose: true, //开启遮罩关闭
            title:'用户信息',
            area:['30%','50%'],
            content: '/showUserInfo'
        });
    }

    function confirmPwd(){
        layer.open({
            type: 2,
            skin: 'layui-layer-lan', //样式类名
            closeBtn: 1, //不显示关闭按钮
            anim: 2,
            shadeClose: true, //开启遮罩关闭
            title:'用户信息',
            area:['30%','30%'],
            content: '/confirmPwd'
        });
    }

    function openRegistry(){
        layer.open({
            type: 2,
            skin: 'layui-layer-lan', //样式类名
            closeBtn: 1, //不显示关闭按钮
            anim: 2,
            shadeClose: true, //开启遮罩关闭
            title:'资料修改',
            area:['30%','70%'],
            content: '/intoRegistry?operational=modify'
        });
    }

</script>

</@shiro.authenticated>
</body>