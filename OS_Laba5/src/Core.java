import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/*
* для выполнения лабораторной 5 я изменял лабораторую 2, из чего вытекает избыточность функционала
*
* В рамках процессов создаются потоки, потоки в 20% случаев нуждаются во вводе, то есть в прерывании.
* Я отразил 2 вида прерываний: Системное и программное
*
* Во время системного прерывания, планирование процессов останавливается, систеа ждёт ввода данных.
* После ввода данных планирование продолжается (2 секунды длится прерывание).
*
* Во время программного прерывания, планирование продолжается, но конкретный процесс (в нашем случае поток) блокируется
* и ждёт ввода. После ввода (на который я даю всего лишь 200 мсек для наглядности), процесс продолжает выполнение.
*
* Порялок работы:
*       Сначала код просто выполняется без прерываний
*       Затем выполняется метод с системыми прерываниями
*       Затем выполняется метод с программными прерываниями
* */

public class Core {

    private final int quantSize = 50;
    private static Random rnd = new Random();
    private ArrayList<Process> processes;
    private ArrayList<Thread> threads;
    private ArrayList<Thread> threadsHolder;
    static Timer timer;

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

        threadsHolder = new ArrayList<>();
        for (int i = 0; i < threads.size(); i++) {
            threadsHolder.add(threads.get(i).clone());
        }
        System.out.println("Список потоков составлен");
    }


    /*Вспомогательные листы, нужные лишь для того, чтобы во время програнных прерываний можно было сохранять ссылки*/
    ArrayList<Thread> breakedThreads = new ArrayList<>();
    ArrayList<Thread> forBreakedThreads=new ArrayList<>();
    Thread currentThread = null;
    int currentQuant;
    /*Програмные прерывания (программа построена на таймере, чтобы сэмитировать системные часы)*/
    TimerTask task3 = new TimerTask() {
        @Override
        public void run() {
            if (threads.isEmpty()&&forBreakedThreads.isEmpty()&&breakedThreads.isEmpty()) {
                timer.cancel();
                System.out.println("\n___________________________________________\n");
            } else {
                currentQuant = quantSize;
                performProgramBreak();
                System.out.println();
            }
        }
    };
    /*Системное прерывание*/
    TimerTask task2 = new TimerTask() {
        @Override
        public void run() {
            if (threads.isEmpty()) {
                timer.cancel();
                timer = new Timer();
                System.out.println("Timer canceled2");
                System.out.println("\n___________________________________________\n");
                System.out.println("Program breaks next:");
                for (int i = 0; i < threadsHolder.size(); i++) {
                    threads.add(threadsHolder.get(i).clone());
                }
                timer.schedule(task3, 0, 100);
            } else {
                currentQuant = quantSize;
                performSystemBreak();
                System.out.println();
            }
        }
    };
    /*Работа без прерываний*/
    TimerTask task1 = new TimerTask() {
        @Override
        public void run() {
            if (threads.isEmpty()) {
                timer.cancel();
                timer = new Timer();
                System.out.println("\n___________________________________________\n");
                System.out.println("System breaks next:");
                for (int i = 0; i < threadsHolder.size(); i++) {
                    threads.add(threadsHolder.get(i).clone());
                }
                timer.schedule(task2, 0, 100);
            } else {
                currentQuant = quantSize;
                performStandart();
                System.out.println();
            }
        }
    };

    public void perform() {
        timer = new Timer();
        timer.schedule(task1, 0, 100);
    }

    /*
    * Программное*/
    private void performProgramBreak() {
        while (currentQuant > 0 && threads.size() > 0 || !breakedThreads.isEmpty()) {

            if (!breakedThreads.isEmpty()) {
                currentThread = breakedThreads.remove(0);
                currentThread.run();
                System.out.println("IO is completely proceed, so we can continue operating");
            } else {
                currentThread = threads.remove(0);
                if (currentThread.getMode() == ThreadMode.Blocked) {
                    currentThread.run();
                    System.out.println("Need IO, so we will continue processing it a little bit latter");
                    Thread th = currentThread;
                    forBreakedThreads.add(th);
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            breakedThreads.add(forBreakedThreads.remove(0));
                            this.cancel();
                        }
                    };
                    Timer t = new Timer();
                    t.schedule(task, 200, 1);
                    currentThread = null;
                } else {
                    currentThread.run();
                    System.out.println("Do not Need IO");
                }
            }
            System.out.println("Размер кванта = " + currentQuant);

            if (currentThread != null) {
                currentQuant = currentThread.reduceExecutionTime(currentQuant);

                if (currentThread.getExecutionTime() == 0)
                    threads.remove(currentThread);
                else {
                    threads.add(currentThread);
                }
            }
        }
    }

    /*
    * Системное*/
    private void performSystemBreak() {
        while (currentQuant > 0 && threads.size() > 0) {

            currentThread = threads.remove(0);
            System.out.println("Размер кванта = " + currentQuant);

            if (currentThread != null)
                currentThread.run();

            if (currentThread.getMode() == ThreadMode.Blocked) {
                System.out.println("Need IO, wait 2 sec");
                try {
                    java.lang.Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("_________");
            } else {
                System.out.println("Do not Need IO");
            }

            currentQuant = currentThread.reduceExecutionTime(currentQuant);

            if (currentThread.getExecutionTime() == 0)
                threads.remove(currentThread);
            else {
                threads.add(currentThread);
            }
        }
    }

    /*
    * Без прерываний*/
    public void performStandart() {
        while (currentQuant > 0 && threads.size() > 0) {

            currentThread = threads.remove(0);
            System.out.println("Размер кванта = " + currentQuant);

            if (currentThread != null)
                currentThread.run();

            currentQuant = currentThread.reduceExecutionTime(currentQuant);

            if (currentThread.getExecutionTime() == 0)
                threads.remove(currentThread);
            else {
                threads.add(currentThread);
            }
        }
    }

}



