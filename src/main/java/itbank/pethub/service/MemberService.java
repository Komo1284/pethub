package itbank.pethub.service;

import itbank.pethub.aop.PasswordEncoder;
import itbank.pethub.dto.MemberDetails;
import itbank.pethub.model.MemberDAO;
import itbank.pethub.repository.MemberRepository;
import itbank.pethub.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberDAO dao;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberVO memberData = memberRepository.findByUserid(username);

        if (memberData != null) {
            return new MemberDetails(memberData);

        }

        return null;
    }

    public MemberVO login(MemberVO input) {
        String hash = input.getUserpw();
        System.out.println(hash);
        return dao.selectOne(input);
    }

    public int signUp(MemberVO input) {
        return dao.insert(input);
    }

    public void update(MemberVO input) {
        dao.update(input);
    }

    public void delete(MemberVO member) {
        dao.delete(member);
    }

    @Transactional(readOnly = true)
    public String findId(MemberVO input) {
        return dao.findId(input);
    }

    public String findPw(MemberVO input) {
        MemberVO member = dao.findPw(input);
        if(member != null){
            String newPw = UUID.randomUUID().toString().substring(0, 8);

            // 해쉬처리
            member.setUserpw(PasswordEncoder.encode(newPw));
            dao.newPw(member);
            return newPw;
        }
        return null;
    }

    public boolean isUserIdExists(String userid) {
        return dao.countByUserId(userid) > 0;
    }
}
