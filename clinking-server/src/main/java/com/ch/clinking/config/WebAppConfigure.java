package com.ch.clinking.config;

import com.ch.clinking.interceptor.SysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;

@Configuration
public class WebAppConfigure implements WebMvcConfigurer {


    @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", clazz -> clazz.isAnnotationPresent(RestController.class));
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE","OPTIONS")
                .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/swiper/**").addResourceLocations("file:/root/MinZuStore/Image/SwiperImage/");
        registry.addResourceHandler("/image/homeType/**").addResourceLocations("file:/root/MinZuStore/Image/HomeTypeImage/");
        registry.addResourceHandler("/image/product/**").addResourceLocations("file:/root/MinZuStore/Image/ProductsImage/");
        registry.addResourceHandler("/image/productDetail/**").addResourceLocations("file:/root/MinZuStore/Image/ProductsDetailImage/");
        registry.addResourceHandler("/image/adminAvatar/**").addResourceLocations("file:/root/MinZuStore/Image/AdminAvatar/");
//        registry.addResourceHandler("/image/swiper/**").addResourceLocations("file:C:\\Users\\汽水\\Desktop\\mzstore\\Image\\SwiperImage\\");
//        registry.addResourceHandler("/image/homeType/**").addResourceLocations("file:C:\\Users\\汽水\\Desktop\\mzstore\\Image\\HomeTypeImage\\");
//        registry.addResourceHandler("/image/product/**").addResourceLocations("file:C:\\Users\\汽水\\Desktop\\mzstore\\Image\\ProductsImage\\");
//        registry.addResourceHandler("/image/productDetail/**").addResourceLocations("file:C:\\Users\\汽水\\Desktop\\mzstore\\Image\\ProductsDetailImage\\");
//        registry.addResourceHandler("/image/adminAvatar/**").addResourceLocations("file:C:\\Users\\汽水\\Desktop\\mzstore\\Image\\AdminAvatar\\");
    }

    @Bean
    public SysInterceptor sysInterceptor() {
        return new SysInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] patterns = new String[] {"/adminLogin","/product/**","/homeType/**","/user/wxlogin","/weixinpay/**","/api/user/login",
                "/api/user/checkLogin"};
        registry.addInterceptor(sysInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns(patterns);
//        registry.addInterceptor(sysInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns(patterns);
    }

}
