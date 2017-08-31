package travel2.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import travel2.Travel2Application;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
public class SslConfiguration {
	
	@Value("${http.client.ssl.key-store}")
	private Resource keyStore;

	@Value("${http.client.ssl.trust-store}")
	private Resource trustStore;

	// I use the same pw for both keystores:
	@Value("${http.client.ssl.trust-store-password}")
	private String keyStorePassword;

	private static final Logger log = LoggerFactory.getLogger(Travel2Application.class);

	@Bean
	RestTemplate restTemplate() throws Exception {

		log.info("-----------------path------------------");
		log.info(String.valueOf(keyStore.getURL()));
		log.info(String.valueOf(keyStorePassword));

		try {
			SSLContext sslContext = SSLContexts.custom()
					// keystore wasn't within the question's scope, yet it might
					// be handy:
					.loadKeyMaterial(keyStore.getURL(), keyStorePassword.toCharArray(), keyStorePassword.toCharArray())
					.loadTrustMaterial(trustStore.getURL(), keyStorePassword.toCharArray(),
							// use this for self-signed certificates only:
							new TrustSelfSignedStrategy())
					.build();

			HttpClient httpClient = HttpClients.custom()
					// use NoopHostnameVerifier with caution, see
					// https://stackoverflow.com/a/22901289/3890673
					.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier()))
					.build();

			return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
		} catch (IOException | GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}
}