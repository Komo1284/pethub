package itbank.pethub.controller;

import itbank.pethub.service.OrderService;
import itbank.pethub.vo.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/shop")
public class ShopController {

    private final OrderService os;

    // 상품 리스트 불러오기
    @GetMapping("/Items")
    public ModelAndView market() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("product", os.selectAll());
        mav.setViewName("/shop/Items");
        return mav;
    }



    @GetMapping("/DetailPage/{id}")
    public ModelAndView detailPage(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("product", os.selectOne(id));
        mav.setViewName("/shop/DetailPage");
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










}