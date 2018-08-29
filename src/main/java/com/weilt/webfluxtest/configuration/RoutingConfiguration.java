package com.weilt.webfluxtest.configuration;

import com.weilt.webfluxtest.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author weilt
 * @com.weilt.webfluxtest.configuration
 * @date 2018/8/29 == 17:48
 */
@Configuration
public class RoutingConfiguration {
    @Bean
    public RouterFunction<ServerResponse> monoRouterFuction(UserHandler userHandler){
        return  route(GET("/api/user/index").and(accept(MediaType.APPLICATION_JSON)),userHandler::getAll)
                .andRoute(GET("/api/user/{id}").and(accept(MediaType.APPLICATION_JSON)),userHandler::getUser)
                .andRoute(POST("/api/user/post").and(accept(MediaType.APPLICATION_JSON)),userHandler::postUser)
                .andRoute(PUT("/api/user/put/{id}").and(accept(MediaType.APPLICATION_JSON)),userHandler::postUser)
                .andRoute(DELETE("/api/user/delete/{id}").and(accept(MediaType.APPLICATION_JSON)),userHandler::deleteUser);
    }
}
