import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/*В файловой системе находится главный каталог.
* Внутри каталогов рекурсией выполняется большинство действий, файловая система же,
* по сути, осуществляет взаимодействие между главным каталогом и самой программой,
* выполняя роль некоторого буфера*/
public class FileSystem {

    Catalog catalog=new Catalog("Main");

    public FileSystem(){

    }

    public File getCatalog() {
        return catalog;
    }

    public void deleteFile(File nodeInfo) {
        catalog.deleteFile(nodeInfo);
    }
}
