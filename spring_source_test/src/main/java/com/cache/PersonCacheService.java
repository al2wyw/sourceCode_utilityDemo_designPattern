package com.cache;

import com.utils.LoggerUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/10/29
 * Time: 11:38
 * Desc:
 */
@Service
@CacheConfig(cacheNames = "person", keyGenerator = "myKeyGenerator")
public class PersonCacheService {

    private static final Map<Long,Person> STORE = new HashMap<>();

    private void create(Long id){
        Person p = new Person();
        p.setName("peter");
        p.setId(id);
        p.setAge(34);
        p.setSalary(new BigDecimal("2343.54"));
        p.setGender(true);
        STORE.put(id,p);
    }

    public void init(){
        create(11L);
        create(12L);
        create(13L);
        create(14L);
        create(15L);
    }

    @Cacheable()
    public Person getById(@CacheKey Long id){
        try {
            Thread.sleep(1000L);
        }catch (Exception e){
            LoggerUtils.getLogger().error("",e);
        }
        Person p = STORE.get(id);
        return p == null ? null : copyOf(p);
    }

    @CachePut()
    public Person updatePerson(@CacheKey Long id, String name){
        Person old = STORE.get(id);
        old.setName(name);
        return old;
    }

    @CacheEvict()
    public void replacePerson(@CacheKey(path = "id") Person person){
        STORE.put(person.getId(),person);
    }

    @CacheEvict()
    public void clearPerson(@CacheKey Long id){
        STORE.remove(id);
    }

    @CacheEvict(allEntries = true)
    public void clearPersons(){
        STORE.clear();
    }

    private Person copyOf(Person ori){
        Person p = new Person();
        p.setName(ori.getName());
        p.setId(ori.getId());
        p.setAge(ori.getAge());
        p.setSalary(ori.getSalary());
        p.setGender(ori.getGender());
        return p;
    }
}
