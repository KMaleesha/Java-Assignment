package org.mySecurityProject.utils;

import org.mySecurityProject.model.Device;
import org.mySecurityProject.status.DeviceStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {

    public static Device parseDevice(String json) {
        Device device = new Device();

        device.setId(extractStringValue(json, "id"));
        device.setName(extractStringValue(json, "name"));
        device.setType(extractStringValue(json, "type"));

        String statusStr = extractStringValue(json, "status");
        if (statusStr != null) {
            try {
                device.setStatus(DeviceStatus.valueOf(statusStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: '" + statusStr + "'. Valid values are: ACTIVE, INACTIVE.");
            }
        }

        String lastCommStr = extractStringValue(json, "lastCommunication");
        LocalDateTime timestamp;
        if (lastCommStr != null) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                timestamp = LocalDateTime.parse(lastCommStr, formatter);
            } catch (Exception e) {
                timestamp = LocalDateTime.now();
            }
        } else {
            timestamp = LocalDateTime.now();
        }

        device.setLastCommunication(timestamp);
        return device;
    }

    public static String toJsonArray(List<Device> devices) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < devices.size(); i++) {
            json.append(devices.get(i).toJson());
            if (i < devices.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    public static String createErrorResponse(String message) {
        return String.format("{\"error\":\"%s\",\"timestamp\":\"%s\"}",
                message, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    private static String extractStringValue(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }
}
