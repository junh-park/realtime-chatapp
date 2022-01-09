package com.jun.chatapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private final String USER = "USER";
	private final String ADMIN = "ADMIN";
	
	private final UserDetailsService userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("password1")).roles(USER, ADMIN);
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http	
			.cors().and().csrf().disable();
		
		http
			.authorizeRequests()
				.antMatchers("/", "/login.html").permitAll()
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login.html")
				.defaultSuccessUrl("/chat", true)
			.and()
			.logout()
				.deleteCookies("JSESSIONID")
				.permitAll();
	}
	
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/static/**", "/css/**", "/js/**");
	}
	
}
