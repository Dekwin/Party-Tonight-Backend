package com.partymaker.mvc.model.party;

import javax.persistence.*;

/**
 * POJO event
 * created by Anton Mostipan
 */
@Entity
@Table(name = "event", schema = "partymaker")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id_event;

    @Column
    private String title;


}
