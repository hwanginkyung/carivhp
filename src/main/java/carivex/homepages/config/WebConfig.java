package carivex.homepages.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.time.Duration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload-dir:./uploads}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        CacheControl staticCache = CacheControl.maxAge(Duration.ofDays(30))
                .cachePublic()
                .immutable();
        registry.addResourceHandler("/css/**", "/js/**", "/img/**", "/fonts/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/js/",
                        "classpath:/static/img/", "classpath:/static/fonts/")
                .setCacheControl(staticCache);

        // Serve uploaded files under /uploads/** (mainly for preview, downloads use /files/** controller)
        Path uploadPath = Path.of(uploadDir).toAbsolutePath().normalize();
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath.toUri().toString())
                .setCacheControl(CacheControl.noCache());
    }
}
