package Main;

import Jama.Matrix;
import Utils.ImageEditor;
import Utils.TextParser;
import Utils.VectorPoint;
import Validators.AffineRansac;
import Validators.PairGenerator;
import Validators.PerspRansac;
import javafx.util.Pair;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class Main {
    public static void main(String[] args){
        String img1 = "";
        String img2 = "";
        File f1 = new File(img1);
        File f2 = new File(img2);
        String points1path = "";
        String points2path = "";
        TextParser textParser = new TextParser(points1path, points2path);
        ArrayList<VectorPoint> points1 = textParser.getPoints(0);
        ArrayList<VectorPoint> points2 = textParser.getPoints(1);
        PairGenerator pairGenerator = new PairGenerator(points1, points2);
        ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs= pairGenerator.generatePairs();

        AffineRansac aR = new AffineRansac(generatedPairs);
        PerspRansac pR = new PerspRansac(generatedPairs);

        Matrix aRtransform = aR.findTransform();
        Matrix pRtransform = pR.findTransform();
        ArrayList<Pair<VectorPoint, VectorPoint>> validAPairs = aR.getValidPairs();
        ArrayList<Pair<VectorPoint, VectorPoint>> validPPairs = pR.getValidPairs();
        ImageEditor imageEditor = new ImageEditor();

        imageEditor.createImage("A" + f1.getName() + f2.getName(), f1, f2, validAPairs);
        imageEditor.createImage("P" + f1.getName() + f2.getName(), f1, f2, validPPairs);


    }
}
