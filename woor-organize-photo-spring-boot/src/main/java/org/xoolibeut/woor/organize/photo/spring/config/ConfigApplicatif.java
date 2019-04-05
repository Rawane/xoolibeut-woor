package org.xoolibeut.woor.organize.photo.spring.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
				configBean.from=new HashMap<>();
				for(JsonNode jsonEnt : jsonArr){
					String key = jsonEnt.fieldNames().next();
					configBean.from.put(key,jsonEnt.get(key).textValue());
					
				}
				if(jsonArr!=null) {
					 Iterator<Entry<String, JsonNode>> fields = jsonArr.fields();
					while(fields.hasNext()) {
						 Entry<String, JsonNode> jsonEnt = fields.next();
						 System.out.println(jsonEnt.getKey());
						 System.out.println(jsonEnt.getValue().toString());
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
