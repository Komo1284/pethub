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

    public int getOrder_status_id(int orderId) {
        return od.getOrder_status_id(orderId);
    }

    public int getDeli_id(int osId) {
        return od.getDeli_id(osId);
    }

    public int getDeli_st_id(int dId) {
        return od.getDeli_st_id(dId);
    }

    public int deleteOrder(int orderId) {
        return od.deleteOrder(orderId);
    }

    public int deleteOrderStatus(int osId) {
        return od.deleteOrderStatus(osId);
    }

    public int deleteDelivery(int dId) {
        return od.deleteDelivery(dId);
    }

    public int deleteDeliveryStatus(int dsId) {
        return od.deleteDeliveryStatus(dsId);
    }

    public int makeDelivery(DeliveryVO dsv) {
        return od.makedelivery(dsv);
    }
}
