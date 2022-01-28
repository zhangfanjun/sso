package com.zhang.gateway.auto;

import com.zhang.gateway.auto.model.RouteSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RequestMapping("/route")
@RestController
public class AutoRouteController {

    @Autowired
    private MyEventPublisherAware myEventPublisherAware;
    @Autowired
    private MyRouteDefinitionRepositoryConfig myRouteDefinitionRepositoryConfig;
    /**
     * 添加路由RouteDefinitionWriter会找到对应的自定义路由缓存类，或者内部的缓存类
     */
    @PostMapping("/save")
    public Object save(@RequestBody RouteSave in) {
        return myEventPublisherAware.save(in);
    }

    /**
     * 删除路由
     */
    @PostMapping("/delete")
    public Object delete(String routeId) {
        Mono<String> just = Mono.just(routeId);
        return myEventPublisherAware.delete(just);
    }

    /**
     * 查看路由
     */
    @PostMapping("/getRoutes")
    public Object getRoutes() {
        Flux<RouteDefinition> routeDefinitions = myRouteDefinitionRepositoryConfig.getRouteDefinitions();
        return routeDefinitions;
    }
}
