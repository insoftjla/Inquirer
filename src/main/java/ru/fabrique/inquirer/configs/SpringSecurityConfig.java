package ru.fabrique.inquirer.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/polls/user/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/polls").permitAll()
                .antMatchers(HttpMethod.GET, "/polls/user?").permitAll()
                .antMatchers("/polls/{\\d+}/pass").permitAll()
                .antMatchers(HttpMethod.GET, "/polls/{\\d+}").permitAll()
                .antMatchers(HttpMethod.GET, "/v2/*").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}
