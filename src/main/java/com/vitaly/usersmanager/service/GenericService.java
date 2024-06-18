package com.vitaly.usersmanager.service;

import reactor.core.publisher.Mono;

public interface GenericService<T, ID> {
    Mono<T> getById(ID id);

    Mono<T> update(T t);

    Mono<T> save(T t);

    Mono<T> delete(ID id);
}
