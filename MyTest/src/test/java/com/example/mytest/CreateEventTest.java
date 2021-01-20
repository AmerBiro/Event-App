package com.example.mytest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateEventTest {




    @Test
    void createEvent() {
    }


    CreateEvent createEvent = new CreateEvent();
    EventModel eventModel = new EventModel();


    @Test
    void getName() {
        assertEquals(eventModel.getName(), createEvent.getName());
    }


    @Test
    void getType() {

    }

    @Test
    void getCreator_id() {

    }
    @Test
    void getCreator_name() {

    }

}