package me.calibri.webprojectspring.models;

public class NoteDeleteModel {
    private final long id;

    public NoteDeleteModel(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
