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

        if ((data.length > 2) && (data.length % 2 != 0) && (result.length > 0))
            return false;

        if ((data.length > 2) && (data.length % 2 != 0) && (result.length != data.length))
            return false;

        return true;

    }

}
