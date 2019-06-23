public class Biases {
    double biases[][];
    public Biases(int [] sizes, boolean random){
        biases = new double[sizes.length-1][];
        for(int i = 0;i<biases.length;i++)
            biases[i] = new double[sizes[i+1]];

        for(int i = 0;i<biases.length;i++)
            biases[i] = Helper.fillArray(biases[i],random);

    }
}

