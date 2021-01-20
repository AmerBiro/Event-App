package com.example.mytest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class EventTest {

    @org.junit.jupiter.api.Test
    void getEvent_id() {
    }

    @Test
    void getName() {
        EventModel eventModel = new EventModel("eventName");
        assertEquals("eventName", eventModel.getName());
    }


}