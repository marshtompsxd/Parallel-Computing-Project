import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static int[] data;
    private static int size;

    public static void main(String []args)
    {
        loadFile();
        //printArray();

        long start = System.currentTimeMillis();
        QuickSort qs = new QuickSort(data, size);
        qs.sort();
        System.out.println("Sequential quick sort in "+(System.currentTimeMillis()-start)+" ms");

        start = System.currentTimeMillis();
        EnumSort es = new EnumSort(data, size);
        es.sort();
        System.out.println("Sequential enum sort in "+(System.currentTimeMillis()-start)+" ms");

        start = System.currentTimeMillis();
        MergeSort ms = new MergeSort(data, size);
        ms.sort();
        System.out.println("Sequential merge sort in "+(System.currentTimeMillis()-start)+" ms");

        start = System.currentTimeMillis();
        QuickSortMultiThread qst = new QuickSortMultiThread(data, size);
        qst.sort();
        System.out.println("parallel quick sort in "+(System.currentTimeMillis()-start)+" ms");

        start = System.currentTimeMillis();
        EnumSortMultiThread est = new EnumSortMultiThread(data, size);
        est.sort();
        System.out.println("parallel enum sort in "+(System.currentTimeMillis()-start)+" ms");

        start = System.currentTimeMillis();
        MergeSortMultiThread mst = new MergeSortMultiThread(data, size);
        mst.sort();
        System.out.println("parallel merge sort in "+(System.currentTimeMillis()-start)+" ms");

    }

    private static void loadFile()
    {
        try {
            Scanner scanner = new Scanner(new File("random.txt"));
            data = new int[2000000];
            size = 0;
            while(scanner.hasNextInt()){
                data[size] = scanner.nextInt();
                size++;
            }
        }catch(IOException e){
            System.out.println("file open fail.");
        }
    }

    private static void printArray()
    {
        System.out.println(size);
        System.out.println(Arrays.toString(data));
    }
}
