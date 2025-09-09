package market.service;

import market.model.Product;
import market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    public ProductRepository productRepository;


    public Product saveProduct(Product p){
        return productRepository.save(p);
    }
    public Product updateProduct(Product p){
        return productRepository.save(p);
    }
    public void deleteProduct(Product p){
        productRepository.delete(p);
    }
    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }
    public Optional<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }


}
