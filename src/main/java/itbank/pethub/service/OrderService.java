package itbank.pethub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import itbank.pethub.model.OrderDAO;
import itbank.pethub.vo.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.hc.client5.http.classic.methods.HttpPost;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;



@Service
public class OrderService {
    @Autowired
    private OrderDAO od;

    private String apiKey = "4586115827888501";
    private String apiSecret = "qv83kwrwpsIg5kvjWk0WVygmqKH8LnooLgOk5cEJpOB6AWwDPfnFJMtKcm8sfmPGfsa2WY7GB34zABri";

    // 장바구니 불러오기
    public CartVO getCartByMemberId(int memberId) {
        CartVO cart = od.findCartByMemberId(memberId);
        List<CartItemVO> cartItems = od.findCartItemsByCartId(cart.getId());
        cart.setCartItems(cartItems);
        int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();
        cart.setTotalPrice(totalPrice);
        return cart;
    }

    public void addItemToCart(CartItemVO cartItem) {
        od.addItemToCart(cartItem);
    }

    public void removeItemFromCart(int cartItemId) {
        od.removeItemFromCart(cartItemId);
    }

    public void updateItemQuantity(int cartItemId, int quantity) {
        od.updateItemQuantity(cartItemId, quantity);
    }

    public OrderVO checkout(int memberId) {
        CartVO cart = getCartByMemberId(memberId);
        OrderVO order = new OrderVO();
        order.setMemberId(memberId);
        order.setStatus("PENDING");
        order.setTotalPrice(cart.getTotalPrice());
        od.createOrder(order);

        for (CartItemVO cartItem : cart.getCartItems()) {
            OrderItemVO orderItem = new OrderItemVO();
            orderItem.setOrderId(order.getId());
            orderItem.setItemId(cartItem.getItemId());
            orderItem.setItemName(cartItem.getItemName());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            od.createOrderItem(orderItem);
        }

        return order;
    }

    public void processPayment(OrderVO order) throws Exception {
        String token = getPortOneToken();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(order);

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("https://api.portone.io/v1/payments/complete");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + token);
            httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                JsonNode responseBody = objectMapper.readTree(response.getEntity().getContent());
                if (!responseBody.path("status").asText().equals("success")) {
                    throw new Exception("결제 실패: " + responseBody.path("message").asText());
                }

                od.updateOrderStatus(order.getId(), "COMPLETED");
            }
        }
    }

    private String getPortOneToken() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.createObjectNode()
                .put("imp_key", apiKey)
                .put("imp_secret", apiSecret)
                .toString();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("https://api.portone.io/v1/tokens");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                JsonNode responseBody = objectMapper.readTree(response.getEntity().getContent());
                return responseBody.path("response").path("access_token").asText();
            }
        }
    }

    public MemberVO getMemberById(int memberId) {
        return od.findMemberById(memberId);
    }

}