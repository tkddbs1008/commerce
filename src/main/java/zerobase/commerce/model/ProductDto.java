package zerobase.commerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.commerce.persist.entity.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private String productName;
	
	public Product toEntity() {
        return Product.builder()
                .productName(this.productName)
                .build();
    }
}
