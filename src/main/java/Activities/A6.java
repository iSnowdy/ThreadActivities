package Activities;

// Start two threads at once. One of them will increase an integer 1_000_000 times
// and a second thread will print the value of that integer all the time

public class A6 {
    public static int iterations = 1_000_000;
    public static int numberToIncrease = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread counterThread = new Thread (() -> {
            for (int i = 0; i < iterations; i++) {
                numberToIncrease++;
            }
        }, "Counter Thread");

        Thread printerThread = new Thread (() -> {
            for (int i = 0; i < iterations; i++) {
                System.out.println("Counter is now at: " + numberToIncrease);
            }
        }, "Printer Thread");

        Thread.sleep(500);

        counterThread.start();
        printerThread.start();
    }
}