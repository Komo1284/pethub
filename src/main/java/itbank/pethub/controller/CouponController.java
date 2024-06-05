package itbank.pethub.controller;

import itbank.pethub.service.CouponService;
import itbank.pethub.vo.CouponVO;
import itbank.pethub.vo.MemberVO;
import itbank.pethub.vo.Member_CouponVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private CouponService cs;

    @GetMapping("/discount")
    public List<Member_CouponVO> getCoupons(HttpSession session) {

        MemberVO user = (MemberVO) session.getAttribute("user");
        int member_id=user.getId();

        return cs.getCouponsByMemberId(member_id);
    }

}