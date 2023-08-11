package com.Security.SessionSecurity.Security;


import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    @Qualifier("customAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;
    @Autowired
    private CustomAuthenticationFilter customAuthenticationFilter;
    private JwtUtil jwtUtil;

    public SecurityConfig(PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {

        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
                                           AuthenticationManager authenticationManager) throws Exception {

        CustomUsernamePasswordAuthenticationFilter authentication = new CustomUsernamePasswordAuthenticationFilter(this.jwtUtil);
        authentication.setAuthenticationManager(authenticationManager);
        authentication.setFilterProcessesUrl("/login");
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                ).httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()))
                .addFilter(authentication)
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //.exceptionHandling((exception) -> exception.authenticationEntryPoint(authEntryPoint))
                .build();

    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.userDetailsService(userDetailsService)
                .getSharedObject(AuthenticationManagerBuilder.class)
                .build();
        //.userDetailsService(userDetailsService())
        //.passwordEncoder(passwordEncoder()).

    }

    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public InMemoryUserDetailsManager userDetails() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1")
                        .passwordEncoder(passwordEncoder::encode)
                        .password("12345")
                        .roles("USER")
                        .build()
        );
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }
}
