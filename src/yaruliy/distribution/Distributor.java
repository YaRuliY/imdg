package yaruliy.distribution;
import cern.jet.random.Distributions;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import yaruliy.util.Util;
import java.util.Random;

public class Distributor {
    private long dispersion;
    private long mathExpectation;

    public Distributor(){
        this.dispersion = Util.getProperty("dispersion");
        this.mathExpectation = Util.getProperty("mathExpectation");
    }

    public double nextErlang(){
        //double c = Distributions.nextErlang(dispersion, mathExpectation, new DRand());
        //double d = Distributions.nextErlang(dispersion, mathExpectation, new MersenneTwister());
        Random rand = new Random();
        return (int) (dispersion + rand.nextInt((int)(mathExpectation - dispersion) + 1));
    }

    public double nextGaussian(){
        Random r = new Random();
        double gaussian = Math.round(r.nextGaussian() * this.dispersion + this.mathExpectation);
        if (gaussian < 0) gaussian = nextGaussian();
        return gaussian;
    }
}