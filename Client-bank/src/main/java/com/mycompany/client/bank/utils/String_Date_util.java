/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank.utils;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author artem
 */
public class String_Date_util {
    public static Date String_to_Date(String temp_str){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy");
        return Date.from(Instant.from(formatter.parse(temp_str)));
    }
}
