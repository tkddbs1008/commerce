package zerobase.commerce.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import zerobase.commerce.model.AccountDto;
import zerobase.commerce.persist.AccountRepository;
import zerobase.commerce.persist.entity.Account;

@Service
@AllArgsConstructor
public class AccountService {
	
	private final AccountRepository accountRepository;
	
	
	//우저 가입
	public Account register(AccountDto member) {
		boolean exists = this.accountRepository.existsByUsername(member.getUsername());
		if(exists) {
			throw new RuntimeException("already exists");
		}
	
		var result = this.accountRepository.save(member.toEntity());
		
		return result;
	}
	
	//유저 삭제
	public void deleteUser(AccountDto member) {
		var user = this.accountRepository.findByUsername(member.getUsername())
                .orElseThrow(() -> new RuntimeException("user does not exists"));

	    if (member.getPassword() != user.getPassword()) {
	        throw new RuntimeException("wrong password");
	    }
	    
	    accountRepository.delete(user);
    }
	
	//유저 로그인
	public Account authenticate(AccountDto member) {
		var user = this.accountRepository.findByUsername(member.getUsername())
	                .orElseThrow(() -> new RuntimeException("user does not exists"));
		int check = member.getPassword().compareTo(user.getPassword());
        if (check != 0) {
            throw new RuntimeException("wrong password");
        }

        return user;
	}
}
