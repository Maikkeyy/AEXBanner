package com.gso;

import com.gso.shared.IEffectenbeurs;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by Maikkeyy on 28-3-2017.
 */
public class Effectenbeurs extends UnicastRemoteObject implements IEffectenbeurs {

    public Effectenbeurs() throws RemoteException {

    }

    @Override
    public List<IFonds> getKoersen() throws RemoteException {
        return null;
    }
}
