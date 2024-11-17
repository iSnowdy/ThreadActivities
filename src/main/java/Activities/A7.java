package Activities;

// Same as A6, but now use notify() and wait()

class CounterThread extends Thread {
    @Override
    public void run() {
        synchronized (A7.lock) {
            for (int i = 0; i < A7.iterations; i++) A7.numberToIncrease++;

            if (A7.numberToIncrease == 1_000_000) {
                System.out.println(Thread.currentThread().getName() + " is done");
                A7.lock.notify();
            }
        }
    }
}

class PrintThread extends Thread {
    @Override
    public void run() {
        synchronized (A7.lock) {
            while (A7.numberToIncrease != 1_000_000) {
                try {
                    A7.lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Counter is: " + A7.numberToIncrease);
        }
    }
}

public class A7 {
    public static int iterations = 1_000_000;
    public static int numberToIncrease = 0;

    public static final Object lock = new Object();

    public static void main(String[] args) {
        CounterThread counter = new CounterThread();
        PrintThread print = new PrintThread();
        counter.start();
        print.start();
    }
}

