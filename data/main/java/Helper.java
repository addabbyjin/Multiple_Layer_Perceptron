//This class contains some matrix-related operations' functions that is mainly builder for assistant our network software.

import java.util.Random;

public class Helper {
    private static Random rnd = new Random();

    public Helper(){
        //initializes a random variable for to use later
        rnd = new Random();
    }

    public static double[][] arrayToMatrix(double[] input){
        //converts an array into a 2-d array (for easier matrix manipulation in java)
        //input[size] into output[size][1]
        double[][] output = new double[input.length][1];
        for(int i=0;i<input.length;i++)
            output[i][0] = input[i];
        return output;
    }

    public static double[] MatrixToArray(double[][] input){
        //converts a 2-d array into an array (for easier matrix manipulation in java)
        //input[size][1] into output[size]
        double[] output = new double[input.length];
        for(int i = 0;i<input.length;i++)
            output[i] = input[i][0];
        return output;
    }

    public static double getRandom(){
        //generates a random number
        return rnd.nextGaussian();
    }

    public static double[][] MatrixMultiplication(double[][] a, double[][] b){
        //calculates the product of two Matrix and returns a new Matrix
        double[] a2 = MatrixToArray(a);
        double[] b2 = MatrixToArray(b);
        double[] out = ArrayMultiplication(a2,b2);
        return arrayToMatrix(out);
    }


    public static double[] ArrayMultiplication(double[] a, double[] b){
        //calculates the product of two arrays a and b and returns a new Array;
        double[] out = new double[a.length];
        for(int i = 0;i<out.length;i++)
            out[i] = a[i]*b[i];

        return out;
    }

    public static double[] fillArray(double a[], boolean random){
        //if random is true, fills an array with random numbers
        //if random is false, fills the array with zeroes
        double[] b = new double[a.length];
        for(int i = 0;i<a.length;i++)
            if(random)
                b[i] = getRandom();
            else
                b[i] = 0;

        return b;
    }

    public static double[][] fillMatrix(double a[][], boolean random){
        //if random is true, fills a matrix with random numbers
        //if random is false, fills the matrix with zeroes
        double[][] b = new double[a.length][];
        for(int i = 0;i<a.length;i++)
            b[i] = fillArray(a[i],random);

        return b;
    }

    public static double[][][] fill3DMatrix(double a[][][], boolean random){
        //if rnd is true, fills a matrix with random numbers
        //if rnd is false, fills the matrix with zeroes
        double[][][] b = new double[a.length][][];
        for(int i = 0;i<a.length;i++)
            b[i] = fillMatrix(a[i],random);

        return b;
    }

    public static double[][] matrixProduct(double[][] a, double[] b){
        //multiplies a matrix by an array
        return matrixProduct( a , arrayToMatrix(b) );
    }

    public static double[][] matrixProduct(double[][] a, double[][] b){
        //returns the product of two matrix a and b
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        double[][] c = new double[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++)
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    public static double[] copyArray(double[] input){
        //copy of an array
        double[] output = new double[input.length];

        for(int i = 0;i<output.length;i++)
            output[i] = input[i];

        return output;
    }

    public static double[][] copy2DArray(double[][] input){
        //copy of a matrix with input[size][1]
        double[][] output = new double[input.length][1];

        for(int i = 0;i<output.length;i++)
            output[i][0] = input[i][0];

        return output;

    }

    public static double[] matrixAdd(double[] a , double b){
        //adds *b to *a element-wise
        //returns a new array
        double[] output = new double[a.length];
        for(int i = 0;i<output.length;i++)
            output[i] = a[i]+b;

        return output;

    }

    public static double[] matrixAdd(double[] a, double[] b){
        //adds two arrays together element-by-element, returns the new array

        double[] output = new double[a.length];
        for(int i = 0;i<output.length;i++)
            output[i] = a[i]+b[i];

        return output;
    }

    public static double[][] matrixAdd(double[][] a, double b){
        //adds two arrays together element-by-element, returns the new array

        double[][] output = new double[a.length][];
        for(int i = 0;i<output.length;i++)
            output[i] = matrixAdd(a[i],b);

        return output;
    }

    public static double[][] matrixAdd(double[][] a, double[][] b){
        //adds two matrices together element-wise, a and b must have the same shape

        double[][] output = new double[a.length][];
        for(int i = 0;i<output.length;i++){
            output[i] = new double[a[i].length];
            for(int j = 0;j<output[i].length;j++){
                output[i][j] = a[i][j]+b[i][j];
            }
        }
        return output;

    }

    public static double[][][] matrixAdd(double[][][] a, double[][][] b){
        //adds two 3-d matrices together element-wise, a and b must have the same shape
        double[][][] output = new double[a.length][][];
        for(int i = 0;i<output.length;i++){
            output[i] = matrixAdd(a[i],b[i]);
        }
        return output;
    }

    public static double[][] matrixAdd(double[][] a, double[] b){
        //wrapper method to add two arrays together element-wise
        //shape of the arrays is a[size][1] and b[size]
        double[][] output = new double[a.length][1];
        for(int i = 0;i<output.length;i++)
            output[i][0] = a[i][0]+b[i];

        return output;
    }

    public static double[][][] matrixAdd(double[][][] a, double b){
        //wrapper method to add number b to matrix a element-wise
        double[][][] output = new double[a.length][][];
        for(int i = 0;i<output.length;i++)
            output[i] = matrixAdd(a[i],b);

        return output;
    }

    public static int Max(double[] a){
        //returns the index of the largest value in array a
        int index = 0;
        double max_val = -1;
        for(int i=0;i<a.length;i++)
            if(a[i]>max_val){
                index = i;
                max_val = a[i];
            }
        return index;
    }

    public static double[][] transposeMatrix(double[][] input)
    {   //returns a new matrix, which is the transposed matrix of input
        int m = input.length;
        int n = input[0].length;

        double[][] output = new double[n][m];

        for(int x = 0; x < n; x++)
        {
            for(int y = 0; y < m; y++)
            {
                output[x][y] = input[y][x];
            }
        }

        return output;
    }


    public static double[] matrixMulti(double[] a, double b){
        // array multiply a double
        double[] out = new double[a.length];
        for(int i = 0;i<a.length;i++)
            out[i] = a[i]*b;
        return out;

    }

    public static double[][] matrixMulti(double[][] a, double b){
        //matrix multiply a double
        double[][] out = new double[a.length][];
        for(int i = 0;i<a.length;i++)
            out[i] = matrixMulti(a[i],b);

        return out;
    }

    public static double[][][] matrixMulti(double[][][] a, double b){
        //3D matrix multiply a double
        double[][][] out = new double[a.length][][];
        for(int i = 0;i<a.length;i++)
            out[i] = matrixMulti(a[i],b);

        return out;
    }

    public static double[][] matrixMulti(double[][] a, double[][]b){
       //matrix Multiply
        double[][] out = new double[a.length][];
        for(int i = 0; i<a.length;i++){
            out[i] = new double[a[i].length];
            for(int j = 0;j<a[i].length;j++)
                out[i][j] = a[i][j] * b[i][j];
        }
        return out;
    }

    public static double[][][] matrixMulti(double[][][] a, double[][][] b){
        //3D matrix Multiply
        double[][][] out = new double[a.length][][];
        for(int i = 0; i<a.length;i++){
            out[i] = new double[a[i].length][];
            for(int j = 0;j<a[i].length;j++){
                out[i][j] = new double[a[i][j].length];
                for(int k = 0;k<a[i][j].length;k++)
                    out[i][j][k] = a[i][j][k] * b[i][j][k];
            }
        }
        return out;
    }


}
