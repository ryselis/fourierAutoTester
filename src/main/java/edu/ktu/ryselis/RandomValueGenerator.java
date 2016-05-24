package edu.ktu.ryselis;

/**
 * Created by ryselis on 16.5.16.
 */
public class RandomValueGenerator implements ValueGenerator {
    @Override
    public int generateValue(int basedOn, double objFunc) {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }

    @Override
    public double generateValue(double basedOn) {
        return Math.random() * Double.MAX_VALUE;
    }
}
