package SRP;

//Single Responsibility Principle
public class OrderServiceMain{
    static void main(String[] args) {
        OrderService orderService = new OrderService(
                new OrderValidator(),
                new PriceCalculator(),
                new OrderRepository(),
                new NotificationService()
        );
        Order order = new Order(1,50.0);
        orderService.placeOrder(order);
    }
}
 class OrderService {
    private OrderValidator orderValidator;
    private PriceCalculator priceCalculator;
    private OrderRepository orderRepository;
    private NotificationService notificationService;
    public OrderService(
             OrderValidator orderValidator,
             PriceCalculator priceCalculator,
             OrderRepository orderRepository,
             NotificationService notificationService
    ){
        this.orderValidator = orderValidator;
        this.priceCalculator = priceCalculator;
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }
    public void placeOrder(Order order){
        orderValidator.validateOrder(order);
        priceCalculator.calculatePrice(order);
        orderRepository.orderSave(order);
        notificationService.sendNotification(order);
    }
}
class Order{
    private int orderNumber;
    private double price;
    public Order(int orderNumber, double price){
        this.orderNumber = orderNumber;
        this.price = price;
    }
    int getOrderNumber(){
        return this.orderNumber;
    }
    int getPrice(){
        return this.getPrice();
    }
}
class OrderValidator{
     void validateOrder(Order order){
        System.out.println(order.getOrderNumber() + " " +
                "SRP.Order is validated");
    }
}
class PriceCalculator{
     void calculatePrice(Order order){
        System.out.println(order.getOrderNumber() + " Price is calculated");
    }
}
class OrderRepository{
     void orderSave(Order order){
        System.out.println(order.getOrderNumber() + " SRP.Order is saved");
    }
}
class NotificationService{
     void sendNotification(Order order){
        System.out.println(order.getOrderNumber() + " notification is sent");
    }
}