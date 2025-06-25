package org.mySecurityProject.service;

import org.mySecurityProject.model.Device;

import java.util.List;

public interface DeviceService {
    void createDevice(Device device);
    Device getDevice(String id);
    List<Device> getAllDevices();
    void updateDevice(String id, Device updatedDevice);
    void deleteDevice(String id);

}
