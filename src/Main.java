//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Object lock = new Object();
    private static boolean firstThreadDone = false;

    public static void main(String[] args) {

        // Thread 1: Counts up to 20
        Thread countUpThread = new Thread(() -> {
            for (int i = 0; i <= 20; i++) {
                System.out.println("Count Up: " + i);
            }

            synchronized (lock) {
                firstThreadDone = true;
                lock.notify(); // Notify the waiting thread
            }
        });

        // Thread 2: Counts down to 0
        Thread countDownThread = new Thread(() -> {
            synchronized (lock) {
                while (!firstThreadDone) {
                    try {
                        lock.wait(); // Wait until count up finishes
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Thread interrupted");
                        return;
                    }
                }
            }

            for (int i = 20; i >= 0; i--) {
                System.out.println("Count Down: " + i);
            }
        });

        countUpThread.start();
        countDownThread.start();
    }
}