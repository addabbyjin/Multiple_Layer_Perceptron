public class Layer {

    public static Neuron[] training_data; //data to train the neural net on
    public static Neuron[] validation_data; //used to validate
    public static Neuron[] test_data; //used to test our networks accuracy

    public Layer(Neuron[] getData)
    {
        getData = shuffle(getData);

        //size of the test and validation sets
        int smallSetSize = getData.length/7;

        //validation data is 1/7 of our total data
        //test data is 1/7 of our total data
        //training_data is 5/7 (the rest of) our data

        validation_data = subset(0,smallSetSize,getData);
        test_data = subset(smallSetSize,smallSetSize*2,getData);
        training_data = subset(smallSetSize*2, getData.length,getData);

        getData = null;


    }

    public static Neuron[] subset(int start, int finish, Neuron[] input){
        //returns slice of our input array
        Neuron[] temp = new Neuron[finish-start];
        int index = 0;
        for(int i = start;i<finish;i++){
            temp[index] = new Neuron(input[i]);
            index++;}
        return temp;
    }

    public static Neuron[] shuffle(Neuron[] input){
        //returns a new array of Neuron, which is the input array shuffled
        Neuron[] output = new Neuron[input.length];
        //deep copy of the input array
        for(int i = 0;i<output.length;i++)
            output[i] = new Neuron(input[i]);


        for(int i = output.length-1;i>0;i--){
            Neuron temp = new Neuron(output[i]);
            int random = (int)(Math.random()*i);
            output[i] = new Neuron(output[random]);
            output[random] = temp;
        }

        return output;
    }

}
