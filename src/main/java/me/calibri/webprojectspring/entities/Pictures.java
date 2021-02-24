package me.calibri.webprojectspring.entities;

import javax.persistence.*;

@Entity
@Table
public class Pictures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long picturesId;

    private String address;

    @ManyToOne
    @JoinColumn(name = "note_id", nullable = false)
    private Note note;


    public long getPicturesId() {
        return picturesId;
    }

    public void setPicturesId(long picturesId) {
        this.picturesId = picturesId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
