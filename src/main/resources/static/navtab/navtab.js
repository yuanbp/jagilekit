layui.use(['layer', 'jquery', 'form', 'element'], function () {
    let $ = layui.jquery
        , form = layui.form
        , layer = layui.layer
        , element = layui.element;

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
        var id = null;
        try {
            id = this.getAttribute('lay-id');
        } catch (e) {
            id = null;
        }
        if (id != null) {
            location.hash = tabfilter + '=' + id;
            ani(id);
        }
        element.render();
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
            alert(iframe);
            //顶部切换卡新增一个卡片
            element.tabAdd(tabfilter, {'title': title, 'content': iframe, 'id': id});
            ani(id);
        }
        // 添加记录
        location.hash = tabfilter + '=' + id;
        // 改变tab
        element.tabChange(tabfilter, id);
        element.render();
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
            alert(item);
            element.tabDelete("top-tab", item);
        });
    }

    element.on('tabDelete()', function(data){
        console.log(this); //当前Tab标题所在的原始DOM元素
        console.log(data.index); //得到当前Tab的所在下标
        console.log(data.elem); //得到当前的Tab大容器
        $('#tabContent').removeChild(data.elem);
        element.render();
    });
});