package Main;

import Jama.Matrix;
import Utils.ImageEditor;
import Utils.TextParser;
import Utils.VectorPoint;
import Validators.AffineRansac;
import Validators.PairGenerator;
import Validators.PerspRansac;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class Main {
    public static void main(String[] args){
        String dirPath = "C:\\\\Users\\macie_000\\Desktop\\RANSAC";
        String img1 = "C:\\\\Users\\macie_000\\Desktop\\RANSAC\\ulotka1.png";
        String img2 = "C:\\\\Users\\macie_000\\Desktop\\RANSAC\\ulotka2.png";
        File f1 = new File(img1);
        File f2 = new File(img2);
        BufferedImage image1 = null;
        BufferedImage image2 = null;
        try {
            image1 = ImageIO.read(f1);
            image2 = ImageIO.read(f2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        String points1path = "C:\\Users\\macie_000\\Desktop\\RANSAC\\ulotka1.png.haraff.sift";
        String points2path = "C:\\Users\\macie_000\\Desktop\\RANSAC\\ulotka2.png.haraff.sift";
        TextParser textParser = new TextParser(points1path, points2path, image1.getWidth(), image1.getHeight(), image2.getWidth(), image2.getHeight());
        ArrayList<VectorPoint> points1 = textParser.getPoints(0);
        ArrayList<VectorPoint> points2 = textParser.getPoints(1);
        PairGenerator pairGenerator = new PairGenerator(points1, points2);
        ArrayList<Pair<VectorPoint, VectorPoint>> generatedPairs = pairGenerator.generatePairs();
        for(Pair<VectorPoint, VectorPoint> pair: generatedPairs){
            System.out.println(pair.getKey().getX() + ", " + pair.getKey().getY() + "    ->   " + pair.getValue().getX()+ ", " + pair.getValue().getY());
        }

        AffineRansac aR = new AffineRansac(generatedPairs);
        PerspRansac pR = new PerspRansac(generatedPairs);

        Matrix aRtransform = aR.findTransform();
        Matrix pRtransform = pR.findTransform();
        ArrayList<Pair<VectorPoint, VectorPoint>> validAPairs = aR.getValidPairs();
        ArrayList<Pair<VectorPoint, VectorPoint>> validPPairs = pR.getValidPairs();
        ImageEditor imageEditor = new ImageEditor();

        imageEditor.createImage(dirPath, "A" + f1.getName() + f2.getName(), image1, image2, validAPairs);
        imageEditor.createImage(dirPath, "P" + f1.getName() + f2.getName(), image1, image2, validPPairs);
    }
}
