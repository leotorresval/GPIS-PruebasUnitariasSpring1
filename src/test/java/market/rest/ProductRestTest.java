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

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

   /* @Test
    void saveProductURIException() throws Exception {
        Product p = create();
        doThrow(new RuntimeException()).when(productService).saveProduct(Mockito.any());
        //when(productService.saveProduct(Mockito.any())).thenReturn(p);
        mockMvc.perform(post("/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p))
                )//SIMULAR LA PETICION POST, PUT, DELETE
                .andExpect(status().isBadRequest())//VERIFICA LA RESPUESTA
        ;
    }*/

    @Test
    void updateProduct() throws Exception{
        Product p = create();
        when(productService.findProductById(anyLong()))
                .thenReturn(Optional.of(create()));
        when(productService.saveProduct(Mockito.any()))
                .thenReturn(p);
        mockMvc.perform(put("/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value("Test"));

    }

    @Test
    void updateProductNotFound() throws Exception{
        Product p = create();
        when(productService.findProductById(anyLong()))
                .thenReturn(Optional.empty());
        mockMvc.perform(put("/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteProductById() throws Exception {
        doNothing().when(productService)
                .deleteProductById(anyLong());
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().
                        string("Producto eliminado exitosamente"));
    }

    @Test
    void deleteProductByIdInternalServer() throws Exception {
        doNothing().when(productService)
                .deleteProductById(anyLong());
        when(productService.findProductById(anyLong()))
                .thenReturn(Optional.of(create()));
        mockMvc.perform(delete("/product/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().
                        string("Problemas al eliminar"));
    }

    @Test
    void findAllProduct() throws Exception {
        when(productService.findAllProduct())
                .thenReturn(Arrays.asList(create(),create()));
        mockMvc.perform(get("/product/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()")
                        .value(2))
                .andExpect(jsonPath("$[0].name")
                        .value("Test"));
    }

    @Test
    void findProductById() throws Exception {
        when(productService.findProductById(Mockito.anyLong()))
                .thenReturn(Optional.of(create()));
        mockMvc.perform(get("/product/1"))
                .andExpect(status().isOk());
    }
}