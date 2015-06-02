package Validators;

import Jama.Matrix;
import Utils.VectorPoint;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class AffineRansac extends Ransac {

    public AffineRansac(ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs) {
        super(generatedPairs);
    }

    @Override
    public Matrix findTransform() {
        Matrix bestModel = null;
        int size = generatedPairs.size();
        int bestScore = 0;
        Random random = new Random();
        ArrayList<Pair<VectorPoint, VectorPoint>> tempValidPairs = new ArrayList<>();
        for(int counter = 0; counter <= 100000; counter++){         //RANSAC
            tempValidPairs.clear();
            int score = 0;
            Matrix model;
            Pair<VectorPoint,VectorPoint> p1;
            Pair<VectorPoint,VectorPoint> p2;
            Pair<VectorPoint,VectorPoint> p3;
            do {
                int i = random.nextInt(size);                           //losuj
                int j;
                do {
                    j = random.nextInt(size);
                } while (j == i);
                int k;
                do {
                    k = random.nextInt(size);
                } while (k == i || k == j);

                p1 = generatedPairs.get(i);
                p2 = generatedPairs.get(j);
                p3 = generatedPairs.get(k);

            } while(!allDifferent(p1,p2,p3));

            model = generateAffineTransform(p1.getKey(), p1.getValue(), p2.getKey(), p2.getValue(), p3.getKey(), p3.getValue());    //generuj
            for(Pair<VectorPoint, VectorPoint> pair: generatedPairs){                                                               //zlicz dobre
                if(PairValidator.validateTransform(pair, model)){
                    score++;
                    tempValidPairs.add(pair);
                }
            }

            if(score > bestScore){
                bestScore = score;
                bestModel = model;
                validPairs.clear();
                validPairs.addAll(tempValidPairs);
            }

        }
        return bestModel;
    }

    private boolean allDifferent(Pair<VectorPoint,VectorPoint> p1, Pair<VectorPoint,VectorPoint>p2, Pair<VectorPoint,VectorPoint> p3) {
        VectorPoint v11 = p1.getKey();
        VectorPoint v12 = p1.getValue();
        VectorPoint v21 = p2.getKey();
        VectorPoint v22 = p2.getValue();
        VectorPoint v31 = p3.getKey();
        VectorPoint v32 = p3.getValue();

        return  (v11.getX() != v21.getX() || v11.getY() != v21.getY()) &&
                (v11.getX() != v31.getX() || v11.getY() != v31.getY()) &&
                (v21.getX() != v31.getX() || v21.getY() != v31.getY());
    }

    public Matrix generateAffineTransform(VectorPoint v11,VectorPoint v12,
                                          VectorPoint v21,VectorPoint v22,
                                          VectorPoint v31,VectorPoint v32){

        double[][] v1arr = {
                {v11.getX(), v11.getY(), 1, 0, 0, 0},
                {v21.getX(), v21.getY(), 1, 0, 0, 0},
                {v31.getX(), v31.getY(), 1, 0, 0, 0},
                {0, 0, 0, v11.getX(), v11.getY(), 1},
                {0, 0, 0, v21.getX(), v21.getY(), 1},
                {0, 0, 0, v31.getX(), v31.getY(), 1}
        };

        double[][] v2arr = {
                {v12.getX()},
                {v22.getX()},
                {v32.getX()},
                {v12.getY()},
                {v22.getY()},
                {v32.getY()}
        };

        Matrix v1 = new Matrix(v1arr);
        Matrix v2 = new Matrix(v2arr);
        v1 = v1.inverse();
        Matrix tempResult = v1.times(v2);

        double[][] resArr = {
                {tempResult.get(0,0), tempResult.get(1,0), tempResult.get(2,0)},
                {tempResult.get(3,0), tempResult.get(4,0), tempResult.get(5,0)},
                {0, 0, 1}
        };
        Matrix result = new Matrix(resArr);

        return result;
    }
}
