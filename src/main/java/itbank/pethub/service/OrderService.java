package itbank.pethub.service;

import itbank.pethub.model.OrderDAO;
import itbank.pethub.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDAO od;

    public List<CartVO> getCarts(int memberId) {
        return od.getCarts(memberId);
    }

    // 상품 목록
    public List<ItemVO> selectAll() {
        return od.selectAll();
    }

    public ItemVO selectOne(int id) {
        return od.selectOne(id);
    }


    public int getExistingOrderId(int memberId, int productId) {
        Integer existingOrderId = od.getExistingOrderId(memberId, productId);
        return existingOrderId != null ? existingOrderId.intValue() : -1;
    }

    public int getdelivery_status_id() {
        return od.getdelivery_status_id();
    }

    public int getdelivery_id() {
        return od.getdelivery_id();
    }


    public int makeOrder(OrderVO ov) {
        return od.makeOrder(ov);
    }

    public ItemVO getItem(int productId) {
        return od.getItem(productId);
    }

    public int getorderid() {
        return  od.getorderid();
    }

    public int makeCart(CartVO cv) {
        return od.makecart(cv);
    }

    public int countUp(CartVO cartVO) {
        return od.countup(cartVO);
    }


    public Object selectCart(int id) {
        return od.selectCart(id);
    }

    public int deleteCart(int orderId) {
        return od.deleteCart(orderId);
    }


    public int getDeli_id(int order_id) {
        return od.getDeli_id(order_id);
    }



    public int deleteOrder(int orderId) {
        return od.deleteOrder(orderId);
    }


    public int deleteDelivery(int dId) {
        return od.deleteDelivery(dId);
    }



    public int makeDelivery(DeliveryVO dsv) {
        return od.makedelivery(dsv);
    }

    public List<MODCVO> selectMODC(int memberId) {
        return od.selectMODC(memberId);
    }

    public void updateCart(CartVO cart){
        od.updateCart(cart.getCount(), cart.getId());
    }

    public int emailupdate(MemberVO user) {
        return od.emailupdate(user);
    }

    public AddressVO getAddress(int memberId) {
        return od.getAddress(memberId);
    }

    public int addressupdate(MODCVO user) {
        return od.addressupdate(user);
    }

    public int updatedelivery(int dId) {
        return od.updatedelivery(dId);
    }

    public int getd_id(int orderId) {
        return od.getd_id(orderId);
    }

    public List<MODCVO> selectAfterpay(int memberId) {
        return od.selectAfterpay(memberId);
    }

    public int updateorder(int orderId) {
        return od.updateorder(orderId);
    }
}
