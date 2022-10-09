package com.uit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Config {
    private static final String[] ACCOUNT_HEADER = {"userName", "password"};

    public static Map<String, String> getAccounts() {
        final Map<String, String> accountMap = new HashMap<>();
        try (final Reader in = new FileReader(Paths.get("account.csv").toString())) {
            final List<CSVRecord> records = CSVFormat.DEFAULT.withHeader(ACCOUNT_HEADER).parse(in).getRecords();
            for (CSVRecord record : records.subList(1, records.size())) {
                accountMap.put(record.get("userName"), record.get("password"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return accountMap;
    }
}
