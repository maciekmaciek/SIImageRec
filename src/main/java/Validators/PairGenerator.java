package Validators;

import Utils.VectorPoint;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class PairGenerator {
    ArrayList<VectorPoint> points1;
    ArrayList<VectorPoint> points2;

    public PairGenerator(ArrayList<VectorPoint> points1, ArrayList<VectorPoint> points2){
        this.points1 = points1;
        this.points2 = points2;
    }

    public ArrayList<Pair<VectorPoint, VectorPoint>> generatePairs(){
        return null;
    }
}
