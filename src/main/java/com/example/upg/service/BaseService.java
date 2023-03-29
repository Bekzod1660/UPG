package com.example.upg.service;

import org.springframework.stereotype.Service;


@Service
public interface BaseService<T,R>{

    R add(T t);

    R delete(int id);

    R list();

    R update(int id,T t);



}
