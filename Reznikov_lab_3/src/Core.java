import java.util.ArrayList;
import java.util.Random;

public class Core {

    PageList PL = new PageList();
    MemoryManager MM;
    private int currentPID = 0;
    private int currentID = 0;
    ArrayList<Process> processList = new ArrayList<Process>();

    public Core(int rom) {
        MM = new MemoryManager(rom);
    }

    /*Здесь создаются процессы
    * Сначала создаётся процесс, внутри него определяется количество страниц
    * Процесс сохраннятеся в ЭррэйЛисте с процессами
    * (потом нигде не используются, но наличие списка процессов я посчитал важным обстоятельством)
    * Затем эти страницы создаются, ссылка на созданную страницу отдаётся процессу,
    * В оперативку отдаётся клон страницы, в PageList тоже отдаётся клон
    * (список процессов должен быть в ядре, сами процессы должны храниться на диске
    * кроме этого, каждый процесс должен иметь список собственных страниц.
    * Конечно, всё это не необходимо для демострации работы, но я сделал. Могу значительно упростить)*/

    public void createProcesses(int count) {
        Random rm = new Random();
        for (int i = 0; i < count; i++) {
            Process process = new Process((int) (1 + Math.random() * 4), currentPID);
            processList.add(process);
            System.out.println("Process PID = " + currentPID + " is created (its size is " + process.checkSize() + "). It includes:");
            currentPID++;
            for (int k = 0; k < process.checkSize(); k++) {
                Page page = new Page(process.checkSize(), currentID);
                System.out.println("Page ID = " + currentID);
                currentID++;
                process.addPage(page);
                MM.addToROM(page.clone());
                PL.addPage(page.clone());
            }
        }
    }

    /*Поскольку мы не знаем, какая сраница понадобится, я сделал выбор траницы через рандом
    * Затем процессор декларирует диспетчеру, что ему нужна страница, диспетчер работает с памятью*/

    public void run(int count) {
        for (int i=0;i<count;i++){
            int pageID= PL.getPageID();
            System.out.println("Require page ID = " + pageID);
            MM.need(pageID);
            System.out.println("Request was completed\n");
        }
    }
}
