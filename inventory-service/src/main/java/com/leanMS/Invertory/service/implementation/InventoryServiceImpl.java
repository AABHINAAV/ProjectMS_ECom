package com.leanMS.Invertory.service.implementation;

import com.leanMS.Invertory.dto.InventoryRequest;
import com.leanMS.Invertory.dto.InventoryResponse;
import com.leanMS.Invertory.model.Inventory;
import com.leanMS.Invertory.repository.InventoryRepository;
import com.leanMS.Invertory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public boolean isInStock(String skuCode) {
        return this.inventoryRepository.findBySkuCode(skuCode).isPresent();
    }

    @Override
    public void addInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = Inventory.builder()
                .skuCode(inventoryRequest.getSkuCode())
                .quantity(inventoryRequest.getQuantity())
                .build();

        this.inventoryRepository.save(inventory);
    }

    @Override
    public List<InventoryResponse> getAllInventory() {
        List<Inventory> inventories = this.inventoryRepository.findAll();
        List<InventoryResponse> inventoryResponses = inventories.stream()
                .map(inventory -> inventory_to_inventoryResponse(inventory))
                .toList();
        return inventoryResponses;
    }

    private InventoryResponse inventory_to_inventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }
}
