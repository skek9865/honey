package project.honey.config;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // handler 종류 확인 => HandlerMethod 타입인지 체크
        // HandlerMethod가 아니면 그대로 진행
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 형 변환 하기
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // @MySequred가 있는 경우이므로, 세션이 있는지 체크
        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect("/auth/login");
            return false;
        }

        // 세션이 존재하면 유효한 유저인지 확인
        String userId = (String) session.getAttribute("user");
        if (userId == null) {
            response.sendRedirect("/auth/login");
            return false;
        }

        // 접근허가
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
