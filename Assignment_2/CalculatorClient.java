import CalculatorApp.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            // Create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // Get the Naming Service
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // Resolve the Calculator object reference
            Calculator calcRef = CalculatorHelper.narrow(ncRef.resolve_str("Calculator"));

            // Create a scanner for user input
            Scanner scanner = new Scanner(System.in);
            
            // User input for operation
            System.out.println("Enter first number: ");
            float num1 = scanner.nextFloat();
            
            System.out.println("Enter second number: ");
            float num2 = scanner.nextFloat();

            // User input for operation choice
            System.out.println("Choose operation (add, subtract, multiply, divide): ");
            String operation = scanner.next().toLowerCase();

            // Perform operation based on user input
            float result = 0;
            switch (operation) {
                case "add":
                    result = calcRef.add(num1, num2);
                    break;
                case "subtract":
                    result = calcRef.subtract(num1, num2);
                    break;
                case "multiply":
                    result = calcRef.multiply(num1, num2);
                    break;
                case "divide":
                    if (num2 != 0) {
                        result = calcRef.divide(num1, num2);
                    } else {
                        System.out.println("Cannot divide by zero.");
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid operation. Please choose from add, subtract, multiply, or divide.");
                    return;
            }

            // Display the result
            System.out.println("Result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

