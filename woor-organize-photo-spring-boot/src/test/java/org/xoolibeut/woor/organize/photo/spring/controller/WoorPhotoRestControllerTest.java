package org.xoolibeut.woor.organize.photo.spring.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.xoolibeut.woor.organize.photo.spring.TagInfoPhoto;
import org.xoolibeut.woor.organize.photo.spring.service.PhotoService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WoorPhotoRestControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PhotoService photoService;

	@Test
	public void demarreIndexationTest() throws Exception {
		mockMvc.perform(get("/api/v1/woorphoto/start")).andExpect(status().isOk()).andExpect(content().string("index"));
	}

	@Test
	public void searchMarqueTest() throws Exception {
		List<TagInfoPhoto> listPhotos = new ArrayList<TagInfoPhoto>();
		TagInfoPhoto photo = new TagInfoPhoto();
		photo.setMarqueAppareil("samsung");
		listPhotos.add(photo);
		Page<TagInfoPhoto> page = new PageImpl<TagInfoPhoto>(listPhotos, PageRequest.of(0, 10), 1);
		given(photoService.findByMarqueAppareil("samsung", 0, 10)).willReturn(page);
		mockMvc.perform(get("/api/v1/woorphoto/search/marque/samsung/0/10")).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].marqueAppareil").value("samsung"));

	}

	@Test
	public void searchByDatePriseTest() throws Exception {
		List<TagInfoPhoto> listPhotos = new ArrayList<TagInfoPhoto>();
		TagInfoPhoto photo = new TagInfoPhoto();
		photo.setMarqueAppareil("samsung");
		photo.setLigneAdresse("rue la nouvelle carquefou");
		listPhotos.add(photo);
		Page<TagInfoPhoto> page = new PageImpl<TagInfoPhoto>(listPhotos, PageRequest.of(0, 10), 10);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		given(photoService.findByDatePriseBetween(dateFormat.parse("2019:01:01 00:10:10"),
				dateFormat.parse("2019:04:01 00:10:10"), 0, 10)).willReturn(page);
		MvcResult mvcResult = mockMvc
				.perform(get("/api/v1/woorphoto/search/date/2019:01:01 00:10:10/2019:04:01 00:10:10/0/10")).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		mockMvc.perform(get("/api/v1/woorphoto/search/date/2019:01:01 00:10:10/2019:04:01 00:10:10/0/10"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].ligneAdresse", containsString("carquefou")));

	}
}
