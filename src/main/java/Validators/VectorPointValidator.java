package Validators;

import Jama.Matrix;
import Utils.VectorPoint;

import java.util.Comparator;

/**
 * Created by Maciej Wolañski
 * maciekwski@gmail.com
 * on 2015-06-01.
 */
public class VectorPointValidator implements Comparator<VectorPoint> {
    public int compare(VectorPoint v1, VectorPoint v2) {
        return 0;
    }

    public boolean isPair(VectorPoint v1, VectorPoint v2){
        return compare(v1,v2) == 0;
    }


}
