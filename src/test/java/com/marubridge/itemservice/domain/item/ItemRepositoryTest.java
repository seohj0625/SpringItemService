package com.marubridge.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();
    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save() {
        Item item = new Item("item1", 100, 10);
        Item item2 = new Item("item2", 100, 10);

        itemRepository.save(item);
        itemRepository.save(item2);

        Assertions.assertThat(item).isEqualTo(itemRepository.findById(item.getId()));
        Assertions.assertThat(item2).isEqualTo(itemRepository.findById(item2.getId()));

        System.out.println("item = " + item);
        System.out.println("item2 = " + item2);

    }

    @Test
    void findAll(){
        Item item = new Item("item1", 100, 10);
        Item item2 = new Item("item2", 100, 10);

        itemRepository.save(item);
        itemRepository.save(item2);

        List<Item> items = itemRepository.findAll();

        Assertions.assertThat(items.size()).isEqualTo(2);
    }

    @Test
    void update() {
        Item item = new Item("item1", 100, 10);
        Item item2 = new Item("item2", 100, 10);

        itemRepository.save(item);
        itemRepository.save(item2);

        Item newitem = new Item("item1", 1000, 100);
        itemRepository.update(item.getId(), newitem);

        Assertions.assertThat(item.getPrice()).isEqualTo(1000); //success
        Assertions.assertThat(item.getQuantity()).isNotEqualTo(10); //fail

    }
}