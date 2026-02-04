import controllers.MainMenuController;
import repositories.json.JsonATMRepository;
import repositories.json.JsonTechnicianRepository;
import repositories.json.JsonTransactionRepository;
import repositories.json.JsonUserRepository;
import services.ATMService;
import services.AuthenticationService;
import services.BankService;
import services.TechnicianService;
import services.TransactionService;
import services.UserService;

public class Main {

    public static void main(String[] args) {

        var userRepository = new JsonUserRepository();
        var atmRepository = new JsonATMRepository();
        var technicianRepository = new JsonTechnicianRepository();
        var transactionRepository = new JsonTransactionRepository();

        var atmService = new ATMService(atmRepository);
        var bankService = new BankService(userRepository);
        var userService = new UserService(bankService);
        var technicianService = new TechnicianService();
        var transactionService = new TransactionService(transactionRepository);
        var authenticationService = new AuthenticationService(bankService, technicianRepository);

        new MainMenuController(
                authenticationService,
                userService,
                atmService,
                technicianService,
                transactionService
        ).start();
    }
}
