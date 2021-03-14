package com.traders.application.items.repository;

import com.traders.application.items.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Items,Integer> {


}
