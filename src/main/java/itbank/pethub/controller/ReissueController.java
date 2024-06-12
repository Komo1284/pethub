package itbank.pethub.controller;

import io.jsonwebtoken.ExpiredJwtException;
import itbank.pethub.jwt.JWTUtil;
import itbank.pethub.model.MemberDAO;
import itbank.pethub.vo.RefreshVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequiredArgsConstructor
public class ReissueController {

    private final JWTUtil jwtUtil;
    private final MemberDAO dao;



    @PostMapping("/reissue")
    public ModelAndView reissue(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView mav = new ModelAndView();
        String msg = "";

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        if (refresh == null) {

            msg = "refresh token is Null";

            System.out.println(msg);

            mav.setViewName("redirect:/reissue");


            return mav;
        }

        //expired check
        try {
            if (jwtUtil.isExpired(refresh)) {
                msg = "refresh token is expired";

                System.out.println(msg);

                mav.setViewName("redirect:/reissue");


                return mav;
            };
        } catch (ExpiredJwtException e) {

            return mav;
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            msg = "this token is not refresh";

            System.out.println(msg);

            mav.setViewName("redirect:/reissue");


            return mav;
        }

        //DB에 저장되어 있는지 확인
        int isExist = dao.existsByRefresh(refresh);
        if (isExist == 0) {

            //response body
            mav.setViewName("redirect:/reissue");
            return mav;
        }

        String userid = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", userid, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", userid, role, 86400000L);

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        RefreshVO refreshVO = new RefreshVO();
        refreshVO.setRefresh(newRefresh);
        refreshVO.setUserid(userid);

        dao.updateByRefresh(refreshVO);


        //response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));
        mav.setViewName("redirect:/member/login");

        return mav;
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
