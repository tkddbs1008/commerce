package zerobase.commerce.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.commerce.persist.entity.Account;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
	private String username;
	private String password;
	
	public Account toEntity() {
        return Account.builder()
                .password(this.password)
                .username(this.username)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
