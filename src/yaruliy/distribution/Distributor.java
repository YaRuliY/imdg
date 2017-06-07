package yaruliy.distribution;
import umontreal.iro.lecuyer.probdist.ErlangDist;
import yaruliy.util.Util;
import java.util.Random;

public class Distributor {
    ErlangDist erlangDistribution;
    //public Distributor(int sigma){ this(sigma, 1); }
    public Distributor(int sigma, int k){ this.erlangDistribution = new ErlangDist(k, sigma); }
    public Distributor(){ this.erlangDistribution = new ErlangDist(Util.getProperty("dispersion"), Util.getProperty("mathExpectation")); }

    public double nextErlang(int a){
        double product = 1.0;
        for(int i=1; i<=a; i++)
            product *= new Random().nextDouble();
        return -Math.log(product);
    }

    public double nextGaussian(){
        Random r = new Random();
        double gaussian = Math.round(r.nextGaussian() * Util.getProperty("dispersion") + Util.getProperty("mathExpectation"));
        if (gaussian < 0) gaussian = nextGaussian();
        return gaussian;
    }
}