import ru.itpark.common.JsonPropertyBeanFactoryPostProcessor
import ru.itpark.common.cached.CachedBeanPostProcessor
import ru.itpark.common.services.PostServiceImpl
import ru.itpark.groovy.GroovyRequestClient

beans {
    xmlns([ctx: 'http://www.springframework.org/schema/context'])
    ctx.'annotation-config'()
    groovyRequestClient GroovyRequestClient
    postService PostServiceImpl, ref(groovyRequestClient)
    cachedBeanPostProcessor CachedBeanPostProcessor
    jsonPropertyBeanFactoryPostProcessor JsonPropertyBeanFactoryPostProcessor
}