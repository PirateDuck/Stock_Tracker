import java.util.ArrayList;


public interface IModel {
	
	
	public void setSourceFile(String path);
	
	public boolean addProduct(Product prod);
	
	public void removeProductID(int id);
	
	public void removeProduct(Product prod);
	
	public ArrayList<Product> getAllProducts();
	
	public void removeOneProductPiece(Product prod);
	
	public int getTotalValue();

	public boolean isEmpty();

}
