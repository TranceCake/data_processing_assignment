package com.niekgigengack.generator;

import java.util.ArrayList;
import java.util.UUID;

public class SyncItem {
    private String id;
    private char grade;
    private SyncData currentData;
    private ArrayList<SyncData> historyData;

    {
        this.id = UUID.randomUUID().toString();
        this.historyData = new ArrayList<>();
    }

    public SyncItem() {
        this.currentData = new SyncData();
        this.grade = (char)(getGrade(currentData) + 65); // 65 is offset for A in ascii table
    }

    private int getGrade(SyncData data) {

        if(data.getDataString0().equals("")
                || data.getDataString1().equals("")
                || data.getDataString3().equals("")) {
            return 5;
        }

        if(data.getDataString0().equals("*"))
            return 3;
        
        if(data.getDataString1().equals("*") || data.getDataString3().equals("*"))
            return 2;

        if(data.getDataString2().equals("") || data.getDataString2().equals("*")
                || data.getDataString4().equals("") || data.getDataString4().equals("*")
                || data.getDataString5().equals("") || data.getDataString5().equals("*")) {
            return 1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return String.format("grade: %c | d0: %s | d1: %s | d2: %s | d3: %s | d4: %s | d5: %s", this.grade,
                this.currentData.getDataString0(), this.currentData.getDataString1(), this.currentData.getDataString2(),
                this.currentData.getDataString3(), this.currentData.getDataString4(), this.currentData.getDataString5());
    }

    public String getId() {
        return this.id;
    }

    public char getGrade() {
        return this.grade;
    }

    public SyncData getCurrentData() {
        return this.currentData;
    }

    public ArrayList<SyncData> getHistoryData() {
        return this.historyData;
    }
}