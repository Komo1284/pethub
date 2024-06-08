package itbank.pethub.service;

import itbank.pethub.model.AdminDAO;
import itbank.pethub.vo.CouponVO;
import itbank.pethub.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminDAO dao;

    public int insertAdmin(MemberVO input) {
        return dao.insertAdmin(input);
    }

    public List<MemberVO> findAllAdmins() {
        return dao.findAllAdmins();
    }

    public int delete(int id) {
        return dao.deleteAdmin(id);
    }

    public List<CouponVO> findAllCoupons() {
        return dao.findAllCoupons();
    }

    @Transactional
    public int issue_coupons(int id) {
        List<MemberVO> list = dao.selectAllMember();

        for (MemberVO m : list) {
            int result = dao.issue_coupons(m.getId(),id);
            if (result == 0) {
                return 0;
            }
        }
        return 1;
    }

    public int del_coupon(int id) {
        return dao.del_coupon(id);
    }

    public int insertCoupon(CouponVO input) {
        return dao.insertCoupon(input);
    }
}
