package ru.itpark.common;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class JsonPropertyBeanFactoryPostProcessor extends PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    private String locations;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Properties mergedProps = loadProperties();
        convertProperties(mergedProps);
        processProperties(beanFactory, mergedProps);
    }

    private Properties loadProperties() {
        Properties props = new Properties();
        if (this.locations != null) {
            InputStream resource = JsonPropertyBeanFactoryPostProcessor.class.getClassLoader().getResourceAsStream(locations);
            DocumentContext properties = JsonPath.parse(resource);
            LinkedHashMap<String, String> jsonProp = properties.json();
            for (Map.Entry<String, String> entry : jsonProp.entrySet()) {
                props.put(entry.getKey(), entry.getValue());
            }
        }
        return props;
    }

    public JsonPropertyBeanFactoryPostProcessor() {
        this.locations = "prop.json";
    }

    public JsonPropertyBeanFactoryPostProcessor(String locations) {
        this.locations = locations;
    }
}