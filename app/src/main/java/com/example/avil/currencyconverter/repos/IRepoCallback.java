package com.example.avil.currencyconverter.repos;


import java.util.List;

public interface IRepoCallback <T>{
    void update(List<T> list);
    void onError();
    void log(String str);
    void log(int str);
}
