package itbank.pethub.service;

import itbank.pethub.model.MemberDAO;
import itbank.pethub.model.OrderDAO;
import itbank.pethub.vo.CouponVO;
import itbank.pethub.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberDAO md;

    public MemberVO login(MemberVO input) {
        return md.selectOne(input);
    }

    public List<CouponVO> couponFindbyId(int member_id) {
        return md.couponFindbyId(member_id);
    }

}
