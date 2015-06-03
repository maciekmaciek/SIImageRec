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
public class AffineRansacH extends Ransac {

    public AffineRansacH(ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs) {
        super(generatedPairs);
    }

    @Override
    public Matrix findTransform() {
        Matrix bestModel = null;
        int size = generatedPairs.size();
        int bestScore = 0;
        Random random = new Random();
        ArrayList<Pair<VectorPoint, VectorPoint>> tempValidPairs = new ArrayList<>();
        Pair<VectorPoint, VectorPoint> p1 = null;
        Pair<VectorPoint, VectorPoint> p2 = null;
        Pair<VectorPoint, VectorPoint> p3 = null;
        for (int counter = 0; counter <= 100000; counter++) {         //RANSAC
           // System.out.println(counter);
            tempValidPairs.clear();
            int score = 0;
            Matrix model;

            int i = random.nextInt(size);                           //losuj
            p1 = generatedPairs.get(i);
            int range = neighbourhood.get(i).size();                //zawê¿
            int rangeLimit = range*range;                //¿eby nie blokowa³o
            int numoftries = 0;                                 //¿eby nie blokowa³o
            if (range >= 2) {
                int j;
                do {
                    j = random.nextInt(range);
                    p2 = neighbourhood.get(i).get(j);
                } while (j == i);
                int k;
                do {
                    numoftries++;
                    k = random.nextInt(range);
                    p3 = neighbourhood.get(i).get(k);
                } while ((k == i || k == j ||!PairValidator.validateNeighbour(p2, p3)) &&numoftries <= rangeLimit);
            }
            if(numoftries < rangeLimit) {
                try {
                    if (!(p1 == null || p2 == null || p3 == null)) {
                        model = generateAffineTransform(p1.getKey(), p1.getValue(), p2.getKey(), p2.getValue(), p3.getKey(), p3.getValue());    //generuj
                        for (Pair<VectorPoint, VectorPoint> pair : generatedPairs) {                                                               //zlicz dobre
                            if (PairValidator.validateTransform(pair, model)) {
                                score++;
                                tempValidPairs.add(pair);
                            }
                        }

                        if (score > bestScore) {
                            bestScore = score;
                            bestModel = model;
                            validPairs.clear();
                            validPairs.addAll(tempValidPairs);
                        }
                    }
                } catch (RuntimeException re) {
                    re.printStackTrace();
                }
            }
        }
        return bestModel;
    }

    private boolean allDifferent(Pair<VectorPoint, VectorPoint> p1, Pair<VectorPoint, VectorPoint> p2, Pair<VectorPoint, VectorPoint> p3) {
        VectorPoint v11 = p1.getKey();
        VectorPoint v12 = p1.getValue();
        VectorPoint v21 = p2.getKey();
        VectorPoint v22 = p2.getValue();
        VectorPoint v31 = p3.getKey();
        VectorPoint v32 = p3.getValue();

        return (v11.getX() != v21.getX() || v11.getY() != v21.getY()) &&
                (v11.getX() != v31.getX() || v11.getY() != v31.getY()) &&
                (v21.getX() != v31.getX() || v21.getY() != v31.getY());
    }

    public Matrix generateAffineTransform(VectorPoint v11, VectorPoint v12,
                                          VectorPoint v21, VectorPoint v22,
                                          VectorPoint v31, VectorPoint v32) throws RuntimeException{

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
                {tempResult.get(0, 0), tempResult.get(1, 0), tempResult.get(2, 0)},
                {tempResult.get(3, 0), tempResult.get(4, 0), tempResult.get(5, 0)},
                {0, 0, 1}
        };
        Matrix result = new Matrix(resArr);

        return result;
    }
}
