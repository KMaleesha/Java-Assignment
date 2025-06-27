package org.mySecurityProject.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.mySecurityProject.model.Device;
import org.mySecurityProject.service.DeviceService;

import java.io.IOException;
import java.util.List;

public class DeviceHandler implements HttpHandler {

    private final DeviceService deviceService;

    public DeviceHandler(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        try {
            switch (method) {
                case "GET":
                    handleGet(exchange, path);
                    break;
                case "POST":
                    handlePost(exchange, path);
                    break;
                case "PUT":
                    handlePut(exchange, path);
                    break;
                case "DELETE":
                    handleDelete(exchange, path);
                    break;
                case "OPTIONS":
                    handleOptions(exchange);
                    break;
                default:
                    HttpUtils.sendResponse(exchange, 405, JsonParser.createErrorResponse("Method not allowed"));
            }
        } catch (Exception e) {
            HttpUtils.sendResponse(exchange, 500, JsonParser.createErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    private void handleGet(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/api/devices")) {
            // GET /api/devices - Get all devices
            List<Device> devices = deviceService.getAllDevices();
            String response = JsonParser.toJsonArray(devices);
            HttpUtils.sendResponse(exchange, 200, response);

        } else if (path.startsWith("/api/devices/")) {
            // GET /api/devices/{id} - Get device by ID
            String id = HttpUtils.extractIdFromPath(path);
            if (id != null) {
                Device device = deviceService.getDevice(id);
                if (device != null) {
                    HttpUtils.sendResponse(exchange, 200, device.toJson());
                } else {
                    HttpUtils.sendResponse(exchange, 404, JsonParser.createErrorResponse("Device not found"));
                }
            } else {
                HttpUtils.sendResponse(exchange, 400, JsonParser.createErrorResponse("Invalid device ID"));
            }
        } else {
            HttpUtils.sendResponse(exchange, 404, JsonParser.createErrorResponse("Endpoint not found"));
        }
    }

    private void handlePost(HttpExchange exchange, String path) throws IOException {
        if (path.equals("/api/devices")) {
            // POST /api/devices - Create new device
            String requestBody = HttpUtils.getRequestBody(exchange);
            Device device = JsonParser.parseDevice(requestBody);

            if (!device.isValid()) {
                HttpUtils.sendResponse(exchange, 400, JsonParser.createErrorResponse("Invalid device data"));
                return;
            }

            try {
                deviceService.createDevice(device);
                HttpUtils.sendResponse(exchange, 201, device.toJson());
            } catch (IllegalArgumentException e) {
                HttpUtils.sendResponse(exchange, 409, JsonParser.createErrorResponse(e.getMessage()));
            }
        } else {
            HttpUtils.sendResponse(exchange, 404, JsonParser.createErrorResponse("Endpoint not found"));
        }
    }

    private void handlePut(HttpExchange exchange, String path) throws IOException {
        if (path.startsWith("/api/devices/")) {
            // PUT /api/devices/{id} - Update entire device
            String id = HttpUtils.extractIdFromPath(path);
            if (id != null) {
                String requestBody = HttpUtils.getRequestBody(exchange);
                Device updatedDevice = JsonParser.parseDevice(requestBody);

                if (!updatedDevice.isValid()) {
                    HttpUtils.sendResponse(exchange, 400, JsonParser.createErrorResponse("Invalid device data"));
                    return;
                }

                try {
                    deviceService.updateDevice(id, updatedDevice);
                    HttpUtils.sendResponse(exchange, 200, updatedDevice.toJson());
                } catch (IllegalArgumentException e) {
                    HttpUtils.sendResponse(exchange, 404, JsonParser.createErrorResponse(e.getMessage()));
                }
            } else {
                HttpUtils.sendResponse(exchange, 400, JsonParser.createErrorResponse("Invalid device ID"));
            }
        } else {
            HttpUtils.sendResponse(exchange, 404, JsonParser.createErrorResponse("Endpoint not found"));
        }
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        if (path.startsWith("/api/devices/")) {
            // DELETE /api/devices/{id} - Delete device
            String id = HttpUtils.extractIdFromPath(path);
            if (id != null) {
                try {
                    deviceService.deleteDevice(id);
                    HttpUtils.sendResponse(exchange, 204, "");
                } catch (IllegalArgumentException e) {
                    HttpUtils.sendResponse(exchange, 404, JsonParser.createErrorResponse(e.getMessage()));
                }
            } else {
                HttpUtils.sendResponse(exchange, 400, JsonParser.createErrorResponse("Invalid device ID"));
            }
        } else {
            HttpUtils.sendResponse(exchange, 404, JsonParser.createErrorResponse("Endpoint not found"));
        }
    }

    private void handleOptions(HttpExchange exchange) throws IOException {
        HttpUtils.sendResponse(exchange, 200, "");
    }
}
