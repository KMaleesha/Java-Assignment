package org.mySecurityProject.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mySecurityProject.model.Device;
import org.mySecurityProject.utils.TestDataBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Device Repository Tests")
public class DeviceRepositoryTest {

    private DeviceRepository repository;

    @BeforeEach
    void setUp() {
        repository = new DeviceRepository();
    }

    @Test
    @DisplayName("Should save device successfully")
    void shouldSaveDevice() {
        // Given
        Device device = TestDataBuilder.createValidDevice();

        // When
        repository.save(device);

        // Then
        assertTrue(repository.existsById(device.getId()));
        assertEquals(device, repository.findById(device.getId()));
    }

    @Test
    @DisplayName("Should find device by ID")
    void shouldFindDeviceById() {
        // Given
        Device device = TestDataBuilder.createValidDevice();
        repository.save(device);

        // When
        Device found = repository.findById(device.getId());

        // Then
        assertNotNull(found);
        assertEquals(device.getId(), found.getId());
        assertEquals(device.getName(), found.getName());
    }

    @Test
    @DisplayName("Should return null for non-existent device")
    void shouldReturnNullForNonExistentDevice() {
        // When
        Device found = repository.findById("NON_EXISTENT");

        // Then
        assertNull(found);
    }

    @Test
    @DisplayName("Should return all devices")
    void shouldReturnAllDevices() {
        // Given
        Device device1 = TestDataBuilder.createDeviceWithId("DEV001");
        Device device2 = TestDataBuilder.createDeviceWithId("DEV002");
        repository.save(device1);
        repository.save(device2);

        // When
        List<Device> devices = repository.findAll();

        // Then
        assertEquals(2, devices.size());
        assertTrue(devices.contains(device1));
        assertTrue(devices.contains(device2));
    }

    @Test
    @DisplayName("Should delete device by ID")
    void shouldDeleteDeviceById() {
        // Given
        Device device = TestDataBuilder.createValidDevice();
        repository.save(device);
        assertTrue(repository.existsById(device.getId()));

        // When
        repository.deleteById(device.getId());

        // Then
        assertFalse(repository.existsById(device.getId()));
        assertNull(repository.findById(device.getId()));
    }

    @Test
    @DisplayName("Should check if device exists by ID")
    void shouldCheckIfDeviceExists() {
        // Given
        Device device = TestDataBuilder.createValidDevice();

        // When & Then
        assertFalse(repository.existsById(device.getId()));

        repository.save(device);
        assertTrue(repository.existsById(device.getId()));
    }
}
