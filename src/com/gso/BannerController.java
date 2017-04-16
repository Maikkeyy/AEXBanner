package com.gso;

import com.gso.client.RMIClient;
import com.gso.server.Effectenbeurs;
import com.gso.shared.IEffectenbeurs;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Maikkeyy on 28-3-2017.
 */
public class BannerController {
    private AEXBanner banner;
    private IEffectenbeurs effectenbeurs;
    private Timer pollingTimer;
    private RMIClient client;

    public BannerController(AEXBanner banner) {
        this.banner = banner;
        client = new RMIClient("192.168.58.1", 1099, this);

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        pollingTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                effectenbeurs = client.getEffectenbeurs();

                try {
                    setKoersen(effectenbeurs.getKoersen());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2000);

    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        //((MockEffectenbeurs)effectenbeurs).cancel();
    }

    public void setKoersen(List<IFonds> fondsen) {
        String koersen = "";

        for(IFonds fonds : fondsen) {
            String koers = String.valueOf(fonds.getKoers());

            koersen = koersen + " " +  fonds.getNaam() + " : " + koers;
        }

        banner.setKoersen(koersen);
    }

    public void setEffectenbeurs(IEffectenbeurs effectenbeurs) {
        this.effectenbeurs = effectenbeurs;
    }

}
