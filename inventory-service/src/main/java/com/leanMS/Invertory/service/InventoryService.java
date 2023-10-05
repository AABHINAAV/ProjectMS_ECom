package com.leanMS.Invertory.service;

import com.leanMS.Invertory.dto.InventoryRequest;
import com.leanMS.Invertory.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    public boolean isInStock(String skuCode);

    public List<InventoryResponse> isInStock(List<String> skuCode);

    public void addInventory(InventoryRequest inventoryRequest);

    public List<InventoryResponse> getAllInventory();
}
