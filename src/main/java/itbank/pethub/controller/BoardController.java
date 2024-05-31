package itbank.pethub.controller;

<<<<<<< HEAD
import itbank.pethub.service.BoardService;
import itbank.pethub.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
=======
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
>>>>>>> develop

@Controller
@RequestMapping("/board")
public class BoardController {

<<<<<<< HEAD
    @Autowired
    private BoardService bs;

=======
    @GetMapping("/help")
    public void help() {}

    @GetMapping("/list")
    public void list() {}

    @GetMapping("/view")
    public void view() {}

    @GetMapping("/write")
    public void write() {}

    @GetMapping("/wroteBoard")
    public void wroteBoard() {}

    @GetMapping("/wroteReply")
    public void wroteReply() {}
>>>>>>> develop
}
