package com.example.aerospikeData;

import com.aerospike.client.AerospikeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AerospikeConfig {

    @Bean(destroyMethod = "close")
    public AerospikeClient aerospikeClient() {
        return new AerospikeClient("localhost", 3000); // Configure Aerospike client connection
    }
}
