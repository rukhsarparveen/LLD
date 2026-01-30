package SRP;//Single Responsibility Principle

import java.util.HashMap;
import java.util.Map;

public class UserProfileServiceMain {

    static void main(String[] args) {
        User user = new User(1,"Nisha",100,"nisha@gmail.com");
        UserRepository userRepository = new UserRepository();
        userRepository.save(user);
        UserProfileService userProfileService = new UserProfileService(new UserValidator()
        ,userRepository,new AuditLogger(), new UserNotificationService());
        userProfileService.updateProfile(user);
    }
}
class User{
    private int id;
    private String name;
    private int age;
    private String email;
    public User(int id, String name,int age,String email){
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
    int getId(){
        return this.id;
    }
    String getName(){
        return this.name;
    }
    int getAge(){
        return this.age;
    }
    String getEmail(){
        return this.email;
    }
}
class UserRepository{
    Map<Integer,User> users;
    public UserRepository(){
        this.users = new HashMap<>();
    }
    boolean userExist(User user){
        return users.containsKey(user.getId());
    }
    void save(User user){
        users.put(user.getId(),user);
    }
}

class UserValidator{
    boolean validate(User user){
        if(user.getAge()<18 || !user.getEmail().contains("@")){
            return false;
        }
        else{
            return true;
        }
    }
}
class AuditLogger{
    void audit(User user){
        System.out.println("Details changed of user: " + user.getName());
    }
}
class UserNotificationService{
    void send(User user){
        System.out.println("Notification sent for user: "+user.getName());
    }
}
class UserProfileService{
    private UserValidator userValidator;
    private UserRepository userRepository;
    private AuditLogger auditLogger;
    private UserNotificationService userNotificationService;
    public UserProfileService(
             UserValidator userValidator,
             UserRepository userRepository,
             AuditLogger auditLogger,
             UserNotificationService userNotificationService
    ){
        this.userValidator = userValidator;
        this.userRepository = userRepository;
        this.auditLogger = auditLogger;
        this.userNotificationService = userNotificationService;
    }
     public void updateProfile(User user){
        if(userRepository.userExist(user)){
            if(userValidator.validate(user)){
                System.out.println("SRP.User Exist and validated audit and notifications are remaining");
                auditLogger.audit(user);
                userNotificationService.send(user);
            }
            else{
                System.out.println("SRP.User Validation failed, check user age and email");
            }
        }
        else {
            System.out.println("SRP.User don't exist");
        }
     }
}
