import java.util.ArrayList;
import java.util.Random;

public class Core {

    private final int quantSize = 50;
    private static Random rnd = new Random();
    private ArrayList<Process> processes;

    private static int currentTID = -1;

    public static int getNextTID()
    {
        currentTID++;
        return currentTID;
    }

    public Core()
    {
        createProcesses();
        planProcesses();
    }

    public void createProcesses()
    {
        processes = new ArrayList<Process>();
        int count = 5 + rnd.nextInt(5);
        for (int i = 0; i < count; i++)
        {
            Process proc = new Process(i,2+rnd.nextInt(5));
            processes.add(proc);
        }
    }

    public void planProcesses()
    {
        int givenTime = 0;
        int requiredTime = 0;

        while(processes.size() > 0)
        {
            Process currentProc;
            Thread currentThread;
            int currentQuant;
            for (int i = 0; i < processes.size(); i++)
            {
                currentQuant = quantSize;
                givenTime += currentQuant;

                currentProc = processes.get(i);

                System.out.println("Процесс PID = " + currentProc.getPID() + " выполняет: ");

                while(currentQuant > 0 && currentProc.getThreadCount() > 0)
                {
                    System.out.println("Размер кванта = " + currentQuant);

                    currentThread = currentProc.getThread();
                    requiredTime += currentThread.getExecutionTime();

                    if(currentThread != null)
                        currentThread.run();

                    currentQuant = currentThread.reduceExecutionTime(currentQuant);
                    requiredTime -= currentThread.getExecutionTime();

                    if(currentThread.getExecutionTime() == 0)
                        currentProc.removeThread(currentThread);
                }

                System.out.println("Процесс завершил свое выполнение!");
                if(currentProc.getThreadCount() == 0)
                    processes.remove(currentProc);
                System.out.println();
            }
        }

        System.out.println("Требовалось времени : "+ requiredTime);
        System.out.println("Затрачено времени : "+ givenTime);
    }
}


