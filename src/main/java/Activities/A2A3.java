package Activities;

// Print Hello World 5 times from 5 different Threads

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello World from " + Thread.currentThread().getName());
    }
}

public class A2A3 {
    public static void main(String[] args) {
        int amountOfThreadsToCreate = 5;

        for (int i = 0; i < amountOfThreadsToCreate; i++) {
            new MyThread().start();
        }
    }
}
