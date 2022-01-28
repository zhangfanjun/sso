package com.zhang.gateway.auto;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Component
//@Configuration
//@AutoConfigureBefore({ GatewayAutoConfiguration.class })
public class MyRouteDefinitionRepositoryConfig implements RouteDefinitionRepository {

//    public static final String  GATEWAY_ROUTES = "geteway_routes";
//    @Autowired
//    private StringRedisTemplate redisTemplate;

    private static final Map<String,String> BAD_REDIS = new ConcurrentHashMap<>();

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        //从缓存中查看路由
        BAD_REDIS.forEach((x,y)->{
            RouteDefinition routeDefinition = JSON.parseObject(y, RouteDefinition.class);
            //防止出现空路由
            if (Objects.nonNull(routeDefinition.getUri())) {
                routeDefinitions.add(JSON.parseObject(y, RouteDefinition.class));
            }
        });
//        redisTemplate.opsForHash().values(GATEWAY_ROUTES).stream().forEach(routeDefinition -> {
//            routeDefinitions.add(JSON.parseObject(routeDefinition.toString(), RouteDefinition.class));
//        });
        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
            //将新的路由添加到缓存中
            BAD_REDIS.put(routeDefinition.getId(),JSON.toJSONString(routeDefinition));
            //方法的放回是空
            return Mono.empty();
        });

//        return route
//                .flatMap(routeDefinition -> {
//                    redisTemplate.opsForHash().put(GATEWAY_ROUTES, routeDefinition.getId(),
//                            JSON.toJSONString(routeDefinition));
//                    return Mono.empty();
//                });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            String v = BAD_REDIS.get(id);
            if (Objects.nonNull(v)) {
                BAD_REDIS.remove(id);
                return Mono.empty();
            }
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
        });

//        return routeId.flatMap(id -> {
//            if (redisTemplate.opsForHash().hasKey(GATEWAY_ROUTES, id)) {
//                redisTemplate.opsForHash().delete(GATEWAY_ROUTES, id);
//                return Mono.empty();
//            }
//            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
//        });
    }
}
