package com.project.fsneaker.services;

import com.project.fsneaker.dtos.ProductDTO;
import com.project.fsneaker.dtos.ProductImageDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.exceptions.InvalidParamException;
import com.project.fsneaker.models.Category;
import com.project.fsneaker.models.Product;
import com.project.fsneaker.models.ProductImage;
import com.project.fsneaker.repositories.CategoryRepository;
import com.project.fsneaker.repositories.ProductImageRepository;
import com.project.fsneaker.repositories.ProductRepository;
import com.project.fsneaker.responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find category with id: "+productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(long id) throws Exception {
        return productRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Cannot find product with id: "+id)
        );
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(
                product -> {
                    ProductResponse productResponse = ProductResponse.builder()
                            .name(product.getName())
                            .price(product.getPrice())
                            .thumbnail(product.getThumbnail())
                            .description(product.getDescription())
                            .categoryId(product.getCategory().getId())
                            .build();
                    productResponse.setCreatedAt(product.getCreatedAt());
                    productResponse.setUpdatedAt(product.getUpdatedAt());
                    return productResponse;
                });
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot find category with id: "+productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot find product with id: "+productImageDTO.getProductId()));
        ProductImage productImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // Ko cho insert > 5 images cho 1 product
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParamException("Number of images must be <= "+ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(productImage);
    }

}
