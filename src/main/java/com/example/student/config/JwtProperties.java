package com.example.student.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt") //Take properties that start with jwt. and map them to this Java object.
                                         //This class is the bridge between your configuration (application.properties / environment variable)
                                         // and your Java code (JwtService).

public record JwtProperties( String secret, long expiration) {   //convenient way to create an immutable data holder.

}
