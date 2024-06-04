package itbank.pethub.controller;

import itbank.pethub.aop.PasswordEncoder;
import itbank.pethub.service.ImageService;
import itbank.pethub.service.MemberService;
import itbank.pethub.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService ms;
    private final ImageService is;


    @GetMapping("/login")
    public void login() {}

    @PostMapping("/login")
    public ModelAndView login(MemberVO input, HttpSession session) {

        ModelAndView mav = new ModelAndView();
        session.setAttribute("user", ms.login(input));
        mav.setViewName("redirect:/");

        return mav;

    }

    // 로그아웃 버튼 클릭시 세션에서 'user'를 삭제후 홈으로 리다이렉트
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/signUp")
    public void signUp() {}

    @PostMapping("/signUp")
    public ModelAndView signUp(MemberVO input) {

        ModelAndView mav = new ModelAndView();

        // 중복된 아이디 체크
        if(ms.isUserIdExists(input.getUserid())){
            mav.addObject("msg", "사용 불가능한 아이디입니다.");
            mav.setViewName("member/signUp");
            return mav;
        }

        // 비밀번호와 비밀번호체크의 내용이 다를 경우
        if(!Objects.equals(input.getUserpw(), input.getPwCheck())){
            mav.addObject("msg", "입력한 두 비밀번호가 서로 다릅니다.");
            mav.setViewName("member/signUp");
            return mav;
        }

        input = ms.signUp(input);
        System.out.println("input = " + input);
        int row = ms.insertAdd(input);
        if (row > 0 && input != null) {
            row = 1;
        } else {
            row = 0;
        }

        mav.addObject("row", row);
        mav.setViewName("member/signUp");
        return mav;
    }


    // 나의정보 페이지로 이동
    @GetMapping("/myPage")
    public ModelAndView myPage(HttpSession session) {
        ModelAndView mav = new ModelAndView("member/myPage");
        MemberVO user = (MemberVO) session.getAttribute("user");
        mav.addObject("coupons", ms.couponFindbyId(user.getId()));

        return mav;
    }

    // 회원정보 수정정보 페이지로 전송
    @GetMapping("/memberUpdate")
    public void update() {}

    // 회원정보 수정요청하여 로그아웃으로 리다이렉트
    @PostMapping("/memberUpdate")
    public ModelAndView myPage(MemberVO input, HttpSession session, MultipartFile file) throws IOException {

        ModelAndView mav = new ModelAndView();
        MemberVO user = (MemberVO) session.getAttribute("user");

        // 현재비밀번호가 일치하는지 확인
        String hashPw = input.getUserpw();
        hashPw = PasswordEncoder.encode(hashPw);

        if(!Objects.equals(user.getUserpw(), hashPw)){
            mav.addObject("msg", "현재 비밀번호가 알맞지 않습니다.");
            mav.setViewName("member/memberUpdate");
            return mav;
        }

        // 변경할 비밀번호와 비밀번호체크가 동일하지 않다면 메세지를 담아서 수정페이지로 포워드
        if(!Objects.equals(input.getNewpw(), input.getPwCheck())){
            mav.addObject("msg", "변경할 비밀번호와 확인이 일치하지 않습니다.");
            mav.setViewName("member/memberUpdate");
            return mav;
        }

        input.setId(user.getId());
        input.setUserpw(input.getNewpw());

        int row = 0;
        // 이미지를 s3 서버에 저장하여 저장된 이미지의 url을 세팅 - 이미지를 변경할 경우
        if(!file.isEmpty()){
            String url = is.imageUploadFromFile(file);
            user.setProfile(url);
            row = ms.update(input);
        } else{ // 이미지 변경 안할 경우
            row = ms.updateNoProfile(input);
        }

        if (row > 0) {
            mav.addObject("msg", "수정이 완료되었습니다.");
            return mav;
        } else {
            mav.addObject("msg", "수정에 실패하였습니다.");
            return mav;
        }
    }

    // 회원탈퇴하고 홈으로 리다이렉트
    @GetMapping("/delete")
    public String delete(HttpSession session) {
        MemberVO member = (MemberVO) session.getAttribute("user");
        ms.delete(member);
        return "redirect:/";
    }

    // 아이디,비밀번호 찾기 페이지로 전송
    @GetMapping("/findAcc")
    public void findId() {}

    // 아이디를 찾아서 userid로 데이터를 전송하여 해당페이지에서 userid가 있으면 userid를 alert하도록 설정
    @PostMapping("/findId")
    public ModelAndView findId(MemberVO input) {
        ModelAndView mav = new ModelAndView("member/findAcc");
        mav.addObject("userid", ms.findId(input));
        return mav;
    }

    // 아이디와 폰번호로 해당 계정을 찾은 뒤 랜덤한 새로운 비밀번호를 발행.
    // 새로 발행된 비밀번호를 유저에게 alert로 전달해주고 새로운 비밀번호를 db에 저장
    @PostMapping("/findPw")
    public ModelAndView findPw(MemberVO input) {
        ModelAndView mav = new ModelAndView("member/findAcc");
        String newPw = ms.findPw(input);
        mav.addObject("newPw", newPw);
        return mav;
    }

    // 회원가입시 중복아이디 체크를 위한 비동기통신 메서드
    @PostMapping("/checkUserId")
    public ResponseEntity<Map<String, Boolean>> checkUserId(@RequestBody Map<String, String> request) {
        String userid = request.get("userid");
        boolean exists = ms.isUserIdExists(userid);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
}


