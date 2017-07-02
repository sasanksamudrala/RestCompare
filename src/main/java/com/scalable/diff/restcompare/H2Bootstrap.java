package com.scalable.diff.restcompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.scalable.diff.restcompare.entity.JsonBinaryDataEntity;
import com.scalable.diff.restcompare.repository.JsonBinaryDataRepository;

/**
 * Bootstraps data in ID value "0" on start of application
 * 
 * @author Sasank Samudrala
 * @version 0.0.1
 */

@Component
public class H2Bootstrap implements CommandLineRunner {
	
	@Autowired
	JsonBinaryDataRepository jsonBinaryDataRepository;

	@Override
	public void run(String... arg0) throws Exception {		
		jsonBinaryDataRepository.save(new JsonBinaryDataEntity(0, "ew0KICAgICJkYXRhIjogIlRoaXMgaXMgYSBTdHJpbmciDQp9", "left"));
		jsonBinaryDataRepository.save(new JsonBinaryDataEntity(0, "ew0KICAgICJkYXRhIjogIlRoaXMgaXMgYSBTdHJpbmciDQp9", "right"));
		
		
		/*Iterable<JsonBinaryDataEntity> itr = jsonBinaryDataRepository.findAll();
		
		for (JsonBinaryDataEntity data: itr) {
			System.out.println(data.getEncodedValue());
		}*/
	}

}
