package com.gestionproyectos.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.path:/home/comfa/gestionproyectos/frontend/dist/gestionproyectos-frontend/browser}")
    private String frontendPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + frontendPath + "/")
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new org.springframework.web.servlet.resource.PathResourceResolver() {
                    @Override
                    protected org.springframework.core.io.Resource getResource(String resourcePath, org.springframework.web.servlet.resource.ResourceHttpRequestHandler requestHandler) {
                        org.springframework.core.io.Resource resource = null;
                        try {
                            resource = super.getResource(resourcePath, requestHandler);
                        } catch (Exception e) {
                        }
                        if (resource == null || !resource.exists()) {
                            if (!resourcePath.contains(".") || resourcePath.endsWith("/")) {
                                try {
                                    java.nio.file.Path indexPath = java.nio.file.Path.of(frontendPath, "index.html");
                                    if (java.nio.file.Files.exists(indexPath)) {
                                        return new org.springframework.core.io.FileSystemResource(indexPath);
                                    }
                                } catch (Exception ex) {
                                }
                            }
                        }
                        return resource;
                    }
                });
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}