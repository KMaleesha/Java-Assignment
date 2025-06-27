package org.mySecurityProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mySecurityProject.model.Device;
import org.mySecurityProject.repository.DeviceRepository;
import org.mySecurityProject.utils.TestDataBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Device Service Integration Tests")
public class DeviceServiceIntegrationTest {

    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        DeviceRepository repository = new DeviceRepository();
        deviceService = new DeviceServiceImpl(repository);
    }

    @Test
    @DisplayName("Should perform complete CRUD operations")
    void shouldPerformCompleteCrudOperations() {
        // CREATE
        Device device = TestDataBuilder.createValidDevice();
        deviceService.createDevice(device);

        // READ
        Device retrievedDevice = deviceService.getDevice(device.getId());
        assertNotNull(retrievedDevice);
        assertEquals(device.getId(), retrievedDevice.getId());

        List<Device> allDevices = deviceService.getAllDevices();
        assertEquals(1, allDevices.size());

        // UPDATE
        Device updatedDevice = TestDataBuilder.createUpdatedDevice(device.getId());
        deviceService.updateDevice(device.getId(), updatedDevice);

        Device retrievedUpdatedDevice = deviceService.getDevice(device.getId());
        assertEquals("Updated Device", retrievedUpdatedDevice.getName());
        assertEquals("Camera", retrievedUpdatedDevice.getType());

        // DELETE
        deviceService.deleteDevice(device.getId());
        Device deletedDevice = deviceService.getDevice(device.getId());
        assertNull(deletedDevice);

        List<Device> remainingDevices = deviceService.getAllDevices();
        assertEquals(0, remainingDevices.size());
    }

    @Test
    @DisplayName("Should handle multiple devices correctly")
    void shouldHandleMultipleDevicesCorrectly() {
        // Given
        Device device1 = TestDataBuilder.createDeviceWithId("DEV001");
        Device device2 = TestDataBuilder.createDeviceWithId("DEV002");
        Device device3 = TestDataBuilder.createDeviceWithId("DEV003");

        // When - Create multiple devices
        deviceService.createDevice(device1);
        deviceService.createDevice(device2);
        deviceService.createDevice(device3);

        // Then
        List<Device> allDevices = deviceService.getAllDevices();
        assertEquals(3, allDevices.size());

        // When - Delete one device
        deviceService.deleteDevice("DEV002");

        // Then
        allDevices = deviceService.getAllDevices();
        assertEquals(2, allDevices.size());
        assertNull(deviceService.getDevice("DEV002"));
        assertNotNull(deviceService.getDevice("DEV001"));
        assertNotNull(deviceService.getDevice("DEV003"));
    }
}
