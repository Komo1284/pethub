package itbank.pethub.service;

import itbank.pethub.model.BoardDAO;
import itbank.pethub.vo.BoardVO;
import itbank.pethub.vo.ReplyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardDAO bd;

    public List<BoardVO> getBoards() {
        return bd.selectAll();
    }

    public int addWrite(BoardVO input) {

        return bd.addWrite(input);
    }

    public BoardVO getBoard(int id) {
        return bd.selectOne(id);
    }

    public int upBoard(BoardVO input) {
        return bd.updateBoard(input);
    }

    public int delBoard(int id) {
        return bd.deleteBoard(id);
    }

    public List<BoardVO> getNotices() {
        return bd.selectAllNotice();
    }

    public List<BoardVO> getDogs() {
        return bd.selectAllDogs();
    }

    public List<BoardVO> getCats() {
        return bd.selectAllCats();
    }

    public List<BoardVO> getBirds() {
        return bd.selectAllBirds();
    }

    public List<BoardVO> getEtcs() {
        return bd.selectAllEtcs();
    }

    public int viewCount(int id) {
        return bd.viewUp(id);
    }

    // 댓글
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
    public int getReply(int id) {
        return bd.selectReply(id);
    }
}
