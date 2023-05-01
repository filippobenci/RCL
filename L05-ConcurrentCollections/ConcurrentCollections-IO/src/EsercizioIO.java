import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;


public class EsercizioIO {
    public static void main(String[] args) throws IOException {

        String basedir = "path/to/dir";

        File startDirectory = new File(basedir);
        if(!startDirectory.exists()){
            System.out.println("Il file iniziale non esiste.\n");
            System.exit(-1);
        }

        if(!startDirectory.isDirectory()){
            System.out.println("Il file iniziale non è una directory.\n");
            System.exit(-1);
        }

        LinkedList<File> directories = new LinkedList<>();
        directories.add(startDirectory);

        try (DataOutputStream d = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("directories"))));
             DataOutputStream f = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("files"))));)
            {
                while(directories.size() > 0) {
                    File [] filesInCurrentDirectory = directories.poll().listFiles();
                    if(filesInCurrentDirectory == null){
                        System.out.println("Il file contenuto nella lista non è una directory\n");
                        System.exit(-1);
                    }
                    for(File currentFile : filesInCurrentDirectory){
                        String to_write = currentFile.getName() + "\n";
                        if(currentFile.isDirectory()){
                            d.write(to_write.getBytes());
                        } else{
                                f.write(to_write.getBytes());
                        }
                    }
                }
            }
    }
}
