package me.calibri.webprojectspring.models;

public class NoteDto {
    private final String title;
    private final String content;
    private final long id;
    private final long ownerId;
    private long userId;

    public NoteDto(String title, String content, long id, long ownerId) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.ownerId = ownerId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getId() {
        return id;
    }

    public boolean isShared() {
        return ownerId != userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getShortContent() {
        return content.substring(0, Math.min(97, content.length())) + "...";
    }
}
