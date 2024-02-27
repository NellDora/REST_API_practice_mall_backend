package org.nelldora.apiserver.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.nelldora.apiserver.domain.Product;
import org.nelldora.apiserver.dto.PageRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {


    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testInsert(){

        for(int i=0; i<50; i++){

            Product product = Product.builder().pname("Test"+i).pdesc("Test Desc"+i).price(1000).build();

            product.addImageString(UUID.randomUUID()+"-IMAGE1.jpg");
            product.addImageString(UUID.randomUUID()+"-IMAGE2.jpg");

            productRepository.save(product);

        }
    }

    @Commit
    @Transactional
    @Test
    public void testDelete(){
        Long pno = 1L;
        productRepository.updateToDelete(1L,true);
    }

    @Test
    public void testUpdate(){
        Product product = productRepository.selectOne(1L).get();

        product.changePrice(30000);

        product.clearList();

        product.addImageString(UUID.randomUUID()+"-"+"PIMAGE1.JPG");

        product.addImageString(UUID.randomUUID()+"-"+"PIMAGE2.JPG");

        product.addImageString(UUID.randomUUID()+"-"+"PIMAGE3.JPG");

        productRepository.save(product);
    }

    @Test
    public void testList(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @Test
    public void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        productRepository.searchList(pageRequestDTO);
    }
}
