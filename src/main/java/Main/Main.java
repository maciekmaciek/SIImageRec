package Main;

import Jama.Matrix;
import Utils.ImageEditor;
import Utils.TextParser;
import Utils.VectorPoint;
import Validators.*;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class Main {
    public static void main(String[] args){
        String dirPath = "C:\\\\Users\\macie_000\\Desktop\\RANSAC\\Results";
        File[] images = new File("C:\\\\Users\\macie_000\\Desktop\\RANSAC\\Images").listFiles((dir, name) -> {
            return name.endsWith(".png");
        });

        File[] params = new File("C:\\\\Users\\macie_000\\Desktop\\RANSAC\\Params").listFiles((dir, name) -> {
            return name.endsWith(".sift");
        });
        Arrays.sort(images);
        Arrays.sort(params);
        for(int i = 0; i < images.length;){
            File i1 = images[i];
            File i2 = images[i+1];
            String points1path = params[i].getAbsolutePath();
            String points2path = params[i+1].getAbsolutePath();
            BufferedImage image1 = null;
            BufferedImage image2 = null;
            try {
                image1 = ImageIO.read(i1);
                image2 = ImageIO.read(i2);

            } catch (IOException e) {
                e.printStackTrace();
            }
            MainWorker worker = new MainWorker(dirPath, i1, i2, points1path, points2path, image1, image2);
            i+=2;

            //String dirPath = "C:\\\\Users\\macie_000\\Desktop\\RANSAC\\Results";
            //String img1 = "C:\\\\Users\\macie_000\\Desktop\\RANSAC\\Images\\kubek1.png";
            //String img2 = "C:\\\\Users\\macie_000\\Desktop\\RANSAC\\Images\\kubek2.png";
            //String points1path = "C:\\Users\\macie_000\\Desktop\\RANSAC\\Params\\kubek1.png.haraff.sift";
            //String points2path = "C:\\Users\\macie_000\\Desktop\\RANSAC\\Params\\kubek2.png.haraff.sift";
        }
    }
}
