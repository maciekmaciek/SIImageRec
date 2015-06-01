package Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class TextParser {
    /*
Pliki *.haraff.sift maj¹ nastêpuj¹c¹ postaæ:
[liczba cech dla ka¿dego puntu kluczowego, zawsze 128 dla .haraff.sift]
[liczba punktów kluczowych]
[wspó³rzêdna x] [wspó³rzêdna y] [param A] [param B] [param C] [cecha 1] [cecha 2] ... [cecha 128]
[wspó³rzêdna x] [wspó³rzêdna y] [param A] [param B] [param C] [cecha 1] [cecha 2] ... [cecha 128]
...
[wspó³rzêdna x] [wspó³rzêdna y] [param A] [param B] [param C] [cecha 1] [cecha 2] ... [cecha 128*/
    private ArrayList<VectorPoint> vps1;
    private ArrayList<VectorPoint> vps2;
    private String points1path;
    private String points2path;

    public TextParser(String points1path, String points2path) {

        this.points1path = points1path;
        this.points2path = points2path;
        parseFiles(points1path, points2path);
    }

    private void parseFiles(String path1, String path2) {
        try {
            int i = 2;
            ArrayList<String> file = (ArrayList<String>)Files.readAllLines(Paths.get(path1));
            for(;i<file.size(); i++){
                String[]arr = file.get(i).split(" ");
                VectorPoint vp = new VectorPoint();
                vp.setLocation(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                double[] traits = new double[128];
                for(int j = 5; j<arr.length; j++){
                    traits[j-5] = Double.parseDouble(arr[j]);
                }
                vp.setTraits(traits);
                vps1.add(vp);
            }

            i = 2;
            file = (ArrayList<String>)Files.readAllLines(Paths.get(path2));
            for(;i<file.size(); i++){
                String[]arr = file.get(i).split(" ");
                VectorPoint vp = new VectorPoint();
                vp.setLocation(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                double[] traits = new double[128];
                for(int j = 5; j<arr.length; j++){
                    traits[j-5] = Double.parseDouble(arr[j]);
                }
                vp.setTraits(traits);
                vps1.add(vp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
