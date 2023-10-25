package org.example;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel extends Stage
{
    private static final int length = 80;
    private final Semaphore semaphore;
    private final Lock lock;

    public Tunnel(int limit)
    {
        super(length, "Тоннель " + length + " метров");

        semaphore = new Semaphore(limit);
        lock = new ReentrantLock();
    }

    @Override
    public void go(Car car)
    {
        try
        {
            Main.outLock.lock();
            System.out.println(car.getName() + " готовится к этапу(ждет): " + description);
            Main.outLock.unlock();

            lock.lock();

            semaphore.acquire();

            Main.outLock.lock();
            System.out.println(car.getName() + " начал этап: " + description);
            Main.outLock.unlock();

            lock.unlock();

            Thread.sleep(length / car.getSpeed() * 100);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        finally
        {
            Main.outLock.lock();
            System.out.println(car.getName() + " закончил этап: " + description);
            semaphore.release();
        }
    }
}
