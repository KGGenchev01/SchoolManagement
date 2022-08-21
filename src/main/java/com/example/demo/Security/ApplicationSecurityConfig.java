package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","index","/css/*","js/*")
                .permitAll()
                .antMatchers("/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.STUDENT.name())
//                .antMatchers(HttpMethod.DELETE,"/management/**").hasAuthority(ApplicationUserPermission.Course_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/management/**").hasAuthority(ApplicationUserPermission.Course_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/**").hasAuthority(ApplicationUserPermission.Course_WRITE.getPermission())
//                .antMatchers("/management/**").hasRole(ApplicationUserRole.ADMIN.name())
                .antMatchers("/management/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),ApplicationUserRole.ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }
    @Override
    @Bean
    protected UserDetailsService userDetailsService(){
       UserDetails stevenSmithUser = User.builder()
                .username("Steven")
                .password(passwordEncoder.encode("password"))
//                .roles(ApplicationUserRole.STUDENT.name()) // ROLE_STUDENT
                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
                .build();

        UserDetails tomUser = User.builder()
                .username("Tom")
                .password(passwordEncoder.encode("password123"))
//                .roles(ApplicationUserRole.ADMIN.name()) // ROLE_ADMIN
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();

        UserDetails thomasUser = User.builder()
                .username("Thomas")
                .password(passwordEncoder.encode("password123"))
//                .roles(ApplicationUserRole.ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
                .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
                .build();

         return new InMemoryUserDetailsManager(
                stevenSmithUser,
                tomUser,
                thomasUser
        );
    }

}
