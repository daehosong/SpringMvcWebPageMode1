package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
//  @RequiredArgsConstructor를 사용하게 되면 final이 붙어 있는 객체의 생성자를 만들어 줄 수 있다.
public class BasicItemController {

    private final ItemRepository itemRepository;
    /*  RequiredArgsConstructor 있어서 생략.
    //  생성자가 딱 하나만 있을 경우 @Autowired 생략을 해도 된다.
    @Autowired
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
 */
    @GetMapping
    public String items (Model model)   {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return  "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }
    //    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model){
        //  1. item이라는 객체 생성
        //  2. item에 들어가는 값들 넣어줌.
        //  3. item 객체에 값이 들어간 것을 저장소에 저장
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        //  4. 세팅 된 객체를 모델에 담음 why? 요청한 곳에 뿌려주기 위해 가공한 것
        model.addAttribute("item",item);
        return "basic/item";
    }

    //  @ModelAttribute 역할은 1. 요청 파라미터 처리, 2. 모델을 추가해주는 역할도 함.
    //  단, @ModelAttribute에서 지정된 이름으로 넣어준다.

    //    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item,
                            Model model){
        itemRepository.save(item);
        //  4. 세팅 된 객체를 모델에 담음 why? 요청한 곳에 뿌려주기 위해 가공한 것
        model.addAttribute("item",item);
        return "basic/item";
    }
    /*
    아래와 같이 모델도 생략 가능하다.
    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
        itemRepository.save(item);
        return "basic/item";
    }
 */
    // 아래와 같이 모델을 생략하고 이름을 생략하게 되면 앞에 오는 클래스의 첫자를 소문자로 바꿔준 것이
    // model.attribute 안에 들어간다. 그래서 이렇게 줄여서 사용도 가능하다.

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    //  @ModelAttribute는 이전 강의에서 생략이 가능하다고 배웠기 때문에 3번과 크게 다른게 없다.
    //  더하여 클래스 첫자를 소문자로 바꾼 이름으로 model.attribute에 삽입 되는 특성과 합쳐서 나온 것이
    //  바로 addItemV4 다.

    //@PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
        return "basic/item";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,
                           Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item",item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    //@PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }

        /**
         *  테스트용 데이터 추가
         */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
        itemRepository.save(new Item("itemC",30000,30));
    }

}
