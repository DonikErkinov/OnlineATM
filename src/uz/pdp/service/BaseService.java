package uz.pdp.service;

import uz.pdp.model.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface BaseService<T> { // T - data type generic
    List<Card> cards = new ArrayList<>();
    List<Card> transferHistory = new ArrayList<>();

    boolean add(T t);// T - data type , t- variable

    void getListByUserId(UUID id);

    void getList();
}