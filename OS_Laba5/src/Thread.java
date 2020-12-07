import java.util.Random;

/*
* Основная единица*/
public class Thread extends Object {

    private static Random rnd = new Random();
    private final int tid;
    private int executionTime;
    public ThreadMode mode;

    public Thread(int tid, ThreadMode mode) {
        this.mode = mode;
        this.tid = tid;
        this.executionTime = 5 + rnd.nextInt(25);
    }

    public int getExecutionTime() {
        return executionTime;
    }

    public int reduceExecutionTime(int amount) {
        int res;
        if (amount >= executionTime) {
            res = amount - executionTime;
            executionTime = 0;
            System.out.println("Поток выполнен");
        } else {
            res = 0;
            executionTime -= amount;
            System.out.println("Поток не выполнен");
        }
        return res;
    }

    public void setExecutionTime(int ex) {
        this.executionTime = ex;
    }

    public void run() {
        StringBuilder sb = new StringBuilder();
        sb.append("Поток TID = ");
        sb.append(tid);
        sb.append(" Время выполнения = ");
        sb.append(executionTime);
        System.out.println(sb.toString());
    }

    @Override
    public Thread clone() {
        Thread tmp = new Thread(tid, mode);
        tmp.setExecutionTime(this.executionTime);
        return tmp;
    }

    public ThreadMode getMode() {
        return mode;
    }
}
