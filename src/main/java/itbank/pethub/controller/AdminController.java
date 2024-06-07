package itbank.pethub.controller;

import itbank.pethub.service.AdminService;
import itbank.pethub.service.MemberService;
import itbank.pethub.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ModelAndView adminInsert(MemberVO input, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("admin/insert");
        String msg;

        input = ms.findUserbyUserId(input);
        int row = as.insertAdmin(input);
        System.out.println("row = " + row);

        if (row != 0) {
            msg = "새로운 관리자가 성공적으로 추가되었습니다.";
        } else {
            msg = "새로운 관리자 추가에 실패하였습니다.";
        }

        mav.addObject("msg", msg);

        return mav;
    }

    @GetMapping("/delete")
    public void adminDelete() {}
}
