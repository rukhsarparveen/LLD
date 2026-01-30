package LSP;

public class AccountMain{
    public static void main(String[] args) {
        Withdrawable savingAccount = new SavingAccount();
        Account fixedDepositAccount = new FixedDepositAccount();
        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.withdraw(savingAccount,50);
        accountTransaction.deposit(fixedDepositAccount, 50);


    }
}
interface Account{
    void deposit(double amount);
}
interface Withdrawable extends Account{
    void withdraw(double amount);
}

class AccountTransaction{
    void withdraw(Withdrawable withdrawable, double amount){
            withdrawable.withdraw(amount);
    }
    void deposit(Account account, double amount){
        account.deposit(amount);
    }
}

class SavingAccount implements Withdrawable{
    public void withdraw(double amount){
        System.out.println("Saving account withdrawn amount: "+amount);
    }
    public void deposit(double amount){
        System.out.println("Amount Deposited in Saving Account is: "+amount);
    }
}

class CurrentAccount implements Withdrawable{
    public void withdraw(double amount){
        System.out.println("Current account withdrawn amount: "+amount);
    }
    public void deposit(double amount){
        System.out.println("Amount Deposited in Current Account is: "+amount);
    }
}

class FixedDepositAccount implements Account{
    public void deposit(double amount){
        System.out.println("Amount Deposited in Fixed Deposit Account is: "+amount);
    }
}

