package com.gmail.elnora.fet.finalcourseproject.data.dataconverter;

import android.os.Build;
import android.text.Html;

public class HtmlConverter {
    public String convertFromHtml(String htmlString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return String.valueOf(Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY));
        } else {
            return htmlString;
        }
    }
}
