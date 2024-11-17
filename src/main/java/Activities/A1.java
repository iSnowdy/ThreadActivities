package Activities;
// Print Hello World from a Thread
public class A1 {
    public static void main(String[] args) {
        Thread helloWorldThread = new Thread(()-> {
            System.out.println("Hello World from " + Thread.currentThread().getName());
        }, "helloThread");
        helloWorldThread.start();
        System.out.println("Hello World from " + Thread.currentThread().getName());
    }
}
