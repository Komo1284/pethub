package itbank.pethub.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import itbank.pethub.dto.CustomOAuth2User;
import itbank.pethub.dto.CustomUserDetails;
import itbank.pethub.dto.MemberDetails;
import itbank.pethub.dto.UserDTO;
import itbank.pethub.entity.UserEntity;
import itbank.pethub.vo.MemberVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 access키에 담긴 토큰을 꺼냄
        String accessToken = request.getHeader("access");

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {

            filterChain.doFilter(request, response);

            return;
        }

// 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        String jwttype = jwtUtil.getCategory(accessToken);

        if (!jwttype.equals("access")) {

            //response body
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            //response status code
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // username, role 값을 획득
        String userid = jwtUtil.getUsername(accessToken);
        String role = jwtUtil.getRole(accessToken);

        MemberVO memberVO = new MemberVO();
        memberVO.setUserid(userid);
        memberVO.setRole(role);
        MemberDetails memberDetails = new MemberDetails(memberVO);


        //userDTO를 생성하여 값 set
        UserDTO userDTO = new UserDTO();
        userDTO.setUserid(userid);
        userDTO.setRole(role);

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(memberVO);

        Authentication authToken2 = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());


        Authentication authToken = new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        SecurityContextHolder.getContext().setAuthentication(authToken2);


        filterChain.doFilter(request, response);
    }
}

