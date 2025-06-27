package org.mySecurityProject.utils;

import org.mySecurityProject.model.Device;
import org.mySecurityProject.status.DeviceStatus;

import java.time.LocalDateTime;

public class TestDataBuilder {

    public static Device createValidDevice() {
        return new Device("DEV001", "Test Device", "Sensor", DeviceStatus.ACTIVE, LocalDateTime.now());
    }

    public static Device createValidDevice(String id) {
        return new Device(id, "Test Device " + id, "Sensor", DeviceStatus.ACTIVE, LocalDateTime.now());
    }

    public static Device createInvalidDevice() {
        return new Device("", "", "", null, null);
    }

    public static Device createDeviceWithId(String id) {
        Device device = createValidDevice();
        device.setId(id);
        return device;
    }

    public static Device createUpdatedDevice(String id) {
        return new Device(id, "Updated Device", "Camera", DeviceStatus.INACTIVE, LocalDateTime.now());
    }
}
