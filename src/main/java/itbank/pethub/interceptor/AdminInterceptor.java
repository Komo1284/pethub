package itbank.pethub.interceptor;

import itbank.pethub.vo.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MemberVO user = (MemberVO) request.getSession().getAttribute("user");
        String requestURI = request.getRequestURI();

        // /board/write로 요청이 들어왔고, HTTP 메서드가 POST인 경우에만 인터셉터 작동
        if ("POST".equals(request.getMethod()) && requestURI.contains("/board/write")) {
            // 카테고리 값 가져오기
            String category = request.getParameter("type");
            // 카테고리가 "공지사항"인 경우에만 인터셉터 작동
            if ("1".equals(category)) {
                // 유저가 관리자인 경우에만 허용
                if (user != null && user.getRole() == 1) {
                    return true;
                } else if (user == null) {
                    response.sendRedirect("/login");
                    return false;
                } else {
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write("<script>alert('공지사항은 관리자만 작성할 수 있습니다.'); history.back();</script>");
                    return false;
                }
            } else {
                // 다른 카테고리인 경우에는 인터셉터 통과
                return true;
            }
        }
        // POST 요청이 아니거나 /board/write가 아닌 경우에는 인터셉터 통과
        return true;
    }
}
