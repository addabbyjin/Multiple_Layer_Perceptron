public class Weights{
    double weights[][][];
    public Weights(int[] s, boolean random){
        weights = new double[s.length-1][][];

        for(int num  =  0;num<weights.length;num++)
            weights[num] = new double[s[num+1]][s[num]];

        for(int num = 0;num<weights.length;num++)
            weights[num] = Helper.fillMatrix(weights[num],random);
    }

}