package com.gso.server;

import com.gso.IFonds;
import com.gso.shared.Fonds;
import com.gso.shared.IEffectenbeurs;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import com.gso.pushstrategy.*;

/**
 * Created by Maikkeyy on 28-3-2017.
 */
public class Effectenbeurs extends UnicastRemoteObject implements IEffectenbeurs {
    private List<IFonds> fondsen;
    private Timer timer;
    private Random r;
    private Publisher publisher = null;

    public Effectenbeurs() throws RemoteException {
        this.fondsen = new ArrayList<IFonds>();
        fondsen.add(new Fonds("Philips", 88.0));
        fondsen.add(new Fonds("VDL", 70.0));
        fondsen.add(new Fonds("Microsoft", 56.0));
        fondsen.add(new Fonds("Sony", 33.0));
        fondsen.add(new Fonds("Samsung", 83.0));
        r = new Random();

        // Publisher
        String[] properties = new String[1];
        properties[0] = "fondsen";
        publisher = new Publisher(properties);

        //Timer om de koersen te updaten.
        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                verversKoersen();
            }

        }, 0, 1000);
    }

    public void verversKoersen() {
        for (IFonds f : fondsen)
        {
            int randomInt = r.nextInt(100);
            Fonds fonds = (Fonds)f;
            fonds.setKoers(randomInt);
        }

        publisher.inform("fondsen", null, fondsen);
    }

    @Override
    public List<IFonds> getKoersen() throws RemoteException {
        return fondsen;
    }
}
