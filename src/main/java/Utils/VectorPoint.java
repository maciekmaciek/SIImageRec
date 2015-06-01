package Utils;

import java.awt.*;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class VectorPoint extends Point.Double{
    double[] traits;
    public int tempPairIndex;

    public VectorPoint(){
        traits = new double[128];
    }

    public VectorPoint(double x, double y, double[] traits){
        super(x, y);
        this.traits = traits;
    }

    public double[] getTraits() {
        return traits;
    }
    public void setTraits(double[] traits) {
        this.traits = traits;
    }
}
