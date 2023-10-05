package com.leanMS.Invertory.repository;

import com.leanMS.Invertory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    public Optional<Inventory> findBySkuCode(String skuCode);

    public List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
