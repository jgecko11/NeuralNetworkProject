import java.util.List;

public class NeuralNetwork {
    //INSTANCE-DATA
    private GrecoMatrix ihWeights; //Input and hidden layer weights
    private GrecoMatrix hoWeights; //Output and hidden layer weights
    private GrecoMatrix hiddenBias; //Hidden layer bias
    private GrecoMatrix outputBias; //Output layer bias
    private double learningRate = 0.01; //Learning rate

    //CONSTRUCTORS
    //Simple constructor: input the size of the input layer, the hidden layer and the output layer
    public NeuralNetwork(int i, int h, int o){
        this.ihWeights = new GrecoMatrix(h,i);
        this.hoWeights = new GrecoMatrix(o,h);
        this.hiddenBias = new GrecoMatrix(h,1);
        this.outputBias = new GrecoMatrix(o,1);
    }

    //METHODS
    //Forward-propogation--input a long column, AI-ify it
    public List<Double> predict (double[] x){
        //Input the data into the neural net!!
        GrecoMatrix input = GrecoMatrix.fromArray(x);
        //Multiply the input hughesMatrix by the input-hidden weights and create the hidden layer HughesMatrix
        GrecoMatrix hidden = GrecoMatrix.multiply(ihWeights, input);
        //Add the biases
        hidden.add(hiddenBias);
        //Sigmoid-curve-ify it
        hidden.initSigmoid();
        //Now create the output layer by multiplying the hidden --> output weights and then adding the biases
        GrecoMatrix output = GrecoMatrix.multiply(hoWeights,hidden);
        output.add(outputBias);
        //Sigmoid-curve-ify it
        output.initSigmoid();
        return output.toList();
    }

    public void train(double [] in, double [] answer){
        //Input the data into the neural net!!
        GrecoMatrix input = GrecoMatrix.fromArray(in);
        //Multiply the input hughesMatrix by the input-hidden weights and create the hidden layer HughesMatrix
        GrecoMatrix hidden = GrecoMatrix.multiply(ihWeights, input);
        //Add the biases
        hidden.add(hiddenBias);
        //Sigmoid-curve-ify it
        hidden.initSigmoid();
        //Now create the output layer by multiplying the hidden --> output weights and then adding the biases
        GrecoMatrix output = GrecoMatrix.multiply(hoWeights,hidden);
        output.add(outputBias);
        //Sigmoid-curve-ify it
        output.initSigmoid();
        //"Correct" output for given input
        GrecoMatrix target = GrecoMatrix.fromArray(answer);
        //Determine error between input and correct output
        GrecoMatrix error = GrecoMatrix.subtract(target, output);
        GrecoMatrix gradient = output.deriveSigmoid();
        gradient.multiply(error);
        gradient.multiply(learningRate);
        GrecoMatrix transposeHidden = GrecoMatrix.clone(hidden);
        GrecoMatrix delta = GrecoMatrix.multiply(gradient,transposeHidden);
        hoWeights.add(delta);
        outputBias.add(gradient);
        GrecoMatrix tempOutputWeight = GrecoMatrix.clone(hoWeights);
        GrecoMatrix hiddenError = GrecoMatrix.multiply(tempOutputWeight,error);
        GrecoMatrix hiddenGradient = hidden.deriveSigmoid();
        hiddenGradient.multiply(hiddenError);
        hiddenGradient.multiply(learningRate);
        GrecoMatrix tempInput = GrecoMatrix.clone(input);
        GrecoMatrix hiddenDelta = GrecoMatrix.multiply(hiddenGradient,tempInput);
        ihWeights.add(hiddenDelta);
        hiddenBias.add(hiddenGradient);
    }

    public void fit(double[][]x, double[][]y, int epochs){
        for(int i = 0; i < epochs; i++){

        }
    }
}
