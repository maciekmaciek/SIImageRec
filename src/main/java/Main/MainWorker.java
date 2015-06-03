package Main;

import Jama.Matrix;
import Utils.ImageEditor;
import Utils.TextParser;
import Utils.VectorPoint;
import Validators.AffineRansacH;
import Validators.PairGenerator;
import Validators.PerspRansacH;
import javafx.util.Pair;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-03.
 */
public class MainWorker implements Runnable{
    private String dirPath;
    private final File i1;
    private final File i2;
    private final String points1path;
    private final String points2path;
    private final BufferedImage image1;
    private final BufferedImage image2;

    public MainWorker(String dirPath, File i1, File i2, String points1path, String points2path, BufferedImage image1, BufferedImage image2) {
        this.dirPath = dirPath;
        this.i1 = i1;
        this.i2 = i2;
        this.points1path = points1path;
        this.points2path = points2path;
        this.image1 = image1;
        this.image2 = image2;
        Thread t = new Thread(this);
        t.start();

    }

    @Override
    public void run() {
        System.out.println("Processing files: " + i1.getName() + ", " + i2.getName() + "... on Thread " + Thread.currentThread().getId());

        TextParser textParser = new TextParser(points1path, points2path, image1.getWidth(), image1.getHeight(), image2.getWidth(), image2.getHeight());
        ArrayList<VectorPoint> points1 = textParser.getPoints(0);
        ArrayList<VectorPoint> points2 = textParser.getPoints(1);
        PairGenerator pairGenerator = new PairGenerator(points1, points2);
        ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs = pairGenerator.generatePairs();

        AffineRansacH ahR = new AffineRansacH(generatedPairs);
        PerspRansacH phR = new PerspRansacH(generatedPairs);
        //AffineRansac aR = new AffineRansac(generatedPairs);
        //PerspRansac pR = new PerspRansac(generatedPairs);

        Matrix aRtransform = ahR.findTransform();
        Matrix pRtransform = phR.findTransform();
        ImageEditor imageEditor = new ImageEditor();

        System.out.println("Results from files: " + i1.getName() + ", " + i2.getName() + "... on Thread " + Thread.currentThread().getId());
        System.out.println(ahR.getClass().toString() + " matches - " + ahR.getValidPairs().size() + "/" + generatedPairs.size());
        System.out.println(phR.getClass().toString() + " matches - " + phR.getValidPairs().size() + "/" + generatedPairs.size());
        ArrayList<Pair<VectorPoint, VectorPoint>> validAPairs = ahR.getValidPairs();
        ArrayList<Pair<VectorPoint, VectorPoint>> validPPairs = phR.getValidPairs();

        imageEditor.createImage(aRtransform, ahR.getClass().toString(), dirPath, i1.getName() + i2.getName() + "A.png", image1, image2, validAPairs, generatedPairs);
        imageEditor.createImage(pRtransform, phR.getClass().toString(), dirPath, i1.getName() + i2.getName() + "P.png", image1, image2, validPPairs, generatedPairs);
    }
}
