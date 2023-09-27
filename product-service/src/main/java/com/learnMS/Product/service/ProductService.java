package com.learnMS.Product.service;

import com.learnMS.Product.dto.ProductRequest;
import com.learnMS.Product.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    public void createProduct(ProductRequest productRequest);

    public List<ProductResponse> getAllProducts();
}
