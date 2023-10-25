package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT = 0;
    private static final Object monitor = new Object();
    private static volatile int i = 0;

    private static CyclicBarrier cb;
    private final String name;
    private final Race race;
    private final int speed;

    private static Lock lock = new ReentrantLock();
    public Car(Race race, int speed, CyclicBarrier cb) {
        this.race = race;
        this.speed = speed;
        this.name = "Участник #" + ++CARS_COUNT;
        this.cb = cb;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);

                if (i < race.getStages().size() - 1) {
                    Main.outLock.unlock();
                }
            }
            lock.lock();

            synchronized (monitor)
            {
                if (i < 3) {
                    i++;
                    System.out.println( i + " МЕСТО " + this.name);
                }
            }

            Main.outLock.unlock();
            lock.unlock();

            cb.await();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}