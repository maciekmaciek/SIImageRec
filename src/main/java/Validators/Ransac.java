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


    public Ransac(ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs){

        this.generatedPairs = generatedPairs;
        validPairs = new ArrayList<>();
        createNeighbourhood();
    }
    protected ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs;
    protected ArrayList<Pair<VectorPoint, VectorPoint>> validPairs;
    protected ArrayList<ArrayList<Pair<VectorPoint, VectorPoint>>> neighbourhood;

    public abstract Matrix findTransform();

    public void createNeighbourhood(){
        neighbourhood = new ArrayList<>();
        for(int i = 0; i < generatedPairs.size(); i++){
            neighbourhood.add(new ArrayList<>());
            for (Pair<VectorPoint, VectorPoint> generatedPair : generatedPairs) {
                if (PairValidator.validateNeighbour(generatedPairs.get(i), generatedPair))
                    neighbourhood.get(i).add(generatedPair);
            }
        }
    }
    public ArrayList<Pair<VectorPoint, VectorPoint>> getValidPairs() {
        return validPairs;
    }
}
