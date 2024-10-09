package com.project.fsneaker.services;

import com.project.fsneaker.dtos.ProductDTO;
import com.project.fsneaker.dtos.ProductImageDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.models.Product;
import com.project.fsneaker.models.ProductImage;
import com.project.fsneaker.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

public interface IProductService {

    Product createProduct(ProductDTO productDTO) throws Exception;

    Product getProductById(long id) throws Exception;

    Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);

    Product updateProduct(long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception;

}
