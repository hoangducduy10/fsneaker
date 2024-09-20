package com.project.fsneaker.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductImageDTO {

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product's ID must be > 0!")
    private Long productId;

    @JsonProperty("image_url")
    @Size(min = 5, max = 200, message = "Image's name!")
    private String imageUrl;

}
