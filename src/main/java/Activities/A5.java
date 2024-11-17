package Activities;

// Same as in A4 but using synchronization

public class A5 {
    public static int counter = 0;
    public static int iterations = 500_000;

    public static synchronized void incrementCounter() {
        counter++;
    }

    public static void main(String[] args) throws InterruptedException {

        Thread firstCounterThread = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                incrementCounter();
            }
        }, "First Counter Thread");

        Thread secondCounterThread = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                incrementCounter();
            }
        }, "Second Counter Thread");

        Thread.sleep(500);

        firstCounterThread.start();
        secondCounterThread.start();

        firstCounterThread.join();
        secondCounterThread.join();

        System.out.println("Final result, synchronized, is: " + counter);
    }
}
