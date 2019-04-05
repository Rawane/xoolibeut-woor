package org.xoolibeut.woor.organize.photo.spring;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xoolibeut.woor.organize.photo.spring.model.GeoCode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

@Component
public class WoorGoogleGeocodeImpl implements WoorGoogleGeocode {
	private static final Logger LOGGER = LoggerFactory.getLogger(WoorGoogleGeocodeImpl.class);
	@Value("${woor.xoolibeut-woor.url.google.geocode}")
	private String urlAccessGetGeocode;
	@Value("${woor.xoolibeut-woor.apiKeys}")
	private String apiKey;
	@Value("${woor.xoolibeut-woor.activeProxy}")
	private boolean actifProxy;
	@Value("${woor.xoolibeut-woor.proxy.username}")
	private String username;
	@Value("${woor.xoolibeut-woor.proxy.password}")
	private String password;
	@Value("${woor.xoolibeut-woor.proxy.ip}")
	private String ipProxy;
	@Value("${woor.xoolibeut-woor.proxy.port}")
	private int port;
	@Value("${woor.xoolibeut-woor.geocode.active}")
	private boolean activeGeocode;
	@Value("${woor.xoolibeut-woor.geo.tolerance}")
	private double tolerance;
	@Autowired
	private ObjectMapper objectMapper;	
	private List<GeoCode> geocodes = new ArrayList<>();

	@Override
	public void addDataAdressToPhoto(TagInfoPhoto tagInfoPhoto) {
		if (activeGeocode) {
			if (tagInfoPhoto.getLatitude() != null && tagInfoPhoto.getLongitude() != null) {
				try {
					String ligneAdress = getAdresseFromGeocode(tagInfoPhoto.getLatitude(), tagInfoPhoto.getLongitude());
					if (ligneAdress != null) {
						tagInfoPhoto.setLigneAdresse(ligneAdress);
					}
				} catch (IOException ioException) {
					LOGGER.error("erreur ajout infos adresses ", ioException);
				}
			}
		} else {
			LOGGER.info("Récupération d'infos adresse vers google non activé ");
		}
	}

	private String getAdresseFromGeocode(Double lat, Double lon) throws IOException {
		String ligneAdress = retrieveLignAdresseSamePoint(lat, lon);
		if (ligneAdress != null) {
			LOGGER.info("Pas de requète necessaire " + ligneAdress);
			return ligneAdress;
		}
		OkHttpClient client = null;
		if (actifProxy) {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ipProxy, port));
			Authenticator proxyAuthenticator = new Authenticator() {
				@Override
				public Request authenticate(Route route, Response response) throws IOException {
					String credential = Credentials.basic(username, password);
					return response.request().newBuilder().header("Proxy-Authorization", credential).build();
				}
			};
			client = new OkHttpClient().newBuilder().followRedirects(false).proxy(proxy)
					.proxyAuthenticator(proxyAuthenticator).build();
		} else {
			client = new OkHttpClient().newBuilder().followRedirects(false).build();
		}

		HttpUrl.Builder urlBuilder = HttpUrl.parse(urlAccessGetGeocode).newBuilder();
		urlBuilder.addQueryParameter("key", apiKey);
		urlBuilder.addQueryParameter("latlng", lat + "," + lon);
		String url = urlBuilder.build().toString();
		LOGGER.info("url Access Geocode  " + url);
		Request request = new Request.Builder().url(url).build();
		Call call = client.newCall(request);
		Response response = call.execute();
		LOGGER.info("Response Code  " + response.code());
		if (response.code() == 200) {
			String responseBody = response.body().string();
			System.out.println(" response  ");
			System.out.println(responseBody);
			JsonNode jsonRoot = objectMapper.readTree(responseBody);

			for (JsonNode jsonN : jsonRoot.get("results")) {
				ligneAdress = jsonN.get("formatted_address").textValue();
				break;
			}
			if (ligneAdress != null) {
				GeoCode geocode = new GeoCode();
				geocode.setLat(lat);
				geocode.setLon(lon);
				geocode.setLigneAdresse(ligneAdress);
				geocode.setUuid(UUID.randomUUID().toString());
				geocodes.add(geocode);
			}
		}
		LOGGER.info("Ligne adresse " + ligneAdress);
		return ligneAdress;
	}

	private String retrieveLignAdresseSamePoint(Double lat, Double lon) {
		for (GeoCode geoCode : geocodes) {
			if (Math.abs(geoCode.getLat().doubleValue() - lat.doubleValue()) <= tolerance
					&& Math.abs(geoCode.getLon().doubleValue() - lon.doubleValue()) <= tolerance) {
				return geoCode.getLigneAdresse();
			}

		}
		return null;
	}
}
