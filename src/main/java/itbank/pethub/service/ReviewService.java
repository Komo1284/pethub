package itbank.pethub.service;

import itbank.pethub.model.ReviewDAO;
import itbank.pethub.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewDAO rd;

    // 리뷰 목록
    public List<ReviewVO> getReviews(int id) {
        return rd.getReviews(id);
    }

    // 리뷰 추가
    public int addReview(ReviewVO input) {
        return rd.addReview(input);
    }

    // 리뷰 삭제
    public int deleteReview(int id) {
        return rd.deleteReview(id);
    }


}
