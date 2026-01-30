package ISP;

public class PrinterSystemMain {
    static void main(String[] args) {
        Printable printer = new SimplePrinter();
        printer.print();

        Scannable scanner = new AdvancedPrinter();
        scanner.scan();

        Faxable fax = new MultiFunctionPrinter();
        fax.fax();


    }
}

interface Printable{
    void print();
}

interface Scannable{
    void scan();
}

interface Faxable{
    void fax();
}

class SimplePrinter implements Printable{
    public void print(){
        System.out.println("Printing");
    }
}

class AdvancedPrinter implements Printable, Scannable{
    public void print(){
        System.out.println("Printing");
    }

    public void scan(){
        System.out.println("Scanning");
    }
}

class MultiFunctionPrinter implements Printable,Scannable,Faxable{
    public void print(){
        System.out.println("Printing");
    }

    public void scan(){
        System.out.println("Scanning");
    }

    @Override
    public void fax() {
        System.out.println("Faxed");
    }
}
