package dev.ngdangkiet.repository;

import dev.ngdangkiet.dkmicroservices.tracking.protobuf.PGetUserTrackingActivitiesRequest;
import dev.ngdangkiet.domain.UserTrackingDataActivity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ngdangkiet
 * @since 11/23/2023
 */

@Repository
@RequiredArgsConstructor
public class UserTrackingDataActivityRepositoryCustomImpl implements UserTrackingDataActivityRepositoryCustom {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<UserTrackingDataActivity> findActivities(PGetUserTrackingActivitiesRequest request) {
        Criteria criteria = new Criteria();
        if (request.getUserId() > 0) {
            criteria.and(Criteria.where("user_id").is(request.getUserId()));
        }
        if (StringUtils.hasText(request.getMethod())) {
            criteria.and(Criteria.where("method").is(request.getMethod()));
        }
        if (StringUtils.hasText(request.getAction())) {
            criteria.and(Criteria.where("action").is(request.getAction()));
        }

        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        SearchHits<UserTrackingDataActivity> searchHits = elasticsearchOperations.search(criteriaQuery, UserTrackingDataActivity.class);
        return searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
