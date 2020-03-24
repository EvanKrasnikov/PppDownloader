package ru.geographer29.pppdownloader.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateParserService {
    final String rootPath = "http://anonymous:anon%40gmail.com@garner.ucsd.edu/pub";
    final String rinexPrefix = "/rinex";
    final String fileName = "apex0010";
    final String fileExtension = ".02d.Z";
    // file : http://anonymous:jason%40ucsd.edu@garner.ucsd.edu/pub/rinex/2002/001/apex0010.02d.Z

    final DateFormat generalDateFormat = new SimpleDateFormat("YYYY-MM-dd");
    final DateFormat yearDateFormat = new SimpleDateFormat("YYYY");
    final DateFormat daysInYearDateFormat = new SimpleDateFormat("DDD");


    public List<String> getRinexLinks(String firstDate, String secondDate) {
        Date first = null;
        Date second = null;

        try {
            first = generalDateFormat.parse(firstDate);
            second = generalDateFormat.parse(secondDate);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Unable to parse data");
        }

        String firstYear = yearDateFormat.format(first);
        String secondYear = yearDateFormat.format(second);
        String firstDaysInYear = daysInYearDateFormat.format(first);
        String secondDaysInYear = daysInYearDateFormat.format(first);

        List<String> list = new ArrayList<>();
        String link = String.format("%s%s/%s/%s/%s%s", rootPath, rinexPrefix, firstYear, firstDaysInYear, fileName, fileExtension);

        list.add(link);
        list.add(link);
        list.add(link);
        list.add(link);
        list.add(link);
        list.add(link);

        return list;
    }

}
