/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client.bank;

/**
 *
 * @author alex
 */
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author al
 */
@Configuration
public class HazelcastConfiguration {
    private static int TEN_MINUES = 10*60;
    @Bean
    public Config config() {
        return new Config()
                .addMapConfig(
                        new MapConfig()
                                .setName("token-cache")
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(TEN_MINUES))
                .setProperty("hazelcast.logging.type", "slf4j")
                .addMapConfig(new MapConfig()
                                .setName("secret-cacle")
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(TEN_MINUES))
                                .setProperty("hazelcast.logging.type", "slf4j");
    }   
}
