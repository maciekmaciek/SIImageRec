package Validators;

import Jama.Matrix;
import Utils.VectorPoint;
import javafx.util.Pair;

import java.util.Comparator;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class PairValidator {
    private static final double ERROR_MARGIN = 0.01;
    private static final double MAX_DIST = 0.25;
    private static final double MIN_DIST = 0.01;

    public static boolean validateTransform(Pair<VectorPoint, VectorPoint> pair, Matrix transform){
        VectorPoint vp2 = pair.getValue();
        double m1arr [][] =
                {
                {pair.getKey().getX()},
                {pair.getKey().getY()},
                {1}
        };
        Matrix m1 = new Matrix(m1arr);
        m1 = transform.times(m1);

        VectorPoint result = new VectorPoint(m1.get(0,0)/m1.get(2,0), m1.get(1,0)/m1.get(2,0), null);

        return result.distance(vp2) <= ERROR_MARGIN;
    }

    public static boolean validateNeighbour(Pair<VectorPoint, VectorPoint> pair, Pair<VectorPoint, VectorPoint> neighbour) {
        double distance =  pair.getKey().distance(neighbour.getKey());
        return distance >= MIN_DIST && distance <= MAX_DIST;
    }

}
