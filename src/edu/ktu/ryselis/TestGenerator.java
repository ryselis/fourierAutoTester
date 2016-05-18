package edu.ktu.ryselis;

import edu.ktu.ryselis.parameterConstraints.ParameterConstraint;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by ryselis on 16.5.14.
 */
public class TestGenerator {
    private Collection<ParameterDefinition> parameterDefinitions;
    private Method method;

    public TestGenerator(java.lang.reflect.Method method, String pathToClassSource){
        this.method = new Method(method, pathToClassSource);
        parameterDefinitions = new ArrayList<>();
        for (Class<?> aClass : method.getParameterTypes()) {
            parameterDefinitions.add(new ParameterDefinition(aClass));
        }
    }

    public TestGenerator(Collection<ParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions = parameterDefinitions;
    }

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

    private Collection<ParameterConstraint> getPossibleParameterConstraints(){
        CodeParser codeParser = new CodeParser(method);
        return codeParser.getBranches();
    }
}
