// Gurman Banwait 
// 501119414
import java.util.ArrayList;


public class Cart 
{
    private ArrayList<CartItem> Item;  
    public Cart()
    {
        this.Item = new ArrayList<CartItem>(); 

    }

    public void addItem(Product product, String productOptions)
    {
        Item.add(new CartItem(product, productOptions)); 
    }

    public void removeItem(String productId)
    {
        for (CartItem item : Item)
        {
            if (item.getProduct().getId().equals(productId))
            {
                Item.remove(item); 
                break; 
            }
        }
    }

    public void printCart()
    {
        for (CartItem item : Item)
        {
            item.getProduct().print(); 
        }
    }
    
    public ArrayList<CartItem>getItems()
    {
        return Item; 
    }

}
