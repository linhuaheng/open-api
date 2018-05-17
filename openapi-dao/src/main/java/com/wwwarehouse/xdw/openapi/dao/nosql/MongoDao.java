package com.wwwarehouse.xdw.openapi.dao.nosql;

import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created by huahui.wu on 2017/7/10.
 */
public interface MongoDao<T> {

    void insert(T t);

    List<T> gets(Query query, Class<T> clazz);

    WriteResult update(Query query, Update update, Class<T> clazz);

    void dumpRecords(List<?> records);
}
