package org.example;
public class Road extends Stage
{
    public Road(int length)
    {
        super(length, "Дорога " + length + " метров");
    }

    @Override
    public void go(Car car)
    {
        try
        {
            Main.outLock.lock();
            System.out.println(car.getName() + " начал этап: " + description);
            Main.outLock.unlock();

            Thread.sleep(length / car.getSpeed() * 100);

            Main.outLock.lock();
            System.out.println(car.getName() + " закончил этап: " + description);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
