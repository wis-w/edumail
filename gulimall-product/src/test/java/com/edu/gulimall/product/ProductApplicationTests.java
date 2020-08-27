package com.edu.gulimall.product;

import com.edu.gulimall.product.entity.BrandEntity;
import com.edu.gulimall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    public void contextLoads() {

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setLogo("you are a logo");
//        brandEntity.setName("阿里巴巴");
//        brandService.save(brandEntity);

        brandService.updateById(brandEntity);

    }

}
