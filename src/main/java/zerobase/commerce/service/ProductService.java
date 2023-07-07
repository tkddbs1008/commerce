package zerobase.commerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import zerobase.commerce.model.ProductDto;
import zerobase.commerce.persist.ProductRepository;
import zerobase.commerce.persist.entity.Product;

@Service
@AllArgsConstructor
public class ProductService {

	private final Trie<String, String> trie;
	
	private final ProductRepository productRepository;
	
	
	public Product addProduct(ProductDto product) {
		boolean exists = this.productRepository.existsByProductName(product.getProductName());
		if(exists) {
			throw new RuntimeException("already exists");
		}
		var result = this.productRepository.save(product.toEntity());
		
		return result;
	}
	
	public void addAutoCompleteKeyword(String keyword) {
		this.trie.put(keyword, null);
	}
	
	public List<String> getProductNamesByKeyword(String keyword){
		Pageable limit = PageRequest.of(0, 10);
		Page<Product> productEntities = this.productRepository
				.findByNameStartingWithIgnoreCase(keyword, limit);
		return productEntities.stream()
								.map(e -> e.getProductName())
								.collect(Collectors.toList());
	}
	
	public void deleteAutocompleteKeyword(String keyword) {
		this.trie.remove(keyword);
	}
	
	public String deleteProduct(String name) {
        Product product = this.productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("없는 상품의 이름입니다"));

        this.productRepository.deleteById(product.getId());
        this.deleteAutocompleteKeyword(product.getProductName());

        return product.getProductName();
    }
}
