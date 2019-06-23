// This class we ues it as a reference for the purpose of extracting image
public class MnistReader {
    private static Neuron[] images;
    private static MnistFile label;
    private static MnistFile image;


    public static Neuron[] getData(String[] fileNames, int num){
        //gets data from label and image filenames stored as string
        //num is the number of Mnist images we would like to load and if num==0, we will load all the images.
        label = new MnistFile(fileNames[0]);
        image = new MnistFile(fileNames[1]);
        buildDataSet(num);
        return images;
    }

    public static Neuron[] getAllData(String[] fileNames){
        return getData(fileNames, 0);
    }

    private static void buildDataSet(int num) {

        int numOfImages;
        int numberOfRows;
        int numberOfColumns;

        int magicNumber = image.fetch(4);
        numOfImages = image.fetch(4);
        numberOfRows = image.fetch(4);
        numberOfColumns = image.fetch(4);

        //gets our info from the label file
        magicNumber = label.fetch(4);
        numOfImages = label.fetch(4);

        int imagesToLoad;
        if(num==0)
            imagesToLoad = numOfImages;
        else
            imagesToLoad = num;

        images = new Neuron[imagesToLoad];

        //loads our images activation and output and stores it into our array
        for(int i = 0;i<imagesToLoad;i++){

            //prints out our progress in 10% increments
            if(i%(imagesToLoad/10)==0){
                System.out.println("Loading Images "+ i+ " of "+imagesToLoad);
            }

            //activation of our next image
            double[] activation  = new double[numberOfRows*numberOfColumns];
            for(int j = 0;j<activation.length;j++)
                activation[j] = ((double)image.fetch(1))/255;

            //the number our image is supposed to represent
            int imageNumber = label.fetch(1);

            //creates our output based on the number of our image
            double[] output = new double[10];
            for(int q = 0;q<output.length;q++)
                output[q] = 0;
            output[imageNumber] = 1;

            //stores our image
            images[i] = new Neuron(activation,output);
        }

        image.close();
        label.close();
        System.out.println("Images Loaded");

    }

    public static void printImage(Neuron a){
        int i = 0;
        int width = 28;
        System.out.println("------------------------------");
        for(int j = 0;j<width;j++){
            for(int index = 0;index<width;index++){
                if(a.activation[i]>.8)
                    System.out.print("X");
                else if(a.activation[i]>.6)
                    System.out.print("0");
                else
                    System.out.print(" ");
                i++;
            }
            System.out.println();
        }
        int solution = 0;
        for(int q =0;q<a.output.length;q++)
            if(a.output[q]>0)
                solution = q;
        System.out.println("Mnist classifies this as a "+solution);

        System.out.println("------------------------------");
        System.out.println();
    }


}