package Activities;

// Simulate a 100m race of 5 animals. Each animal is represented by a thread
// that advances 1m in each iteration of the loop.
// Show the progress of each animal and the final ranking. You can use the yield()
// method at the end of each iteration to make the race more balanced

class AnimalThread extends Thread {
    private final String NAME;
    private final int RACE_LENGTH;
    public static int finishingPosition = 0;

    public static synchronized void increaseFinishingPosition() {
        finishingPosition++;
    }

    public AnimalThread(String name, int raceLength) {
        this.NAME = name;
        this.RACE_LENGTH = raceLength;
    }

    @Override
    public void run() {
        int currentProgress = 0;
        while (currentProgress < RACE_LENGTH) {
            synchronized (A10.lock) {
                currentProgress++; // One at a time
                System.out.println(this.NAME + " ran " + currentProgress + "m so far");
            }
            // Handles over the control to any other thread IF the thread scheduler decides so
            Thread.yield();
        }
        // Outside while-loop means this animal (thread) has finished running the 100m
        synchronized (A10.lock) {
            A10.attachResults(this.NAME, finishingPosition);
            increaseFinishingPosition();
        }
    }
}

public class A10 {
    public static String[] animals = {"Cheetah", "Leopard", "Elephant", "Bunny", "Turtle"};
    public static String[] finalResults = new String[animals.length];

    public static final Object lock = new Object();

    public static void attachResults(String name, int position) {
        String result = name + " finished in " + (position + 1) + " position";
        A10.finalResults[position] = result;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] animalThreads = new Thread[animals.length];

        for (int i = 0; i < animalThreads.length; i++) {
            animalThreads[i] = new AnimalThread(animals[i],100);
        }

        for (Thread animalThread : animalThreads) {
            animalThread.start();
        }

        for (Thread animalThread : animalThreads) {
            animalThread.join();
        }

        System.out.println("\nFinal results are...\n");
        for (String a : finalResults) {
            System.out.println(a);
        }
    }
}