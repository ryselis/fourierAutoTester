package edu.ktu.ryselis;

/**
 * Created by ryselis on 16.5.16.
 */
public class NeighbourValueGenerator implements ValueGenerator {
    @Override
    public int generateValue(int basedOn) {
        int upperBound = Math.abs(basedOn) / 2 + 2;
        int lowerBound = -Math.abs(basedOn) / 2 - 2;
        int variance = (int) (Math.random() * (upperBound - lowerBound) + lowerBound);
        long result = basedOn + variance;
        if (result > upperBound || result < lowerBound){
            result = generateValue(basedOn);
        }
        return (int) result;
    }

    @Override
    public double generateValue(double basedOn) {
        double upperBound = Double.MAX_VALUE / 1000000;
        double lowerBound = Double.MIN_VALUE / 1000000;
        double variance = Math.random() * (upperBound - lowerBound) + lowerBound;
        double result = basedOn + variance;
        if (result > Double.MAX_VALUE || result < Double.MIN_VALUE){
            result = basedOn;
        }
        return result;
    }
}
