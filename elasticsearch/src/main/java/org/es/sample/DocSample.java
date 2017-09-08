package org.es.sample;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

public class DocSample {
	
	static final Logger logger = LogManager.getLogger(Logger.class.getName());

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// Settings settings = Settings.builder()
		// .put("cluster.name", "myClusterName").build();

		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

		// String json = "{" + "\"user\":\"kimchy\"," +
		// "\"postDate\":\"2013-01-30\","
		// + "\"message\":\"trying out Elasticsearch\"" + "}";
		//
		// Map<String, Object> json = new HashMap<String, Object>();
		// json.put("user", "kimchy");
		// json.put("postDate", new Date());
		// json.put("message", "trying out Elasticsearch");
		//
		// // instance a json mapper
		// ObjectMapper mapper = new ObjectMapper(); // create once, reuse
		//
		// // generate json
		// byte[] json = mapper.writeValueAsBytes(yourbeaninstance);
		//
		// XContentBuilder builder = jsonBuilder().startObject().field("user",
		// "kimchy").field("postDate", new Date())
		// .field("message", "trying out Elasticsearch").endObject();
		// String json = builder.string();
		//
		//
		//

		// IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
		// .setSource(jsonBuilder().startObject().field("user",
		// "kimchy").field("postDate", new Date())
		// .field("message", "trying out Elasticsearch").endObject())
		// .get();

		String json = "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\","
				+ "\"message\":\"trying out Elasticsearch\"" + "}";

		IndexResponse response = client.prepareIndex("twitter", "tweet").setSource(json).get();

		// Index name
		String _index = response.getIndex();
		// Type name
		String _type = response.getType();
		// Document ID (generated or not)
		String _id = response.getId();
		// Version (if it's the first time you index this document, you will
		// get: 1)
		long _version = response.getVersion();
		// status has stored current instance statement.
		RestStatus status = response.status();
		
		logger.log(Level.ERROR, _index);
		logger.log(Level.ERROR, _type);
		logger.log(Level.ERROR, _id);
		logger.log(Level.ERROR, _version);
		logger.log(Level.ERROR, status);

		// on shutdown

		client.close();

	}

}
