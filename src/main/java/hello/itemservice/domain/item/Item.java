package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//  @Data 쓰는 건 위험. why? tostring이랑 argument까지 모두 생성해주기 때문에
//  단, DTO 같은 경우에는 써줘도 된다.
@Getter @Setter
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;


    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
