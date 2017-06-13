package de.dhbw.sort.algorithms;

public abstract class SortingAlgorithm extends Thread
{
  private boolean done = false;
  protected AbstractAlgorithmHelper helper;

  public synchronized void run()
  {
    while (true)
    {
      if (!done)
      {
        initialize();
        sort();
        done = true;
      }
      else
      {
        try{
        wait();
        }catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }

  public abstract void initialize();
  public abstract void sort();
  public AbstractAlgorithmHelper helper()
  {
    return helper;
  }
  public boolean done() { 
    return done;
  }
  public synchronized void reset()
  {
    done = false;
    notify();
  }
}