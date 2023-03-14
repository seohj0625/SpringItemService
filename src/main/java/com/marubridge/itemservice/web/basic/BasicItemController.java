package com.marubridge.itemservice.web.basic;

import com.marubridge.itemservice.domain.item.Item;
import com.marubridge.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;
    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("DAEJEON", "대전");
        return  regions;
    }

 /*   @Autowired
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }*/ //-RequiredArgsConstructor

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){

        log.info("itemId = " + itemId);
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable long itemId, Model model){
        Item byId = itemRepository.findById(itemId);
        model.addAttribute("item", byId);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String update(@PathVariable long itemId, @ModelAttribute("item") Item item, Model model){
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("item", new Item());
        return "basic/addForm";
    }

   // @PostMapping("/add")
    public String save(@RequestParam String itemName, @RequestParam(defaultValue = "0") Integer price, @RequestParam(defaultValue = "0") Integer quantity, Model model){
        Item item = new Item(itemName, price, quantity);
        Item save = itemRepository.save(item);

        model.addAttribute("item", save);

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String save2(@ModelAttribute("item") Item item, Model model, RedirectAttributes redirectAttributes) {
        Item save = itemRepository.save(item);
        //model.addAttribute("item", save); //생략가능 ModelAttribute 사용!!
        redirectAttributes.addAttribute("itemId", save.getId());
        redirectAttributes.addAttribute("status", true);

        log.info("item.open = " + item.getOpen());

        return "redirect:/basic/items/{itemId}";
    }

    @PostConstruct
    public void init(){
        itemRepository.save(new Item("item1", 1000, 10));
        itemRepository.save(new Item("item2", 2000, 20));
    }
}

