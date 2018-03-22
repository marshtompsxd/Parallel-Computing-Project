public class QuickSort extends Sort{

    public QuickSort(int[] origin, int n )
    {
       super(origin, n);
    }

    public QuickSort(int[] origin, int left, int right)
    {
        super(origin, left, right);
    }

    public void sort()
    {
        quickSort(0, size-1);
        check();
        writeToFile("order1.txt");
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

}
