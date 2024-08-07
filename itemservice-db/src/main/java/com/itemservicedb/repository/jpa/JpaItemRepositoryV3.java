package com.itemservicedb.repository.jpa;

import com.itemservicedb.domain.Item;
import com.itemservicedb.domain.QItem;
import com.itemservicedb.repository.ItemRepository;
import com.itemservicedb.repository.ItemSearchCond;
import com.itemservicedb.repository.ItemUpdateDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.itemservicedb.domain.QItem.item;

@Repository
@Transactional
public class JpaItemRepositoryV3 implements ItemRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public JpaItemRepositoryV3(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item item = em.find(Item.class, itemId);
        item.setItemName(updateParam.getItemName());
        item.setPrice(updateParam.getPrice());
        item.setQuantity(updateParam.getQuantity());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }

    //
//    @Override
//    public List<Item> findAll(ItemSearchCond cond) {
//        String itemName = cond.getItemName();
//        Integer maxPrice = cond.getMaxPrice();
//
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        if (StringUtils.hasText(itemName)) {
//            booleanBuilder.and(QItem.item.itemName.like("%" + itemName + "%"));
//        }
//        if (maxPrice != null) {
//            booleanBuilder.and(item.price.loe(maxPrice));
//        }
//
//
//        List<Item> result = query.select(item)
//                .from(item)
//                .where(booleanBuilder)
//                .fetch();
//
//        return result;
//    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        return query.select(item)
                .from(item)
                .where(likeItemName(itemName), maxPrice(maxPrice))
                .fetch();
    }

    private BooleanExpression likeItemName(String itemName) {
        if (StringUtils.hasText(itemName)) {
            return QItem.item.itemName.like("%" + itemName + "%");
        }
        return null;
    }

    private BooleanExpression maxPrice(Integer maxPrice) {
        if (maxPrice != null) {
            item.price.loe(maxPrice);
        }
        return null;
    }
}
