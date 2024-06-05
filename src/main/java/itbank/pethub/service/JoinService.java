package itbank.pethub.service;

import itbank.pethub.dto.JoinDTO;
import itbank.pethub.entity.UserEntity;
import itbank.pethub.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String userid = joinDTO.getUserid();
        String userpw = joinDTO.getUserpw();

        Boolean isExist = userRepository.existsByUserid(userid);

        if (isExist) {

            return;
        }

        UserEntity data = new UserEntity();

        data.setUserid(userid);
        data.setUserpw(bCryptPasswordEncoder.encode(userpw));
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}