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
public class PerspRansac extends Ransac {
    private ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs;
    private ArrayList<Pair<VectorPoint, VectorPoint>> validPairs;

    public PerspRansac(ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs) {
        this.generatedPairs = generatedPairs;
    }

    @Override
    public Matrix findTransform() {
        return null;
    }

    public Matrix generateAffineTransform(VectorPoint v11, VectorPoint v12,
                                          VectorPoint v21, VectorPoint v22,
                                          VectorPoint v31, VectorPoint v32,
                                          VectorPoint v41, VectorPoint v42){
        return null;
    }
}
