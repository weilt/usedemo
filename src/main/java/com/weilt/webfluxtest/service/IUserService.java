package com.weilt.webfluxtest.service;

import com.weilt.webfluxtest.entity.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author weilt
 * @com.weilt.webfluxtest.service
 * @date 2018/8/29 == 17:04
 */
public interface IUserService {
    public Mono<User> getUserById(Integer id);

    public Flux<User> getAllUsers();

    public Mono<Void> saveUser(Mono<User> user);

    public Mono<User> putUser(Integer id, Mono<User> user);

    public Mono<String> deleteUser(Integer id);
}
