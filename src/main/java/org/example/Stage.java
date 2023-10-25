package org.example;
public abstract class Stage
{
    protected final int length;
    protected final String description;

    public Stage(int length, String description)
    {
        this.length = length;
        this.description = description;
    }

    public abstract void go(Car car);
}
