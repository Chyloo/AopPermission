package com.ropz.aopann.aop;

import com.ropz.aopann.token.AuthToken;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;


/**
 * @author ropz
 */

@Aspect
@Component
public class TokenAop {

    /**
     * Spring中使用@Pointcut注解来定义方法切入点
     *
     * @Pointcut 用来定义切点，针对方法  @Aspect 用来定义切面，针对类
     * 后面的增强均是围绕此切入点来完成的
     * 此处仅配置被我们刚才定义的注解：AuthToken修饰的方法即可
     */
    @Pointcut("@annotation(authToken)")
    public void doAuthToken(AuthToken authToken) {
    }

    /**
     * 权限判断
     */
    @Around(value = "doAuthToken(authToken)", argNames = "pjp,authToken")
    public Object deBefore(ProceedingJoinPoint pjp, AuthToken authToken) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //获取roleName
        String roleName = authToken.roleName();
        if ("".equals(roleName)) {
            String id = request.getParameter("id");
            System.out.println("登入ID:" + id);
            if (id == null || "".equals(id)) {
                return "请登入后再试！";
            }
            return pjp.proceed();
        } else {
            String role = request.getParameter("role");
            if (roleName.equals(role)) {
                return pjp.proceed();
            }
            return "无权限！";
        }
    }
}
