public class Localconvol extends Thread {
    int k, pos;
    int[][] t,a;
    int[] b;

    Localconvol(int k,int pos,int[][] a0,int[] b0) {
        this.k = k;
        this.pos = pos;

        a = a0;
        b = b0;
    }

    @Override
    public void run() {
        int i,j;

        t[pos][0] = a[pos][k]*b[(int) Math.pow(2,k)-pos-1];
    }
}
