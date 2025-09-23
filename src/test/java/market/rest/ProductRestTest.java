package market.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import market.model.Product;
import market.service.ProductService;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRest.class)
class ProductRestTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Product create(){
        Product p = new Product();
        p.setId(1);
        p.setName("Test");
        p.setDescription("Test Description");
        p.setPrice(10);
        p.setStock(5);
        return p;
    }

    @Test
    void saveProduct() throws Exception {
        Product p = create();
        when(productService.saveProduct(Mockito.any())).thenReturn(p);
        mockMvc.perform(post("/product/")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(p))
                        )//SIMULAR LA PETICION POST, PUT, DELETE
                .andExpect(status().isCreated())//VERIFICA LA RESPUESTA
                .andExpect(jsonPath("$.id").value(1))
        ;
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProductById() {
    }

    @Test
    void findAllProduct() {
    }

    @Test
    void findProductById() throws Exception {
        when(productService.findProductById(Mockito.anyLong()))
                .thenReturn(Optional.of(create()));
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk());
    }
}