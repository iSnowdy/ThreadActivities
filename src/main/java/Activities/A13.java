package Activities;

// Repeat the previous activity but now
// instead of a word, analyze a text document

import java.io.*;

class ImprovedVowelCounter extends Thread {
    private final File FILE;
    private final char MY_VOWEL;
    private int individualVowelCounter = 0;

    public ImprovedVowelCounter(final File FILE, final char MY_VOWEL) {
        this.FILE = FILE;
        this.MY_VOWEL = MY_VOWEL;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.toUpperCase(); // Vowels are in uppercase
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == MY_VOWEL) {
                        individualVowelCounter++;
                        synchronized (A13.lock) {
                            A13.sharedTotalVowelCounter++;
                        }
                    }

                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file from " + Thread.currentThread().getName());
            e.printStackTrace();
        }

    }

    public int getIndividualVowelCounter() {
        return individualVowelCounter;
    }
}

public class A13 {
    public static final Object lock = new Object();
    public static int sharedTotalVowelCounter = 0;

    public static void main(String[] args) throws InterruptedException {
        final String PATH = "src/main/java/Notes";
        File file = new File(PATH);
        if (!file.exists()) {
            System.out.println("The given file does not exist");
            System.exit(-1);
        }

        // We take by granted that the user's alphabet has 5 vowels
        final char[] AVAILABLE_VOWELS = {'A', 'E', 'I', 'O', 'U'};
        final int NUMBER_OF_THREADS = AVAILABLE_VOWELS.length;
        final ImprovedVowelCounter[] VOWEL_COUNTER_THREADS = new ImprovedVowelCounter[NUMBER_OF_THREADS];
        // Thread creation
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            VOWEL_COUNTER_THREADS[i] = new ImprovedVowelCounter(file, AVAILABLE_VOWELS[i]);
        }
        // Start the threads | Wait until they are finished | Extract results | Print
        for (Thread thread : VOWEL_COUNTER_THREADS) {
            thread.start();
        }
        final int[] RESULTS = new int[NUMBER_OF_THREADS];
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            VOWEL_COUNTER_THREADS[i].join();
            RESULTS[i] = VOWEL_COUNTER_THREADS[i].getIndividualVowelCounter();
        }
        printResults(RESULTS, AVAILABLE_VOWELS, file);
    }

    public static void printResults(final int[] results, char[] availableVowels, final File file) {
        System.out.println("--------------------------------");
        for (int i = 0; i < results.length; i++) {
            System.out.println(availableVowels[i] + " count: " + results[i]);
        }
        System.out.println("--------------------------------");
        System.out.println("Total amount of vowels in '" + file.getName() + "' : " + sharedTotalVowelCounter);
        System.out.println("--------------------------------");
    }
}