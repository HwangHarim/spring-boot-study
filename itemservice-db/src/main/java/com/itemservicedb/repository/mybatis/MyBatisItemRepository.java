package com.itemservicedb.repository.mybatis;

import com.itemservicedb.domain.Item;
import com.itemservicedb.repository.ItemRepository;
import com.itemservicedb.repository.ItemSearchCond;
import com.itemservicedb.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MyBatisItemRepository implements ItemRepository {

    private final ItemMapper itemMapper;

    @Override
    public Item save(Item item) {
        itemMapper.save(item);
        return item;
    }

    @Override
    public void update(Long id, ItemUpdateDto updateParam) {
        itemMapper.update(id, updateParam);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return itemMapper.findById(id);
    }

    @Override
    public List<Item> findAll(ItemSearchCond itemSearchCond) {
        return itemMapper.findAll(itemSearchCond);
    }
}
