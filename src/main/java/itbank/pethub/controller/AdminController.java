package itbank.pethub.controller;

import itbank.pethub.service.AdminService;
import itbank.pethub.service.MemberService;
import itbank.pethub.vo.CouponVO;
import itbank.pethub.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService as;
    private final MemberService ms;

    @GetMapping("/product_registration")
    public void adminProductRegistration() {}

    @GetMapping("/manage_orders")
    public void adminManageOrders() {}

    @GetMapping("/insert")
    public void adminInsert() {}

    @PostMapping("/insert")
    public ModelAndView adminInsert(MemberVO input) {
        ModelAndView mav = new ModelAndView("admin/insert");
        String msg;

        input = ms.findUserbyUserId(input);
        int row = as.insertAdmin(input);

        if (row != 0) {
            msg = "새로운 관리자가 성공적으로 추가되었습니다.";
        } else {
            msg = "새로운 관리자 추가에 실패하였습니다.";
        }

        mav.addObject("msg", msg);

        return mav;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView adminDelete(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("member/myPage");
        String msg;
        int row = as.delete(id);

        if (row != 0) {
            msg = "성공적으로 관리자를 삭제하였습니다.";
        } else {
            msg = "관리자 삭제에 실패하였습니다.";
        }

        mav.addObject("msg", msg);

        return mav;
    }

    @GetMapping("/newCoupon")
    public void adminNewCoupon() {}

    @PostMapping("/newCoupon")
    public ModelAndView adminNewCoupon(CouponVO input) {
        ModelAndView mav = new ModelAndView("admin/newCoupon");
        String msg;

        int row = as.insertCoupon(input);

        if (row != 0) {
            msg = "새로운 쿠폰이 성공적으로 추가되었습니다.";
        } else {
            msg = "새로운 쿠폰 추가에 실패하였습니다.";
        }

        mav.addObject("msg", msg);

        return mav;
    }

    @GetMapping("/issue_coupons/{id}")
    public ModelAndView adminIssueCoupons(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("member/myPage");
        String msg = "";

        int result = as.issue_coupons(id);
        if (result != 0) {
            msg = "쿠폰 발급에 성공하였습니다.";
        } else {
            msg = "쿠폰 발급중 오류가 발생하였습니다.";
        }
        mav.addObject("msg", msg);
        return mav;
    }

    @GetMapping("/del_coupon/{id}")
    public ModelAndView adminDelCoupon(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("member/myPage");
        String msg = "";

        int result = as.del_coupon(id);
        if (result != 0) {
            msg = "쿠폰 삭제에 성공하였습니다.";
        } else {
            msg = "쿠폰 삭제중 오류가 발생하였습니다.";
        }
        mav.addObject("msg", msg);
        return mav;
    }
}
