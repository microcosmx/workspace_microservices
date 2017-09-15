/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.myspark.sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/** Represents a page view on a website with associated dimension data. */
class SerializableSample implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1406547708559088738L;

	private int width;
	private transient int height;

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		// out.writeInt(height);
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		// height = in.readInt();
	}

	// public static void main(String[] args) {
	// SerializableSample myBox = new SerializableSample();
	// myBox.setWidth(50);
	// myBox.setHeight(30);
	//
	// try {
	// FileOutputStream fs = new FileOutputStream("foo.ser");
	// ObjectOutputStream os = new ObjectOutputStream(fs);
	// os.writeObject(myBox);
	// os.close();
	//
	// FileInputStream fi = new FileInputStream("foo.ser");
	// ObjectInputStream is = new ObjectInputStream(fi);
	// SerializableSample myBox1 = (SerializableSample) is.readObject();
	// System.out.println(myBox1);
	// is.close();
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }

	public String toString() {
		return "width:" + this.width + "\nheight:" + this.height;
	}

}

// scalastyle:off
/**
 * Generates streaming events to simulate page views on a website.
 *
 * This should be used in tandem with PageViewStream.scala. Example:
 *
 * To run the generator `$ bin/run-example
 * org.apache.spark.examples.streaming.clickstream.PageViewGenerator 44444 10`
 * To process the generated stream `$ bin/run-example \
 * org.apache.spark.examples.streaming.clickstream.PageViewStream
 * errorRatePerZipCode localhost 44444`
 *
 */
// scalastyle:on
public class RestServiceDataGenerator {

	public static String getNextJSON() {
		int random = new Random().nextInt();
		return "www.baidu.com 200 201203 "+random+"\n"
			+ "www.google.com 201 201204 "+random+"\n"
			+ "www.sina.com 203 201206 "+random+"\n";
	}

	public static void main(String[] args) throws IOException {
		int port = 44444;
		int sleepDelayMs = 6000;
		ServerSocket listener = new ServerSocket(port);
		System.out.println("Listening on port: " + port);

		while (true) {
			Socket socket = listener.accept();
			new Thread(() -> {
				System.out.println("Got client connected from: " + socket.getInetAddress());
				PrintWriter out = null;
				try {
					out = new PrintWriter(socket.getOutputStream(), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				while (true) {
					try {
						Thread.sleep(sleepDelayMs);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					out.write(getNextJSON());
					out.flush();
				}
				// socket.close();
			}).start();
		}
	}
}
