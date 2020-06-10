package com.niekgigengack.generator;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class Producer {
    // these should be in a properties file but it's just a stub
    private static final String EXCHANGE_NAME = "filter";
    private static final String QUEUE_NAME = "filter";
    private static final String ROUTING_KEY = "filter";

    private Connection conn;
    private Channel channel;

    public Producer() throws IOException, TimeoutException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri("amqp://otrmjmki:lReftAHSXWS9SSFUgioHlElGLzeecvt3@rattlesnake.rmq.cloudamqp.com/otrmjmki"); // not usable for production system, vulnerability!
        
        conn = factory.newConnection();
        channel = conn.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

    }

    public void send(byte[] message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, 
                new AMQP.BasicProperties.Builder().contentType("application/json").deliveryMode(2).build(), message);
    }

    public void close() throws IOException, TimeoutException {
        this.channel.close();
        this.conn.close();
    }
}