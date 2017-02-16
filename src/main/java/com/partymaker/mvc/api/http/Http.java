package com.partymaker.mvc.api.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by anton on 07.02.17.
 */
public class Http<T> {

    private static final Logger logger = LoggerFactory.getLogger(Http.class);

    private HttpClient client;

    public HttpResponse post(T entity, String url, String token) throws IOException {

        logger.info("Post entity = " + entity + " tu url = " + url + " and token = " + token);
        client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        post.setHeader("Content-Type", "application/json");
        post.setHeader("Authorization", token);
        StringEntity requestEntity = null;

        requestEntity = new StringEntity(new ObjectMapper().writeValueAsString(entity));
        post.setEntity(requestEntity);

        return client.execute(post);
    }
}
