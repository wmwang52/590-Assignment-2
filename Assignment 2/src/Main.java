import java.util.concurrent.Semaphore;
import java.util.Random;

public class Main {
    private static final int numOfPhilosophers = 5;
    private static final int numOfForks = numOfPhilosophers;
    private static Semaphore[] forks = new Semaphore[numOfForks];

    public static void main(String[] args) {
        for (int i = 0; i < numOfPhilosophers; i++) {
            forks[i] = new Semaphore(1);
            new Thread(new Philosopher(i)).start();
            System.out.println("Philosopher " + i + " is thinking...");
        }
    }
    static class Philosopher implements Runnable {
        private int philosopherIndex;
        private Random random = new Random();
        private Semaphore global = new Semaphore(1);

        public Philosopher(int philosopherIndex) {
            this.philosopherIndex = philosopherIndex;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    eat();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void eat() throws InterruptedException {

            Thread.sleep(randomTime());
            this.global.acquire();

            int leftForkIndex = philosopherIndex;
            int rightForkIndex = (philosopherIndex + 1) % numOfForks;

            forks[leftForkIndex].acquire();
            forks[rightForkIndex].acquire();

            this.global.release();

            System.out.println("Philosopher " + philosopherIndex + " is eating...");
            Thread.sleep(randomTime());
            System.out.println("Philosopher " + philosopherIndex + " is thinking...");

            forks[leftForkIndex].release();
            forks[rightForkIndex].release();
        }

        private int randomTime() {
            return random.nextInt(4000) + 1000;
        }
    }
}