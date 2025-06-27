package org.mySecurityProject.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mySecurityProject.utils.TestDataBuilder;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Device Model Tests")
public class DeviceTest {

    @Test
    @DisplayName("Should create valid device successfully")
    void shouldCreateValidDevice() {
        // Given
        Device device = TestDataBuilder.createValidDevice();

        // When & Then
        assertNotNull(device.getId());
        assertNotNull(device.getName());
        assertNotNull(device.getType());
        assertNotNull(device.getStatus());
        assertTrue(device.isValid());
    }

    @Test
    @DisplayName("Should return false for invalid device")
    void shouldReturnFalseForInvalidDevice() {
        // Given
        Device device = TestDataBuilder.createInvalidDevice();

        // When & Then
        assertFalse(device.isValid());
    }

    @Test
    @DisplayName("Should generate valid JSON representation")
    void shouldGenerateValidJson() {
        // Given
        Device device = TestDataBuilder.createValidDevice();

        // When
        String json = device.toJson();

        // Then
        assertNotNull(json);
        assertTrue(json.contains(device.getId()));
        assertTrue(json.contains(device.getName()));
        assertTrue(json.contains(device.getType()));
        assertTrue(json.contains(device.getStatus().toString()));
    }
}
