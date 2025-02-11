package com.project.fsneaker.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.fsneaker.models.Product;
import com.project.fsneaker.models.ProductImage;
import com.project.fsneaker.services.TranslationService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse extends BaseResponse{

    private Long id;

    private String name;

    private Float price;

    private String thumbnail;

    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("product_images")
    private List<ProductImage> productImages = new ArrayList<>();

    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .productImages(product.getProductImages())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }

    public static ProductResponse fromProductWithTranslation(
            Product product,
            TranslationService translationService,
            Locale locale
    ) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(translationService.translate(
                        "product.name",
                        new Object[]{product.getName()},
                        locale
                ))
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(translationService.translate(
                        "product.description",
                        new Object[]{product.getDescription()},
                        locale
                ))
                .categoryId(product.getCategory().getId())
                .productImages(product.getProductImages())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }

}

