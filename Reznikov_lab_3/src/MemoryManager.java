public class MemoryManager {

    /*Часть операционной системы, управляющая оперативной памятью, называется
менеджером или диспетчером памяти.*/

    RAM ram;
    ROM rom = new ROM();

    public MemoryManager(int size){
        ram = new RAM(size);
    }

    /*Клонированная страница передаётся в постоянную память*/
    public void addToROM(Page page) {
        rom.addPage(page);
    }

    /*Проверка наличия страницы в оперативе, если она там не обнаружена, приступаем в подкачке
    *
    * Если страница есть, то у этой страницы увеличиваетяс индикатор изменений, который
    * в дальнейшем поможет ей не быть выкинутой из памяти*/
    public void need(int pageID) {
        if(!ram.has(pageID)){
            System.out.println("This page is not in RAM");
            insertPageIntoRAM(pageID);
        }
    }

    /*Если есть место, то просто клонирует страницу в оперативку (именно клонируем, потому что там она может измениться)
    * Если места нет, выполняем процедуру 'swap'*/
    private void insertPageIntoRAM(int pageID) {
        if(!ram.isFull()){
            System.out.println("RAM has enough place for one more page");
            ram.addPage(rom.getPage(pageID).clone());
        }
        else{
            System.out.println("'Swap' process");
            swap(pageID);
        }
    }

    /*Находится страница с наименьшим количеством изменений (алгоритм NRU).
    * Ссылка на уже измененную (по идее) страницу передаётся на диск, из оперативы она удаляется
    * Затем клонируется страница диска и передаётся в оперативу
    *
    * ///Такое мелкое дробение сделал для нагладности
    *
    * Затем мы уменьшаем количество изменений в всех объектах (чтобы использованная 3 года назад страница
    * не считалась востребованной)*/
    private void swap(int pageID){
        Page tmp = ram.findFoolest();
        rom.updatePage(tmp);
        System.out.println("Deleting Page ID = " + tmp.getID() + " from RAM");
        ram.addPage(rom.getPage(pageID).clone());
        System.out.println("Inserting of Page ID = " + pageID+" is complited");
        ram.degradeChanges();
        System.out.println("Changes count are changed");
    }
}
