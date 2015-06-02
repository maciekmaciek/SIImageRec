package Utils;

import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class ImageEditor {

    public void createImage(String dirPath, String filename, BufferedImage img1, BufferedImage img2, ArrayList<Pair<VectorPoint, VectorPoint>> validPairs, ArrayList<Pair<VectorPoint, VectorPoint>> allPairs) {
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
            g2.setFont(new Font("Arial", Font.PLAIN, 40));
            g2.drawString("Valid Pairs: " + validPairs.size() + "/" + allPairs.size(), 20, h+40);
            g2.setPaint(Color.RED);
            for(Pair<VectorPoint, VectorPoint> pair: allPairs){
                g2.drawOval((int)(pair.getKey().getX()*img1.getWidth())-3, (int)(pair.getKey().getY()*img1.getHeight())+3, 3, 3);
                g2.drawOval((int)(pair.getValue().getX()*img2.getWidth())-3 + w1, (int)(pair.getValue().getY()*img2.getHeight())+3, 3, 3);
            }

            g2.setPaint(Color.GREEN);
            for(Pair<VectorPoint, VectorPoint> pair: validPairs){
                g2.drawOval((int)(pair.getKey().getX()*img1.getWidth())-3, (int)(pair.getKey().getY()*img1.getHeight())+3, 3, 3);
                g2.drawOval((int)(pair.getValue().getX()*img2.getWidth())-3 + w1, (int)(pair.getValue().getY()*img2.getHeight())+3, 3, 3);
            }

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
            g2.dispose();

            ImageIO.write(resultIMG, "png", new File(dirPath + "\\" + filename));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
