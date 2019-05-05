package ru.itpark;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import ru.itpark.common.JsonPropertyBeanFactoryPostProcessor;
import ru.itpark.common.cached.CachedBeanPostProcessor;
import ru.itpark.common.model.Model;
import ru.itpark.common.services.PostService;
import ru.itpark.common.services.PostServiceImpl;
import ru.itpark.java.JavaConfig;
import ru.itpark.programmatic.ProgrammaticRequestClient;

public class Main {
    public static void main(String[] args) {
        {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
            PostService postService = (PostService) context.getBean("postService");
            Model modelFirst = postService.get(5);
            Model modelSecond = postService.get(15);
            Model modelThird = postService.get(5);
            Model modelFourth = postService.get(15);
        }
        {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.itpark");
            PostService postService = (PostService) context.getBean("postService");
            Model modelFirst = postService.get(6);
            Model modelSecond = postService.get(16);
            Model modelThird = postService.get(6);
            Model modelFourth = postService.get(16);
        }
        {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
            PostService postService = (PostService) context.getBean("postService");
            Model modelFirst = postService.get(7);
            Model modelSecond = postService.get(17);
            Model modelThird = postService.get(7);
            Model modelFourth = postService.get(17);
        }
        {
            GenericApplicationContext context = new AnnotationConfigApplicationContext();
            context.registerBean(JsonPropertyBeanFactoryPostProcessor.class);
            context.registerBean(CachedBeanPostProcessor.class);

            ProgrammaticRequestClient requestClient = new ProgrammaticRequestClient();
            context.registerBean(ProgrammaticRequestClient.class, () -> requestClient);
            context.registerBean("postService", PostServiceImpl.class, () -> new PostServiceImpl(requestClient));
            context.refresh();

            PostService postService = (PostService) context.getBean("postService");
            Model modelFirst = postService.get(8);
            Model modelSecond = postService.get(18);
            Model modelThird = postService.get(8);
            Model modelFourth = postService.get(18);
        }
        {
            GenericGroovyApplicationContext context = new GenericGroovyApplicationContext("beans.groovy");
            PostService postService = (PostService) context.getBean("postService");
            Model modelFirst = postService.get(9);
            Model modelSecond = postService.get(19);
            Model modelThird = postService.get(9);
            Model modelFourth = postService.get(19);
        }
    }
}