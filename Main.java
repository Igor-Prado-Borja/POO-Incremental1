public class Main{
    public static void main(String[] args){
        App session = new App();
        AppReadFromFile file_session = new AppReadFromFile();
        if (args.length > 0){
            file_session.run(args[0]);
        } else {
            session.run();
        }   
    }
}