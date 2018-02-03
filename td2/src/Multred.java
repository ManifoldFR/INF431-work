public class Multred extends Thread {
    int k;
    int pos;
    int a[][];

    Multred(int k,int pos,int[][] a0) {
        this.k = k;
        this.pos = pos;
        a = a0;
    }

    @Override
    public void run() {
        int i,j;

        for (i=1; i<=k;i++) {
            j = pos-1<<(i-1);
            if (j>=0) {
                a[pos][i] = a[pos][i-1]*a[j][i-1];
            } else {
                a[pos][i] = a[pos][i-1];
            }
        }


    }
}
