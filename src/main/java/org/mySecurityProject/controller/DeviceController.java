package org.mySecurityProject.controller;

import com.sun.net.httpserver.HttpServer;
import org.mySecurityProject.service.DeviceService;
import org.mySecurityProject.utils.DeviceHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/api/devices", new DeviceHandler(deviceService));
            server.setExecutor(Executors.newFixedThreadPool(10));
            server.start();

            System.out.println("✅ REST API started at: http://localhost:8080/api/devices");
            System.out.println("📘 Use Postman or curl to test the following endpoints:");
            System.out.println("   ➤ GET    /api/devices");
            System.out.println("   ➤ GET    /api/devices/{id}");
            System.out.println("   ➤ POST   /api/devices");
            System.out.println("   ➤ PUT    /api/devices/{id}");
            System.out.println("   ➤ DELETE /api/devices/{id}");

        } catch (IOException e) {
            System.err.println("❌ Failed to start HTTP server: " + e.getMessage());
        }
    }
}
