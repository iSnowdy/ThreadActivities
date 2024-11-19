package Activities;

// Given a String, calculate the amount of vowels it has.
// In order to accomplish that, you must use five different threads. Each thread will
// be in charge of one vowel.
// Show how many of each vowel the word has and the total amount as well.

import java.util.Scanner;

class VowelCounter extends Thread {
    private final String WORD;
    private final char MY_VOWEL;
    private int individualVowelCounter = 0;


    public VowelCounter(final String WORD, final char MY_VOWEL) {
        this.WORD = WORD.toUpperCase(); // Vowels char array is in uppercase
        this.MY_VOWEL = MY_VOWEL;
    }

    @Override
    public void run() {
        for (int i = 0; i < WORD.length(); i++) {
            if (MY_VOWEL == WORD.charAt(i)) {
                individualVowelCounter++;
                synchronized (A12.lock) {
                    A12.sharedTotalVowelCounter++;
                }
            }
        }
    }

    public int getIndividualVowelCounter() {
        return individualVowelCounter;
    }
}

public class A12 {
    public static final Object lock = new Object();
    public static int sharedTotalVowelCounter = 0;

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        // We take by granted that the user's alphabet has 5 vowels
        System.out.print("Word: ");

        final String WORD = sc.nextLine();
        final char[] AVAILABLE_VOWELS = {'A', 'E', 'I', 'O', 'U'};
        final int NUMBER_OF_THREADS = AVAILABLE_VOWELS.length;
        final VowelCounter[] VOWEL_COUNTER_THREADS = new VowelCounter[NUMBER_OF_THREADS];
        // Thread creation
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            VOWEL_COUNTER_THREADS[i] = new VowelCounter(WORD, AVAILABLE_VOWELS[i]);
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
        printResults(RESULTS, AVAILABLE_VOWELS, WORD);
    }

    public static void printResults(final int[] results, char[] availableVowels, String word) {
        System.out.println("--------------------------------");
        for (int i = 0; i < results.length; i++) {
            System.out.println(availableVowels[i] + " count: " + results[i]);
        }
        System.out.println("--------------------------------");
        System.out.println("Total amount of vowels in '" + word + "' : " + sharedTotalVowelCounter);
        System.out.println("--------------------------------");
    }
}