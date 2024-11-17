package Examples;

public class MultipleWaitSets {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                try {
                    System.out.println("Thread 1 waiting on lock1...");
                    lock1.wait();
                    System.out.println("Thread 1 resumed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                try {
                    System.out.println("Thread 2 waiting on lock2...");
                    lock2.wait();
                    System.out.println("Thread 2 resumed");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread notifier = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Notifier notifying lock1...");
                lock1.notify(); // Notifies only lock 1 pool
            }

            synchronized (lock2) {
                System.out.println("Notifier notifying lock2...");
                lock2.notify(); // Notifies only lock 2 pool
            }
        });

        t1.start();
        t2.start();
        notifier.start();
    }
}
