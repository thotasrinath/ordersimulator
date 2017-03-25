package com.srinath;

/**
 * Created by sthota on 3/25/17.
 */
public class OrderSimulator {

    SeedCreator seedCreator = new SeedCreator();


    public void startSimulator() {

        new Thread(seedCreator).start();
    }

    public void stopSimulator() {

        seedCreator.stopSimultator();
    }
}
