package org.gso.brinder.match.repository;

import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.gso.brinder.match.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import static com.mongodb.client.model.Filters.eq;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CustomUserRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    //MongoCollection<Document> collectionUser = mongoTemplate.getCollection("user");

    public Page<UserModel> getUserById(Criteria criteria, Pageable pageable){

        Query query = new Query().addCriteria(criteria).with(pageable);
        List<UserModel> results = mongoTemplate.find(query, UserModel.class);
        Page<UserModel> profilesPage = PageableExecutionUtils.getPage(
                results,
                pageable,
                () -> mongoTemplate.count(query, UserModel.class));

        return profilesPage;
    }

    public List<UserModel> getAllUser(){
        return mongoTemplate.findAll(UserModel.class, "user");
    }

}
