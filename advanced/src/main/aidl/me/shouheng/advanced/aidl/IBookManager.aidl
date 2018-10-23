package me.shouheng.advanced.aidl;

import me.shouheng.advanced.aidl.Book;

interface IBookManager {
    Book getBook(long id);
    void addBook(long id, String name);
}
