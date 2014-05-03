package nl.ikoodi.bol.openapi;

import static nl.ikoodi.bol.openapi.HealthStatus.Status.HEALTHY;
import static nl.ikoodi.bol.openapi.HealthStatus.Status.UNHEALTY;

public class HealthStatus {

    enum Status {
        HEALTHY,
        UNHEALTY
    }

    private final boolean healthy;

    private String message;

    public HealthStatus(Status status) {
        healthy = HEALTHY.equals(status);
        this.message = "";
    }

    public HealthStatus(Status status, String message) {
        this(status);
        this.message = message;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public String getMessage() {
        return message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        public HealthStatus isHealthy() {
            return new HealthStatus(HEALTHY);
        }

        public HealthStatus isUnhealthy(String message) {
            return new HealthStatus(UNHEALTY, message);
        }
    }
}
