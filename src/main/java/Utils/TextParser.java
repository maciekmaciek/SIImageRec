package Utils;

import java.util.ArrayList;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class TextParser {
    private ArrayList<VectorPoint> vps1;
    private ArrayList<VectorPoint> vps2;
    private String points1path;
    private String points2path;

    public TextParser(String points1path, String points2path) {

        this.points1path = points1path;
        this.points2path = points2path;
        parseFile(points1path);
        parseFile(points2path);
    }

    private void parseFile(String points2path) {

    }

    public ArrayList<VectorPoint> getPoints(int i) {
        if(i == 1){
            return vps1;
        } else if(i == 2) {
            return vps2;
        }
        return null;
    }
}
