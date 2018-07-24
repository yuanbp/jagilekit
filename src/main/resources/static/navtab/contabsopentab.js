// $('.n-open-tab-link').on('click', menuItem);

function menuItem(settings) {
    var dataUrl = $(this).attr('data-url'),
        dataIndex = $(this).data('data-id'),
        menuName = $.trim($(this).attr('data-title') ? $(this).attr('data-title') : $(this).text()),
        flag = true;
    var defaultSetting = {
        url: dataUrl,
        title: menuName
    };
    $.extend(defaultSetting, settings);


    // 获取标识数据
    if (defaultSetting.url == undefined || $.trim(defaultSetting.url).length == 0) return false;

    // 选项卡菜单已存在
    top.$('.J_menuTab').each(function () {
        if ($(this).data('id') == defaultSetting.url) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                scrollToTab(this);
                // 显示tab对应的内容区
                top.$('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == defaultSetting.url) {
                        $(this).show().siblings('.J_iframe').hide();
                        return false;
                    }
                });
            }
            top.$('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == defaultSetting.url) {
                    console.log($(this).attr('src', $(this).data('id')))
                    return false;
                }
            });

            flag = false;
            return false;
        }
    });

    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + defaultSetting.url + '">' + defaultSetting.title + ' <i class="fa fa-times-circle"></i></a>';
        top.$('.J_menuTab').removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + defaultSetting.url + '" frameborder="0" data-id="' + defaultSetting.url + '" seamless></iframe>';
        top.$('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);
        var freamindex = 'iframe' + dataIndex


        //显示loading提示
//            var loading = layer.load();
//
//            $('.J_mainContent iframe:visible').load(function () {
//                //iframe加载完成后隐藏loading提示
//                layer.close(loading);
//            });
        // 添加选项卡
        top.$('.J_menuTabs .page-tabs-content').append(str);
        scrollToTab(top.$('.J_menuTab.active'));
    }
    return false;
}


//滚动到指定选项卡
function scrollToTab(element) {
    var marginLeftVal = calSumWidth($(element).prevAll()),
        marginRightVal = calSumWidth($(element).nextAll());
    // 可视区域非tab宽度
    var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
    //可视区域tab宽度
    var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
    //实际滚动宽度
    var scrollVal = 0;
    if ($(".page-tabs-content").outerWidth() < visibleWidth) {
        scrollVal = 0;
    } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
        if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
            scrollVal = marginLeftVal;
            var tabElement = element;
            while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                scrollVal -= $(tabElement).prev().outerWidth();
                tabElement = $(tabElement).prev();
            }
        }
    } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
        scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
    }
    $('.page-tabs-content').animate({
        marginLeft: 0 - scrollVal + 'px'
    }, "fast");
}

//计算元素集合的总宽度
function calSumWidth(elements) {
    var width = 0;
    $(elements).each(function () {
        width += $(this).outerWidth(true);
    });
    return width;
}

var openTab = (function () {
    $('.n-open-tab-link').on('click', menuItem);


    function menuItem(settings) {
        var dataUrl = $(this).attr('href'),
            dataIndex = $(this).data('index'),
            menuName = $.trim($(this).attr('data-title') ? $(this).attr('data-title') : $(this).text()),
            flag = true;
        var defaultSetting = {
            url: dataUrl,
            title: menuName
        };
        $.extend(defaultSetting, settings);


        // 获取标识数据
        if (defaultSetting.url == undefined || $.trim(defaultSetting.url).length == 0) return false;

        // 选项卡菜单已存在
        top.$('.J_menuTab').each(function () {
            if ($(this).data('id') == defaultSetting.url) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                    scrollToTab(this);
                    // 显示tab对应的内容区
                    top.$('.J_mainContent .J_iframe').each(function () {
                        if ($(this).data('id') == defaultSetting.url) {
                            $(this).show().siblings('.J_iframe').hide();
                            return false;
                        }
                    });
                }
                top.$('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == defaultSetting.url) {
                        console.log($(this).attr('src', $(this).data('id')))
                        return false;
                    }
                });

                flag = false;
                return false;
            }
        });

        // 选项卡菜单不存在
        if (flag) {
            var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + defaultSetting.url + '">' + defaultSetting.title + ' <i class="fa fa-times-circle"></i></a>';
            top.$('.J_menuTab').removeClass('active');

            // 添加选项卡对应的iframe
            var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + defaultSetting.url + '" frameborder="0" data-id="' + defaultSetting.url + '" seamless></iframe>';
            top.$('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);
            var freamindex = 'iframe' + dataIndex


            //显示loading提示
//            var loading = layer.load();
//
//            $('.J_mainContent iframe:visible').load(function () {
//                //iframe加载完成后隐藏loading提示
//                layer.close(loading);
//            });
            // 添加选项卡
            top.$('.J_menuTabs .page-tabs-content').append(str);
            scrollToTab(top.$('.J_menuTab.active'));
        }
        return false;
    }


    //滚动到指定选项卡
    function scrollToTab(element) {
        var marginLeftVal = calSumWidth($(element).prevAll()),
            marginRightVal = calSumWidth($(element).nextAll());
        // 可视区域非tab宽度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        if ($(".page-tabs-content").outerWidth() < visibleWidth) {
            scrollVal = 0;
        } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
            if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
                scrollVal = marginLeftVal;
                var tabElement = element;
                while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                    scrollVal -= $(tabElement).prev().outerWidth();
                    tabElement = $(tabElement).prev();
                }
            }
        } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
            scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
        }
        $('.page-tabs-content').animate({
            marginLeft: 0 - scrollVal + 'px'
        }, "fast");
    }

    //计算元素集合的总宽度
    function calSumWidth(elements) {
        var width = 0;
        $(elements).each(function () {
            width += $(this).outerWidth(true);
        });
        return width;
    }

    return {
        openTab: menuItem
    }
})();