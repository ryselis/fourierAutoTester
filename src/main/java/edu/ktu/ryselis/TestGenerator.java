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

    private double getStartingTemperature() {
        return 1;
    }

    public Collection<Solution> generate() {
        ParameterConstraintGenerator generator = new ParameterConstraintGenerator(getPossibleParameterConstraints());
        Collection<Solution> solutions = new ArrayList<>();
        while (true) {
            Collection<ParameterConstraint> parameterConstraints = generator.next();
            if (parameterConstraints == null) {
                break;
            }
            Solution startingSolution = getStartingSolution();
            double temp = getStartingTemperature();
            int cannotFindBetterSolutionForThisManyRounds = 0;
            do {
                cannotFindBetterSolutionForThisManyRounds++;
                double objStartingSolution = obj(startingSolution, parameterConstraints);
                Solution solution = chooseRandomSolution(startingSolution, objStartingSolution);
                double newSolutionObj = obj(solution, parameterConstraints);
                if (newSolutionObj == 0){
                    startingSolution = solution;
                    cannotFindBetterSolutionForThisManyRounds = 101;
                    continue;
                }

                double deltaE = newSolutionObj - objStartingSolution;
                if (deltaE < 0) {
                    startingSolution = solution;
                    cannotFindBetterSolutionForThisManyRounds = 0;
                } else {
                    double r = Math.random();
                    if (r < Math.pow(Math.E, ((-deltaE - 0.1) / temp))) {
                        startingSolution = solution;
                        cannotFindBetterSolutionForThisManyRounds = 0;
                    }
                }
                temp = decreaseTemperatureAccordingToSchedule(temp);
            } while (!stoppingConditionReached(cannotFindBetterSolutionForThisManyRounds));
            solutions.add(startingSolution);
        }
        return solutions;
    }

    private double decreaseTemperatureAccordingToSchedule(double temp) {
        double beta = Math.random() * 0.2;
        return temp / (1 + beta * temp);
    }

    private double obj(Solution startingSolution, Collection<ParameterConstraint> parameterConstraints) {
        double totalResult = 0;
        int count = 0;
        for (ParameterConstraint possibleParameterConstraint : parameterConstraints) {
            if (possibleParameterConstraint.acceptsSingleValue()) {
                for (Parameter parameter : startingSolution.getParameters()) {
                    totalResult += possibleParameterConstraint.validateValue(parameter.getValue());
                    count += 1;
                }
            }
            else {
                Object[] values = new Object[startingSolution.getParameters().size()];
                Parameter[] parameters = new Parameter[startingSolution.getParameters().size()];
                startingSolution.getParameters().toArray(parameters);
                for (int i = 0; i < values.length; i++){
                    values[i] = parameters[i].getValue();
                }
                totalResult += possibleParameterConstraint.validateValue(values);
                count += 1;
            }
        }
        return totalResult / (count == 0 ? 1 : count);
    }

    private Solution chooseRandomSolution(Solution startingSolution, double objFunc) {
        Collection<Parameter> newParameters = new ArrayList<>();
        for (Parameter parameter: startingSolution.getParameters()){
            ObjectGenerator chooser = new ObjectGenerator();
            Parameter newParameter = chooser.constructNewParameter(parameter, objFunc);
            newParameters.add(newParameter);
        }
        return new Solution(newParameters);
    }

    private boolean stoppingConditionReached(int cannotFindBetterSolutionForThisManyRounds) {
        return cannotFindBetterSolutionForThisManyRounds > 100;
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
