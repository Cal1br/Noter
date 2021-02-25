package me.calibri.webprojectspring.models;

public class NoteShareModel {
    private final long noteId;
    private final String username;

    public NoteShareModel(long noteId, String username) {
        this.noteId = noteId;
        this.username = username;
    }

    public long getNoteId() {
        return noteId;
    }

    public String getUsername() {
        return username;
    }
}
