package infilms.asee.giiis.unex.es.thenoworder.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductList implements Serializable {

    private List<Product> elements;

    public ProductList() {
        elements = new ArrayList<>();
    }

    public ProductList(List<Product> e) {
        elements = e;
    }

    /**
     * Removes all the elements from the list
     */
    public void clear() {
        elements.clear();
    }

    /**
     * Getter of the elements attribute
     *
     * @return List with all the elements
     */
    public List<Product> getElements() {
        return elements;
    }

    /**
     * Setter of the elements attribute
     *
     * @param elements List of elements
     */
    public void setElements(List<Product> elements) {
        this.elements = elements;
    }

    /**
     * Add all the elements to the list
     *
     * @param e List of elements to be added
     */
    public void addAll(List<Product> e) {
        elements.addAll(e);
    }

    /**
     * Adds one element to the list
     *
     * @param e Element to be added
     */
    public void addItem(Product e) {
        elements.add(e);
    }

    /**
     * Removes a element from the list
     *
     * @param e Item to be removed
     */
    public void deleteItem(Product e) {
        elements.remove(e);
    }

    /**
     * GET an Product from a certain position
     * @param i Position
     * @return The Product
     */
    public Product getProduct(int i) {
        return elements.get(i);
    }

    public int getSize(){
        return this.elements.size();
    }
}
