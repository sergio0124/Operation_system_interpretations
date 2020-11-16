public class Main {

    /*Из мэйна я создаю ядро, передаю туда размер опреативки
    (размер страницы указан внутри оператики как константа).
    После этого, я запускаю работу процессора.

    Т.к. работа с компьютером может продолжаться неограниченное время, втечение которого
    могут требоваться разные виртуальные страницы, я передаю в функцию работы количество действий.*/

    public static void main(String[] args) {
        final int ROM_SIZE = 32;
        final int PROCESS_NUMBER = 10;
        final int RUNNING_NUMBER = 50;

        Core core = new Core(ROM_SIZE);
        System.out.println("Program is running now!");
        core.createProcesses(PROCESS_NUMBER);
        System.out.println();

        System.out.println();
        core.run(RUNNING_NUMBER);
    }
}
