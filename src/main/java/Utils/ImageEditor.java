package Utils;

import Jama.Matrix;
import com.sun.javafx.binding.StringFormatter;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class ImageEditor {

    public void createImage(Matrix transform, String method, String dirPath, String filename, BufferedImage img1, BufferedImage img2, ArrayList<Pair<VectorPoint, VectorPoint>> validPairs, ArrayList<Pair<VectorPoint, VectorPoint>> allPairs) {
        try {
            int w1 = img1.getWidth();
            int h = Math.max(img1.getHeight(), img2.getHeight());
            BufferedImage resultIMG = new BufferedImage(w1+img2.getWidth(), h + 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = resultIMG.createGraphics();
            g2.setPaint(Color.BLACK);
            g2.drawRect(0, 0, resultIMG.getWidth(), resultIMG.getHeight());
            g2.drawImage(img1, 0, 0, null);
            g2.drawImage(img2, w1, 0, null);

            g2.setPaint(Color.WHITE);
            g2.setFont(new Font("Arial", Font.PLAIN, 25));
            g2.drawString(method + ", Valid Pairs: " + validPairs.size() + "/" + allPairs.size(), 20, h + 40);
            g2.drawString("Params: " + stringify(transform), 20, h + 70);

            int i = 0;
            for(Pair<VectorPoint, VectorPoint> pair: validPairs){
                int temp = i++%4;
                if(temp == 0)
                    g2.setPaint(Color.BLACK);
                else if(temp == 1)
                    g2.setPaint(Color.WHITE);
                else if(temp == 2)
                    g2.setPaint(Color.BLUE);
                else if(temp == 3)
                    g2.setPaint(Color.PINK);

                g2.drawLine(
                        (int) (pair.getKey().getX() * img1.getWidth()), (int) (pair.getKey().getY() * img1.getHeight()),
                        (int) (pair.getValue().getX() * img2.getWidth()) + w1, (int) (pair.getValue().getY() * img2.getHeight()));
            }

            g2.setPaint(Color.RED);
            for(Pair<VectorPoint, VectorPoint> pair: allPairs){
                g2.drawOval((int) (pair.getKey().getX() * img1.getWidth()) - 3, (int) (pair.getKey().getY() * img1.getHeight()) + 3, 3, 3);
                g2.drawOval((int) (pair.getValue().getX() * img2.getWidth()) - 3 + w1, (int) (pair.getValue().getY() * img2.getHeight()) + 3, 3, 3);
            }

            g2.setPaint(Color.GREEN);
            for(Pair<VectorPoint, VectorPoint> pair: validPairs){
                g2.drawOval((int)(pair.getKey().getX()*img1.getWidth())-3, (int)(pair.getKey().getY()*img1.getHeight())+3, 3, 3);
                g2.drawOval((int)(pair.getValue().getX()*img2.getWidth())-3 + w1, (int)(pair.getValue().getY()*img2.getHeight())+3, 3, 3);
            }

            g2.dispose();

            ImageIO.write(resultIMG, "png", new File(dirPath + "\\" + filename));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String stringify(Matrix transform) {
        String result="";
        DecimalFormat df = new DecimalFormat("#.####");
        result += "A: " + df.format(transform.get(0, 0)) + ", ";
        result += "B: " + df.format(transform.get(0, 1)) + ", ";
        result += "C: " + df.format(transform.get(0, 2)) + ", ";
        result += "D: " + df.format(transform.get(1, 0)) + ", ";
        result += "E: " + df.format(transform.get(1, 1)) + ", ";
        result += "F: " + df.format(transform.get(1, 2)) + ", ";
        result += "G: " + df.format(transform.get(2, 0)) + ", ";
        result += "H: " + df.format(transform.get(2, 1)) + ", ";
        result += "I: " + df.format(transform.get(2, 2));
        return result;
    }
}
