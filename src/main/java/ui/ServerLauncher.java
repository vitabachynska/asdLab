package ui;

import service.NetworkCodec;
import service.Service;



public class ServerLauncher {
    public static void main(String[] args) {
        Service service = new Service();
        service.startup();

        UniversityServer server = new UniversityServer(service);
        server.start(5555);
    }
}