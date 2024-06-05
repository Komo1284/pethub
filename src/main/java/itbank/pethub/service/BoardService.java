package itbank.pethub.service;

import itbank.pethub.components.Paging;
import itbank.pethub.model.BoardDAO;
import itbank.pethub.vo.BoardVO;
import itbank.pethub.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    @Autowired
    private BoardDAO bd;

    // 게시판 목록
    public Map<String, Object> getBoards(Map<String, Object> param) {

        String sint = (String) param.get("page");
        sint = (sint == null) ? "1" : sint;

        int reqPage = Integer.parseInt(sint);
        int boardnum = 5;

        int totalcount;
        if (param.containsKey("group") || param.containsKey("search")) {
            param.put("num", boardnum); // 검색 조건이 있는 경우에만 param에 num 추가
            totalcount = bd.searchboard(param);
        } else {
            totalcount = bd.totalboard(boardnum);
        }

        Paging page = new Paging(reqPage, totalcount);

        param.put("offset", page.getOffset());
        param.put("boardCount", page.getBoardCount());


        Map<String, Object> result = new HashMap<>();

        result.put("pg", page);
        result.put("list", bd.selectAll(param));

        return result;
    }

    // 글 작성
    public int addWrite(BoardVO input) {

        return bd.addWrite(input);
    }

    // 글 하나 선택
    public BoardVO getBoard(int id) {
        return bd.selectOne(id);
    }

    // 글 수정
    public int upBoard(BoardVO input) {
        return bd.updateBoard(input);
    }

    // 글 삭제
    public int delBoard(int id) {
        return bd.deleteBoard(id);
    }

    // 공지사항 목록
    public Map<String, Object> getNotices(Map<String, Object> param) {

        String sint = (String) param.get("page");
        sint = (sint == null) ? "1" : sint;

        int reqPage = Integer.parseInt(sint);
        int boardnum = 1;

        int totalcount;
        if (param.containsKey("group") || param.containsKey("search")) {
            param.put("num", boardnum);
            totalcount = bd.searchboard(param);
        } else {
            totalcount = bd.totalboard(boardnum);
        }

        Paging page = new Paging(reqPage, totalcount);

        param.put("offset", page.getOffset());
        param.put("boardCount", page.getBoardCount());


        Map<String, Object> result = new HashMap<>();

        result.put("pg", page);
        result.put("list", bd.selectAllNotice(param));

        return result;
    }

    // 강아지 목록
    public Map<String, Object> getDogs(Map<String, Object> param) {

        String sint = (String) param.get("page");
        sint = (sint == null) ? "1" : sint;

        int reqPage = Integer.parseInt(sint);
        int boardnum = 6;

        int totalcount;
        if (param.containsKey("group") || param.containsKey("search")) {
            param.put("num", boardnum);
                totalcount = bd.searchboard(param);
            } else {
                totalcount = bd.totalboard(boardnum);
            }

            Paging page = new Paging(reqPage, totalcount);

            param.put("offset", page.getOffset());
            param.put("boardCount", page.getBoardCount());


            Map<String, Object> result = new HashMap<>();

            result.put("pg", page);
            result.put("list", bd.selectAllDogs(param));

            return result;
        }

    // 고양이 목록
    public Map<String, Object> getCats(Map<String, Object> param) {

        String sint = (String) param.get("page");
        sint = (sint == null) ? "1" : sint;
        int boardnum = 7;

        int reqPage = Integer.parseInt(sint);


        int totalcount;
        if (param.containsKey("group") || param.containsKey("search")) {
            param.put("num", boardnum);
            totalcount = bd.searchboard(param);
        } else {
            totalcount = bd.totalboard(boardnum);
        }

        Paging page = new Paging(reqPage, totalcount);

        param.put("offset", page.getOffset());
        param.put("boardCount", page.getBoardCount());


        Map<String, Object> result = new HashMap<>();

        result.put("pg", page);
        result.put("list", bd.selectAllCats(param));

        return result;
    }

    // 새 목록
    public Map<String, Object> getBirds(Map<String, Object> param) {

        String sint = (String) param.get("page");
        sint = (sint == null) ? "1" : sint;

        int reqPage = Integer.parseInt(sint);
        int boardnum = 8;


        int totalcount;
        if (param.containsKey("group") || param.containsKey("search")) {
            param.put("num", boardnum);
            totalcount = bd.searchboard(param);
        } else {
            totalcount = bd.totalboard(boardnum);
        }

        Paging page = new Paging(reqPage, totalcount);

        param.put("offset", page.getOffset());
        param.put("boardCount", page.getBoardCount());


        Map<String, Object> result = new HashMap<>();

        result.put("pg", page);
        result.put("list", bd.selectAllBirds(param));

        return result;
    }

    // 기타 목록
    public Map<String, Object> getEtcs(Map<String, Object> param) {

        String sint = (String) param.get("page");
        sint = (sint == null) ? "1" : sint;

        int reqPage = Integer.parseInt(sint);
        int boardnum = 9;


        int totalcount;
        if (param.containsKey("group") || param.containsKey("search")) {
            param.put("num", boardnum);
            totalcount = bd.searchboard(param);
        } else {
            totalcount = bd.totalboard(boardnum);
        }

        Paging page = new Paging(reqPage, totalcount);

        param.put("offset", page.getOffset());
        param.put("boardCount", page.getBoardCount());


        Map<String, Object> result = new HashMap<>();

        result.put("pg", page);
        result.put("list", bd.selectAllEtcs(param));

        return result;
    }

    // 조회수
    public int viewCount(int id) {
        return bd.viewUp(id);
    }

    // 댓글 목록
    public List<ReplyVO> getReplies(int id) {
        return bd.getReplies(id);
    }

    // 댓글 추가
    public int addReply(ReplyVO input) {
        return bd.addReply(input);
    }

    // 댓글 삭제
    public int deleteReply(int id) {
        return bd.deleteReply(id);
    }

    // 댓글 수정
    public int updateReply(ReplyVO input) {
        return  bd.updateReply(input);
    }

    // 댓글 수정하기 위한 댓글 선택
    public ReplyVO getReply(int id) {
        return bd.selectReply(id);
    }

    // 내가 쓴 글
    public Map<String, Object> getWroteBoard(Map<String, Object> param) {

        String sint = (String) param.get("page");
        sint = (sint == null) ? "1" : sint;

        int reqPage = Integer.parseInt(sint);


        int totalcount;
        if (param.containsKey("group") || param.containsKey("search")) {
            totalcount = bd.searchboard(param);
        } else {
            totalcount = bd.total();
        }

        Paging page = new Paging(reqPage, totalcount);

        param.put("offset", page.getOffset());
        param.put("boardCount", page.getBoardCount());


        Map<String, Object> result = new HashMap<>();

        result.put("pg", page);
        result.put("list", bd.selectAllwroteBoard(param));

        return result;
    }


    public Map<String, Object> getWroteReply(Map<String, Object> param) {

        String sint = (String) param.get("page");
        sint = (sint == null) ? "1" : sint;

        int reqPage = Integer.parseInt(sint);


        int totalcount;
        if (param.containsKey("group") || param.containsKey("search")) {
            totalcount = bd.search(param);
        } else {
            totalcount = bd.totalReply();
        }

        Paging page = new Paging(reqPage, totalcount);

        param.put("offset", page.getOffset());
        param.put("boardCount", page.getBoardCount());


        Map<String, Object> result = new HashMap<>();

        result.put("pg", page);
        result.put("list", bd.selectAllwroteReply(param));

        return result;
    }
}
