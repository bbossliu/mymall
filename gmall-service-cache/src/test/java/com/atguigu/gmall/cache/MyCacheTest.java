package com.atguigu.gmall.cache;

import com.atguigu.gmall.cache.service.ICacheService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dalaoban
 * @create 2020-03-15-11:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyCacheTest {

    @Resource
    ICacheService cacheService;

    @Test
    public void test1(){
        Map<String,String> map=new HashMap<>();
        map.put("name","liux");
        boolean set = cacheService.hmset("d",map);
        List<String> d = cacheService.hval("d");
        d.stream().forEach(System.out::print);
    }
}
