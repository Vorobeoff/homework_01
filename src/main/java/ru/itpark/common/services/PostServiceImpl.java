package ru.itpark.common.services;

import org.springframework.stereotype.Component;
import ru.itpark.common.model.Model;

@Component("postService")
public class PostServiceImpl implements PostService {

    private RequestClient requestClient;

    public PostServiceImpl(RequestClient requestClient) {
        this.requestClient = requestClient;
    }

    @Override
    public Model get(Integer id) {
        try {
            return requestClient.getModel(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}