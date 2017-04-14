package com.gso;

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
        this.effectenbeurs = new MockEffectenbeurs();

        // Start polling timer: update banner every two seconds
        pollingTimer = new Timer();
        pollingTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
              // effectenbeurs.getKoersen();
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
        List<IFonds> fondsen = effectenbeurs.getKoersen();
        String koersen = "";

        for(IFonds fonds : fondsen) {
            String koers = String.valueOf(fonds.getKoers());
            koersen = koersen + " " +  fonds.getNaam() + " : " + koers;
        }

        banner.setKoersen(koersen);
    }

}
