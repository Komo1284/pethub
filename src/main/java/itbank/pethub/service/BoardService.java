package itbank.pethub.service;

import itbank.pethub.model.BoardDAO;
import itbank.pethub.vo.BoardVO;
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
}
