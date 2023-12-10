package abdu.tumg.controller;

import abdu.tumg.model.Label;

public interface Controller<T> {
    T createObject(boolean isNotSave);
    T editObject(T t, boolean isNotSave);
    T getByObject();
    void deleteObject();
}
