// Gurman Banwait 
// 501119414
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem {
	ArrayList<Product> products = new ArrayList<Product>();
	ArrayList<Customer> customers = new ArrayList<Customer>();

	ArrayList<ProductOrder> orders = new ArrayList<ProductOrder>();
	ArrayList<ProductOrder> shippedOrders = new ArrayList<ProductOrder>();

	// These variables are used to generate order numbers, customer id's, product
	// id's
	int orderNumber = 500;
	int customerId = 900;
	int productId = 700;

	// General variable used to store an error message when something is invalid
	// (e.g. customer id does not exist)
	String errMsg = null;

	// Random number generator
	Random random = new Random();

	public ECommerceSystem() {
		// NOTE: do not modify or add to these objects!! - the TAs will use for testing
		// If you do the class Shoes bonus, you may add shoe products

		// Create some products
		try {
			File prodsFile = new File("products.txt");
			Scanner s = new Scanner(prodsFile);
			while (s.hasNextLine()) {
				String catagoryStr = s.nextLine().replace("\n", "");
				if (catagoryStr.isEmpty() || catagoryStr.isBlank())
					continue;
				Product.Category category = Product.Category.valueOf(catagoryStr);
				String name = s.nextLine().replace("\n", "");
				float price = Float.parseFloat(s.nextLine().replace("\n", ""));
				if (category == Product.Category.BOOKS) {
					// 4 2
					// Ahm Gonna Make You Learn:T. McInerney:2021
					String[] stocks = s.nextLine().replace("\n", "").split(" ");
					int paperbackStock = Integer.parseInt(stocks[0]);
					int hardcoverStock = Integer.parseInt(stocks[1]);

					String[] info = s.nextLine().replace("\n", "").split(":");
					String title = info[0];
					String author = info[1];
					int year = Integer.parseInt(info[2]);

					products.add(new Book(name, generateProductId(), price, paperbackStock, hardcoverStock, title, author, year));
				} else {
					int stock = Integer.parseInt(s.nextLine().replace("\n", ""));
					products.add(new Product(name, generateProductId(), price, stock, category));
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.toString());
		}
		// products.add(new Product("Acer Laptop", generateProductId(), 989.0, 99,
		// Product.Category.COMPUTERS));
		// products.add(new Product("Apex Desk", generateProductId(), 1378.0, 12,
		// Product.Category.FURNITURE));
		// products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "Ahm Gonna
		// Make You Learn", "T. McInerney", 2021));
		// products.add(new Product("DadBod Jeans", generateProductId(), 24.0, 50,
		// Product.Category.CLOTHING));
		// products.add(new Product("Polo High Socks", generateProductId(), 5.0, 199,
		// Product.Category.CLOTHING));
		// products.add(new Product("Tightie Whities", generateProductId(), 15.0, 99,
		// Product.Category.CLOTHING));
		// products.add(new Book("Book", generateProductId(), 35.0, 4, 2, "How to Fool
		// Your Prof", "D. Umbast", 1997));
		// products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "How to Escape
		// from Prison", "A. Fugitive", 1963));
		// products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "How to Teach
		// Programming", "T. McInerney", 2001));
		// products.add(new Product("Rock Hammer", generateProductId(), 10.0, 22,
		// Product.Category.GENERAL));
		// products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "Ahm Gonna
		// Make You Learn More", "T. McInerney", 2022));
		// int[][] stockCounts = {{4, 2}, {3, 5}, {1, 4,}, {2, 3}, {4, 2}};
		// products.add(new Shoes("Prada", generateProductId(), 595.0, stockCounts));

		// Create some customers
		customers.add(new Customer(generateCustomerId(), "Inigo Montoya", "1 SwordMaker Lane, Florin"));
		customers.add(new Customer(generateCustomerId(), "Prince Humperdinck", "The Castle, Florin"));
		customers.add(new Customer(generateCustomerId(), "Andy Dufresne", "Shawshank Prison, Maine"));
		customers.add(new Customer(generateCustomerId(), "Ferris Bueller", "4160 Country Club Drive, Long Beach"));
	}

	private String generateOrderNumber() {
		return "" + orderNumber++;
	}

	private String generateCustomerId() {
		return "" + customerId++;
	}

	private String generateProductId() {
		return "" + productId++;
	}

	public String getErrorMessage() {
		return errMsg;
	}

	public void printAllProducts() {
		for (Product p : products)
			p.print();
	}

	public void printAllBooks() {
		for (Product p : products) {
			if (p.getCategory() == Product.Category.BOOKS)
				p.print();
		}
	}

	public ArrayList<Book> booksByAuthor(String author) {
		ArrayList<Book> books = new ArrayList<Book>();
		for (Product p : products) {
			if (p.getCategory() == Product.Category.BOOKS) {
				Book book = (Book) p;
				if (book.getAuthor().equals(author))
					books.add(book);
			}
		}
		return books;
	}

	public void printAllOrders() {
		for (ProductOrder o : orders)
			o.print();
	}

	public void printAllShippedOrders() {
		for (ProductOrder o : shippedOrders)
			o.print();
	}

	public void printCustomers() {
		for (Customer c : customers)
			c.print();
	}

	/*
	 * Given a customer id, print all the current orders and shipped orders for them
	 * (if any)
	 */
	public boolean printOrderHistory(String customerId) throws UnknownCustomerException {
		// Make sure customer exists
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1) {
			throw new UnknownCustomerException("Customer " + customerId + " Not Found"); ////////////
		}
		System.out.println("Current Orders of Customer " + customerId);
		for (ProductOrder order : orders) {
			if (order.getCustomer().getId().equals(customerId))
				order.print();
		}
		System.out.println("\nShipped Orders of Customer " + customerId);
		for (ProductOrder order : shippedOrders) {
			if (order.getCustomer().getId().equals(customerId))
				order.print();
		}
		return true;
	}

	public Product getProduct(String productId) ///
	{
		int index = products.indexOf(new Product(productId));
		if (index == -1) {

			return null;
		}
		Product product = products.get(index);
		return product;
	}

	public String orderProduct(String productId, String customerId, String productOptions) throws UnknownCustomerException, UnknownProductException, InvalidproductOptionsException, NoStockException /////////////
	{
		// Get customer
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1) {
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}
		Customer customer = customers.get(index);

		// Get product
		index = products.indexOf(new Product(productId));
		if (index == -1) {
			throw new UnknownProductException("Product " + productId + " Not Found");
		}
		Product product = products.get(index);

		// Check if the options are valid for this product (e.g. Paperback or Hardcover
		// or EBook for Book product)
		if (!product.validOptions(productOptions)) {
			throw new InvalidproductOptionsException(
					"Product " + product.getName() + " ProductId " + productId + " Invalid Options: " + productOptions);
		}
		// Is it in stock?
		if (product.getStockCount(productOptions) == 0) {
			throw new NoStockException("Product " + product.getName() + " ProductId " + productId + " Out of Stock");
		}
		// Create a ProductOrder
		ProductOrder order = new ProductOrder(generateOrderNumber(), product, customer, productOptions);
		product.reduceStockCount(productOptions);

		// Add to orders and return
		orders.add(order);

		return order.getOrderNumber();
	}

	/*
	 * Create a new Customer object and add it to the list of customers
	 */

	public boolean createCustomer(String name, String address) throws NoNameException, InvalidAddyException {  /////////
		// Check to ensure name is valid
		if (name == null || name.equals("")) {
			throw new NoNameException("Invalid Customer Name " + name);
		}
		// Check to ensure address is valid
		if (address == null || address.equals("")) {
			throw new InvalidAddyException("Invalid Customer Address " + address);
		}
		Customer customer = new Customer(generateCustomerId(), name, address);
		customers.add(customer);
		return true;
	}

	public ProductOrder shipOrder(String orderNumber) throws InvalidOrderNumException {
		// Check if order number exists
		int index = orders.indexOf(new ProductOrder(orderNumber, null, null, ""));
		if (index == -1) {
			throw new InvalidOrderNumException("Order " + orderNumber + " Not Found");
		}
		ProductOrder order = orders.get(index);
		orders.remove(index);
		shippedOrders.add(order);
		return order;
	}

	/*
	 * Cancel a specific order based on order number
	 */
	public boolean cancelOrder(String orderNumber) throws InvalidOrderNumException {
		// Check if order number exists
		int index = orders.indexOf(new ProductOrder(orderNumber, null, null, ""));
		if (index == -1) {
			throw new InvalidOrderNumException("Order " + orderNumber + " Not Found");
		}
		ProductOrder order = orders.get(index);
		orders.remove(index);
		return true;
	}

	// Sort products by increasing price
	public void sortByPrice() {
		Collections.sort(products, new PriceComparator());
	}

	private class PriceComparator implements Comparator<Product> {
		public int compare(Product a, Product b) {
			if (a.getPrice() > b.getPrice())
				return 1;
			if (a.getPrice() < b.getPrice())
				return -1;
			return 0;
		}
	}

	// Sort products alphabetically by product name
	public void sortByName() {
		Collections.sort(products, new NameComparator());
	}

	private class NameComparator implements Comparator<Product> {
		public int compare(Product a, Product b) {
			return a.getName().compareTo(b.getName());
		}
	}

	// Sort products alphabetically by product name
	public void sortCustomersByName() {
		Collections.sort(customers);
	}

	public void addToCart(String productId, String customerId, String productOptions) { /////////////
		Customer c = this.customers.get(this.customers.indexOf(new Customer(customerId)));
		Product p = this.getProduct(productId);
		c.getCart().addItem(p, productOptions);
	}

	public void removeFromCart(String productId, String customerId) {   //////////////////
		Customer c = this.customers.get(this.customers.indexOf(new Customer(customerId)));
		c.getCart().removeItem(productId);
	}

	public void printCart(String customerId) {  /////////////
		Customer c = this.customers.get(this.customers.indexOf(new Customer(customerId)));
		c.getCart().printCart();
	}

	public void orderItems(String customerId) { ////////////////
		Customer c = this.customers.get(this.customers.indexOf(new Customer(customerId)));
		ArrayList<CartItem> cart = c.getCart().getItems();
		for (int i = 0; i < cart.size(); i++) {
			try {
				this.orderProduct(cart.get(i).getProduct().getId(), customerId, cart.get(i).getProductOptions());
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public class UnknownCustomerException extends Exception////////////
	{
		public UnknownCustomerException(String errMsg) {
			super(errMsg);
		}

	}

	public class UnknownProductException extends Exception ////////////
	{
		public UnknownProductException(String errMsg) {
			super(errMsg);
		}
	}

	public class InvalidproductOptionsException extends Exception//////////
	{
		public InvalidproductOptionsException(String errMsg) {
			super(errMsg);
		}
	}

	public class NoStockException extends Exception /////////////
	{
		public NoStockException(String errMsg) {
			super(errMsg);
		}
	}

	public class NoNameException extends Exception ///////////
	{
		public NoNameException(String errMsg) {
			super(errMsg);
		}
	}

	public class InvalidAddyException extends Exception /////////
	{
		public InvalidAddyException(String errMsg) {
			super(errMsg);
		}
	}

	public class InvalidOrderNumException extends Exception //////////
	{
		public InvalidOrderNumException(String errMsg) {
			super(errMsg);
		}
	}
}
