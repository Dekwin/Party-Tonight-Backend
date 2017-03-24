package com.partymaker.mvc.model.whole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_has_event", schema = "partymaker2")
public class UserToEvent implements Serializable {

    @Column(name = "id_user")
    private int id_user;

    @Id
    @Column(name = "id_event")
    private int id_event;

    public int getUserId() {
        return id_user;
    }

    public int getEventId() {
        return id_event;
    }
}
