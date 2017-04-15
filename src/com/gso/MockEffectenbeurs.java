package com.gso;

import com.gso.shared.Fonds;
import com.gso.shared.IEffectenbeurs;
import com.gso.IFonds;

import java.util.*;

/**
 * Created by Maikkeyy on 28-3-2017.
 */
public class MockEffectenbeurs implements IEffectenbeurs {
    private List<IFonds> fondsen;
    private Timer timer;
    private Random r;

    public MockEffectenbeurs() {
        this.fondsen = new ArrayList<IFonds>();
        fondsen.add(new Fonds("Philips", 88.0));
        fondsen.add(new Fonds("VDL", 70.0));
        fondsen.add(new Fonds("Microsoft", 56.0));
        fondsen.add(new Fonds("Sony", 33.0));
        fondsen.add(new Fonds("Samsung", 83.0));
        r = new Random();

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
    }

    @Override
    public List<IFonds> getKoersen() {
        return this.fondsen;
    }

    public void cancel() {
        timer.cancel();
    }
}
