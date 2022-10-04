package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

public class Application {

    private static final String PATH = "/example/cache";

    public static void main(String[] args) throws Exception {

        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;
        final RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);

        try (CuratorFramework client = CuratorFrameworkFactory.newClient("zookeeper:2181", retryPolicy)) {
            client.start();
            final ObjectMapper objectMapper = new ObjectMapper();
            //here we are making some random changes
            while (true) {
                final SomeConfiguration config = new SomeConfiguration(RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(5));
                client.create().orSetData().creatingParentsIfNeeded().forPath(PATH,
                        objectMapper.writeValueAsBytes(config));
                System.out.println(String.format("Saved: %s", config));
                Thread.sleep(5000);
            }
        }
    }
}
