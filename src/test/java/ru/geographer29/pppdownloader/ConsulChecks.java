package ru.geographer29.pppdownloader;

public class ConsulChecks {

    public static final String lifeCheck =
                    "{\n" +
                    "  \"ID\": \"life_check\",\n" +
                    "  \"Name\": \"HTTP TCP on port 8080\",\n" +
                    "  \"ServiceID\": \"ppp_downloader\",\n" +
                    "  \"Tcp\": \"localhost:8080\",\n" +
                    "  \"Interval\": \"2s\",\n" +
                    "  \"Timeout\": \"1s\"\n" +
                    "}";


    public static final String failCheck =
                            "{\n" +
                            "  \"ID\": \"fail_check\",\n" +
                            "  \"Name\": \"HTTP TCP on port 8081\",\n" +
                            "  \"ServiceID\": \"ppp_downloader\",\n" +
                            "  \"Tcp\": \"localhost:8081\",\n" +
                            "  \"Interval\": \"2s\",\n" +
                            "  \"Timeout\": \"1s\"\n" +
                            "}";


}
