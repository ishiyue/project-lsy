package com.lsy.test.system.common.mongodb;

import com.google.common.collect.Lists;
import com.lsy.test.system.common.page.Page;
import com.lsy.test.system.common.result.QueryList;
import com.lsy.test.system.common.util.CheckUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author lsy
 * @since 2019/12/3 14:51
 */
@Component
public class MongoQuerytempalte {


    private static final Logger LOGGER = LoggerFactory.getLogger(MongoQuerytempalte.class);

    private static final String TOTAL = "total";

    private static final String GMT_CREATE_TIME = "gmt_create";

    private static final String ID = "_id";


    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 分页查询,单条件
     *
     * @param table
     * @param fields
     * @param paCriteria
     * @return
     */
    public QueryList<Document> queryPageMongoTemplate(Page page, String table, String[] fields, Criteria paCriteria, Sort sort) {
        if (null == sort) {
            sort = Sort.by(Sort.Order.desc(GMT_CREATE_TIME));
        }
        Query query = new Query(paCriteria);
        query.with(sort);
        query.with(PageRequest.of(page.getPageNum() - 1, page.getPageSize()));
        long total = mongoTemplate.count(query, table);
        for (String field : fields) {
            query.fields().include(field);
        }
        LOGGER.info("[mongodb 查询]-->表 table：{}，入参query ：{} ", table, query);
        QueryList<Document> pageBox = new QueryList<>();
        pageBox.setTotal(total);
        List<Document> list = Lists.newArrayList();
        mongoTemplate.executeQuery(query, table, document -> {
            document.remove(ID);
            list.add(document);
        });
        pageBox.setList(list);

        return pageBox;

    }

    /**
     * 分页查询,多条件
     *
     * @param table
     * @param fields
     * @param criteriaList
     * @return
     */
    public QueryList<Document> queryPageMongoTemplate(Page page, String table, String[] fields, List<Criteria> criteriaList, Sort sort) {

        CheckUtils.checkIllegal(CollectionUtils.isEmpty(criteriaList), "mongo搜索条件不能为空,criteriaList");
        if (null == sort) {
            sort = Sort.by(Sort.Order.desc(GMT_CREATE_TIME));
        }
        Query query = new Query(new Criteria().andOperator((Criteria[]) criteriaList.toArray()));
        query.with(sort);
        query.with(PageRequest.of(page.getPageNum() - 1, page.getPageSize()));

        long total = mongoTemplate.count(query, table);
        for (String field : fields) {
            query.fields().include(field);
        }
        LOGGER.info("[mongodb 查询]-->表 table：{}，入参query ：{} ", table, query);
        QueryList<Document> pageBox = new QueryList<>();
        pageBox.setTotal(total);

        List<Document> list = Lists.newArrayList();
        mongoTemplate.executeQuery(query, table, document -> {
            document.remove(ID);
            list.add(document);
        });
        pageBox.setList(list);

        return pageBox;

    }

    /**
     * 查询列表,单一条件
     *
     * @param table
     * @param fields
     * @param paCriteria
     * @return
     */
    public List<Document> queryListMongoTemplate(String table, String[] fields, Criteria paCriteria, Sort sort) {
        if (null == sort) {
            sort = Sort.by(Sort.Order.desc(GMT_CREATE_TIME));
        }
        Query query = new Query(paCriteria);
        query.with(sort);
        for (String field : fields) {
            query.fields().include(field);
        }
        List<Document> list = Lists.newArrayList();
        LOGGER.info("[mongodb 查询]-->表 table：{}，入参query ：{} ", table, query);
        mongoTemplate.executeQuery(query, table, document -> {
            document.remove(ID);
            list.add(document);
        });
        return list;

    }

    /**
     * 查询列表，多条件
     *
     * @param table
     * @param fields
     * @param criteriaList
     * @return
     */
    public List<Document> queryListMongoTemplate(String table, String[] fields, List<Criteria> criteriaList, Sort sort) {

        CheckUtils.checkIllegal(CollectionUtils.isEmpty(criteriaList), "mongo搜索条件不能为空,criteriaList");
        if (null == sort) {
            sort = Sort.by(Sort.Order.desc(GMT_CREATE_TIME));
        }

        Query query = new Query(new Criteria().andOperator((Criteria[]) criteriaList.toArray()));
        query.with(sort);
        for (String field : fields) {
            query.fields().include(field);
        }

        List<Document> list = Lists.newArrayList();
        LOGGER.info("[mongodb 查询]-->表 table：{}，入参query ：{} ", table, query);
        mongoTemplate.executeQuery(query, table, document -> {
            document.remove(ID);
            list.add(document);
        });
        return list;

    }


    /**
     * 获取单个document
     *
     * @param table
     * @param fields
     * @param paCriteria
     * @return
     */
    public Document queryMongoTemplate(String table, String[] fields, Criteria paCriteria) {

        Query query = new Query(paCriteria);
        for (String field : fields) {
            query.fields().include(field);
        }

        AtomicReference<Document> document = new AtomicReference<>(new Document());
        LOGGER.info("[mongodb 查询]-->表 table：{}，入参query ：{} ", table, query);
        mongoTemplate.executeQuery(query, table, documentCallBack -> {
            documentCallBack.remove(ID);
            document.set(documentCallBack);
        });
        return document.get();

    }

    /**
     * 获取单个document
     *
     * @param table
     * @return
     */
    public <T> T aggregationMongoTemplate(String table, List<AggregationOperation> aggregations, Class<T> clazz) {
        AggregationResults<T> aggregate = mongoTemplate.aggregate(Aggregation.newAggregation(aggregations), table, clazz);
        List<T> mappedResults = aggregate.getMappedResults();
        if (CollectionUtils.isNotEmpty(mappedResults)) {
            return mappedResults.get(0);
        }
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * mongo联表查询
     *
     * @param localTable       主表
     * @param foreignTable     从表
     * @param aliasName        别名
     * @param fields           字段
     * @param localField       主表字段
     * @param foreignField     从表字段
     * @param localCriterias   主表查询条件
     * @param foreignCriterias 从表查询条件
     * @param groups           分组
     * @param direction        升、降序
     * @param sortFields       排序字段
     * @return
     */
    @Deprecated
    public List<Map> queryUnionFromMongoTemplate(String localTable, String foreignTable, String aliasName, String[] fields,
                                                 String localField, String foreignField, List<Criteria> localCriterias,
                                                 List<Criteria> foreignCriterias, String[] groups,
                                                 Sort.Direction direction, String[] sortFields) {
        //构建条件
        List<AggregationOperation> aggregationOperations = buildAggregationOperation(foreignTable, aliasName, fields, localField, foreignField, localCriterias, foreignCriterias, groups, direction, true, sortFields);

        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        return mongoTemplate.aggregate(aggregation, localTable, Map.class).getMappedResults();
    }


    /**
     * mongo分页联表查询
     *
     * @param localTable       主表
     * @param foreignTable     从表
     * @param aliasName        别名
     * @param fields           字段
     * @param localField       主表字段
     * @param foreignField     从表字段
     * @param localCriterias   主表查询条件
     * @param foreignCriterias 从表查询条件
     * @param groups           分组
     * @param pageNum          分页开始索引
     * @param pageSize         页数
     * @param direction        升、降序
     * @param sortFields       排序字段
     * @return
     */
    @Deprecated
    public QueryList<Map> queryUnionFromPageMongoTemplate(String localTable, String foreignTable, String aliasName, String[] fields,
                                                          String localField, String foreignField, List<Criteria> localCriterias,
                                                          List<Criteria> foreignCriterias, String[] groups, Integer pageNum,
                                                          Integer pageSize, Sort.Direction direction, String[] sortFields) {

        List<AggregationOperation> aggregationOperationCount = buildAggregationOperation(foreignTable, aliasName, fields, localField, foreignField, localCriterias, foreignCriterias, groups, direction, false, sortFields);

        aggregationOperationCount.add(Aggregation.count().as(TOTAL));
        Aggregation aggregationCount = Aggregation.newAggregation(aggregationOperationCount);
        List<Map> mappedResults = mongoTemplate.aggregate(aggregationCount, localTable, Map.class).getMappedResults();
        Integer total;
        QueryList<Map> result = new QueryList<>();
        List<Map> resultLists = null;
        if (CollectionUtils.isNotEmpty(mappedResults)) {
            total = (Integer) mappedResults.get(0).get(TOTAL);

            List<AggregationOperation> aggregationOperations = buildAggregationOperation(foreignTable, aliasName, fields, localField, foreignField, localCriterias, foreignCriterias, groups, direction, true, sortFields);

            aggregationOperations.add(Aggregation.skip((long) (pageNum - 1) * pageSize));

            aggregationOperations.add(Aggregation.limit(pageSize));

            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);

            resultLists = mongoTemplate.aggregate(aggregation, localTable, Map.class).getMappedResults();
        } else {
            total = 0;
        }


        result.setTotal(total);
        result.setList(resultLists);
        return result;
    }

    /**
     * 构建联表条件
     *
     * @param foreignTable     从表
     * @param aliasName        别名
     * @param fields           字段
     * @param localField       主表字段
     * @param foreignField     从表字段
     * @param localCriterias   主表查询条件
     * @param foreignCriterias 从表查询条件
     * @param groups           分组
     * @param direction        升、降序
     * @param sortFields       排序字段
     * @return
     */
    @Deprecated
    private List<AggregationOperation> buildAggregationOperation(String foreignTable, String aliasName, String[] fields,
                                                                 String localField, String foreignField, List<Criteria> localCriterias,
                                                                 List<Criteria> foreignCriterias, String[] groups,
                                                                 Sort.Direction direction, boolean isSort, String[] sortFields) {

        CheckUtils.checkIllegal(StringUtils.isEmpty(aliasName), "aliasName不能为空");

        List<AggregationOperation> aggregationOperations = Lists.newArrayList();
        //from:从表 localField 主表关联字段 foreignField 从表关联字段 as:查询结果名
        LookupOperation lookupOperation = LookupOperation.newLookup().
                from(foreignTable).
                localField(localField).
                foreignField(foreignField).
                as(aliasName);
        aggregationOperations.add(lookupOperation);

        //主表条件(以下aggregationOperations构建组装顺序勿动)
        if (CollectionUtils.isNotEmpty(localCriterias)) {
            aggregationOperations.add(Aggregation.match(new Criteria().andOperator(localCriterias.toArray(new Criteria[0]))));
        }
        if (CollectionUtils.isNotEmpty(foreignCriterias)) {
            //从表条件
            aggregationOperations.add(Aggregation.match(new Criteria().andOperator(foreignCriterias.toArray(new Criteria[0]))));
        }

        UnwindOperation unwind = Aggregation.unwind(aliasName);
        aggregationOperations.add(unwind);

        if (ArrayUtils.isNotEmpty(groups)) {
            aggregationOperations.add(Aggregation.group(groups));
        }

        if (isSort) {
            if (ArrayUtils.isNotEmpty(sortFields)) {
                aggregationOperations.add(Aggregation.sort(Sort.by(direction, sortFields)));
            } else {
                aggregationOperations.add(Aggregation.sort(Sort.by(direction, GMT_CREATE_TIME)));
            }
        }
        if (ArrayUtils.isNotEmpty(fields)) {
            aggregationOperations.add(Aggregation.project(fields).andExclude(ID));
        }

        return aggregationOperations;
    }


    /**
     * 关联查询
     * db.getCollection("deviceunits").aggregate([{
     * $lookup:
     * {
     * from: "spaceunits",
     * localField: "spaceUnit.current",
     * foreignField: "suid",
     * as: "space_doc"
     * }
     * }, {
     * $lookup:
     * {
     * from: "merchants",
     * localField: "space_doc.subject_id",
     * foreignField: "mcid",
     * as: "mc_doc"
     * }
     * }, {
     * $match: {
     * "devseq": /200000000/,
     * "mc_doc.name":"煜帝【测试商户】"
     * }
     * }]);
     * 默认创建时间排序
     * 分页查询
     */
    public QueryList<Document> queryUnionFromPageMongoTemplate(String table, int pageNum, int pageSize, String[] fields, List<Criteria> criteriaList, List<LookupOperation> lookupOperations) {
        return queryUnionFromPageMongoTemplate(table, pageNum, pageSize, fields, criteriaList, lookupOperations, Sort.Direction.DESC, new String[]{GMT_CREATE_TIME});
    }

    public QueryList<Document> queryUnionFromPageMongoTemplate(String table, int pageNum, int pageSize, String[] fileds, List<Criteria> criteriaList, List<LookupOperation> lookupOperations, Sort.Direction direction, String[] sortFields) {

        List<AggregationOperation> countAggregationOperations = getAggregationOperations(fileds, criteriaList, lookupOperations);
        countAggregationOperations.add(Aggregation.count().as(TOTAL));

        Aggregation countAggregation = Aggregation.newAggregation(countAggregationOperations);

        List<Document> countMappedResults = mongoTemplate.aggregate(countAggregation, table, Document.class).getMappedResults();
        Integer total = 0;
        List<Document> resultLists = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(countMappedResults)) {
            total = (Integer) countMappedResults.get(0).get(TOTAL);
            List<AggregationOperation> aggregationOperations = getAggregationOperations(fileds, criteriaList, lookupOperations);
            aggregationOperations.add(Aggregation.skip((long) (pageNum - 1) * pageSize));
            aggregationOperations.add(Aggregation.limit(pageSize));
            aggregationOperations.add(Aggregation.sort(direction, sortFields));
            Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
            resultLists = mongoTemplate.aggregate(aggregation, table, Document.class).getMappedResults();
        }

        QueryList<Document> result = new QueryList<>();
        result.setTotal(total);
        result.setList(resultLists);
        return result;
    }

    /**
     * 列表查询
     *
     * @param table
     * @param fields
     * @param criteriaList
     * @param lookupOperations
     * @return
     */
    public List<Document> queryUnionFromMongoTemplate(String table, String[] fields, List<Criteria> criteriaList, List<LookupOperation> lookupOperations) {
        return queryUnionFromMongoTemplate(table, fields, criteriaList, lookupOperations, Sort.Direction.DESC, new String[]{GMT_CREATE_TIME});
    }

    public List<Document> queryUnionFromMongoTemplate(String table, String[] fileds, List<Criteria> criteriaList, List<LookupOperation> lookupOperations, Sort.Direction direction, String[] sortFields) {

        List<AggregationOperation> aggregationOperations = getAggregationOperations(fileds, criteriaList, lookupOperations);
        aggregationOperations.add(Aggregation.sort(direction, sortFields));
        Aggregation aggregation = Aggregation.newAggregation(aggregationOperations);
        List<Document> mappedResults = mongoTemplate.aggregate(aggregation, table, Document.class).getMappedResults();

        return mappedResults;
    }

    /**
     * 获取总数
     *
     * @param localTable       主表
     * @param foreignTable     从表
     * @param aliasName        别名
     * @param fields           字段
     * @param localField       主表字段
     * @param foreignField     从表字段
     * @param localCriterias   主表查询条件
     * @param foreignCriterias 从表查询条件
     * @param groups           分组
     * @param direction        升、降序
     * @param sortFields       排序字段
     * @return
     */
    public Integer getUnionTableCount(String localTable, String foreignTable, String aliasName, String[] fields,
                                      String localField, String foreignField, List<Criteria> localCriterias,
                                      List<Criteria> foreignCriterias, String[] groups, Sort.Direction direction, String[] sortFields) {
        List<AggregationOperation> aggregationOperationCount = buildAggregationOperation(foreignTable, aliasName, fields, localField, foreignField, localCriterias, foreignCriterias, groups, direction, false, sortFields);

        aggregationOperationCount.add(Aggregation.count().as(TOTAL));
        Aggregation aggregationCount = Aggregation.newAggregation(aggregationOperationCount);
        List<Map> mappedResults = mongoTemplate.aggregate(aggregationCount, localTable, Map.class).getMappedResults();
        Integer total;
        if (CollectionUtils.isNotEmpty(mappedResults)) {
            total = (Integer) mappedResults.get(0).get(TOTAL);
        } else {
            total = 0;
        }


        return total;
    }

    private List<AggregationOperation> getAggregationOperations(String[] fileds, List<Criteria> criteriaList, List<LookupOperation> lookupOperations) {
        List<AggregationOperation> aggregationOperations = Lists.newArrayList();
        aggregationOperations.addAll(lookupOperations);
        aggregationOperations.add(Aggregation.project(fileds).andExclude(ID));
        aggregationOperations.add(Aggregation.match(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()]))));
        return aggregationOperations;
    }


}
