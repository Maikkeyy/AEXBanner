package com.gso;

import com.gso.shared.IEffectenbeurs;

import java.rmi.RemoteException;
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

    public BannerController(AEXBanner banner) {
        this.banner = banner;

        try {
            this.effectenbeurs = new Effectenbeurs();
        } catch (RemoteException ex) {
            System.out.println("Remote Exception: " + ex.getMessage());
        }

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        pollingTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
               setKoersen();
            }
        }, 0, 2000);

    }

    // Stop banner controller
    public void stop() {
        pollingTimer.cancel();
        // Stop simulation timer of effectenbeurs
        ((MockEffectenbeurs)effectenbeurs).cancel();
    }

    public void setKoersen() {
        List<IFonds> fondsen = null;
        try {
            fondsen = effectenbeurs.getKoersen();
        } catch (RemoteException ex) {
            System.out.println("RemoteException: " + ex.getMessage());
        }
        String koersen = "";

        for(IFonds fonds : fondsen) {
            String koers = String.valueOf(fonds.getKoers());
            koersen = koersen + " " +  fonds.getNaam() + " : " + koers;
        }

        banner.setKoersen(koersen);
    }

}
