package com.example.mytest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import static org.junit.jupiter.api.Assertions.*;

class FieldCheckerTest {

    FieldChecker fieldChecker = new FieldChecker();

    @Test
    void isEmpty() {
        assertTrue(fieldChecker.getGender());

    }

}