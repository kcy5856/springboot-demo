package com.example.demo.service;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ElasticSearchService {
    @Autowired
    RestHighLevelClient highLevelClient;

    //创建文档
    public IndexResponse createDocument(String esIndex, String id, String dataJson) throws IOException {
        Assert.notNull(esIndex, "index can not be null");
        //创建请求
        IndexRequest request = new IndexRequest(esIndex);
        //规则
        request.timeout(TimeValue.timeValueSeconds(1));
        //将数据放到请求中
        if (!StringUtils.isEmpty(dataJson)) {
            request.source(dataJson, XContentType.JSON);
        }
        if (!StringUtils.isEmpty(id)) {
            request.id(id);
        }
        //客户端发送请求，获取相应的结果
        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
        //打印一下
        return response;
    }

    //判断是否存在
    public void existDocument(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        //不获取返回的_source 的上下文
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none");

        boolean exists = highLevelClient.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档信息
    public String getDocument(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        GetResponse response = highLevelClient.get(request, RequestOptions.DEFAULT);
        return response.getSourceAsString();
    }

    //更新文档
    public void updateDocument(String index, String id, String dataJson) throws IOException {
        //创建对象
        UpdateRequest request = new UpdateRequest(index, id);
        request.timeout("1s");

        request.doc(dataJson, XContentType.JSON);
        UpdateResponse response = highLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //删除文档
    public void deleteDocument(String index, String id) throws IOException{
        DeleteRequest request = new DeleteRequest(index, id);
        request.timeout("1s");

        DeleteResponse response = highLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //批量添加
    public void bulkDocument(String index, List dataList) throws IOException{
        BulkRequest request = new BulkRequest();
        request.timeout("10s");


        //进行批处理请求
        for (int i = 0; i <dataList.size() ; i++) {
            request.add(
                    new IndexRequest(index)
                            .id(""+(i+1))
                            .source(JSON.toJSONString(dataList.get(i)), XContentType.JSON));
        }

        BulkResponse response = highLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
    }

    //查询
    public String SearchDocument(String index, Map<String, Object> params) throws IOException{
        SearchRequest request = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //查询条件使用QueryBuilders工具来实现
        //QueryBuilders.termQuery 精准查询
        //QueryBuilders.matchAllQuery() 匹配全部
        if (!CollectionUtils.isEmpty(params)){
            for (Map.Entry<String, Object> entry: params.entrySet()){
                MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(entry.getKey(), entry.getValue());
                builder.query(matchQuery);
            }
        }

        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(builder);

        SearchResponse response = highLevelClient.search(request, RequestOptions.DEFAULT);
        List<Map<String, Object>> collect = Arrays.stream(response.getHits().getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
        String result = JSON.toJSONString(collect);
        return result;
    }
}
