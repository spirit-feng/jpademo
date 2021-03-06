package com.sephiroth.jpademo.commtools.helper;

import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 吴占超
 * @Description:
 * @Date: Create in 11:41 2018/2/8
 * @Modified By:
 */
public class WebAppContextHelper {

    /**
     *  @Author: 吴占超
     *  @Description: 获取注入jpa dao
     *  @Date:  11:45 2018/2/8
     *  @param tClass
     *  @param request
     *  @return
     *
     */
    public <T> T getJPADAO(Class<T> tClass, HttpServletRequest request) {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()).getBean(tClass);
    }

    /**
     * 获取客户端ip地址
     * @param request
     * @return
     */
    public static String getCliectIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }

    /**
     * 判断是否为ajax请求
     * @param request
     * @return
     */
    public static String getRequestType(HttpServletRequest request) {
        return request.getHeader("X-Requested-With");
    }
}
