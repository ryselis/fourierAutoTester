package edu.ktu.ryselis;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ryselis on 16.5.14.
 */
public class TestGenerator {
    private Collection<ParameterDefinition> parameterDefinitions;

    public TestGenerator(Collection<ParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions = parameterDefinitions;
    }

    public Collection<TestCase> generateTestCases(Method method, Collection<Parameter> parameters) {
        Collection<TestCase> testCaseCases = new ArrayList<>();
        return testCaseCases;
    }

//    private Solution generateSolution() {
//        double temp = getStartingTemperature();
//        Solution x = createRandomSolution();
//        double currentTemperature = temp;
//        int numberOfIterations = 10;
//        double coolingRate = 1;
//        for (int i = 0; i < numberOfIterations; i++) {
//            double fitness = x.getFitness();
//            Solution mutated = mutate(x);
//            double newFitness = mutated.getFitness();
//            if (newFitness < fitness) {
//                double p = PR(); // no idea what you're talking about here
//                if (p > UR(0, 1)) { // likewise
//                    // then do nothing
//                } else {
//                    x = mutated;
//                }
//                currentTemperature *= coolingRate;
//            }
//        }
//        return x;
//    }
//
//    private Solution mutate(Solution x) {
//        return null;
//    }
//
//    private Solution createRandomSolution() {
//        return null;
//    }

    private double getStartingTemperature() {
        return 0;
    }

    private Solution generate() {
        Solution startingSolution = getStartingSolution();
        double temp = getStartingTemperature();
        do {
            int it = 0;
            int numberOfSolutions = 200;
            do {
                Solution solution = chooseRandomSolution(startingSolution);
                double deltaE = obj(solution) - obj(startingSolution);
                if (deltaE < 0) {
                    startingSolution = solution;
                } else {
                    double r = Math.random();
                    if (r < Math.pow(Math.E, -deltaE / temp)) {
                        startingSolution = solution;
                    }
                }
                it++;
            } while (it != numberOfSolutions);
            temp = decreaseTemperatureAccordingToSchedule(temp);
        } while (!stoppingConditionReached());
        return startingSolution;
    }

    private double decreaseTemperatureAccordingToSchedule(double temp) {
        double beta = Math.random() * 0.1;
        return temp / (1 + beta * temp);
    }

    private double obj(Solution startingSolution) {
        return 0;
    }

    private Solution chooseRandomSolution(Solution startingSolution) {
        Collection<Parameter> newParameters = new ArrayList<>();
        for (Parameter parameter: startingSolution.getParameters()){
            ObjectGenerator chooser = new ObjectGenerator();
            Parameter newParameter = chooser.constructNewParameter(parameter);
            newParameters.add(newParameter);
        }
        return new Solution(newParameters);
    }

    private boolean stoppingConditionReached() {
        return false;
    }

    private Solution getStartingSolution() {
        Collection<Parameter> newParameters = new ArrayList<>();
        for (ParameterDefinition parameterDefinition: parameterDefinitions){
            ObjectGenerator chooser = new ObjectGenerator();
            Parameter newParameter = chooser.constructRandomParameter(parameterDefinition);
            newParameters.add(newParameter);
        }
        return new Solution(newParameters);
    }

}
