import java.util.Scanner;
import java.util.Random;


public class Main {
   static String[] goodBye = {"Bye! Thanks for using our chatbot.", "See you again later.", "Bye! We hope you visit us again soon!", "Looking forward to the next time you use our service!"};
   static String[] menuItems = {"pizza", "sandwich","drink"};
   static Random rand = new Random();
   public static final String ANSI_RESET = "\u001B[0m"; 
   public static final String ANSI_YELLOW = "\u001B[33m"; 
   public static final String ANSI_BLUE = "\u001B[34m"; 
   public static final String ANSI_GREEN = "\u001B[32m"; 
   public static final String ANSI_RED = "\u001B[31m"; 

   static char[][] seatingChart = new char[5][4]; // 5 rows, 4 columns seating chart


   public static void main(String[] args) {
       Scanner in = new Scanner(System.in);
       String userResp;


       initializeSeatingChart();
       System.out.println("Hey there, welcome to"+ANSI_YELLOW +" The Classic Plate!"+ANSI_RESET+ " I'm PlatePal. I can help you with the "+ANSI_BLUE+"menu, "
       +ANSI_RESET+ ANSI_GREEN+"placing togo orders, "+ANSI_RESET+ANSI_RED+"and reserving seats."+ANSI_RESET);


       while (true) {
            System.out.println(" ");
           System.out.print("You're at the main chat! What would you like to do?: (press 'q' anytime to exit)");
           userResp = in.nextLine().trim().toLowerCase();


           if (checkGoodbye(userResp)) {
               System.out.println(getRandomGoodbye());
               break;
           } else if (userResp.contains("menu")) {
               menu();
           } else if (userResp.contains("togo") || userResp.contains("to go")) {
               toGoOrder(in); // Trigger the to-go order process
           }
           /*  else if (checkMenu(userResp)) {
               System.out.println("Great choice! You've ordered " + userResp + ". Would you like to place an order?");
               String confirmation = in.nextLine().trim();
               if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("sure")) {
                   System.out.println("Your order for " + userResp + " has been placed and will be ready in 30 minutes.");
               } else {
                   System.out.println("No worries! Let me know if you'd like to order something else.");
               
           } */else if (checkReserve(userResp)) {
               reserveSeat(in); // Ask for seat reservations
           } else {
               System.out.println(getRandomResponse()); // Random response for unclear inputs
           }
       }
       in.close();
   }


   public static boolean checkGoodbye(String statement) {
       return containsKeyword(statement, new String[]{"bye", "goodbye", "see you", "later", "quit", "q"});
   }


   public static boolean checkMenu(String statement) {
       return containsKeyword(statement, menuItems);
   }


   public static boolean checkReserve(String statement) {
       return containsKeyword(statement, new String[]{"reserve", "seat", "reservation"});
   }


   public static boolean containsKeyword(String statement, String[] keywords) {
       for (String keyword : keywords) {
           if (statement.contains(keyword)) {
               return true;
           }
       }
       return false;
   }


   public static String getRandomGoodbye() {
       return goodBye[rand.nextInt(goodBye.length)];
   }


   public static void showMenu() {
       System.out.println("Here is the menu for to go:");
       for (String item : menuItems) {
           System.out.println("- " + item);
       }
       System.out.print("What would you like to order? ");
   }


   public static void toGoOrder(Scanner in) {
       showMenu();
       String order = in.nextLine().trim().toLowerCase();


       if (checkMenu(order)) {
           System.out.println("Great choice! Go to our menu for more info on a " + order + ". Please provide your address for delivery.");
           System.out.print("Enter your address: ");
           String address = in.nextLine().trim();


           // Generate a random delivery time
           int deliveryTime = generateRandomDeliveryTime();
           System.out.println("Your order for " + order + " will be delivered to: " + address);
           System.out.println("Expected delivery time: " + deliveryTime + " minutes.");
       } else {
           System.out.println("Sorry, we don't have that item on the menu. Please choose something from the menu.");
       }
   }


   // Method to generate a random delivery time (between 20 and 60 minutes)
   public static int generateRandomDeliveryTime() {
       return rand.nextInt(41) + 20; // Random time between 20 and 60 minutes
   }


   public static void reserveSeat(Scanner in) {
       while (true) {
           showSeatingChart();
           System.out.print("Enter seat (example, 2A, 3B, 5C): ");
           String seat = in.nextLine().trim().toUpperCase();


           if (!isValidSeat(seat)) {
               System.out.println("Invalid seat. Please try again.");
               continue;
           }


           int row = Integer.parseInt(seat.substring(0, 1)) - 1; // Extract the row number and adjust
           int col = seat.charAt(1) - 'A'; // Extract the column letter and convert to index


           if (isSeatAvailable(row, col)) {
               seatingChart[row][col] = 'X';  // Seat taken
               System.out.println("Your reservation for " + seat + " is confirmed!");
               showSeatingChart();
           } else {
               System.out.println("Sorry, that seat is already taken.");
           }


           // Ask if they want to reserve another seat
           System.out.print("Would you like to reserve another seat? (yes/no): ");
           String response = in.nextLine().trim().toLowerCase();


           if (response.equals("no") || response.equals("n")) {
               System.out.println("Returning to the main menu...");
               break;  // Break the loop and return to the main menu
           }
       }
   }


   public static void showSeatingChart() {
       System.out.println("\n------ Restaurant Floorplan ------");


       // Print Column Labels (A, B, C, D)
       System.out.println("     A    B    C    D ");
       System.out.println("  +---+---+---+---+");


       // Print Rows (1, 2, 3, 4, 5)
       for (int i = 0; i < seatingChart.length; i++) {
           System.out.print((i + 1) + " | "); // Row label (1, 2, 3...)
          
           for (int j = 0; j < seatingChart[i].length; j++) {
               // Seat status (O or X)
               System.out.print(" " + seatingChart[i][j] + " | ");
           }
           System.out.println(); // Move to next line


           // Print Divider Line after each row
           System.out.println("  +---+---+---+---+---+");
       }


       System.out.println("\nNote: Seat 1D is the first seat from the entrance");
   }


   public static boolean isValidSeat(String seat) {
       if (seat.length() != 2 || !Character.isDigit(seat.charAt(0)) || !Character.isLetter(seat.charAt(1))) {
           System.out.println("Invalid format. Please enter a seat in the format (e.g., 2A, 3B).");
           return false;
       }
      
       int row = Integer.parseInt(seat.substring(0, 1)) - 1; // Get the row number and adjust
       int col = seat.charAt(1) - 'A'; // Get the column letter and adjust


       // Ensure the row and column are within valid bounds
       return row >= 0 && row < seatingChart.length && col >= 0 && col < seatingChart[row].length;
   }


   public static boolean isSeatAvailable(int row, int col) {
       return seatingChart[row][col] == 'O';  // 'O' means available
   }


   public static void initializeSeatingChart() {
       for (int i = 0; i < seatingChart.length; i++) {
           for (int j = 0; j < seatingChart[i].length; j++) {
               seatingChart[i][j] = 'O'; // O means available
           }
       }
       // Optionally, we can explicitly confirm seat 1D is available here
       seatingChart[0][3] = 'O'; // Seat 1D (first seat in row 1 and column D)
   }


   // Random responses when the user input isn't clear
   public static String getRandomResponse() {
       String[] randomResponses = {
           "I'm not sure what you mean. Can you clarify?",
           "Interesting! Tell me more.",
           "Could you clarify what you're asking?",
           "I don't quite understand, but I'll try to help."
       };


       return randomResponses[rand.nextInt(randomResponses.length)];
   }


   public static void menu(){
    Scanner in = new Scanner(System.in);
    System.out.println("Here is the menu! What would you like to order?\n" +
                      "- Pizza\n- Sandwiches\n- Drinks\n ");
                      String userResp = in.nextLine();
    
    if (userResp.contains("Pizza")||userResp.contains("pizza")){
        System.out.println("Here are our pizza topping options:\n"+"Cheese\n"+"Veggie\n"+"Pepperoni\n");
        String[] toppings={"Cheese","cheese","Veggie","veggie","Pepperoni","pepperoni"};
        userResp=in.nextLine();
        String x="";

        for(int i=0;i<toppings.length;i++){
            if(userResp.contains(toppings[i])){
                System.out.println(toppings[i]+". Noted");
                x=toppings[i];
                break;
            }
        }
        System.out.println("Here are our sizes(order now?):- small($8)/n- medium($12)/n- large($16)");
        userResp=in.nextLine();
        if(userResp.contains("Small")||userResp.contains("small")){
            System.out.println("Great! You ordered a small "+x+" pizza for $8");
        }
        else if(userResp.contains("Medium")||userResp.contains("medium")){
            System.out.println("Great! You ordered a medium "+x+" pizza for $12");
        }
        else if(userResp.contains("Large")||userResp.contains("large")){
            System.out.println("Great! You ordered a large "+x+" pizza for $16");
        }
        else{
            System.out.println("Sorry, that's not a size we have");
        }
        


    }
    else if(userResp.contains("Sandwich")||userResp.contains("sandwich")){
        System.out.println("Here are our sandwich options!\n- 1. Chicken Lovers\n- 2. Beef Sandwich \n- 3. All veggie \nWhat do you want?");
        System.out.println("Which option would you like?" );
        userResp=in.nextLine();
        if(userResp.contains("1")||userResp.contains("chicken")||userResp.contains("Chicken")||userResp.contains("2")||
        userResp.contains("beef")||userResp.contains("Beef")||userResp.contains("3")||userResp.contains("veggie")||userResp.contains("Veggie")){
            Sandwich x=new Sandwich(" ",1);
            if (userResp.contains("1")||userResp.contains("chicken")||userResp.contains("Chicken")){
                x.setName("Chicken Lovers");
                
            }
            else if (userResp.contains("2")||userResp.contains("beef")||userResp.contains("Beef")){
                x.setName("Beef Sandwich");
                
            }
            else {
                x.setName("All veggie");
            }
            System.out.println("What spice level do you want?(1-10). only type the number(if >10 we'll just put 1)");
            userResp=in.nextLine();
            int number = Integer.parseInt(userResp);
            if(number>=1&&number<=10){
                x.changeSpiceLevel(number);
            }
            //ADD STUFF HERE

            System.out.println("Your ordered a "+ x.getName()+" sandwich with a spice level of "+x.getSpiceLevel());
    }
    else{
        System.out.println("Sorry, we don't have that on the menu");
    }
    }

    else if (userResp.contains("Drink")||userResp.contains("drink")){
            System.out.println("Here are our drinks options:\n- Water(free)\n- Soda($2)\n- Coffee($3)");
            String x=in.nextLine();
            String[] cool={"Great! ","Cool Beans! ","Awesome! ", "Good Choice! "};
            int randomIndex = rand.nextInt(cool.length);
            if (x.contains("water")||x.contains("Water")){
                System.out.println(cool[randomIndex]+"You ordered a water for free");
            }
            else if(x.contains("Soda")||x.contains("soda")){
                System.out.println("Awesome! You ordered a soda for $2");
            }
            else if(x.contains("Coffee")||x.contains("coffee")){
                System.out.println("Cool (coffee) beans! You ordered a coffee for $3");
            }
            else{
                System.out.println("Sorry, we don't have that on the menu");
            }



    
   }
   else{
    System.out.println("SOrry, that's not on the menu.");
   }
   
}
}





