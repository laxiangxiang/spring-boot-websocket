package com.websocket.demo.intercepteor;

import com.websocket.demo.domain.SysUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SysUserLoginIntercepter implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        return false;
//        //得到请求的url
//        String url = request.getRequestURI();
//        //判断是否是公开 地址
//        //实际开发中需要公开 地址配置在配置文件中
//        //从配置中取逆名访问url
//        List<String> open_urls = ResourcesUtil.gekeyList("config/anonymousURL");
//        //遍历公开 地址，如果是公开 地址则放行
//        for(String open_url:open_urls){
//            if(url.indexOf(open_url)>=0){
//                //如果是公开 地址则放行
//                return true;
//            }
//        }
        //判断用户身份在session中是否存在
        HttpSession session = httpServletRequest.getSession();
        SysUser sysUser = (SysUser) session.getAttribute("activeUser");
        //如果用户身份在session中存在放行
        if (sysUser != null){
            return true;
        }
        //执行到这里拦截，跳转到登陆页面，用户进行身份认证
        httpServletRequest.getRequestDispatcher("/pages/jsp/login.jsp").forward(httpServletRequest,httpServletResponse);
        //如果返回false表示拦截不继续执行handler，如果返回true表示放行
        return false;
    }

    /**
     * //在执行handler返回modelAndView之前来执行
     //如果需要向页面提供一些公用 的数据或配置一些视图信息，使用此方法实现 从modelAndView入手
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("SysUserLoginIntercepter...postHandle");
    }

    /**
     * //执行handler之后执行此方法
     //作系统 统一异常处理，进行方法执行性能监控，在preHandle中设置一个时间点，在afterCompletion设置一个时间，两个时间点的差就是执行时长
     //实现 系统 统一日志记录
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("SysUserLoginIntercepter...afterCompletion");
    }
}
