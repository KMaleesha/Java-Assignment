package org.mySecurityProject;

import org.mySecurityProject.controller.DeviceController;
import org.mySecurityProject.repository.DeviceRepository;
import org.mySecurityProject.service.DeviceService;
import org.mySecurityProject.service.DeviceServiceImpl;

public class Main {
    public static void main(String[] args) {
        DeviceRepository repository = new DeviceRepository();
        DeviceService service = new DeviceServiceImpl(repository);
        DeviceController controller = new DeviceController(service);

        controller.start();
    }
}