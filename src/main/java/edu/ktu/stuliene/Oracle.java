package edu.ktu.stuliene;

import edu.ktu.ryselis.Parameter;

/**
 * Created by Aiste on 2016.05.16.
 */
public class Oracle {

    public boolean checkResult(Parameter[] data, Parameter[] result) {

        /*if(data.getClass() != result.getClass())
            return false;
        if(data.getClass().getName() != result.getClass().getName())
            return false;
        if (data.getType() != result.getType())
            return false;*/

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < result.length; j++) {
                if (data[i].getType() != result[j].getType())
                    return false;
            }
        }

        if ((data.length > 1) && (data.length % 2 != 0) && (result.length > 0))
            return false;

        if ((data.length > 1) && (data.length % 2 == 0) && (result.length != data.length))
            return false;

        if((data.length == 1) && (result.length != 1))
            return false;

        if((data.length == 0) && (result.length != 0))
            return false;

        double dat = Double.parseDouble(data[0].toString());
        double rez = Double.parseDouble(result[0].toString());
        double eps = Math.pow(10, -10);
        double dat2 = Math.pow(dat, 2);
        if((data.length == 1) && !((Math.abs(dat)- Math.abs(rez) < eps) || (dat2 - Math.abs(rez) < eps)))
            return false;

        return true;

    }

}
