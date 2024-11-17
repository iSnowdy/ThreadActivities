package Activities;

// Recreate the previous exercise but using wait() and notify()

class RunThread extends Thread {
    private final String NAME;
    private final String[] runnerNames;
    private static int turn = 0;

    public RunThread(String name, String[] runnersNames) {
        NAME = name;
        this.runnerNames = runnersNames;
    }

    @Override
    public void run() {
        synchronized (A9.lock) {
            try {
                while (!(runnerNames[turn].equals(this.NAME))) {
                    // If it is not the runner's turn, wait
                    A9.lock.wait();
                }
                A9.startRunning(this.NAME);
                turn++; // Relay
                A9.lock.notifyAll();
                // I think it must be notifyAll() because we do not know what thread is on the wait set
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class A9 {
    public static final Object lock = new Object();

    public static void startRunning(String name) throws InterruptedException {
        System.out.println(name + " is running!");
        Thread.sleep(500);
        System.out.println(name + " is done running");
    }

    public static void main(String[] args) throws InterruptedException {
        String[] runners = {"A", "B", "C", "D"};
        Thread[] threads = new Thread[runners.length];

        for (int i = 0; i < runners.length; i++) {
            threads[i] = new RunThread(runners[i], runners);
        }

        for (Thread runner : threads) {
            runner.start();
        }

        Thread.sleep(4000);
        System.out.println("And the race is over!");
    }
}