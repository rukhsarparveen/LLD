package OCP;

public class PaymentServiceMain {
    public static void main(String[] args){
        PaymentMethod paymentMethod = new CryptoPayment();
        PaymentService paymentService = new PaymentService(paymentMethod);
        paymentService.payment();
    }
}
interface PaymentMethod{
     void pay();
}
class UPIPayment implements PaymentMethod{
    public void pay(){
        System.out.println("UPI Payment");
    }
}
class WalletPayment implements PaymentMethod{
    public void pay(){
        System.out.println("Wallet Payment");
    }
}
class CryptoPayment implements PaymentMethod{
    public void pay(){
        System.out.println("Crypto Payment");
    }
}
class PaymentService{
    private PaymentMethod paymentMethod;
    public PaymentService(PaymentMethod paymentMethod){
        this.paymentMethod = paymentMethod;
    }
    void payment(){
        paymentMethod.pay();
    }
}

