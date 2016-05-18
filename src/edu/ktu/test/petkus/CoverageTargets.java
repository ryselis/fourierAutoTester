package edu.ktu.test.petkus;

/**
 * Created by Tautvydas on 5/17/2016.
 */
public class CoverageTargets {
    public int method1(int value){
        int res = 0;
        if (value > 0)
            res += value;
        else
            res -= value;
        return value;
    }

    public int method2(int value){
        int res = 0;
        if (value == 1)
            res  = 1;
        else if (value == 2)
            res  = 2;
        else if (value == 3)
            res  = 3;
        else if (value == 4)
            res  = 4;
        else
            res  = 5;
        return value;
    }
}
