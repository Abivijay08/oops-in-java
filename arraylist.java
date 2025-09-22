
import java.util.ArrayList;
import java.util.Scanner;

class Contact {
    String name;
    String phoneNumber;
    String email;

    Contact(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + email;
    }
}

public class ram {
    public static void main(String[] args) {
        ArrayList<Contact> contactList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Welcome to Contact Management System ---");

        while (true) {
            System.out.println("\nMENU:");
            System.out.println("1. Add a new contact");
            System.out.println("2. View all contacts");
            System.out.println("3. Update a contact");
            System.out.println("4. Delete a contact");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter phone number: ");
                    String phone = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    contactList.add(new Contact(name, phone, email));
                    System.out.println("Contact added successfully.");
                    break;

                case 2:
                    System.out.println("Contact List:");
                    for (Contact c : contactList) {
                        System.out.println(c);
                    }
                    break;

                case 3:
                    System.out.print("Enter contact name to update: ");
                    String updateName = scanner.nextLine();
                    boolean foundUpdate = false;
                    for (Contact c : contactList) {
                        if (c.name.equalsIgnoreCase(updateName)) {
                            System.out.print("Enter new phone number: ");
                            c.phoneNumber = scanner.nextLine();
                            System.out.print("Enter new email: ");
                            c.email = scanner.nextLine();
                            System.out.println("Contact updated.");
                            foundUpdate = true;
                            break;
                        }
                    }
                    if (!foundUpdate) {
                        System.out.println("Contact not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter contact name to delete: ");
                    String deleteName = scanner.nextLine();
                    boolean removed = contactList.removeIf(c -> c.name.equalsIgnoreCase(deleteName));
                    if (removed) {
                        System.out.println("Contact deleted.");
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting... Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
