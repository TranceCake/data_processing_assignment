package com.niekgigengack.generator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {
    private static Logger logger = LoggerFactory.getLogger(Generator.class);

    ObjectMapper objectMapper;
    Producer producer;

    public Generator() throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        objectMapper = new ObjectMapper();
        producer = new Producer();
    }

    public static void main(String[] args) {
        try {
            Generator generator = new Generator();
            generator.run();
            generator.producer.close();
        } catch (InterruptedException | IOException | TimeoutException | URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            logger.error(e.toString());
        }
    }

    private void run() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            SyncBag data = new SyncBag();
            try{
                submitData(data);
            } catch(IOException e) {
                logger.error(e.toString());
            }

            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    private void submitData(SyncBag data) throws IOException {
        producer.send(objectMapper.writeValueAsBytes(data));
    }

}

