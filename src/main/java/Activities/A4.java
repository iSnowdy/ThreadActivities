package Activities;

// Using 2 threads, increase an integer by 1, without synchronization,
// 1_000_000 times. Each thread must increase it by 500_000. Finally,
// print the end result

public class A4 {
    public static int counter = 0;
    public static int iterations = 500_000;
    public static void main(String[] args) throws InterruptedException {

        Thread firstCounterThread = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                counter++;
            }
        }, "First Counter Thread");
        Thread secondCounterThread = new Thread(() -> {
            for (int i = 0; i < iterations; i++) {
                counter++;
            }
        }, "Second Counter Thread");

        Thread.sleep(500);
        // Start Threads
        firstCounterThread.start();
        secondCounterThread.start();
        // Wait until they are finished
        firstCounterThread.join();
        secondCounterThread.join();

        System.out.println("Final result is: " + counter);
    }
}
