package com.chieftain.agile.common.datasource;

import java.io.Serializable;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * com.chieftain.junite.common.druid [workset_idea_01]
 * Created by Richard on 2018/5/9
 *
 * @author Richard on 2018/5/9
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
    initParams = {
        @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
    }
)
public class DruidStatFilter extends WebStatFilter implements Serializable {

    private static final long serialVersionUID = 6470232633173234068L;
}
