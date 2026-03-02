import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Add User");
            System.out.println("4. View Users");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Book Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    System.out.print("Quantity: ");
                    int qty = sc.nextInt();
                    Book b = new Book(title, author, qty);
                    b.addBook();
                    break;
                case 2:
                    Book.viewBooks();
                    break;
                case 3:
                    System.out.print("User Name: ");
                    String name = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    User u = new User(name, email);
                    u.addUser();
                    break;
                case 4:
                    User.viewUsers();
                    break;
                case 5:
                    System.out.print("User ID: ");
                    int uid = sc.nextInt();
                    System.out.print("Book ID: ");
                    int bid = sc.nextInt();
                    Transaction t = new Transaction(uid, bid);
                    t.issueBook();
                    break;
                case 6:
                    System.out.print("User ID: ");
                    int uidR = sc.nextInt();
                    System.out.print("Book ID: ");
                    int bidR = sc.nextInt();
                    Transaction tr = new Transaction(uidR, bidR);
                    tr.returnBook();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
        sc.close();
    }
}