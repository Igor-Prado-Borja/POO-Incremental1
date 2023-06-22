import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main{
    public static void main(String[] args){
        if (args.length > 0){
            try{
                File f = new File(args[0]);
                FileInputStream fsource = new FileInputStream(f);
                App session = new App(fsource);
                session.run();
            } catch (FileNotFoundException notFoundEx){
                System.out.println("Arquivo não encontrado.");
            }
        } else {
            App session = new App(System.in);
            session.run();
        }   
    }
}
