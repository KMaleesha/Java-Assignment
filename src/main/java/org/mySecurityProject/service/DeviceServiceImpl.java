package org.mySecurityProject.service;

import org.mySecurityProject.model.Device;
import org.mySecurityProject.repository.DeviceRepository;

import java.util.List;

public class DeviceServiceImpl implements DeviceService{

    private final DeviceRepository repository;

    public DeviceServiceImpl(DeviceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createDevice(Device device) {
        repository.save(device);
    }

    @Override
    public Device getDevice(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Device> getAllDevices() {
        return repository.findAll();
    }

    @Override
    public void updateDevice(String id, Device updatedDevice) {
        if (repository.existsById(id)) {
            repository.save(updatedDevice);
        } else {
            throw new IllegalArgumentException("Device not found with ID: " + id);
        }
    }

    @Override
    public void deleteDevice(String id) {
        repository.deleteById(id);
    }
}
