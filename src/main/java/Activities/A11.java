package Activities;

// Given an integer N divisible by 5, calculate the sum of all the integers
// from 1 to N, both included.
// Use at least 5 threads to accomplish this

class IntegerSum extends Thread {
    private final int START;
    private final int END;

    private int mySum = 0;

    public IntegerSum(int start, int end) {
        this.START = start;
        this.END = end;
    }

    @Override
    public void run() {
        for (int i = START; i <= END; i++) {
            mySum += i;
        }
        synchronized (A11.lock) {
            A11.finalResult += mySum;
        }

        System.out.println("Thread " + Thread.currentThread().getName() +
                " finished with sum of " + mySum);
    }
}

public class A11 {
    public static int N = 500;
    public static int finalResult = 0;
    public static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        int numberOfThreadsToUse = 5;

        Thread[ ] threads = new Thread[numberOfThreadsToUse];
        // Equal amount of operations by thread
        int rangeToCalculate = N / numberOfThreadsToUse;

        for (int i = 0; i < numberOfThreadsToUse; i++) {
            int start = i * rangeToCalculate + 1; // +1 because 1 is included
            int end = (i + 1) * rangeToCalculate;
            System.out.println("From " + start + " to " + end + " in iteration " + i);
            // [1, 100] - [101, 200] and so on
            // [1, N] ---> [1, 500] divided between [M] threads = 5 threads
            threads[i] = new IntegerSum(start, end);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Final result is : " + finalResult);
    }
}