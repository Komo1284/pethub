package itbank.pethub.service;

import itbank.pethub.model.CouponDAO;
import itbank.pethub.vo.Member_CouponVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponDAO cd;

    public List<Member_CouponVO> getCouponsByMemberId(int member_id) {

        return cd.selectCouponsByMemberId(member_id);
    }
}