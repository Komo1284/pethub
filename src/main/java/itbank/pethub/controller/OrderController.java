package itbank.pethub.controller;

import itbank.pethub.service.OrderService;
import itbank.pethub.vo.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService os;
    private final MemberService ms;
    //장바구니 - 결제전 물건 가져오기
    @GetMapping("/cart")
    public ModelAndView cart(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("user") == null) {
            // 로그인 페이지로 리다이렉트
            mav.setViewName("redirect:/member/login");
            return mav;
        }

        MemberVO user = (MemberVO) session.getAttribute("user");
        int memberId = user.getId();
        mav.addObject("list", os.getCarts(memberId));

        mav.setViewName("/order/cart");
        return mav;
    }

    // 정보 삭제
    @GetMapping("/delete/{order_id}")
    public ModelAndView delete(@PathVariable("order_id") int order_id) {
        ModelAndView mav = new ModelAndView();

        int d_id=os.getDeli_id(order_id);
        os.deleteCart(order_id);
        os.deleteOrder(order_id);
        int row =os.deleteDelivery(d_id);



        String msg = "삭제 되었습니다. ";
        if (row != 1)
            msg = "삭제 실패하였습니다.";

        mav.addObject("path", "/order/cart");
        mav.addObject("msg", msg);

        mav.setViewName("/order/Message");

        return mav;
    }

    //결제 페이지로 이동
    @PostMapping("/cart")
    public ModelAndView orderStatus() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("redirect:/order/orderStatus");
        return mav;
    }

    //장바구니 물품 수량 변경
    @PostMapping("/cart/update")
    public ResponseEntity<Map<String, Object>> updateCart(@RequestBody CartVO cartVO) {
        Map<String, Object> response = new HashMap<>();
        try {
            os.updateCart(cartVO);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    //결제 페이지- 배송지 수정
    @PostMapping("/cart/deliveryupdate")
    public ResponseEntity<Map<String, Object>> deliveryupdate(@RequestBody MODCVO user) {
        Map<String, Object> response = new HashMap<>();


        try {
            os.addressupdate(user);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    //결제 페이지 가져오기
    @GetMapping("/orderStatus")
    public ModelAndView orderStatus(HttpSession session) {
        ModelAndView mav= new ModelAndView();
        if (session.getAttribute("user") == null) {
            // 로그인 페이지로 리다이렉트
            mav.setViewName("redirect:/member/login");
            return mav;
        }

        MemberVO user = (MemberVO) session.getAttribute("user");
        int member_id=user.getId();

        mav.addObject("list", os.selectMODC(member_id));
        mav.setViewName("/order/orderStatus");
        return mav;
    }

    @GetMapping("/cartdelete/{cdi}")
    public ModelAndView deleteCartItem(@PathVariable("cdi") int cartItemId) {

        ModelAndView mav = new ModelAndView();

        List<Integer> orderIds = os.getOrderIds(cartItemId); // 장바구니에 속한 모든 주문 아이디 가져오기

        int row = os.deleteallcart(cartItemId);
        for (int o_id : orderIds) {
            int d_id = os.getDeli_id(o_id); // 주문 아이디로 배송 아이디 가져오기

            // 주문 삭제
            os.deleteOrder(o_id);

            // 배송 삭제
            os.deleteDelivery(d_id);

            // 카트 삭제
            os.deleteCart(o_id);
        }





        String msg = "삭제 되었습니다. ";
        if (row != 1)
            msg = "삭제 실패하였습니다.";

        mav.addObject("path", "/order/AfterPay");
        mav.addObject("msg", msg);

        mav.setViewName("/order/Message");
        return mav;
    }


    //결제 성공 시 배송정보 업데이트
    @PostMapping("/updateDeliveryInfo")
    public ModelAndView updateDeliveryInfo(@RequestBody CartVO deliveryInfo, HttpSession session) {

        ModelAndView mav = new ModelAndView();
        int order_id=deliveryInfo.getOrder_id();

        os.updateorder(order_id);
        mav.setViewName("redirect:/order/cart");
        return mav;
    }

    //주문 현황 페이지
    @GetMapping("/AfterPay")
    public ModelAndView AfterPay(HttpSession session) {
        ModelAndView mav= new ModelAndView();
        if (session.getAttribute("user") == null) {
            // 로그인 페이지로 리다이렉트
            mav.setViewName("redirect:/member/login");
            return mav;
        }

        MemberVO user = (MemberVO) session.getAttribute("user");
        int member_id=user.getId();

        mav.addObject("list", os.selectAfterpay(member_id));
        mav.setViewName("/order/AfterPay");
        return mav;
    }

    //주문 현황 페이지
    @GetMapping("/ordercheck")
    public ModelAndView ordercheck(HttpSession session) {
        ModelAndView mav= new ModelAndView();
        if (session.getAttribute("user") == null) {
            // 로그인 페이지로 리다이렉트
            mav.setViewName("redirect:/member/login");
            return mav;
        }

        MemberVO user = (MemberVO) session.getAttribute("user");
        int member_id=user.getId();

        mav.addObject("list", os.ordercheck(member_id));
        mav.setViewName("/order/ordercheck");
        return mav;
    }

    @GetMapping("/coupon")
    @ResponseBody
    public List<CouponVO> getCoupons(HttpSession session) {

        MemberVO user = (MemberVO) session.getAttribute("user");
        int member_id=user.getId();

        return ms.couponFindbyId(member_id);
    }

}