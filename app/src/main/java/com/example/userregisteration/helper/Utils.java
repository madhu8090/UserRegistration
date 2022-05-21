package com.example.userregisteration.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String getAge(String dob) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        int age = today.get(Calendar.YEAR) - year;

        if ((month < today.get(Calendar.MONTH))
                || ((month == today.get(Calendar.MONTH)) && (day < today
                .get(Calendar.DAY_OF_MONTH)))) {
            --age;
        }
        String ageS = String.valueOf(age);
        System.out.println("age:" + ageS);
        return ageS;
    }

}
