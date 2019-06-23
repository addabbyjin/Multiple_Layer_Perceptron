//This is the main class for running our project
public class Driver {
    public static void main(String[] args) {
        int[] sizes = new int[]{784, 30, 20, 10};

        //our label and image MNIST data files
        //it should be the exact path of files
        String[] fileNames = {"C:\\Users\\chenz\\OneDrive\\Final Project\\src\\main\\data\\t10k-labels.idx1-ubyte",
                              "C:\\Users\\chenz\\OneDrive\\Final Project\\src\\main\\data\\t10k-images.idx3-ubyte"};

     //   Layer layer = new Layer(MnistReader.getData(fileNames,5000)); //load the first 5000 images
          Layer layer = new Layer(MnistReader.getAllData(fileNames)); //loads all images


        //create a new perceptron with random weights and biases
        MLP perceptron = new MLP(sizes);

        //train our network on our test data
        // network.SGD(data.training_data, 30, 10, 3.0, data.test_data);

        perceptron.SGD(layer.training_data, 30, 10, 3.0,layer.test_data);
        //export our network for later use and testing!
        try {
            perceptron.export("mynetwork.txt");
        } catch (Exception e) {
            System.out.println(e);
        }

        //output 30 images at once and calculate it's accuracy
        double Accuracy = 300.00;
        Neuron[] test_images = new Neuron[30];
        for(int i=0; i < 30; i++) {
            try {
                MLP perceptron2 = new MLP("mynetwork.txt");
                test_images[i] = Layer.test_data[i];
                MnistReader.printImage(test_images[i]);
                int solution = 0;
                for(int q =0;q<test_images[i].output.length;q++)
                    if(test_images[i].output[q]>0)
                        solution = q;

                int guess = Helper.Max(perceptron2.feedforward(test_images[i].activation));
                System.out.println("Network thinks this is a " + guess);
                System.out.println();
                if(solution != guess ){
                    Accuracy -= 10;
                }
            }catch (Exception e){
                System.out.println(e);
            }
        }

        System.out.println("Current Accuracy:" + (Accuracy/300.00)*100.00 + "%");
    }
}
