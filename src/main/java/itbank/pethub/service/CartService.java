package itbank.pethub.service;

import itbank.pethub.model.CartDAO;
import itbank.pethub.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDAO cd;

    public List<CartVO> getCartItems(int member_id) {
        return cd.findCartItems(member_id);
    }



}
