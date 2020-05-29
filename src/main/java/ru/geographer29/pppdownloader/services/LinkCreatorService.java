package ru.geographer29.pppdownloader.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import ru.geographer29.pppdownloader.entities.LinksTable;

@Service
public class LinkCreatorService {
    final String rootPath = "http://anonymous:anon%40gmail.com@garner.ucsd.edu/pub";
    final String rinexPrefix = "/rinex";
    final String fileName = "apex0010";
    final String fileExtension = ".02d.Z";
    // file : http://anonymous:jason%40ucsd.edu@garner.ucsd.edu/pub/rinex/2002/001/apex0010.02d.Z

    final long step = 1000 * 60 * 60 * 24;

    final DateFormat rinexDateFormat = new SimpleDateFormat("yyyy/ddd");
    final DateFormat generalDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public List<LinksTable> getLinks(String date1, String date2, Params... params) {
        if (date1 == null || date2 == null)
            return Collections.emptyList();

        long first = tryParse(date1).getTime();
        long second = tryParse(date2).getTime();

        List<String> links = new ArrayList<>();
        String link = "";

        for (Params p : params){
            while (first <= second) {
                switch (p) {
                    case RINEX:
                        link = String.format("%s%s/%s/%s%s", rootPath, rinexPrefix, rinexDateFormat.format(first), fileName, fileExtension);
                        break;

                    default:
                        link = String.format("%s%s/%s/%s%s", rootPath, rinexPrefix, rinexDateFormat.format(first), fileName, fileExtension);
                }

                links.add(link);
                first += step;
            }
        }


        List<LinksTable> elements = new ArrayList<>();
        elements.add(new LinksTable(links, "rinexes"));
        elements.add(new LinksTable(links, "not rinex"));

        return elements;
    }

    private Date tryParse(String str){
        Date date = null;
        try {
            date = generalDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Unable to parse dates " + date);
        }
        return date;
    }

    public enum Params {
        RINEX
    }

}
