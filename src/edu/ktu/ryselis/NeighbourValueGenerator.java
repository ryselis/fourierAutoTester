package edu.ktu.ryselis;

/**
 * Created by ryselis on 16.5.16.
 */
public class NeighbourValueGenerator implements ValueGenerator {
    @Override
    public int generateValue(int basedOn) {
        int upperBound = Integer.MAX_VALUE / 1000000;
        int lowerBound = Integer.MIN_VALUE / 1000000;
        int variance = (int) (Math.random() * (upperBound - lowerBound) + lowerBound);
        long result = basedOn + variance;
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE){
            result = basedOn;
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
