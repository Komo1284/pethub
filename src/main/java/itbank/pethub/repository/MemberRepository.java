package itbank.pethub.repository;


import itbank.pethub.vo.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MemberRepository extends JpaRepository<MemberVO, Integer> {

    MemberVO findByUserid(String userid);

    Boolean existsByUserid(String userid);


}
