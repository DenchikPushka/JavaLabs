package lab.threads;

import java.util.ArrayList;

public class ShellSort {
    public void sort (int[] arr) {
        int increment = arr.length / 2;
        while (increment >= 1) {
            for (int startIndex = 0; startIndex < increment; startIndex++) {
                insertionSort(arr, startIndex, increment);
            }
            increment = increment / 2;
        }
    }

    private void insertionSort (int[] arr, int startIndex, int increment) {
        for (int i = startIndex; i < arr.length - 1; i = i + increment) {
            for (int j = Math.min(i + increment, arr.length - 1); j - increment >= 0; j = j - increment) {
                if (arr[j - increment] > arr[j]) {
                    int tmp = arr[j];
                    arr[j] = arr[j - increment];
                    arr[j - increment] = tmp;
                } else {
                    break;
                }
            }
        }
    }

    public void sortThreads (int[] arr, int countThreads) {
        if (countThreads == 0) {
            return;
        }
        int increment = arr.length >> 1;
        ArrayList<HelperThread> hThreadsList = new ArrayList<>();
        HelperThread hThread;

        for (int i = 1; i < countThreads; i++) {
            hThread = new HelperThread(arr, increment >> i, countThreads);
            hThreadsList.add(hThread);
            hThread.start();
        }

        System.out.println("count hThreads: "+hThreadsList.size());

        while (increment >= 1) {
            for (int startIndex = 0; startIndex < increment; startIndex++) {
                insertionSort(arr, startIndex, increment);
            }
            increment = increment >> countThreads;
        }

        for (HelperThread item : hThreadsList) {
            try {
                item.join();
            } catch(InterruptedException e){
                System.out.println(e.toString());
            }
        }
    }

    private class HelperThread extends Thread {
        private int[] arr;
        private int increment;
        private int countThreads;

        public HelperThread(int[] arr, int increment, int countThreads) {
            this.arr = arr;
            this.increment = increment;
            this.countThreads = countThreads;
        }

        @Override
        public void run() {
            while (increment >= 1) {
                for (int startIndex = 0; startIndex < increment; startIndex++) {
                    insertionSort(arr, startIndex, increment);
                }
                increment = increment >> countThreads;
            }
        }
    }
}
