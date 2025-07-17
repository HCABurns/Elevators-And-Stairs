import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Agent2 {

    static int h,w;
    static ArrayList<String> map = new ArrayList<>();

    public static void main(String[] args) {

        // Read inputs.
        InputStream inStream = ClassLoader.getSystemResourceAsStream("test1data.txt");
        if (inStream == null){return;}
        System.out.println("Successfully read in...\n");
        try (Scanner in = new Scanner(inStream)) {
            w = in.nextInt();
            h = in.nextInt();
            in.nextLine();

            for (int i = 0; i < h; i++) {
                String row = in.nextLine().substring(0, w);
                map.add(row);
            }
        }

        // Check that the input is correct.
        System.out.println("Rows");
        for (String row : map){
            System.out.println(row);
        }
        // Process...



    }
}
