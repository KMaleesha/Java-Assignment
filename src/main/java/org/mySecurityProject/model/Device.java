package org.mySecurityProject.model;

import org.mySecurityProject.status.DeviceStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Device {
    private String id;
    private String name;
    private String type;
    private DeviceStatus status;
    private LocalDateTime lastCommunication;

    public Device(String id, String name, String type, DeviceStatus status, LocalDateTime lastCommunication) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.lastCommunication = LocalDateTime.now();
    }

    public Device() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastCommunication() {
        return lastCommunication;
    }

    public void setLastCommunication(LocalDateTime lastCommunication) {
        this.lastCommunication = (lastCommunication != null) ? lastCommunication : LocalDateTime.now();
    }

    public String toJson() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format(
                "{\"id\":\"%s\",\"name\":\"%s\",\"type\":\"%s\",\"status\":\"%s\",\"lastCommunication\":\"%s\"}",
                id, name, type, status,
                lastCommunication != null ? lastCommunication.format(formatter) : null
        );
    }

    public boolean isValid() {
        return id == null || id.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                type == null || type.trim().isEmpty() ||
                status == null;
    }
}
