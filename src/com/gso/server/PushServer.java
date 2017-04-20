package com.gso.server;

import com.gso.shared.IEffectenbeurs;
import fontyspublisher.IRemotePublisherForDomain;
import fontyspublisher.RemotePublisher;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Maikkeyy on 20-4-2017.
 */
public class PushServer {
    private static int portNumber = 1099;
    private static String bindingName = "publisher";
    private static IRemotePublisherForDomain publisherForDomain;
    private static Timer timer;

    // Properties to be published
    private static String[] properties;

    // Note that listeners are informed asynchronously
    // Define delay to ensure that listeners have been informed
    private static final int delay = 10; // 10 ms
    private static Registry registry;
    private static IEffectenbeurs effectenbeurs = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
        // Create an instance of RemotePublisher
        publisherForDomain = new RemotePublisher();

        // Create an instance of Effectenbeurs
        effectenbeurs = new Effectenbeurs();

        // Create registry and register remote publisher
        registry = LocateRegistry.createRegistry(portNumber);
        registry.rebind(bindingName, publisherForDomain);

        // Remote publisher registered
        System.out.println("Remote publisher test server:");
        System.out.println("RemotePublisher registered.");
        System.out.println("Port number  : " + portNumber);
        System.out.println("Binding name : " + bindingName);

        // Initialize remote publisher by registering properties
        properties = new String[]{"Effectenbeurs"};
        for (String property : properties) {
            publisherForDomain.registerProperty(property);
        }

        //Timer om de koersen te updaten.
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                effectenbeurs.verversKoersen();
                try {
                    publisherForDomain.inform("fondsen", null, effectenbeurs.getKoersen());
                   // wait(delay);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1000);

       // publisherForDomain.inform("Effectenbeurs", );
    }

    // Note that listeners are informed asynchronously
    // Wait some time to ensure that listeners have been informed
    private void wait(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            System.out.println("Interrupted Exception: " + ex.getMessage());
        }
    }
}
