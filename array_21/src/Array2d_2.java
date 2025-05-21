public class Array2d_2 {
    public static void main(String[] args) throws Exception {
        String[][] negara = new String[5][2];
        negara[0][0] = "Belanda";
        negara[0][1] = "Amsterdam";
        negara[1][0] = "Indonesia";
        negara[1][1] = "Jakarta";
        negara[2][0] = "Korea";
        negara[2][1] = "Seoul";
        negara[3][0] = "Inggris";
        negara[3][1] = "London";
        negara[4][0] = "India";
        negara[4][1] = "New Delhi";
        String namaNegara;
        for (int baris=0; baris< negara.length; baris ++){
            namaNegara = negara[baris][0];
            if (namaNegara.charAt(0)== 'I')
            System.out.println(negara[baris][0]+ " - " + negara[baris][1]);
        }
        System.out.println("Hello, World!");
    }
}
