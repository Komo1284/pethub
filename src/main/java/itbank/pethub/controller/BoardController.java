package itbank.pethub.controller;

import itbank.pethub.vo.ContactForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/help")
    public void help(Model model) {
        model.addAttribute("contactForm", new ContactForm());
    }

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
}
