package Validators;

import Jama.Matrix;
import Utils.VectorPoint;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public abstract class Ransac {
    protected ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs;
    protected ArrayList<Pair<VectorPoint, VectorPoint>> validPairs;

    public abstract Matrix findTransform();

    public boolean isRANSACpair(VectorPoint v1, VectorPoint v2, double margin, Matrix transform){
        return false;
    }

    public ArrayList<Pair<VectorPoint, VectorPoint>> getValidPairs() {
        return validPairs;
    }
}
