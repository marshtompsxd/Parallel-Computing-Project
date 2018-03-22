import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MergeSortMultiThread extends Thread{

    private int[] data;
    private int size;
    private int left, right;
    public static int numThreads = Runtime.getRuntime().availableProcessors();
    public static int count = 0;
    private static Lock lock = new ReentrantLock();

    private int[] buffer;

    public MergeSortMultiThread(int[] origin, int n){
        data = new int[n];
        buffer = new int[n];
        size = n;
        left = 0;
        right = n - 1;
        System.arraycopy(origin,0, data, 0, n);
    }

    public MergeSortMultiThread(int[] origin, int[] buf, int left, int right, int size){
        this.left = left;
        this.right = right;
        data = origin;
        buffer = buf;
        this.size = size;

    }

    public void sort(){
        //System.out.println(numThreads);

        run();
        //System.out.println(count);
        check();
        writeToFile("order6.txt");
    }

    @Override
    public void run(){
        mergeSortParallel(left, right);
    }

    private void mergeSortParallel(int left, int right){
        if(left >= right)return;
        int middle = (left + right)/2;

        if(right - left < 1500){
            mergeSort(left, middle);
            mergeSort(middle + 1, right);
            merge(left, middle, right);
        }
        else {
            lock.lock();
            if (numThreads - count > 0) {
                count++;
                lock.unlock();
                MergeSortMultiThread msm = new MergeSortMultiThread(data, buffer, left, middle, size);
                msm.start();
                mergeSortParallel(middle + 1, right);

                try {
                    msm.join();
                } catch (InterruptedException e) {
                    System.out.println("join fail.");
                }

                lock.lock();
                count--;
                lock.unlock();
            }
            else{
                lock.unlock();
                mergeSortParallel(left, middle);
                mergeSortParallel(middle + 1, right);
            }
            merge(left, middle, right);
        }


    }

    private void mergeSort(int left, int right){
        if(left >= right)return;
        int middle = (left + right)/2;
        mergeSort(left, middle);
        mergeSort(middle + 1, right);
        merge(left, middle, right);
    }

    private void merge(int left, int middle, int right){
        System.arraycopy(data, left, buffer, left, right - left + 1);

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right){
            if(buffer[i] < buffer[j]) data[k++] = buffer[i++];
            else data[k++] = buffer[j++];
        }

        if(i <= middle) System.arraycopy(buffer, i, data, k, middle - i + 1);
        if(j <= right) System.arraycopy(buffer, j, data, k, right - j + 1);
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
