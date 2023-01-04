package hsleiden.iprwc.config;

import hsleiden.iprwc.DAOs.UserDAO;
import hsleiden.iprwc.filters.CustomAuthenticationFilter;
import hsleiden.iprwc.filters.CustomAuthorizationFilter;
import hsleiden.iprwc.filters.CustomCorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final UserDAO userDAO;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService, UserDAO userDAO) {
        this.userDetailsService = userDetailsService;
        this.userDAO = userDAO;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager, userDAO);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        return http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationManager(authenticationManager)
                .authorizeRequests().antMatchers("/api/login").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST,  "/api/users/admins/**").hasAnyAuthority("ROLE_SUPERADMIN")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST,  "/api/**").hasAnyAuthority("ROLE_ADMIN")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.PUT,  "/api/users/admins/**").hasAnyAuthority("ROLE_SUPERADMIN")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.PUT,  "/api/**").hasAnyAuthority("ROLE_ADMIN")
                .and()
                .authorizeRequests().antMatchers(HttpMethod.DELETE,  "/api/**").hasAnyAuthority("ROLE_SUPERADMIN")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomCorsFilter(), ChannelProcessingFilter.class)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }
}





