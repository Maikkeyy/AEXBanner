package com.gso.shared;

import com.gso.IFonds;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Maikkeyy on 28-3-2017.
 */
public interface IEffectenbeurs extends Remote {
    List<IFonds> getKoersen() throws RemoteException;

    void verversKoersen();
}
