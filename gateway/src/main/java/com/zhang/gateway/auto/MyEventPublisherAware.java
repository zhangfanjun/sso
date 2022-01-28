package com.zhang.gateway.auto;

import com.zhang.gateway.auto.model.RouteSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Service
public class MyEventPublisherAware implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    private ApplicationEventPublisher publisher;

    /**
     * 保存路由
     */
    public String save(RouteSave in) {
        RouteDefinition definition = new RouteDefinition();
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);

        definition.setId(in.getId());
        predicate.setName("Path");
        //请替换成本地可访问的路径
        predicateParams.put("pattern",in.getPath());
        //请替换成本地可访问的路径
        predicateParams.put("pathPattern", in.getPath());
        predicate.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicate));
        //请替换成本地可访问的域名
        URI uri = UriComponentsBuilder.fromHttpUrl(in.getUri()).build().toUri();
        definition.setUri(uri);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();

        notifyChange();
        return "success";
    }

    /**
     * 删除路由
     */
    public String delete(Mono<String> just) {
        routeDefinitionWriter.delete(just).subscribe();
        notifyChange();
        return "success";
    }

    /**
     * 出发刷新事件
     */
    public void notifyChange() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
}
