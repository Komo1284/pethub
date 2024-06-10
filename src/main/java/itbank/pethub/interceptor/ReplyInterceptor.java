package itbank.pethub.interceptor;

import itbank.pethub.service.BoardService;
import itbank.pethub.vo.BoardVO;
import itbank.pethub.vo.MemberVO;
import itbank.pethub.vo.ReplyVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class ReplyInterceptor implements HandlerInterceptor {

    private BoardService bs;

    public ReplyInterceptor(BoardService boardService) {
        this.bs = boardService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        MemberVO user = (MemberVO) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("/member/login");
            return false;
        }

        if (user.getRole() == 1 || user.getRole() == 2) {
            return true;
        }

        String uri = request.getRequestURI();
        int replyId = Integer.parseInt(uri.substring(uri.lastIndexOf('/') + 1));
        ReplyVO reply = bs.getReply(replyId);


        if (reply.getMember_id() != user.getId()) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>alert('댓글 작성자만 수정/삭제할 수 있습니다.'); history.back();</script>");
            return false;
        }

        return true;
    }
}


