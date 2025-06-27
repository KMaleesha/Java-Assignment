package org.mySecurityProject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mySecurityProject.model.Device;
import org.mySecurityProject.repository.DeviceRepository;
import org.mySecurityProject.utils.TestDataBuilder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Device Service Implementation Tests")
public class DeviceServiceImplTest {

    @Mock
    private DeviceRepository repository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    private Device testDevice;

    @BeforeEach
    void setUp() {
        testDevice = TestDataBuilder.createValidDevice();
    }

    //create operation test
    @Test
    @DisplayName("Should create device successfully")
    void shouldCreateDeviceSuccessfully() {
        // Given
        when(repository.existsById(testDevice.getId())).thenReturn(false);

        // When
        deviceService.createDevice(testDevice);

        // Then
        verify(repository).save(testDevice);
        verify(repository).existsById(testDevice.getId());
    }

    @Test
    @DisplayName("Should throw exception when creating device with existing ID")
    void shouldThrowExceptionWhenCreatingDeviceWithExistingId() {
        // Given
        when(repository.existsById(testDevice.getId())).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> deviceService.createDevice(testDevice)
        );

        assertEquals("Device already exists with ID: " + testDevice.getId(), exception.getMessage());
        verify(repository, never()).save(any());
    }


    // read test operation
    @Test
    @DisplayName("Should get device by ID successfully")
    void shouldGetDeviceById() {
        // Given
        when(repository.findById(testDevice.getId())).thenReturn(testDevice);

        // When
        Device result = deviceService.getDevice(testDevice.getId());

        // Then
        assertNotNull(result);
        assertEquals(testDevice.getId(), result.getId());
        verify(repository).findById(testDevice.getId());
    }

    @Test
    @DisplayName("Should return null for non-existent device")
    void shouldReturnNullForNonExistentDevice() {
        // Given
        when(repository.findById(anyString())).thenReturn(null);

        // When
        Device result = deviceService.getDevice("NON_EXISTENT");

        // Then
        assertNull(result);
        verify(repository).findById("NON_EXISTENT");
    }

    @Test
    @DisplayName("Should get all devices successfully")
    void shouldGetAllDevices() {
        // Given
        List<Device> expectedDevices = Arrays.asList(
                TestDataBuilder.createDeviceWithId("DEV001"),
                TestDataBuilder.createDeviceWithId("DEV002")
        );
        when(repository.findAll()).thenReturn(expectedDevices);

        // When
        List<Device> result = deviceService.getAllDevices();

        // Then
        assertEquals(2, result.size());
        assertEquals(expectedDevices, result);
        verify(repository).findAll();
    }

    //update test operation
    @Test
    @DisplayName("Should update device successfully")
    void shouldUpdateDeviceSuccessfully() {
        // Given
        Device updatedDevice = TestDataBuilder.createUpdatedDevice(testDevice.getId());
        when(repository.existsById(testDevice.getId())).thenReturn(true);

        // When
        deviceService.updateDevice(testDevice.getId(), updatedDevice);

        // Then
        verify(repository).save(updatedDevice);
        verify(repository).existsById(testDevice.getId());
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent device")
    void shouldThrowExceptionWhenUpdatingNonExistentDevice() {
        // Given
        Device updatedDevice = TestDataBuilder.createUpdatedDevice("NON_EXISTENT");
        when(repository.existsById("NON_EXISTENT")).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> deviceService.updateDevice("NON_EXISTENT", updatedDevice)
        );

        assertEquals("Device not found with ID: NON_EXISTENT", exception.getMessage());
        verify(repository, never()).save(any());
    }

    // delete test operation
    @Test
    @DisplayName("Should delete device successfully")
    void shouldDeleteDeviceSuccessfully() {
        // Given
        when(repository.existsById(testDevice.getId())).thenReturn(true);

        // When
        deviceService.deleteDevice(testDevice.getId());

        // Then
        verify(repository).deleteById(testDevice.getId());
        verify(repository).existsById(testDevice.getId());
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent device")
    void shouldThrowExceptionWhenDeletingNonExistentDevice() {
        // Given
        when(repository.existsById("NON_EXISTENT")).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> deviceService.deleteDevice("NON_EXISTENT")
        );

        assertEquals("Device not found with ID: NON_EXISTENT", exception.getMessage());
        verify(repository, never()).deleteById(any());
    }

}
