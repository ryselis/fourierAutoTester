package edu.ktu.ryselis;

/**
 * Created by ryselis on 16.5.16.
 */
public interface ValueGenerator {
    int generateValue(int basedOn, double objFunc);
    double generateValue(double basedOn);
}
