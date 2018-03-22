public class EnumSort extends Sort{

    private int[] pos;
    private int[] buffer;

    public EnumSort(int[] origin, int n){
        super(origin, n);
        pos = new int[n];
        buffer = new int[n];
        System.arraycopy(origin, 0, buffer, 0, n);
    }

    public void sort(){
        enumSort();
        check();
        writeToFile("order2.txt");
    }

    private void enumSort(){
        for(int i=0; i<size; i++){
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

        for(int i=0; i<size; i++){
            data[pos[i]] = buffer[i];
        }
    }
}
