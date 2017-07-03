package com.scalable.diff.restcompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.scalable.diff.restcompare.entity.JsonBinaryDataEntity;
import com.scalable.diff.restcompare.repository.JsonBinaryDataRepository;

/**
 * Bootstraps data on startup of the application
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */

@Component
public class H2Bootstrap implements CommandLineRunner {
    
    @Autowired
    JsonBinaryDataRepository jsonBinaryDataRepository;

    /**
     * Bootstraps data into the ID value "0" on start of application as default
     * 
     */
    @Override
    public void run(String... arg0) throws Exception {        
        jsonBinaryDataRepository.save(new JsonBinaryDataEntity(0, "ew0KICAgICJkYXRhIjogIlRoaXMgaXMgYSBTdHJpbmciDQp9", "left"));
        jsonBinaryDataRepository.save(new JsonBinaryDataEntity(0, "ew0KICAgICJkYXRhIjogIlRoaXMgaXMgYSBTdHJpbmciDQp9", "right"));
    }
}
