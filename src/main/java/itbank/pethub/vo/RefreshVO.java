package itbank.pethub.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshVO {
    private Long id;

    private String userid;
    private String refresh;
    private String expiration;

}
