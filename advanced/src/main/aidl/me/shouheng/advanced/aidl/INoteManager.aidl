package me.shouheng.advanced.aidl;

import me.shouheng.advanced.aidl.Note;

interface INoteManager {
    Note getNote(long id);
    void addNote(long id, String name);
}
