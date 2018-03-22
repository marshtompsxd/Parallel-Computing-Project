import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EnumSortMultiThread extends Thread{

    private int[] data;
    private int[] pos;
    private int size;
    private int left, right;
    private static int numThreads = Runtime.getRuntime().availableProcessors();
    //private static int numThreads = 4;
    public static int count = 0;
    private static Lock lock = new ReentrantLock();
    private int[] buffer;
    private EnumSortMultiThread[] esm;

    public EnumSortMultiThread(int[] origin, int n){
        data = new int[n];
        buffer = new int[n];
        pos = new int[n];
        esm = new EnumSortMultiThread[numThreads];
        size = n;
        left = 0;
        right = n - 1;
        System.arraycopy(origin,0, data, 0, n);
    }

    public EnumSortMultiThread(int[] origin, int[]buf, int left, int right, int[] position, int osize){
        this.left = left;
        this.right = right;
        data = origin;
        buffer = buf;
        pos = position;
        size = osize;
    }

    public void sort(){

        mainEnumSortParallel();
        check();
        writeToFile("order5.txt");
    }

    @Override
    public void run(){
        subEnumSortParallel(left, right);
    }

    private void mainEnumSortParallel(){
        int interval = (right - left + 1)/numThreads;
        for(int i = 1;i<numThreads;i++){
            if(i != numThreads - 1){
                esm[i] = new EnumSortMultiThread(data, buffer, left + i*interval, left + (i+1)*interval - 1, pos, size);
            }
            else{
                esm[i] = new EnumSortMultiThread(data, buffer, left + i*interval, right, pos, size);
            }

            esm[i].start();
        }
        subEnumSortParallel(left, left + interval - 1);
        for(int i = 1;i<numThreads;i++){
            try {
                esm[i].join();
            } catch (InterruptedException e) {
                System.out.println("join fail.");
            }
        }
        System.arraycopy(buffer, 0, data, 0, size);
    }

    private void subEnumSortParallel(int low, int high){
        for(int i=low; i<=high; i++){
            int index = 0;
            for(int j = 0; j<size; j++){
                if(i == j)continue;
                if(data[j] < data[i])index++;
                else if(data[j] == data[i]) {
                    if(j < i)index++;
                }
            }
            pos[i] = index;
        }

        for(int i=low; i<=high; i++){
            buffer[pos[i]] = data[i];
        }
    }

    private void check()
    {
        for(int i=1;i<size;i++) {
            if(data[i] < data[i-1]){System.out.println("error");}
        }
    }

    private void writeToFile(String filename)
    {
        try {
            Writer wr = new FileWriter(filename);
            for(int i=0;i<size;i++){
                wr.write(data[i] + " ");
            }
            wr.close();
        }catch (IOException e){
            System.out.println("file open fail.");
        }
    }

}
