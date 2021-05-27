package com.sgokcen.dbcontrol.server.service;

import java.util.List;

import com.sgokcen.dbcontrol.server.dto.DataTransferObject;

public interface ModelService<T extends DataTransferObject> {
    public List<T> getModels(int page, int limit);
    public T createModel(T info);
    public T getModel(T modelInfo);
    public void deleteModel(T modelInfo);
    public T updateModel(T modelInfo);

}
