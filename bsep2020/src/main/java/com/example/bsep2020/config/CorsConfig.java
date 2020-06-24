package com.example.bsep2020.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.bsep2020.repository.UserRepository;
import com.example.bsep2020.service.CustomUserDetailServiceImpl;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class CorsConfig  extends WebSecurityConfigurerAdapter{
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowedHeaders("*")
					.allowedOrigins("http://localhost:4200");
			}
		};
	}
	
	@Autowired
	private CustomUserDetailServiceImpl userDetailService;

	@Bean
	public BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder(); //treba videti za bcrypt pass nec kako treba da sacuvam prvi podatak da bi ga prohvatio
	} 
	
	@Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService)
			.passwordEncoder(getEncoder());
		//password encoder
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("**/secured").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin().permitAll(); // umesto formlogin, moze nas login page da se ubaci
		//testirati i implkementirati na zelejen endpointe
		
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).
        passwordEncoder(getEncoder());
    }
	
	
	

}
