package itbank.pethub.controller;

import itbank.pethub.service.OrderService;
import itbank.pethub.vo.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService os;

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
    // 상품 리스트 불러오기
    @GetMapping("/Items")
    public ModelAndView market() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("product", os.selectAll());
        mav.setViewName("/order/Items");
        return mav;
    }

    @GetMapping("/orderUpdate/{id}")
    public ModelAndView updatePage(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", os.selectCart(id));
        mav.setViewName("/order/orderUpdate");
        return mav;
    }

    @GetMapping("/DetailPage/{id}")
    public ModelAndView detailPage(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", os.selectOne(id));
        mav.setViewName("/order/DetailPage");
        return mav;
    }


    // 상세페이지 정보를 받아 주문페이지로 이동
    @PostMapping("/DetailPage/{id}")
    public ModelAndView processOrder(@PathVariable("id") int productId, @RequestParam("quantity") int quantity,
                                     HttpSession session) {
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("user") == null) {
            // 로그인 페이지로 리다이렉트
            mav.setViewName("redirect:/member/login");
            return mav;
        }

        // 로그인 한 멤버 정보 + 아이디 + 주소 가져오기
        MemberVO user = (MemberVO) session.getAttribute("user");
        int memberId = user.getId();


        // 주문이 이미 존재하는지 확인 - order 페이지에만 있는지 확인
        int existingOrderId = os.getExistingOrderId(memberId, productId);
        if (existingOrderId != -1) {
            // 주문이 이미 존재하면 수량을 업데이트
            CartVO cartVO = new CartVO();
            cartVO.setId(existingOrderId);
            cartVO.setCount(quantity);
            os.countUp(cartVO);

        } else {
            AddressVO av=os.getAddress(memberId);
            DeliveryVO dv=new DeliveryVO();

            String add=av.getPrimary_address();
            String add_detail=av.getAddress_details();

            String address=add+" "+add_detail;
            int post=av.getZip_code();

            dv.setAddress(address);
            dv.setPost(post);
            os.makeDelivery(dv);

            int delivery_id=os.getdelivery_id();

            OrderVO ov=new OrderVO();
            ov.setMember_id(memberId);
            ov.setDelivery_id(delivery_id);
            os.makeOrder(ov);

            ItemVO iv=os.getItem(productId);

            int orderid = os.getorderid();

            CartVO cv=new CartVO();
            cv.setOrder_id(orderid);
            cv.setOrder_item(productId);
            cv.setOrder_price(iv.getPrice());
            cv.setCount(quantity);
            cv.setOrigin_price(iv.getPrice());
            os.makeCart(cv);
        }

        // 주문이 성공적으로 추가되거나 업데이트된 후 주문 페이지로 리다이렉트
        mav.setViewName("redirect:/order/cart");

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

    @PostMapping("/cart")
    public ModelAndView orderStatus() {
        ModelAndView mav = new ModelAndView();




        mav.setViewName("redirect:/order/orderStatus");
        return mav;
    }

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

    @PostMapping("/updateDeliveryInfo")
    public ModelAndView updateDeliveryInfo(@RequestBody CartVO deliveryInfo, HttpSession session) {

        ModelAndView mav = new ModelAndView();
        int order_id=deliveryInfo.getOrder_id();

        os.updateorder(order_id);
        mav.setViewName("redirect:/order/cart");
        return mav;
    }

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
}