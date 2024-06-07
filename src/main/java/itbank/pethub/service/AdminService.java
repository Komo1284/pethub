package itbank.pethub.service;

import itbank.pethub.model.AdminDAO;
import itbank.pethub.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
