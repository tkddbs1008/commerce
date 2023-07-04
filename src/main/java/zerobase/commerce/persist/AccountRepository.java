package zerobase.commerce.persist;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import zerobase.commerce.persist.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByUsername(String username);

	boolean existsByUsername(String username);

}
