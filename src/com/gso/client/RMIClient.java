package com.gso.client;

/**
 * Created by Maikkeyy on 15-4-2017.
 */
import com.gso.BannerController;
import com.gso.server.Effectenbeurs;
import com.gso.shared.IEffectenbeurs;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

public class RMIClient {

    // Set binding name for effectenbeurs
    private static final String bindingName = "Effectenbeurs";

    // References to registry and effectenbeurs
    private Registry registry = null;
    private IEffectenbeurs effectenbeurs = null;
    private Timer pollingTimer;

    // Reference to bannerController for updates
    private BannerController bannerController;

    // Constructor
    public RMIClient(String ipAddress, int portNumber, BannerController bc) {
        this.bannerController = bc;

        // Print IP address and port number for registry
        System.out.println("Client: IP Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Locate registry at IP address and port number
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Print result locating registry
        if (registry != null) {
            System.out.println("Client: Registry located");
        } else {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: Registry is null pointer");
        }

       /* if (registry != null)
        {
            try
            {
                effectenbeurs = (IEffectenbeurs)registry.lookup(bindingName);
                bannerController.setEffectenbeurs(effectenbeurs);
            }
            catch (RemoteException ex)
            {
                System.out.println("Client: Cannot bind effectenbeurs");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                bannerController = null;
            }
            catch (NotBoundException ex)
            {
                System.out.println("Client: Cannot bind effectenbeurs");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                bannerController = null;
            }
        } */

        if(registry != null) {
            printContentsRegistry();
        }

        // Bind effectenbeurs using registry
        pollingTimer = new Timer();
        pollingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (registry != null)
                {
                    try
                    {
                        effectenbeurs = (IEffectenbeurs)registry.lookup(bindingName);
                        bannerController.setEffectenbeurs(effectenbeurs);
                    }
                    catch (RemoteException ex)
                    {
                        System.out.println("Client: Cannot bind effectenbeurs");
                        System.out.println("Client: RemoteException: " + ex.getMessage());
                        bannerController = null;
                    }
                    catch (NotBoundException ex)
                    {
                        System.out.println("Client: Cannot bind effectenbeurs");
                        System.out.println("Client: NotBoundException: " + ex.getMessage());
                        bannerController = null;
                    }
                }
            }
        }, 0 , 1000);

        // Print result binding effectenbeurs
        if (effectenbeurs != null) {
            System.out.println("Client: effectenbeurs bound");
        } else {
            System.out.println("Client: effectenbeurs is null pointer");
        }

        // Test RMI connection
        if (effectenbeurs != null) {

        }
    }

    public IEffectenbeurs getEffectenbeurs() {
        return this.effectenbeurs;
    }

    // Print contents of registry
    private void printContentsRegistry() {
        try {
            String[] listOfNames = registry.list();
            System.out.println("Client: list of names bound in registry:");
            if (listOfNames.length != 0) {
                for (String s : registry.list()) {
                    System.out.println(s);
                }
            } else {
                System.out.println("Client: list of names bound in registry is empty");
            }
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot show list of names bound in registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
        }
    }

}

