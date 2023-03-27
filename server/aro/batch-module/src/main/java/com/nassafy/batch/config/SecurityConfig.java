package com.nassafy.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    private final CorsFilter corsFilter;

    public SecurityConfig(
            CorsFilter corsFilter
    ) {
        this.corsFilter = corsFilter;//////////
    }

//    @Bean
//    public void configure(WebSecurity web) throws Exception
//    {
//        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
//        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
//        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
//                "/swagger-ui.html", "/webjars/**", "/swagger/**");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        httpSecurity
                .httpBasic().disable()
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()

                // enable h2-console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
//                .requestMatchers("/api/hello", "/api/authenticate", "/api/signup").permitAll()
//                .antMatchers("/api/hello", "/api/authenticate", "/api/signup").permitAll()
//                .antMatchers("/**").permitAll()
//                .antMatchers("/api/**").permitAll()
//                .antMatchers("/article").permitAll()
//                .antMatchers("/test").permitAll()
                .antMatchers("/batch/fcm/**").permitAll()
//                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()


//                .and()
//                .logout() // 로그아웃을 지원하는 메소드
//                .logoutSuccessUrl("/") // 로그아웃 성공시 이동되는 페이지
//                .invalidateHttpSession(true) // HTTP 세션을 초기화하는 작업
        ;
        return httpSecurity.build();
    }

//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder().encode("1234")).roles("ADMIN")
//                .and()
//                .withUser("guest").password(passwordEncoder().encode("guest")).roles("GUEST");
//    }
}
