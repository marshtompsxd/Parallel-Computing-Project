public class MergeSort extends Sort {

    private int[] buffer;

    public  MergeSort(int[] origin, int n){
        super(origin, n);
        buffer = new int[size];
    }

    public void sort(){
        mergeSort(0, size-1);
        check();
        writeToFile("order3.txt");
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

}
