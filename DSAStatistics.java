import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Samuel Sampaio
 */

public class DSAStatistics {
    // Variable declarations
    static long start, end, time;
    static String[] words = new String[10000];

    public static void main(String[] args) {
        // Execution of the first set of tests (5 sorting methods)
        System.out.println("First set of tests:");
        testSortMethods();

        // Execution of the second set of tests (linear search with unsorted array)
        System.out.println("\nSecond set of tests:");
        testSearchMethods(false);

        // Execution of the third set of tests (linear and binary search with sorted
        // array)
        System.out.println("\nThird set of tests:");
        testSearchMethods(true);
    }

    /*
     * Sorting methods:
     */

    /*
     * Selection Sort: Sorts the list by selecting the smallest element and placing
     * it in the first position,
     * then selecting the second smallest element and placing it in the second
     * position, and so on.
     */
    public static void selectionSort(String[] array) {
        String[] arr = array.clone();
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            String temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    /*
     * Insertion Sort: Sorts the list by building a sorted sublist at the beginning
     * of the list and then
     * inserting each new element into the correct position of that sorted sublist.
     */
    public static void insertionSort(String[] array) {
        String[] arr = array.clone();
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            String key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    /*
     * Merge Sort: Divides the list in half, sorts each half, and then
     * merges the sorted halves into a single sorted list.
     */
    public static void mergeSort(String[] array, int left, int right) {
        String[] arr = array.clone();
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    // Helper method for mergeSort
    private static void merge(String[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        String[] L = new String[n1];
        String[] R = new String[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }

    }

    /*
     * Bubble Sort: Sorts the list by comparing adjacent elements and
     * swapping them if they are in the wrong order.
     */
    public static void bubbleSort(String[] array) {
        boolean sorted = false;
        int n = array.length;

        while (!sorted) {
            sorted = true;
            for (int i = 1; i < n; i++) {
                if (array[i - 1].compareTo(array[i]) > 0) {
                    String temp = array[i];
                    array[i] = array[i - 1];
                    array[i - 1] = temp;
                    sorted = false;
                }
            }
            n--;
        }
    }

    /*
     * Quick Sort: Sorts the list by dividing it into two parts, based on a pivot,
     * and recursively sorting each part until the entire list is sorted.
     */
    public static void quickSort(String[] arr, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(arr, left, right);
            quickSort(arr, left, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, right);
        }
    }

    // Helper method that partitions the list with respect to the pivot
    private static int partition(String[] arr, int low, int high) {
        String pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].compareTo(pivot) < 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    // Helper method to swap two elements in the list
    private static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /*
     * Search methods:
     */

    // Linear search method
    public static boolean linearSearch(String word, String[] arr) {
        for (String item : arr) {
            if (item.equals(word)) {
                return true;
            }
        }
        return false;
    }

    // Binary search method
    public static boolean binarySearch(String word, String[] arr) {
        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            int comparison = word.compareTo(arr[mid]);
            if (comparison == 0) {
                return true;
            } else if (comparison < 0) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return false;
    }

    /*
     * Auxiliary methods
     */

    // Starts timing
    private static void startTiming() {
        start = System.currentTimeMillis();
    }

    // Finishes timing and displays a message containing the elapsed time
    private static void finishTiming(String method) {
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("Task " + method + " executed in " + time + " milliseconds");

    }

    // Method responsible for opening the text file and copying its contents to an
    // array
    private static void openFile() {
        File file = new File(
                "words.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
        int index = 0;
        while (scanner.hasNext()) {
            String word = scanner.next();
            words[index] = word;
            index++;
        }

        scanner.close();
    }

    // Helper method to check if an array is sorted
    private static boolean isSorted(String[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].compareTo(arr[i + 1]) > 0) {
                return false;
            }
        }
        return true;
    }

    /*
     * Methods responsible for testing the sorting and searching algorithms
     */

    // Method that tests the sorting algorithms (Selection Sort, Insertion Sort,
    // Merge Sort, Quick Sort, Bubble Sort)
    private static void testSortMethods() {
        String[] sortingMethods = { "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Bubble Sort" };

        for (String method : sortingMethods) {
            startTiming();
            openFile();

            switch (method) {
                case "Selection Sort" ->
                    selectionSort(words);
                case "Insertion Sort" ->
                    insertionSort(words);
                case "Merge Sort" ->
                    mergeSort(words, 0, words.length - 1);
                case "Quick Sort" ->
                    quickSort(words, 0, words.length - 1);
                case "Bubble Sort" ->
                    bubbleSort(words);
            }

            finishTiming(method);
        }

        // Expected order: Quick Sort > Insertion Sort > Merge Sort > Selection Sort >
        // Bubble Sort
    }

    // Method that tests the searching algorithms (Linear and Binary) with both
    // sorted and unsorted arrays
    private static void testSearchMethods(boolean sortArray) {
        String[] searchingMethods = { "Linear Search", "Binary Search" };
        String[] existingWords = { "arruinai", "capitel", "curso", "eslavo", "gravataria", "longo", "obtenhais",
                "progenitor", "seria", "vaca" };
        String[] absentWords = { "algoritmo", "condicional", "direita", "esquerda", "grande", "livros", "prata",
                "silencio", "verde", "xarões" };

        for (String method : searchingMethods) {

            long totalTime = 0;

            switch (method) {
                // Execute and test linear search with existing words
                case "Linear Search" -> {
                    System.out.println("Positive Test");
                    for (String word : existingWords) {
                        openFile();
                        startTiming();

                        if (sortArray) {
                            quickSort(words, 0, words.length - 1);
                        }

                        /*
                         * Check if the searched word exists in the array using linear search. If it
                         * does,
                         * display a success message; otherwise, display an error message along with the
                         * search time.
                         */
                        if (linearSearch(word, words)) {
                            end = System.currentTimeMillis();
                            time = end - start;
                            totalTime += time;
                            System.out.println(method + ": Word " + word + " found, " + time + " milliseconds");
                        } else {
                            end = System.currentTimeMillis();
                            time = end - start;
                            totalTime += time;
                            System.out.println(
                                    "ERROR " + method + ": Word " + word + " not found, " + time + " milliseconds");
                        }
                    }
                    System.out.println("Total execution time: " + totalTime + " milliseconds" + "\n");
                    totalTime = 0;

                    // Execute and test linear search with absent words
                    System.out.println("Negative Test");
                    for (String word : absentWords) {
                        openFile();
                        startTiming();

                        if (sortArray) {
                            quickSort(words, 0, words.length - 1);
                        }

                        /*
                         * Check if the searched word exists in the array using linear search. If it
                         * does,
                         * display an error message; otherwise, display a success message along with the
                         * search time.
                         */
                        if (!linearSearch(word, words)) {
                            end = System.currentTimeMillis();
                            time = end - start;
                            totalTime += time;
                            System.out.println(
                                    "ERROR " + method + ": Word " + word + " not found, " + time + " milliseconds");
                        } else {
                            end = System.currentTimeMillis();
                            time = end - start;
                            totalTime += time;
                            System.out.println(method + ": Word " + word + " found, " + time + " milliseconds");
                        }
                    }
                    System.out.println("Total execution time: " + totalTime + " milliseconds" + "\n");
                }

                // Execute and test binary search with existing words
                case "Binary Search" -> {
                    // Skip binary search if the array is not sorted
                    if (!sortArray) {
                        return;
                    }
                    System.out.println("Positive Test");
                    for (String word : existingWords) {
                        openFile();
                        startTiming();

                        quickSort(words, 0, words.length - 1);

                        /*
                         * Check if the searched word exists in the array using binary search. If it
                         * does,
                         * display a success message; otherwise, display an error message along with the
                         * search time.
                         */
                        if (binarySearch(word, words)) {
                            end = System.currentTimeMillis();
                            time = end - start;
                            totalTime += time;
                            System.out.println(method + ": Word " + word + " found, " + time + " milliseconds");
                        } else {
                            end = System.currentTimeMillis();
                            time = end - start;
                            totalTime += time;
                            System.out.println(
                                    "ERROR " + method + ": Word " + word + " not found, " + time + " milliseconds");
                        }

                    }
                    System.out.println("Total execution time: " + totalTime + " milliseconds" + "\n");
                    totalTime = 0;

                    // Execute and test binary search with absent words

                    System.out.println("Negative Test");
                    for (String word : absentWords) {
                        openFile();
                        startTiming();

                        quickSort(words, 0, words.length - 1);

                        /*
                         * Check if the searched word exists in the array using binary search. If it
                         * does,
                         * display a success message; otherwise, display an error message along with the
                         * search time.
                         */
                        if (binarySearch(word, words)) {
                            end = System.currentTimeMillis();
                            time = end - start;
                            totalTime += time;
                            System.out.println(method + ": Word " + word + " found, " + time + " milliseconds");
                        } else {
                            end = System.currentTimeMillis();
                            time = end - start;
                            totalTime += time;
                            System.out.println(
                                    "ERROR " + method + ": Word " + word + " not found, " + time + " milliseconds");
                        }
                    }
                    System.out.println("Total execution time: " + totalTime + " milliseconds" + "\n");
                }

            }

        }
    }

}