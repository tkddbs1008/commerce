package zerobase.commerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import zerobase.commerce.config.TokenProvider;
import zerobase.commerce.model.AccountDto;
import zerobase.commerce.service.AccountService;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;
	private final TokenProvider tokenProvider;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody AccountDto request){
		var result = this.accountService.register(request);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody AccountDto request){
		var member = this.accountService.authenticate(request);
        var token = this.tokenProvider.generateToken(member.getUsername());
        log.info("user login -> " + request.getUsername());
        return ResponseEntity.ok(token);
	}
	
	@DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody AccountDto request) {
		accountService.deleteUser(request);
        return ResponseEntity.ok("delete successfully");
    }
}
