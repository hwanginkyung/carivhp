package carivex.homepages.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Value("${ADMIN_USERNAME:admin}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD:admin1234}")
    private String adminPassword;

    @Value("${app.cors.allowed-origins:*}")
    private String[] corsAllowedOrigins;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername(adminUsername)
                        .password(encoder.encode(adminPassword))
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // NOTE: To keep the provided static admin HTML working out-of-the-box,
        // CSRF is disabled. Enable CSRF later and add CSRF tokens to forms.
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/", "/index.html",
                        "/en/**",
                        "/sub*.html",
                        "/css/**", "/js/**", "/img/**",
                        "/files/**",
                        "/inquiry"
                ).permitAll()
                .requestMatchers("/admin/login", "/admin/login.html").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        );

        http.formLogin(form -> form
                .loginPage("/admin/login.html")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/admin/notice.html", true)
                .failureUrl("/admin/login.html?error=1")
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login.html?logout=1")
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        if (corsAllowedOrigins.length == 1 && "*".equals(corsAllowedOrigins[0])) {
            configuration.setAllowedOriginPatterns(List.of("*"));
        } else {
            configuration.setAllowedOrigins(Arrays.asList(corsAllowedOrigins));
        }
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
