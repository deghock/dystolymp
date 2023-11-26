package ru.spbu.distolymp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.spbu.distolymp.security.AuthProviderImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthProviderImpl authProvider;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }

    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder)
//                .withUser("user").password(passwordEncoder.encode("user123")).roles("USER")
//                .and()
//                .withUser("admin").password(passwordEncoder.encode("admin123")).roles("USER", "ADMIN");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/", "/welcome", "/login").permitAll()
//                .antMatchers("/authenticated/**").hasAnyRole("ADMIN", "USER")
//                .and().formLogin()
//                .and().logout().logoutSuccessUrl("/welcome").permitAll()
//                .and().csrf().disable();
//    }
}
