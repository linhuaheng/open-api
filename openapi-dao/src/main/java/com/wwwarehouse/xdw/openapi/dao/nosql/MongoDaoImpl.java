package com.wwwarehouse.xdw.openapi.dao.nosql;

import com.mongodb.WriteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class MongoDaoImpl<T> implements MongoDao<T>{
    private static Logger log = LogManager.getLogger(MongoDao.class);

    @Resource
    protected MongoTemplate mongoTemplate;

    public MongoDaoImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public void insert(T t) {
        mongoTemplate.insert(t);
    }

    public List<T> gets(Query query, Class<T> clazz) {
        return mongoTemplate.find(query, clazz);
    }

    public WriteResult update(Query query, Update update, Class<T> clazz) {
        WriteResult result = mongoTemplate.updateMulti(query, update, clazz);
        return result;
    }

    public void dumpRecords(List<?> records) {
        if (records != null)
            for (Object item : records) {
                try {
                    mongoTemplate.insert(item);
                } catch (Exception e) {
                    log.error(e);
                }
            }
    }
}
