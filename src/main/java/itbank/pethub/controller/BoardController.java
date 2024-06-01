package itbank.pethub.controller;

import itbank.pethub.service.BoardService;
import itbank.pethub.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService bs;

    @GetMapping("/help")
    public void help() {}

    // 일반 게시판
    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("list", bs.getBoards());
        mav.setViewName("board/list");

        return mav;
    }

    @GetMapping("/write")
    public void write() {}

    @PostMapping("/write")
    public ModelAndView addWrite(BoardVO input) {
        ModelAndView mav = new ModelAndView();

        bs.addWrite(input);
        mav.setViewName("redirect:/board/list");

        return mav;
    }

    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("row", bs.getBoard(id));

        mav.setViewName("board/view");

        return mav;
    }

    @GetMapping("/updateBd/{id}")
    public ModelAndView updateBd(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();

        mav.addObject("row", bs.getBoard(id));
        mav.setViewName("board/write");

        return mav;
    }

    @PostMapping("/updateBd/{id}")
    public ModelAndView updateBd(@PathVariable int id, BoardVO input) {
        ModelAndView mav = new ModelAndView();
        input.setId(id);
        bs.upBoard(input);
        mav.setViewName("redirect:/board/list");

        return mav;
    }

    @GetMapping("/deleteBd/{id}")
    public ModelAndView deleteBd(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();
        bs.delBoard(id);
        mav.setViewName("redirect:/board/list");
        return mav;
    }

    @GetMapping("/wroteBoard")
    public void wroteBoard() {}

    @GetMapping("/wroteReply")
    public void wroteReply() {}
}
