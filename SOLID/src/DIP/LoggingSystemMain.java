package DIP;

public class LoggingSystemMain {
    static void main(String[] args) {
        Logging console = new ConsoleLogging();
        ApplicationService applicationService = new ApplicationService(console);
        applicationService.log();
    }
}

interface Logging{
    void log();
}

class FileLogging implements Logging{
    @Override
    public void log() {
        System.out.println("File Logging");
    }
}

class DBLogging implements Logging{
    @Override
    public void log() {
        System.out.println("DB Logging");
    }
}

class ConsoleLogging implements Logging{
    @Override
    public void log() {
        System.out.println("Console Logging");
    }
}

class ApplicationService{
    Logging logging;
    public  ApplicationService(Logging logging){
        this.logging = logging;
    }
    void log(){
        logging.log();
    }
}
