public class Test {

    public static void main(String args[]) throws InterruptedException {
        final int Ninserts = 500000;
        final int Nthreads = 10;

        Tester[] tasks = new Tester[Nthreads];
        Thread[] threads = new Thread[Nthreads];





        //System.out.println(stack.toString());


        //Stack<Integer> stack = new Stack<>();
        SafeStackSimple<Integer> safeStack = new SafeStackSimple<>();
        safeStack.enableDebug();
        for (int i=0; i<Nthreads; i++) {
            tasks[i] = new Tester(Ninserts, safeStack);
            //tasks[i] = new Tester(Ninserts, stack);
            threads[i] = new Thread(tasks[i]);
            threads[i].setName("Thread " + i);
        }


        for (int i=0; i<Nthreads; i++) {
            System.out.println(threads[i].getName() + " started");
            threads[i].start();
        }

        for (int i=0; i<Nthreads; i++) threads[i].join();
        //stack.print();

        //safeStack.print();
    }


}
