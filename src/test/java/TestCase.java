
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestCase {
    private double[] arr = {1.0,2.0,3.0,4.0,5.0};
    private double[] arr2 = {1.0,2.0,3.0,4.0,5.0};
    private double[] arr3 = {1.0,4.0,9.0,16.0,25.0};
    private double[][] MatrixArr = {{1.0},{2.0},{3.0},{4.0},{5.0}};
    private double[][] MatrixArr2 = {{1.0},{2.0},{3.0},{4.0},{5.0}};
    private double[][] MatrixArr3 = {{1.0},{4.0},{9.0},{16.0},{25.0}};
    private double[][] MatrixArr4 = {{2.0},{4.0},{6.0},{8.0},{10.0}};

    @org.junit.Test
    public void arrayToMatrix() {
        assertEquals(MatrixArr,Helper.arrayToMatrix(arr));
    }

    @org.junit.Test
    public void MatrixToArray() {
        assertArrayEquals(arr,Helper.MatrixToArray(MatrixArr),0.0);
    }
    @org.junit.Test
    public void MatrixMultiplication(){
        assertEquals(MatrixArr3,Helper.MatrixMultiplication(MatrixArr,MatrixArr2));
    }

    @org.junit.Test
    public void ArrayMultiplication(){
        assertArrayEquals(arr3,Helper.ArrayMultiplication(arr,arr2),0.0);
    }

    @org.junit.Test
    public void matrixProduct(){
        assertEquals(MatrixArr3,Helper.matrixMulti(MatrixArr,MatrixArr2));
    }

    @org.junit.Test
    public void copyArray(){
        assertArrayEquals(arr,Helper.copyArray(arr),0.0);
    }

    @org.junit.Test
    public void copy2DArray(){
        assertEquals(MatrixArr,Helper.copy2DArray(MatrixArr));
    }

    @org.junit.Test
    public void matrixMulti(){
        assertEquals(MatrixArr3,Helper.matrixMulti(MatrixArr,MatrixArr2));
    }

    @org.junit.Test
    public void matrixAdd(){
        assertEquals(MatrixArr4,Helper.matrixAdd(MatrixArr,MatrixArr2));
    }

    @org.junit.Test
    public void Max(){
        assertEquals(4,Helper.Max(arr));
    }


}
