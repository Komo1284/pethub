package itbank.pethub.repository;

import itbank.pethub.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserid(String userid);

    Boolean existsByUserid(String userid);

}
