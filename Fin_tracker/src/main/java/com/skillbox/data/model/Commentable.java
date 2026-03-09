package com.skillbox.data.model;


import java.util.List;

/**
 * Интерфейс для транзакций, к которым могут быть добавлены комментарии.
 */
public interface Commentable {

    /**
     * Возвращает список комментариев, добавленных к транзакции.
     *
     * @return список комментариев.
     */
    List<String> getComments();

}
