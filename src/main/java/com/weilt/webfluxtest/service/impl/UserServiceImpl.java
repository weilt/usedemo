package com.weilt.webfluxtest.service.impl;

import com.weilt.webfluxtest.entity.User;
import com.weilt.webfluxtest.service.IUserService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author weilt
 * @com.weilt.webfluxtest.service.impl
 * @date 2018/8/29 == 17:04
 */
@Repository
public class UserServiceImpl implements IUserService {

    private Map<Integer, User> users = new HashMap<Integer, User>();

    @PostConstruct
    public void init() throws Exception {
        users.put(Integer.valueOf(1), new User(1, "Jack"));
        users.put(Integer.valueOf(2), new User(2, "Peter"));
    }

    @Override
    public Mono<User> getUserById(Integer id) {
        return Mono.justOrEmpty(users.get(id));
    }

    @Override
    public Flux<User> getAllUsers() {
        return Flux.fromIterable(this.users.values());
    }

    @Override
    public Mono<Void> saveUser(Mono<User> monoUser) {
        Mono<User> userMono = monoUser.doOnNext(user -> {
            users.put(user.getUserId(), user);
        });
        return userMono.then();
    }

    @Override
    public Mono<User> putUser(Integer id, Mono<User> monoUser) {
        Mono<User> userMono = monoUser.doOnNext(user -> {
            // reset user.Id
            user.setUserId(id);

            // do put
            users.put(id, user);

            // log on console
        });

        return userMono;
    }

    @Override
    public Mono<String> deleteUser(Integer id) {
        users.remove(id);
        return Mono.just("delete successfully");
    }
}
