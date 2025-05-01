package com.packsure.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.packsure.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
