package lab.threads;

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

    public void sort2Threads (int[] arr) {
        int increment = arr.length / 2,
            increment2 = arr.length / 4;

        HelperThread helperThread = new HelperThread(arr, increment2);
        helperThread.start();

        while (increment >= 1) {
            for (int startIndex = 0; startIndex < increment; startIndex++) {
                insertionSort(arr, startIndex, increment);
            }
            increment = increment >> 2;
            //System.out.println("1T: "+increment);
        }
        try {
            helperThread.join();
        } catch(InterruptedException e){
            System.out.println(e.toString());
        }
    }

    private class HelperThread extends Thread {
        private int[] arr;
        private int increment;

        public HelperThread(int[] arr, int increment) {
            this.arr = arr;
            this.increment = increment;
        }

        @Override
        public void run() {
            while (increment >= 1) {
                for (int startIndex = 0; startIndex < increment; startIndex++) {
                    insertionSort(arr, startIndex, increment);
                }
                increment = increment >> 2;
                //System.out.println("2T: "+increment);
            }
        }
    }
}
