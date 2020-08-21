package demo.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers("/auth").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .loginPage("/auth.html")
        .permitAll()
        .loginProcessingUrl("/auth")
        .defaultSuccessUrl("/",false)
        .failureUrl("/auth");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        
    }
}
