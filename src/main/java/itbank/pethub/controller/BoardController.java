package itbank.pethub.controller;

import itbank.pethub.service.BoardService;
import itbank.pethub.vo.BoardVO;
import itbank.pethub.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    // 글작성
    @GetMapping("/write")
    public void write() {}

    @PostMapping("/write")
    public ModelAndView addWrite(BoardVO input) {
        ModelAndView mav = new ModelAndView();

        bs.addWrite(input);
        mav.setViewName("redirect:/board/list");

        return mav;
    }

    // 글 내용 보기
    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();

        bs.viewCount(id);
        mav.addObject("row", bs.getBoard(id));
        mav.addObject("reply", bs.getReplies(id));

        mav.setViewName("board/view");

        return mav;
    }

    // 게시판 수정
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

    // 게시판 삭제
    @GetMapping("/deleteBd/{id}")
    public ModelAndView deleteBd(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();
        bs.delBoard(id);
        mav.setViewName("redirect:/board/list");
        return mav;
    }

    // 댓글 추가
    @PostMapping("/addReply")
    public ModelAndView addReply(ReplyVO input) {
        ModelAndView mav = new ModelAndView();
        bs.addReply(input);
        mav.setViewName("redirect:/board/view/" + input.getBoard_id());

        return mav;
    }

    // 댓글 삭제
    @PostMapping("/deleteReply/{id}")
    public ModelAndView deleteReply(@PathVariable int id, @RequestParam("board_id") int boardId) {
        ModelAndView mav = new ModelAndView();
        bs.deleteReply(id);
        mav.setViewName("redirect:/board/view/" + boardId);

        return mav;
    }

    // 댓글 수정
    @GetMapping("/updateReply/{replyId}") // replyId를 사용하도록 수정
    public ModelAndView updateReply(@PathVariable int replyId) { // replyId로 수정
        ModelAndView mav = new ModelAndView();
        mav.addObject("replyId", bs.getReply(replyId));
        mav.setViewName("board/view");
        return mav;
    }

    @PostMapping("/updateReply/{id}")
    public ModelAndView updateReply(@PathVariable int id , ReplyVO input, @RequestParam("board_id") int boardId) {
        ModelAndView mav = new ModelAndView();
        input.setId(id);
        bs.updateReply(input);
        mav.setViewName("redirect:/board/view/" + boardId);

        return mav;
    }

    @GetMapping("/wroteBoard")
    public void wroteBoard() {}

    @GetMapping("/wroteReply")
    public void wroteReply() {}
}
