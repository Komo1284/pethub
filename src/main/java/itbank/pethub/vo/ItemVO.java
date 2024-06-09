package itbank.pethub.vo;

import lombok.Data;

@Data
public class ItemVO {
    private int type, category, price;
    private String name, pic, detail;
}
