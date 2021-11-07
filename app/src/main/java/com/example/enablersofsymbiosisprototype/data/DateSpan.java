package com.example.enablersofsymbiosisprototype.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateSpan {
    public ArrayList<Date> dates = new ArrayList<>();

    public void initDatesBetween(Date startDate, Date endDate) {
        Calendar accumulatorDate = Calendar.getInstance();
        accumulatorDate.setTime(startDate);
        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.setTime(endDate);

        while (accumulatorDate.before(endDateCalendar) || accumulatorDate.equals(endDateCalendar)) {
            dates.add(accumulatorDate.getTime());
            accumulatorDate.add(Calendar.DATE, 1);
        }
    }

    public Date getFirstDate() {
        Date smallestDate = dates.get(0);
        for (Date date : dates) {
            if (date.before(smallestDate)) {
                smallestDate = date;
            }
        }
        return smallestDate;
    }

    public Date getLastDate() {
        Date largestDate = dates.get(0);
        for (Date date : dates) {
            if (date.after(largestDate)) {
                largestDate = date;
            }
        }
        return largestDate;
    }
}
