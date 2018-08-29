package com.weilt.webfluxtest.handler;

import com.weilt.webfluxtest.entity.User;
import com.weilt.webfluxtest.service.IUserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

/**
 * @author weilt
 * @com.weilt.webfluxtest.handler
 * @date 2018/8/29 == 17:03
 */
@Component
public class UserHandler {

    private final IUserService iUserService;

    public UserHandler(IUserService iUserService) {
        this.iUserService = iUserService;
    }


    public Mono<ServerResponse> getAll(ServerRequest request){
        Flux<User> users = iUserService.getAllUsers();
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(users,User.class);
    }

    public Mono<ServerResponse> getUser(ServerRequest request){
        Integer userId = Integer.valueOf(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        Mono<User> userMono = iUserService.getUserById(userId);
        return userMono.flatMap(user ->
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(user))).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> postUser(ServerRequest request){
        Mono<User> userMono = request.bodyToMono(User.class);
        return ServerResponse.ok().build(iUserService.saveUser(userMono));
    }


    public Mono<ServerResponse> putUser(ServerRequest request){
        Integer userId = Integer.valueOf(request.pathVariable("id"));
        Mono<User> userMono = request.bodyToMono(User.class);
        Mono<User> responseMono = iUserService.putUser(userId,userMono);
        return responseMono.flatMap(user->ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(fromObject(user)));
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request){
        Integer userId = Integer.valueOf(request.pathVariable("id"));
        Mono<String> stringMono = iUserService.deleteUser(userId);
        return  stringMono.flatMap(strMono->ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(fromObject(strMono)));
    }
}
