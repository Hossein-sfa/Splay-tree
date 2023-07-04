import java.io.*;
import java.util.*;

public class Test {
    static Random r = new Random();
    
    // calculating the average time of execution normal and uniform for 1000 runs for each input size and writing it to a file
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("data.txt", true);
        for (int i = 500; i > 0; i -= 50) {
            int numbers = i * 1000, uni_sum = 0, norm_sum = 0;
            for (int j = 0; j < 1000; j++) {
                uni_sum += uniform(numbers);
                norm_sum += normal(numbers);
            }
            int uni_avg = uni_sum / 1000, norm_avg = norm_sum / 1000;
            fw.write(numbers + " " + uni_avg + " " + norm_avg + "\n");
        }
        fw.close();
    }

    // calculating the time of execution for num inputs for uniform distribution
    public static long uniform(int num) {
        long start = System.currentTimeMillis(); 
        SplayTree tree = new SplayTree();
        int[] array = new int[num];
        for (int i = 0; i < array.length; i++) {
            int random = r.nextInt(99) + 1;
            array[i] = random;
            if (!tree.find(tree.root, random)) {
                tree.add(random);
                tree.find(tree.root, array[r.nextInt(i + 1)]);
            } 
        }
        return System.currentTimeMillis() - start;
    }

    // calculating the time of execution for num inputs for normal distribution
    public static long normal(int num) {
        long start = System.currentTimeMillis();
        SplayTree tree = new SplayTree();
        for (int i = 0; i < num; i++) {
            int random = (int) r.nextGaussian(50, 25);
            if (!tree.find(tree.root, random))
                tree.add(random);
            else 
                tree.find(tree.root, random);
        }
        return System.currentTimeMillis() - start;
    }
}
