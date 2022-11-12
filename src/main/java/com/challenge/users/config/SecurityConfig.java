package com.challenge.users.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.challenge.users.jwt.CustomAuthenticationFilter;
import com.challenge.users.jwt.CustomAuthorizationFilter;
import com.challenge.users.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final UserDetailsService userDetailsService;
    
    private final BCryptPasswordEncoder decryptPasswordEncoder;

    private final ApplicationContext context;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService).passwordEncoder(decryptPasswordEncoder);
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		CustomAuthenticationFilter customAuthFilter = new CustomAuthenticationFilter(authenticationManagerBean(), context.getBean(UserRepository.class));
		customAuthFilter.setFilterProcessesUrl("/challenge/v1/login");
		http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
		http.authorizeRequests()
		.antMatchers("/challenge/v1/sign_up", "/h2-console/**", "/challenge/v1/login").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(customAuthFilter);
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
