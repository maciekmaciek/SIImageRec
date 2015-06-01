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
        ArrayList<Pair<VectorPoint, VectorPoint>> pairs = new ArrayList<>();
        for(int i = 0; i<points1.size(); i++){
            double minDiff = difference(points1.get(i), points2.get(0));
            points1.get(i).tempPairIndex = 0;
            for(int j = 1; j < points2.size(); j++){    //znajdŸ najmniejsz¹ ró¿nicê
                double diff = difference(points1.get(i), points2.get(j));
                if(minDiff > diff){
                    minDiff = diff;
                    points1.get(i).tempPairIndex = j;
                }
            }

            int tempPairIndex = points1.get(i).tempPairIndex;
            minDiff = difference(points2.get(tempPairIndex), points1.get(0));
            points2.get(tempPairIndex).tempPairIndex = 0;
            for(int k = 1; k < points1.size(); k++){    //sprawdŸ czy wzajemnie
                double diff = difference(points1.get(k), points2.get(tempPairIndex));
                if(minDiff > diff){
                    minDiff = diff;
                    points2.get(tempPairIndex).tempPairIndex = k;
                }
            }
            if(points2.get(tempPairIndex).tempPairIndex == i){  //je¿eli wskazuj¹ wzajemnie, dodaj
                pairs.add(new Pair<>(points1.get(i), points2.get(tempPairIndex)));
            }
        }
        return pairs;
    }

    private double difference(VectorPoint vp1, VectorPoint vp2) {
        double[] t1 = vp1.getTraits();
        double[] t2 = vp2.getTraits();
        double diff = 0;
        for(int i = 0; i < t1.length; i++){
            diff += (t1[i]- t2[i])*(t1[i]- t2[i]);
        }
        return diff;
    }
}
