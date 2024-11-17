package Activities;

// Simulate a relay race with 4 different runners. Each runner is represented
// by a thread that will start only once the previous thread has finished (except
// the first runner). Use the methods start() and join() for each thread.

class RunnerThread extends Thread {
    private final String NAME;

    public RunnerThread(String name) {
        this.NAME = name;
    }

    @Override
    public void run() {
        System.out.println("Thread " + NAME + " started running!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread " + NAME + " finished running!");
    }
}

public class A8 {
    public static void main(String[] args) throws InterruptedException {
        String[] runners = {"A", "B", "C", "D"};
        Thread[] runnerThreads = new Thread[runners.length];

        for (int i = 0; i < runners.length; i++) {
            runnerThreads[i] = new RunnerThread(runners[i]);
        }

        for (Thread runner : runnerThreads) {
            runner.start();
            runner.join();
        }

        System.out.println("Aaaand the race is OVER");
    }
}
