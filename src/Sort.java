import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Sort {

    protected int[] data;
    protected int size;
    protected int left, right;

    public Sort(int[] origin, int n )
    {
        data = new int[n];
        size = n;
        left = 0;
        right = n - 1;
        System.arraycopy(origin,0, data, 0, n);
    }

    public Sort(int[] origin, int left, int right)
    {
        this.left = left;
        this.right = right;
        data = origin;
    }

    public void sort()
    {
        System.out.println("not implement sort.");
    }

    protected void check()
    {
        for(int i=1;i<size;i++) {
            if(data[i] < data[i-1]){System.out.println("error");}
        }
    }

    protected void writeToFile(String filename)
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
