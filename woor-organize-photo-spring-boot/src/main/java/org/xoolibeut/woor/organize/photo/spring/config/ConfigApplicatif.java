package org.xoolibeut.woor.organize.photo.spring.config;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConfigApplicatif {
	@Value("classpath:mapping.json")
	private Resource mappingResourceFile;
	@Autowired
	private ObjectMapper mapper;
	private ConfigBean configBean;

	private static class ConfigBean {
		private Map<String, String> from;

	}

	@PostConstruct
	private void init() {
		if (configBean == null) {
			try {
				configBean = new ConfigBean();
				JsonNode json = mapper.readTree(mappingResourceFile.getFile());
				System.out.println(json);				
				JsonNode jsonArr = json.get("from");
				if(jsonArr!=null) {
					Iterator<String> fieldNames = jsonArr.fieldNames();
					while(fieldNames.hasNext()) {
						String key=fieldNames.next();
						configBean.from.put(key, jsonArr.get(key).toString());
					}
					
				}
			} catch (IOException e) {
				throw new RuntimeException("Erreur de chargement du fichier mapping ",e);

			}
		}

	}

	public String from(String model) {
		return configBean.from.get(model);
	}
}
