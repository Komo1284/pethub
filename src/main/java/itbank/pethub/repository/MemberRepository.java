package itbank.pethub.repository;

import itbank.pethub.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    MemberEntity findByUserid(String userid);

}
