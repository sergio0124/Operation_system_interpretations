import java.util.ArrayList;


/*Промежуточный класс, рудимент*/
public class Process {

    private final int pid;
    private ArrayList<Thread> threads = new ArrayList<Thread>();

    public Process(int pid, int threadCount) {
        this.pid = pid;
        for (int i = 0; i < threadCount; i++) {
            int tmp = (int) (Math.random() * 10);
            if (tmp >= 8) {
                threads.add(new Thread(Core.getNextTID(), ThreadMode.Blocked));
            } else {
                threads.add(new Thread(Core.getNextTID(), ThreadMode.Accessible));
            }
        }
    }

    public int getThreadCount() {
        return threads.size();
    }

    ;

    public int getPID() {
        return pid;
    }

    public void removeThread(Thread thread) {
        if (thread != null)
            threads.remove(thread);
    }

    public Thread getThread() {
        if (getThreadCount() > 0) {
            Thread res = threads.get(getThreadCount() - 1);
            return res;
        }
        return null;
    }

    public Thread getThread(int index) {
        if (getThreadCount() > 0 && index < threads.size()) {
            Thread res = threads.get(index);
            return res;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Процесс PID = ");
        sb.append(pid);
        sb.append(" кол-во потоков = ");
        sb.append(getThreadCount());
        return sb.toString();
    }
}
