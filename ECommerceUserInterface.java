// Gurman Banwait 
// 501119414
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

//import ECommerceSystem.UnknownCustomerException;

// Simulation of a Simple E-Commerce System (like Amazon)	

public class ECommerceUserInterface {
	public static void main(String[] args) {
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine()) {
			String action = scanner.nextLine();

			if (action == null || action.equals("")) {
				System.out.print("\n>");
				continue;
			} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;

			else if (action.equalsIgnoreCase("PRODS")) // List all products for sale
			{
				amazon.printAllProducts();
			} else if (action.equalsIgnoreCase("BOOKS")) // List all books for sale
			{
				amazon.printAllBooks();
			} else if (action.equalsIgnoreCase("BOOKSBYAUTHOR")) // ship an order to a customer
			{
				String author = "";

				System.out.print("Author: ");
				if (scanner.hasNextLine())
					author = scanner.nextLine();

				ArrayList<Book> books = amazon.booksByAuthor(author);
				Collections.sort(books);
				for (Book book : books)
					book.print();
			} else if (action.equalsIgnoreCase("CUSTS")) // List all registered customers
			{
				amazon.printCustomers();
			} else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
			{
				amazon.printAllOrders();
			} else if (action.equalsIgnoreCase("SHIPPED")) // List all orders that have been shipped
			{
				amazon.printAllShippedOrders();
			} else if (action.equalsIgnoreCase("NEWCUST")) // Create a new registered customer
			{
				String name = "";
				String address = "";

				System.out.print("Name: ");
				if (scanner.hasNextLine())
					name = scanner.nextLine();

				System.out.print("\nAddress: ");
				if (scanner.hasNextLine())
					address = scanner.nextLine();

				try 
				{
					boolean success = amazon.createCustomer(name, address);
				}
				catch (Exception e)
				{
					System.err.println(e); 
				}
			} else if (action.equalsIgnoreCase("SHIP")) // ship an order to a customer
			{
				String orderNumber = "";

				System.out.print("Order Number: ");
				if (scanner.hasNextLine())
					orderNumber = scanner.nextLine();

				try 
				{
					ProductOrder order = amazon.shipOrder(orderNumber);
				}
				catch (Exception e)
				{
					System.err.println(e); 
				}
			} else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this
																// customer
			{
				String customerId = "";

				System.out.print("Customer Id: ");
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();

				// Prints all current orders and all shipped orders for this customer
				try 
				{
					boolean validCustomer = amazon.printOrderHistory(customerId);
				}
				catch (Exception e)
				{
					System.err.println(e); 
				}
			} else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
			{
				String productId = "";
				String customerId = "";

				System.out.print("Product Id: ");
				if (scanner.hasNextLine())
					productId = scanner.nextLine();

				System.out.print("\nCustomer Id: ");
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();

				
				try 
				{
					String orderNumber = amazon.orderProduct(productId, customerId, "");
				}
				catch (Exception e)
				{
					System.err.println(e); 
				}
			} else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format
																// (Paperback,
																// Hardcover or EBook)
			{
				String productId = "";
				String customerId = "";
				String format = "";

				System.out.print("Product Id: ");
				if (scanner.hasNextLine())
					productId = scanner.nextLine();

				System.out.print("\nCustomer Id: ");
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();

				System.out.print("\nFormat [Paperback Hardcover EBook]: ");
				if (scanner.hasNextLine())
					format = scanner.nextLine();

				try {
					String orderNumber = amazon.orderProduct(productId, customerId, format);
				} catch (Exception e) {
					System.err.println(e);
				}
			} else if (action.equalsIgnoreCase("ORDERSHOES")) // order a book for a customer, provide a format
																// (Paperback, Hardcover or EBook)
			{
				String productId = "";
				String customerId = "";
				String sizeColor = "";

				System.out.print("Product Id: ");
				if (scanner.hasNextLine())
					productId = scanner.nextLine();

				System.out.print("\nCustomer Id: ");
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();

				System.out.print("\nSize (6, 7, 8, 9, 10) and Color (Black or Brown): ");
				if (scanner.hasNextLine())
					sizeColor = scanner.nextLine();

				try 
				{
					String orderNumber = amazon.orderProduct(productId, customerId, sizeColor);
				} 
				catch (Exception e)
				{
					System.err.println(e); 
				}
				
			} else if (action.equalsIgnoreCase("ADDTOCART")) ///// all me
			{
				String productId = "";
				String customerId = "";
				String productOptions = "";

				System.out.print("Product Id: ");
				if (scanner.hasNextLine())
					productId = scanner.nextLine();

				System.out.print("\nCustomer Id: ");
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();

				System.out.print("Product Options: ");
				if (scanner.hasNextLine())
					productOptions = scanner.nextLine();

				try 
				{
					amazon.addToCart(productId, customerId, productOptions);
				} 
				catch (Exception e)
				{
					System.err.println(e); 
				}

			} else if (action.equalsIgnoreCase("REMOVEITEM")) //////
			{
				String customerId = "";
				String productId = "";

				System.out.print("Customer Id: ");
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();

				System.out.print("\nProduct Id: ");
				if (scanner.hasNextLine())
					productId = scanner.nextLine();

				try 
				{
					amazon.removeFromCart(productId, customerId);
				} 
				catch (Exception e) 
				{
					System.out.println(e); 
				}

			} else if (action.equalsIgnoreCase("PRINTCART"))////////
			{
				String customerId = "";

				System.out.print("Customer Id: ");
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();

				try 
				{
					amazon.printCart(customerId);
				} 
				catch (Exception e)
				{
					System.err.println(e); 
				}

			} else if (action.equalsIgnoreCase("ORDERITEMS"))////////
			{
				String customerId = "";

				System.out.print("Customer Id: ");
				if (scanner.hasNextLine())
					customerId = scanner.nextLine();

				try 
				{
					amazon.orderItems(customerId);
				}	
				catch (Exception e)
				{
					System.err.println(e); 
				}

			} else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
			{
				String orderNumber = "";

				System.out.print("Order Number: ");
				if (scanner.hasNextLine())
					orderNumber = scanner.nextLine();

				try {
					boolean success = amazon.cancelOrder(orderNumber);
				} catch (ECommerceSystem.InvalidOrderNumException e) {
					System.err.println(e);
				}

			} else if (action.equalsIgnoreCase("SORTBYPRICE")) // sort products by price
			{
				amazon.sortByPrice();
			} else if (action.equalsIgnoreCase("SORTBYNAME")) // sort products by name (alphabetic)
			{
				amazon.sortByName();
			} else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
			{
				amazon.sortCustomersByName();
			}
			System.out.print("\n>");
		}
	}
}
