public class Array2d {
    public static void main(String[] args) throws Exception {
        int[][] piksel = new int[2][3];
        piksel[0][0] = 70;
        piksel[0][1] = 30;
        piksel[0][2] = 45;
        piksel[1][0] = 75;
        piksel[1][1] = 86;
        piksel[1][2] = 89;
        int i, j;
        for ( i=0; 1<2; i++){
            for(j=0; j<3; j++){
                 System.out.print(piksel[i][j] + " ");
        }
        System.out.println("");
        }
    }
}