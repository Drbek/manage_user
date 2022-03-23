package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.concurrent.TimeUnit;
import org.springframework.http.CacheControl;
@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class DemoApplication  implements WebMvcConfigurer {
                
                  
    public static void main(String[] args) {
    	SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Register resource handler for images
        registry.addResourceHandler("/images**").addResourceLocations("classpath:/resources/" ,"classpath:/images/")
                .setCachePeriod(0);
    }      
}