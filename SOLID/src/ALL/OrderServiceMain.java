package ALL;

public class OrderServiceMain {
    public static void main(String[] args) {
        OrderRequest orderRequest = new OrderRequest(1, 5, 10);
        OrderService orderService = new OrderService(new ValidateOrder(), new SimpleCalculation(),new CardPayment(), new EmailNotification(), new EmailLog());
        orderService.placeOrder(orderRequest);
    }
}

class OrderRequest{
    private int id;
    private int quantity;
    private double amountOfEach;

    public OrderRequest(int id, int quantity, double amountOfEach){
        this.id = id;
        this.quantity = quantity;
        this.amountOfEach = amountOfEach;
    }

    int getId(){
        return this.id;
    }

    int getQuantity(){
        return this.quantity;
    }

    double getAmountOfEach(){
        return this.amountOfEach;
    }
}

class Order{
    private int id;
    private double amount;
    Order(int id, double amount){
        this.id = id;
        this.amount = amount;
    }

    int getOrderId(){
        return this.id;
    }
    double getOrderAmount(){
        return this.amount;
    }
}

class ValidateOrder{
    void validate(OrderRequest orderRequest){
        System.out.println("Order Request Id: "+orderRequest.getId() + " is validated");
    }
}

interface CalculateAmount{
    double calculate(int quantity, double amountOfEach);
}

class SimpleCalculation implements CalculateAmount{
    public double calculate(int quantity, double amountOfEach){
        return quantity * amountOfEach;
    }
}

class DiscountCalulation implements CalculateAmount{
    public double calculate(int quantity, double amountOfEach){
        return quantity * amountOfEach * 0.8;
    }
}
interface PayableMethod{
    void pay(Order order);
}
interface Refundable{
    void refund(Order order);
}
class UPIPayment implements PayableMethod{
    public void pay(Order order){
        System.out.println("Order ID: "+order.getOrderId()+" payment is done through UPI");
    }
    public void paymentStatus(){
        System.out.println("Payment is done by UPI");
    }
}

class CardPayment implements PayableMethod, Refundable{
    public void pay(Order order){
        System.out.println("Order ID: "+order.getOrderId()+" payment is done through Card");
    }
    public void paymentStatus(){
        System.out.println("Payment is done by CARD");
    }
    public void refund(Order order){
        System.out.println("Order ID: "+order.getOrderId()+" payment is refunded through to you registered Card");
    }
}
interface Notifier{
    void notifying(Order order);
}

class EmailNotification implements Notifier{
    public void notifying(Order order){
        System.out.println("Order ID: "+order.getOrderId()+" notification is sent thorugh email");
    }
}

class SMSNotification implements Notifier{
    public void notifying(Order order){
        System.out.println("Order ID: "+order.getOrderId()+" notification is sent thorugh sms");
    }
}
interface Logger{
    void createLog();
}
class EmailLog implements Logger{
    public void createLog(){
        System.out.println("Email log is created");
    }
}
class ConsoleLog implements Logger{
    public void createLog(){
        System.out.println("Console log is created");
    }
}
class OrderService{
    private ValidateOrder validateOrder;
    private CalculateAmount calculateAmount;
    private PayableMethod payableMethod;
    private Notifier notifier;
    private Logger logger;

    public OrderService(
            ValidateOrder validateOrder,
            CalculateAmount calculateAmount,
            PayableMethod payableMethod,
            Notifier notifier,
            Logger logger
    ){
        this.validateOrder = validateOrder;
        this.calculateAmount = calculateAmount;
        this.payableMethod = payableMethod;
        this.notifier = notifier;
        this.logger = logger;
    }

    void placeOrder(OrderRequest orderRequest){
        validateOrder.validate(orderRequest);
        double amount = calculateAmount.calculate(orderRequest.getQuantity(),orderRequest.getAmountOfEach());
        Order order = new Order(1, amount);
        payableMethod.pay(order);
        notifier.notifying(order);
        logger.createLog();
    }
}