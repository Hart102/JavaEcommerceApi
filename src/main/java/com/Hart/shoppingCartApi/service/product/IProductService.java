package com.Hart.shoppingCartApi.service.product;

import com.Hart.shoppingCartApi.dto.ProductDto;
import com.Hart.shoppingCartApi.model.Product;
import com.Hart.shoppingCartApi.request.AddProductRequest;
import com.Hart.shoppingCartApi.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    void deleteProduct(Long id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}