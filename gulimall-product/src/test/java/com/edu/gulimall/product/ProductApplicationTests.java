package com.edu.gulimall.product;

import com.aliyun.oss.OSSClient;
import com.edu.gulimall.product.entity.BrandEntity;
import com.edu.gulimall.product.service.BrandService;
import com.edu.gulimall.product.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 对象处处 引入 oss-starter
 * 配置key endpoint相关信息
 * 使用OSSClient
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    OSSClient ossClient;

    @Autowired
    CategoryService categoryService;

    @Test
    public void test(){
        Long[] paths = categoryService.findCateLogPath(225L);
        System.out.println(Arrays.asList(paths));
    }



    @Test
    public void contextLoads() {

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setBrandId(1L);
        brandEntity.setLogo("you are a logo");
//        brandEntity.setName("阿里巴巴");
//        brandService.save(brandEntity);

        brandService.updateById(brandEntity);
    }

    @Test
    public void testOss() throws FileNotFoundException {
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
//        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
//        String accessKeyId = "LTAI4GLAoqcGxxHxMRGXTMhP";
//        String accessKeySecret = "2CcmBzdZPqzdJsvvgeFTwIv0hoADz2";
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        // 上传文件流。
        InputStream inputStream = new FileInputStream("D:\\Wis\\商城项目\\商城视频\\源码\\pics\\ccd1077b985c7150.jpg");
            ossClient.putObject("wyg2020", "123.jpg", inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        System.out.println("上传完成");
    }

}
