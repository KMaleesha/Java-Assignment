package org.mySecurityProject.repository;

import org.mySecurityProject.model.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceRepository {

    private final Map<String, Device> deviceMap = new HashMap<>();

    public synchronized void save(Device device) {
        deviceMap.put(device.getId(), device);
    }

    public synchronized Device findById(String id){
        return deviceMap.get(id);
    }

    public synchronized List<Device> findAll() {
        return new ArrayList<>(deviceMap.values());
    }

    public synchronized void deleteById(String id){
        deviceMap.remove(id);
    }

    public synchronized boolean existsById(String id){
        return deviceMap.containsKey(id);
    }
}
