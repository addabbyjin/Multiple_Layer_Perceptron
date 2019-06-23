public class Neuron {
    public double[] output;              //the output of the solution
    public double[] activation;     //the inputs for the first layer


    public Neuron(double[] activation, double[] output) {
        this.output =  new double[output.length];

        this.activation = new double[activation.length];

        for(int i = 0;i<activation.length;i++)
            this.activation[i] = activation[i];

        for(int i = 0;i<output.length;i++)
            this.output[i] =  output[i];


    }

    public Neuron(Neuron old){
        this.activation = new double[old.activation.length];

        this.output = new double[old.output.length];

        for(int i = 0;i<activation.length;i++)
            this.activation[i] = old.activation[i];

        for(int i = 0;i<output.length;i++)
            this.output[i] = old.output[i];

    }
}
