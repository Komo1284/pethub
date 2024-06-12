package itbank.pethub.repository;

import itbank.pethub.vo.MemberVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberVO, Integer> {

    Boolean existsByUsername (String username);



}
