package ru.itpark.java;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itpark.common.JsonPropertyBeanFactoryPostProcessor;
import ru.itpark.common.cached.CachedBeanPostProcessor;
import ru.itpark.common.services.PostService;
import ru.itpark.common.services.PostServiceImpl;
import ru.itpark.common.services.RequestClient;

@Configuration
public class JavaConfig {

    @Bean
    public JavaConfigRequestClient requestClient() {
        return new JavaConfigRequestClient();
    }

    @Bean(name = "postService")
    public PostService postService(RequestClient requestClient) {
        return new PostServiceImpl(requestClient);
    }

    @Bean
    public CachedBeanPostProcessor cachedBeanPostProcessor() {
        return new CachedBeanPostProcessor();
    }

    @Bean
    public JsonPropertyBeanFactoryPostProcessor jsonPropertyBeanFactoryPostProcessor() {
        return new JsonPropertyBeanFactoryPostProcessor("prop.json");
    }
}