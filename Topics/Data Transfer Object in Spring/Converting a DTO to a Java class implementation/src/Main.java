import java.time.LocalDate;
import java.time.Month;

class Solution {    
    Product convertProductDTOToProduct(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setModel(dto.getModel());
        product.setPrice(dto.getPrice());
        product.setVendor("SuperVendor");
        product.setDateOfArrival(LocalDate.of(2023, Month.JANUARY,15));

        return product;
    }
}