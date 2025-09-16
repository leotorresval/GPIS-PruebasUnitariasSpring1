package market.service;

import market.model.Product;
import market.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp(){
        productRepository = mock(ProductRepository.class);
        productService = new ProductService();
        productService.productRepository =productRepository;
    }

    @Test
    void saveProduct() {
        Product p = new Product();
        p.setId(1);
        p.setName("Name");
        p.setDescription("Description");
        p.setPrice(10);
        p.setStock(10);
        when(productRepository.save(Mockito.any(Product.class)))
                .thenReturn(p);
        Product respuesta = productService.saveProduct(p);
        assertEquals(respuesta.getPrice(),p.getPrice());
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void deleteProductById() {
    }

    @Test
    void findProductById() {
        Product p = new Product();
        p.setId(1);
        p.setName("Test1");
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        Optional<Product> respuesta = productService.findProductById(1L);
        assertEquals(p.getId(),respuesta.get().getId());
        assertNotNull(respuesta);
        verify(productRepository).findById(1L);
    }

    @Test
    void findAllProduct() {
        Product p = new Product();
        Product p1 = new Product();
        p.setName("Test0");
        p.setName("Test1");
        when(productRepository.findAll()).thenReturn(Arrays.asList(p,p1));
        List<Product> lista = productService.findAllProduct();
        assertEquals(2,lista.size());
        assertEquals(lista.get(1).getName(),p1.getName());
        verify(productRepository).findAll();
    }
}