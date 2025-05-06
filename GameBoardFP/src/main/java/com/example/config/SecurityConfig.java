package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		// アクセス権限に関する設定
		http.authorizeHttpRequests(
						// /はアクセス制限をかけない
						(requests) -> requests.requestMatchers("/").permitAll()
								.requestMatchers("/css/**").permitAll() //cssへアクセス許可
								.requestMatchers("/upload/**").permitAll()
								.requestMatchers("/signup").permitAll()
								.requestMatchers("/login").permitAll()
								.requestMatchers("/board").permitAll()
								.requestMatchers("/thread").permitAll()
								//ADMINロールを持つユーザだけアクセス可能
								.requestMatchers("/admin").hasRole("ADMIN")
								.requestMatchers("/thread_delete").hasRole("ADMIN")
								.requestMatchers("/genre_add").hasRole("ADMIN")
								// それ以外のページは認証が必要
								.anyRequest().authenticated())
				.formLogin((form) -> form
						// ログインを実行するページを指定。
						.loginProcessingUrl("/answer")
						// ログイン画面の設定
						.loginPage("/login")
						// ログインに失敗した場合の遷移先
						.failureUrl("/login")
						// ユーザIDとパスワードのname設定
						.usernameParameter("name")
						.passwordParameter("pass")
						// ログインに成功した場合の遷移先
						.defaultSuccessUrl("/answer",true))
				.logout((form) -> form
						// ログアウト処理を行うページ指定
						.logoutUrl("/logout")
						// ログアウトした場合の遷移先
						.logoutSuccessUrl("/"));

		
		return http.build();
	}

	//ハッシュ化
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
