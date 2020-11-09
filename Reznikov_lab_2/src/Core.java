import java.util.ArrayList;
import java.util.Random;

public class Core {

    private final int quantSize = 50;
    private static Random rnd = new Random();
    private ArrayList<Process> processes;
    private ArrayList<Thread> threads;

    private static int currentTID = -1;

    public static int getNextTID() {
        currentTID++;
        return currentTID;
    }

    public Core() {
        createProcesses();
        plan();
        perform();
    }

    public void createProcesses() {
        processes = new ArrayList<Process>();
        int count = 5 + rnd.nextInt(5);
        for (int i = 0; i < count; i++) {
            Process proc = new Process(i, 2 + rnd.nextInt(5));
            processes.add(proc);
        }
    }

    public void plan() {
        threads = new ArrayList<>();

        Process currentProc;
        Thread currentThread;
        for (int i = 0; i < processes.size(); i++) {
            currentProc = processes.get(i);

            int index = 0;
            while (currentProc.getThread(index) != null) {

                currentThread = currentProc.getThread(index);
                threads.add(currentThread);
                index++;
            }
        }

        System.out.println("Список потоков составлен");
    }

    public void perform() {
        int givenTime = 0;
        int requiredTime = 0;

        while (threads.size() > 0) {
            Thread currentThread = null;
            int currentQuant;
            currentQuant = quantSize;
            givenTime += currentQuant;
            while (currentQuant > 0 && threads.size() > 0) {

                currentThread = threads.remove(0);
                System.out.println("Размер кванта = " + currentQuant);
                requiredTime += currentThread.getExecutionTime();

                if (currentThread != null)
                    currentThread.run();

                currentQuant = currentThread.reduceExecutionTime(currentQuant);
                requiredTime -= currentThread.getExecutionTime();

                if (currentThread.getExecutionTime() == 0)
                    threads.remove(currentThread);
                else {
                    threads.add(currentThread);
                }
            }
            System.out.println();
        }
        System.out.println("Процессы выполнены");
    }


}



