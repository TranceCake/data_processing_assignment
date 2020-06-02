package com.niekgigengack.generator;

import java.util.concurrent.TimeUnit;

public class Generator {

    public static void main(String[] args) {
        Generator generator = new Generator();
        try {
            generator.run();
        } catch( InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void run() throws InterruptedException {
        for(int i = 0; i < 100; i++) {
            SyncBag data = new SyncBag();
            submitData(data);

            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    private void submitData(SyncBag data) {
        //TODO;
        System.out.println(data.getSyncItem().toString());
    }

}

