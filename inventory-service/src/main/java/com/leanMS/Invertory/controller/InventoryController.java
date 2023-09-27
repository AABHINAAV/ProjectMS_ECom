package com.leanMS.Invertory.controller;

import com.leanMS.Invertory.dto.InventoryRequest;
import com.leanMS.Invertory.dto.InventoryResponse;
import com.leanMS.Invertory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin("*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStack(@PathVariable("sku-code") String skuCode) {
        return this.inventoryService.isInStock(skuCode);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addInventory(@RequestBody InventoryRequest inventoryRequest) {
        this.inventoryService.addInventory(inventoryRequest);
        return "Inventory generated";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventory() {
        return this.inventoryService.getAllInventory();
    }
}
