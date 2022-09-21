package com.example.demo.Security;

import com.example.demo.auth.ApplicationUserService;
import com.example.demo.jwt.JwtConfig;
import com.example.demo.jwt.JwtTokenVerifier;
import com.example.demo.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                                     ApplicationUserService applicationUserService, SecretKey secretKey, JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login", "index", "/css/*", "js/*")
                .permitAll()
                .antMatchers("/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.STUDENT.name())
//                .antMatchers(HttpMethod.DELETE,"/management/**").hasAuthority(ApplicationUserPermission.Course_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/management/**").hasAuthority(ApplicationUserPermission.Course_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/**").hasAuthority(ApplicationUserPermission.Course_WRITE.getPermission())
//                .antMatchers("/management/**").hasRole(ApplicationUserRole.ADMIN.name())
                .antMatchers("/management/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }


//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService(){
//        UserDetails stevenSmithUser = User.builder()
//                .username("Steven")
//                .password(passwordEncoder.encode("password"))
////                .roles(ApplicationUserRole.STUDENT.name()) // ROLE_STUDENT
//                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
//                .build();
//
//        UserDetails tomUser = User.builder()
//                .username("Tom")
//                .password(passwordEncoder.encode("password123"))
////                .roles(ApplicationUserRole.ADMIN.name()) // ROLE_ADMIN
//                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails thomasUser = User.builder()
//                .username("Thomas")
//                .password(passwordEncoder.encode("password123"))
////                .roles(ApplicationUserRole.ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
//                .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                stevenSmithUser,
//                tomUser,
//                thomasUser
//        );
//    }


}
