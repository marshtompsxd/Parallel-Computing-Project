import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class QuickSortMultiThread extends Thread  {

    private int[] data;
    private int size;
    private int left, right;
    public static int numThreads = Runtime.getRuntime().availableProcessors();
    public static int count = 0;
    private static Lock lock = new ReentrantLock();

    public QuickSortMultiThread(int[] origin, int n){
        data = new int[n];
        size = n;
        left = 0;
        right = n - 1;
        System.arraycopy(origin,0, data, 0, n);
    }

    public QuickSortMultiThread(int[] origin, int left, int right){
        this.left = left;
        this.right = right;
        data = origin;
    }

    public void sort(){

        run();
        check();
        writeToFile("order4.txt");
    }

    @Override
    public void run(){
        quickSortParallel(left, right);
    }

    private void quickSortParallel(int left, int right){
        if(left >= right)return;
        int pivot_pos = partition(left, right);

        if(right - left < 1500){
            quickSort(left, pivot_pos - 1);
            quickSort(pivot_pos + 1, right);
        }
        else{
            lock.lock();
            if (numThreads - count > 0) {
                count++;
                lock.unlock();
                QuickSortMultiThread qsm = new QuickSortMultiThread(data, left, pivot_pos - 1);
                qsm.start();
                quickSortParallel(pivot_pos + 1, right);

                try {
                    qsm.join();
                } catch (InterruptedException e) {
                    System.out.println("join fail.");
                }

                lock.lock();
                count--;
                lock.unlock();
            }
            else{
                lock.unlock();
                quickSortParallel(left, pivot_pos - 1);
                quickSortParallel(pivot_pos + 1, right);
            }
        }
    }

    private void quickSort(int left, int right)
    {
        if(left >= right)return;
        int pivot_pos = partition(left, right);
        quickSort(left, pivot_pos - 1);
        quickSort(pivot_pos + 1, right);
    }

    private int partition(int left, int right)
    {
        int pos = median3(left, right, (left + right)/2);
        exchange(pos, left);
        int pivot = data[left];
        while (left<right){
            while (left<right && data[right]>=pivot) --right;
            data[left]=data[right];
            while (left<right && data[left]<=pivot) ++left;
            data[right] = data[left];
        }
        data[left] = pivot;
        return left;
    }

    private int median3(int a, int b, int c){
        if(data[a] >= data[b] && data[b] >= data[c]) return b;
        else if(data[a] >= data[c] && data[c] >= data[b]) return c;
        else return a;
    }

    private void exchange(int i, int j)
    {
        if(i == j)return;
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
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
