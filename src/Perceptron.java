import java.util.List;
import java.util.Random;

public class Perceptron {
    private final Random random;
    private final double[] weights;
    private final int dimension;
    private final double alpha;
    private double theta;

    public Perceptron(int dimension, double alpha) {
        this.random = new Random();
        this.dimension = dimension;
        this.alpha = alpha;
        this.weights = new double[dimension];
        initializeWeights();
    }

    private void initializeWeights() {
        for (int i = 0; i < dimension; i++) {
            weights[i] = random.nextDouble() * 2 - 1;
        }
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double compute(DataPoint dataPoint) {
        if (dataPoint.getDimension() != dimension) {
            throw new IllegalArgumentException("Nieprawidłowy wymiar");
        }
        return calculateWeightedSum(dataPoint.getFeatures()) >= theta ? 1 : 0;
    }

    private double calculateWeightedSum(double[] features) {
        double sum = 0;
        for (int i = 0; i < dimension; i++) {
            sum += features[i] * weights[i];
        }
        return sum;
    }

    public void learn(List<DataPoint> data, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            int errors = 0;
            for (DataPoint point : data) {
                double prediction = compute(point);
                double error = point.getExpectedOutput() - prediction;
                if (error != 0) {
                    errors++;
                    updateWeights(point, error);
                }
            }

            if (epoch % 10 == 0) {
                System.out.println("Epoka " + (epoch + 1) + "/" + epochs +
                        ", błędy: " + errors + "/" + data.size());
            }

            if (errors == 0) {
                System.out.println("Zakończono na epoce " + (epoch + 1));
                break;
            }
        }
    }

    private void updateWeights(DataPoint point, double error) {
        double[] features = point.getFeatures();
        for (int i = 0; i < dimension; i++) {
            weights[i] += alpha * error * features[i];
        }
        theta = theta - error * alpha;
    }
}