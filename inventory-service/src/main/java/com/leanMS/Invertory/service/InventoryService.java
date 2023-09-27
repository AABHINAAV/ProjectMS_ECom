package com.leanMS.Invertory.service;

import com.leanMS.Invertory.dto.InventoryRequest;
import com.leanMS.Invertory.dto.InventoryResponse;
import com.leanMS.Invertory.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    public boolean isInStock(String skuCode);

    public void addInventory(InventoryRequest inventoryRequest);

    public List<InventoryResponse> getAllInventory();
}
