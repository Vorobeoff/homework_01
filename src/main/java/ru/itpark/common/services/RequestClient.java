package ru.itpark.common.services;

import ru.itpark.common.model.Model;

public interface RequestClient {
    Model getModel(Integer id) throws Exception;
}