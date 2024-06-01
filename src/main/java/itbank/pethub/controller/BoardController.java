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

    // 공지사항
    @GetMapping("/notice")
    public ModelAndView notice() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("list", bs.getNotices());
        mav.setViewName("board/notice");

        return mav;
    }
    // 전체 게시판
    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("list", bs.getBoards());
        mav.setViewName("board/list");

        return mav;
    }

    // 강아지 게시판
    @GetMapping("/dog")
    public ModelAndView dog() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("list", bs.getDogs());
        mav.setViewName("board/dog");

        return mav;
    }

    // 고양이 게시판
    @GetMapping("/cat")
    public ModelAndView cat() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("list", bs.getCats());
        mav.setViewName("board/cat");

        return mav;
    }

    // 새 게시판
    @GetMapping("/bird")
    public ModelAndView bird() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("list", bs.getBirds());
        mav.setViewName("board/bird");

        return mav;
    }

    // 기타 게시판
    @GetMapping("/etc")
    public ModelAndView etc() {
        ModelAndView mav = new ModelAndView();

        mav.addObject("list", bs.getEtcs());
        mav.setViewName("board/etc");

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
