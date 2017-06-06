package yaruliy.distribution;
import umontreal.iro.lecuyer.probdist.ErlangDist;
import yaruliy.util.Util;

import java.util.Random;

public class Erlang{
    ErlangDist erlangDistribution;
    public Erlang(int sigma){ this(sigma, 1); }
    public Erlang(int sigma, int k){ this.erlangDistribution = new ErlangDist(k, sigma); }
    public Erlang(){ this.erlangDistribution = new ErlangDist(Util.getProperty("dispersion"), Util.getProperty("mathExpectation")); }

    public double nextErlang(int a){
        double product = 1.0;
        for(int i=1; i<=a; i++)
            product *= new Random().nextDouble();
        return -Math.log(product);
    }
}