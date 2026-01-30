package LSP;

public class DocumentMain{
    public static void main(String[] args){
        PrintableDocument pdfDocument = new PDFDocument();
        DocumentSevice documentSevice = new DocumentSevice();
        documentSevice.print(pdfDocument);
    }
}
interface Document{
    public void readFile();
}
interface PrintableDocument extends Document{
    public void printFile();
}
class DocumentSevice{
    void print(PrintableDocument printableDocument){
        printableDocument.printFile();
    }
}
class PDFDocument implements PrintableDocument{
    public void readFile(){
        System.out.println("Reading PDF");
    }
    public void printFile(){
        System.out.println("Printing PDF");
    }
}
class WordDocument implements PrintableDocument{
    public void readFile(){
        System.out.println("Reading Word");
    }
    public void printFile(){
        System.out.println("Printing DOC");
    }
}
class ReadOnlyDocument implements Document{
    public void readFile(){
        System.out.println("Reading Read Only");
    }
}


