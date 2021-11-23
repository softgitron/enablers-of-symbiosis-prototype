package com.example.enablersofsymbiosisprototype.ui;

import android.text.Html;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {
    public static CharSequence boldTitle(String title, String content) {
        String textHtml =
                String.format("<font color=#000000><b>%s: </b></font><font color=#666666>%s</font>", title, content);
        return Html.fromHtml(textHtml, Html.FROM_HTML_MODE_COMPACT);
    }

    public static CharSequence boldTitle(String title, int content, String postFix) {
        String textHtml =
                String.format(Locale.US, "<font color=#000000><b>%s: </b></font><font color=#666666>%d %s</font>", title, content, postFix);
        return Html.fromHtml(textHtml, Html.FROM_HTML_MODE_COMPACT);
    }

    public static CharSequence boldTitle(String title, float content, String postFix) {
        String textHtml =
                String.format(Locale.US, "<font color=#000000><b>%s: </b></font><font color=#666666>%.2f %s</font>", title, content, postFix);
        return Html.fromHtml(textHtml, Html.FROM_HTML_MODE_COMPACT);
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(date);
    }

    public static String formatPhoneNumber(long number) {
        return String.valueOf(number)
                .replaceFirst("(\\d{3})(\\d{2})(\\d{3})(\\d+)", "+$1 $2 $3 $4");
    }
}
