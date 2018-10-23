package me.shouheng.advanced.binder;

import me.shouheng.advanced.binder.Book;

interface IBookManager {
    Book getBook(long id);
    void addBook(long id, String name);
}
