package org.example;

import java.nio.charset.StandardCharsets;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.curator.retry.RetryNTimes;

public class Application {

    private static final String PATH = "/example/cache";

    public static void main(String[] args) throws InterruptedException {

        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;
        final RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);

        try (CuratorFramework client = CuratorFrameworkFactory.newClient("zookeeper:2181", retryPolicy)) {
            client.start();
            try (CuratorCache cache = CuratorCache.build(client, PATH)) {
                CuratorCacheListener listener = CuratorCacheListener.builder()
                        .forCreates(node -> {
                            System.out.println(String.format("Node created: [%s]", node));
                            final String data = new String(node.getData(), StandardCharsets.UTF_8);
                            System.out.println(String.format("node:%s", data));
                        })
                        .forChanges((oldNode, newNode) -> {
                            System.out.println(String.format("Node changed. Old: [%s] New: [%s]", oldNode, newNode));
                            final String oldData = new String(oldNode.getData(), StandardCharsets.UTF_8);
                            final String newData = new String(newNode.getData(), StandardCharsets.UTF_8);
                            System.out.println(String.format("old:%s\nnew:%s", oldData, newData));
                        })
                        .forDeletes(
                                oldNode -> System.out.println(String.format("Node deleted. Old value: [%s]", oldNode)))
                        .forInitialized(() -> System.out.println("Cache initialized"))
                        .build();

                // register the listener
                cache.listenable().addListener(listener);
                // the cache must be started
                cache.start();
                Thread.sleep(500000000);
            }
        }
    }
}
