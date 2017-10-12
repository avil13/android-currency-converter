package com.example.avil.currencyconverter.repos;


import java.util.List;

public interface IRepoCallback <T>{
    void update(List<T> list);
    // метод связанный с интерфейсом IRepoCallbackData
    void onFinish();
    void log(String str);
    void log(int str);
}
