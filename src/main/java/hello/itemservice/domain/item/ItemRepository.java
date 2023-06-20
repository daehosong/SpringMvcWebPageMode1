package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {
    //  실무에서는 멀티 쓰레드 환경에서 스토어에 여러개가 접근 할 때는 해쉬맵을 쓰면 안된다.
    //  ConcurrentHashMap을 사용해야함.
    private static final Map<Long,Item> stroe = new HashMap<>();    // static
    //  마찬가지로 long이 아니라 AtomicLong을 써야함.
    private static long sequence = 0L;  //static

    public Item save(Item item){
        item.setId(++sequence);
        stroe.put(item.getId(), item);
        return item;
    }
    public Item findById(Long id){
        return stroe.get(id);
    }
    public List<Item> findAll(){
        return new ArrayList<>(stroe.values());
    }
    public void update(Long itemId,Item updateParam){
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }
    public void clearStore(){
        stroe.clear();
    }

}
