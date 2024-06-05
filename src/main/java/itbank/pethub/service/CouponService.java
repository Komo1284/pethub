package itbank.pethub.service;

import itbank.pethub.model.CouponDAO;
import itbank.pethub.vo.CouponVO;
import itbank.pethub.vo.Member_CouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CouponService {
    @Autowired
    private CouponDAO cd;

    public List<Member_CouponVO> getCouponsByMemberId(int member_id) {

        return cd.selectCouponsByMemberId(member_id);
    }
}