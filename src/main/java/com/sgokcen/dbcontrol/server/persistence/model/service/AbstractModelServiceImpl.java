package com.sgokcen.dbcontrol.server.persistence.model.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sgokcen.dbcontrol.server.dto.DataTransferObject;
import com.sgokcen.dbcontrol.server.exception.ResourceAlreadyExistsException;
import com.sgokcen.dbcontrol.server.exception.ResourceNotFoundException;
import com.sgokcen.dbcontrol.server.persistence.dao.DataAccessObject;
import com.sgokcen.dbcontrol.server.persistence.model.AbstractEntity;
import com.sgokcen.dbcontrol.server.service.ModelService;
import com.sgokcen.dbcontrol.server.service.RandomStringService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class AbstractModelServiceImpl<E extends AbstractEntity, T extends DataTransferObject> implements ModelService<T> {

    private static final Logger LOGGER =  LoggerFactory.getLogger(AbstractModelServiceImpl.class);
    
    private DataAccessObject<E, Long> dao;
    private RandomStringService random;
    private Class<E> entityType;
    private Class<T> dtoType;

    public AbstractModelServiceImpl(DataAccessObject<E, Long> dao, RandomStringService random, Class<E> entityType, Class<T> dtoType) {
        this.dao = dao;
        this.random = random;
        this.entityType = entityType;
        this.dtoType = dtoType;
    }
    
    public DataAccessObject<E, Long> getDAO() {
        return dao;
    }

    protected E getEntity(T info) {
        E entity = null;

        Long primaryKey = info.getPk(); 
        String uniqueId = info.getUniqueId();

        if (primaryKey != null && primaryKey > 0) {
            Optional<E> maybeEntity = dao.findById(primaryKey);
            if (maybeEntity.isPresent()) {
                entity = maybeEntity.get();
            }
        }
        else if (uniqueId != null) {
            Optional<E> maybeEntity = dao.findByUniqueId(uniqueId);
            if (maybeEntity.isPresent()) {
                entity = maybeEntity.get();
            }
        }

        return entity;
    }

    public List<T> getModels(int page, int limit) {
        // start paging from 1
        if (page > 0) {
            page -= 1;
        }

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<E> pages = dao.findAll(pageableRequest);
        List<E> entityList = pages.getContent();

        List<T> models = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        entityList.forEach(entity -> {
            models.add(mapper.map(entity, dtoType));
        });
        return models;
    }
    
    public T createModel(T info) {
        return createModel(info, new AbstractEntity[] {});
    }
    
    public <K extends AbstractEntity> T createModel(T info, @SuppressWarnings("unchecked") K... refs) {
        ModelMapper mapper = new ModelMapper();

        E exisitingEntity = getEntity(info);
        
        if (exisitingEntity != null) {
            throw new ResourceAlreadyExistsException();
        }
        
        E entity = mapper.map(info, entityType);
        entity.setUniqueId(random.next());
        
        //TODO: set referenced entities
        int k = 0;
        Field[] declaredFields = entityType.getDeclaredFields();
        for (Field f : declaredFields) {
            if (f.getType().getSuperclass() == AbstractEntity.class) {
                try {
                    String fieldName = f.getName();
                    String setterMethodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    Method setter = entityType.getMethod(setterMethodName, f.getType());
                    setter.invoke(entity, refs[k++]);
                }
                catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        E savedEntity = dao.save(entity);
        return mapper.map(savedEntity, dtoType);
    }

    public T getModel(T info) {
        E entity = getEntity(info);

        if (entity != null) {
            return new ModelMapper().map(entity, dtoType);
        }

        throw new ResourceNotFoundException();
    }

    public void deleteModel(T info) {
        E entity = getEntity(info);

        if (entity != null) {
            dao.delete(entity);
            
            return;
        }
        
        throw new ResourceNotFoundException();
    }

    public T updateModel(T info) {
        E entity = getEntity(info);
        
        if (entity == null) {
            throw new ResourceNotFoundException();
        }

        Field[] fields = entityType.getDeclaredFields();
        String fieldName = null;
        ParameterizedType genericType = null;
        String getterMethodName = null, setterMethodName = null;
        Method getter = null, setter = null;
        Object value = null;
        for (Field f: fields) {
            try {
                fieldName = f.getName();
                if (f.getGenericType() != null && f.getGenericType() instanceof ParameterizedType) {
                    genericType = (ParameterizedType) f.getGenericType();
                }
                getterMethodName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                getter = dtoType.getMethod(getterMethodName);
                value = getter.invoke(info);
                if (value != null) {
                
                    setterMethodName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                    setter = entityType.getMethod(setterMethodName, f.getType());
                    
                    if (genericType != null && genericType.getRawType() == List.class) {
                        Type[] actualTypeArguments = genericType.getActualTypeArguments();
                        List<? extends AbstractEntity> newValue = new ArrayList<>();
                        ((List<?>)value).stream().forEach(o->newValue.add(new ModelMapper().map(o, actualTypeArguments[0])));
                        
                        setter.invoke(entity, newValue);
                    }
                    else if (AbstractEntity.class.isInstance(value)) {
                        setter.invoke(entity, new ModelMapper().map(value, f.getType()));
                    }
                    else {
                        setter.invoke(entity, value);
                    }
                }
            }
            catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                LOGGER.warn("cannot update a field");
            }
        }

        E savedEntity = dao.save(entity);
        return new ModelMapper().map(savedEntity, dtoType);
    }

}
