import java.io.*;
import java.text.DecimalFormat;

public class MLP {
    int[] sizes;
    int num_layers;
    Biases biases;
    Weights weights;

    //when running the MLP, if test is True, display the accuracy of the MLP during each epoch of testing
    boolean test;
    //test_data is used to test the accuracy of our network
    Neuron[] test_data;

    public MLP(int[] sizes) {
        this.sizes = sizes;
        num_layers = sizes.length;
        biases = new Biases(sizes, true);
        weights = new Weights(sizes, true);
        test = false;

    }

    public double[] feedforward(double[] a) {
        //feedforward our input through the neural net
        double[][] output = Helper.arrayToMatrix(a);

        for (int i = 0; i < num_layers - 1; i++) {
            double[][] weight = Helper.matrixProduct(weights.weights[i], output);
            double[][] z = Helper.matrixAdd(weight, biases.biases[i]);
            output = sigmoid(z);
        }

        //converts the output of our final layer back into a single dimensional array
        return Helper.MatrixToArray(output);
    }

    public Gradient backprop(Neuron a) {
        //back propagation for our network

        Biases new_b = new Biases(sizes, false);
        Weights new_w = new Weights(sizes, false);
        double[][] nb = new_b.biases;
        double[][][] nw = new_w.weights;

        double[] activation = a.activation;
        double[] y = a.output;

        //activations stores each layers output
        //inputs stores each layers inputs
        double[][] activations = new double[num_layers][];
        double[][] inputs = new double[num_layers - 1][];
        activations[0] = activation;

        double[][] output = Helper.arrayToMatrix(activation);

        //feedforward our input through the neural net, storing each layers inputs and outputs into inputs and activations
        for (int i = 0; i < num_layers - 1; i++) {
            double[][] wa = Helper.matrixProduct(weights.weights[i], output);
            double[][] layer_input = Helper.matrixAdd(wa, biases.biases[i]);
            inputs[i] = Helper.MatrixToArray(layer_input);
            output = sigmoid(layer_input);
            activations[i + 1] = Helper.MatrixToArray(output);
        }


        //compute error of last layer
        double[] delta = cost_derivative(activations[activations.length - 1], y);
        delta = Helper.ArrayMultiplication(delta, sigmoid_prime(inputs[inputs.length - 1]));
        nb[nb.length - 1] = Helper.copyArray(delta);
        nw[nw.length - 1] = Helper.matrixProduct(
                Helper.arrayToMatrix(delta),
                Helper.transposeMatrix(Helper.arrayToMatrix(activations[activations.length - 2])));

        //backprop our error through the network, storing the changes in weights and biases layer by layer
        for (int L = 2; L < num_layers; L++) {
            double[] z = inputs[inputs.length - L];
            double[] sp = sigmoid_prime(z);
            double[][] transposed = Helper.transposeMatrix(weights.weights[weights.weights.length - L + 1]);

            delta = Helper.MatrixToArray(Helper.matrixProduct(transposed, delta));
            delta = Helper.ArrayMultiplication(delta, sp);

            nb[nb.length - L] = Helper.copyArray(delta);

            nw[nw.length - L] = Helper.matrixProduct(
                    Helper.arrayToMatrix(delta),
                    Helper.transposeMatrix(Helper.arrayToMatrix(activations[activations.length - L - 1])));

        }

        return (new Gradient(nb, nw));
    }

    public void update_mini_batch(Neuron[] mini_batch, double learningRate) {
        //The "mini_batch" is an array of data sets

        Biases nb = new Biases(sizes, false);
        Weights nw = new Weights(sizes, false);

        for (Neuron i : mini_batch) {
            Gradient temp = backprop(i);

            nb.biases = Helper.matrixAdd(nb.biases, temp.biases);
            nw.weights = Helper.matrixAdd(nw.weights, temp.weights);

        }

        //compute our new biases and weights
        double diff = learningRate / ((double) mini_batch.length);

        //saves our new weights
        nw.weights = Helper.matrixMulti(nw.weights, -diff);
        weights.weights = Helper.matrixAdd(weights.weights, nw.weights);

        //saves our new biases
        nb.biases = Helper.matrixMulti(nb.biases, -diff);
        biases.biases = Helper.matrixAdd(biases.biases, nb.biases);

    }


    public double[] cost_derivative(double[] output_activation, double[] y) {
        //computes the cost derivative for our function by
        //subtracting the actual answer from our desired output element-wise
        //returns a new array

        double[] answer = new double[y.length];

        for (int i = 0; i < y.length; i++)
            answer[i] = output_activation[i] - y[i];
        return answer;
    }

    public void SGD(Neuron[] training_data, int epochs, int mini_batch_size, double learningRate) {
        //stochastic gradient descent function

        int n_test = 0;
        if (test) {
            n_test = test_data.length;
        }

        int n = training_data.length;
        int numOfMiniBatches = n / mini_batch_size;

        for (int i = 0; i < epochs; i++) {

            training_data = Layer.shuffle(training_data);
            int start = 0;

            for (int j = 0; j < numOfMiniBatches; j += mini_batch_size) {
                Neuron[] mini_batch = Layer.subset(start, start + mini_batch_size, training_data);
                update_mini_batch(mini_batch, learningRate);
                start += mini_batch_size;
            }

            //if we have test data, display how accurate our network is once every epoch
            if (test) {
                double correct = evaluate(test_data);
                DecimalFormat decimalFormat = new DecimalFormat("##.##");
                System.out.println("Epoch " + i + ": " + (int) correct + "/" + n_test + " : " + decimalFormat.format(correct / ((double) n_test) * 100) + "%");
            } else {
                System.out.println("Epoch " + i + " Complete ");
            }

        }

        System.out.println("Training Complete");

    }


    public void SGD(Neuron[] training_data, int epochs, int mini_batch_size, double learningRate, Neuron[] test_data) {
        //display accuracy of our network

        this.test = true;
        this.test_data = test_data;

        SGD(training_data, epochs, mini_batch_size, learningRate);

        this.test = false;
        this.test_data = null;

    }

    public double evaluate(Neuron[] test_data) {
        double correct = 0;
        for (Neuron i : test_data) {
            double[] answer = feedforward(i.activation);

            if (Helper.Max(answer) == Helper.Max(i.output))
                correct++;

        }
        return correct;
    }

    public static double sigmoid(double z){
        //computs the sigmoid function
         return 1.0/(1.0+Math.exp(-z));
    }

    public static double tanh(double z){
        //computs the tanh function
        return  2.0/(1.0+Math.exp(-2.0*z))-1;
    }

    public static double relu(double z){
        //computs the Relu function
        return Math.max(0,z);

    }


    public static double[][] sigmoid(double[][] input){
        double[][] output = new double[input.length][1];
        for(int i = 0;i<input.length;i++){

            output[i][0] = sigmoid(input[i][0]);

        }
        return output;
    }

    public static double[] sigmoid(double[] input){
        double[] output = new double[input.length];
        for(int i = 0;i<input.length;i++){

            output[i] = sigmoid(input[i]);

        }
        return output;
    }

    public static double[] sigmoid_prime(double[] z){
        // sig_prime = sig*(1-sig)
        double[] a = sigmoid(z);
        double[] b = Helper.matrixMulti(sigmoid(z),-1);
        b = Helper.matrixAdd(b,1);

        return Helper.ArrayMultiplication(a,b);
    }



    public void export(String fileName) throws IOException {
        //exports our networks biases and weights

        //file to write network info to
        File file = new File(fileName);

        // creates the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        //first line of the file is Sizes
        for(int i : sizes)
            writer.write(i+" ");
        writer.newLine();

        //next set of lines is biases, layer by layer
        for(double[] layer : biases.biases)
        {
            for(double node : layer){
                writer.write(node+ " ");
            }
            writer.newLine();
        }

        // print out each set of weights, layer by layer, node by node
        for(double[][] between : weights.weights){
            for(double[] layer : between){
                for(double node : layer){
                    writer.write(node+" ");
                }
                writer.newLine();
            }

        }
        writer.flush();
        writer.close();
        System.out.println("Network Exported Successfully");
    }

    public MLP(String fileName) throws IOException{

        //reader pointed at our file
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        //read in the first line, which is our sizes
        String line = br.readLine();
        sizes = new int[line.split(" ").length];
        for(int i = 0;i<sizes.length;i++)
            sizes[i] = Integer.parseInt(line.split(" ")[i]);


        this.sizes = sizes;
        this.num_layers = sizes.length;

        biases = new Biases(sizes, false);
        weights = new Weights(sizes, false);


        //read in and store the biases from our file line by line
        for(int i = 0;i<biases.biases.length;i++){
            String[] splitup = br.readLine().split(" ");
            double[] temp = new double[splitup.length];
            for(int j = 0;j<temp.length;j++){
                temp[j] = Double.parseDouble(splitup[j]);
            }
            biases.biases[i] = temp;
        }

        //read in and store the weights layer by layer, node by node
        for(int base = 0;base<weights.weights.length;base++){
            double[][] between = new double[sizes[base+1]][];
            for(int i = 0;i<between.length;i++){
                String[] splitup = br.readLine().split(" ");
                double[] temp = new double[splitup.length];
                for(int j = 0;j<splitup.length;j++){
                    temp[j] = Double.parseDouble(splitup[j]);

                }
                between[i] = temp;
            }
            weights.weights[base] = between;
        }
        br.close();

        System.out.println("Network Imported Successfully");
    }
}
