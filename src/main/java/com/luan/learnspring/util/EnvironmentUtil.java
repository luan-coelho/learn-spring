package com.luan.learnspring.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil {

    private final Environment environment;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    public EnvironmentUtil(Environment environment) {
        this.environment = environment;
    }

    public boolean isDevelopment() {
        return Profile.DEVELOPMENT.value.equalsIgnoreCase(activeProfile);
    }

    public boolean isProduction() {
        return Profile.PRODUCTION.value.equalsIgnoreCase(activeProfile);
    }

    public boolean isTest() {
        return Profile.TEST.value.equalsIgnoreCase(activeProfile);
    }

    public boolean isPropertyDefined(String propertyName) {
        return environment.containsProperty(propertyName);
    }

    public String getActiveProfile() {
        return switch (activeProfile) {
            case "development":
                yield Profile.DEVELOPMENT.value;
            case "production":
                yield Profile.PRODUCTION.value;
            case "test":
                yield Profile.TEST.value;
            default:
                yield Profile.DEFAULT.value;
        };
    }

    @Getter
    @AllArgsConstructor
    public enum Profile {

        DEVELOPMENT("development"),
        PRODUCTION("production"),
        TEST("test"),
        DEFAULT("default");

        private final String value;

    }

}
