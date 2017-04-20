package com.gso.client;

import fontyspublisher.IRemotePropertyListener;
import fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maikkeyy on 20-4-2017.
 */
public class RemoteViewOnDomain extends UnicastRemoteObject implements IRemotePropertyListener {
    // Remote publisher
    private static IRemotePublisherForListener publisherForListener;
    private static int portNumber = 1099;
    private static String bindingName = "publisher";

    private Registry registry = null;

    // List of property change events received by this listener
    List<PropertyChangeEvent> receivedEvents;

    public RemoteViewOnDomain() throws RemoteException {
        receivedEvents = new ArrayList<>();

        try {
            registry = LocateRegistry.getRegistry("192.168.58.1", portNumber);
            publisherForListener = (IRemotePublisherForListener) registry.lookup(bindingName);
            System.out.println("Connection with remote publisher established");

            publisherForListener.subscribeRemoteListener(this, "Effectenbeurs");
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Cannot establish connection to remote publisher");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        receivedEvents.add(evt);
    }
}
