package zerobase.commerce.persist;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import zerobase.commerce.persist.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByName(String name);

	Page<Product> findByNameStartingWithIgnoreCase(String keyword, Pageable limit);

	boolean existsByProductName(String productName);
}
