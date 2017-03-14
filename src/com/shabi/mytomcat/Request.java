package com.shabi.mytomcat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Request {
	private String uri;

	public Request(SocketChannel socketChannel) throws IOException {
		String msg = "";

		// 创建一个Buffer有1024个字节
		ByteBuffer webBuffer = ByteBuffer.allocate(1024);

		socketChannel.read(webBuffer);
		msg = new String(webBuffer.array());

		// int len = socketChannel.read(webBuffer);
		// while (len != -1) {
		// webBuffer.flip();
		// while (webBuffer.hasRemaining()) {
		// msg += (char) webBuffer.get();
		// // System.out.print((char) webBuffer.get());
		// }
		// System.out.println("---" + msg + "---");
		// webBuffer.clear();
		// len = socketChannel.read(webBuffer);
		// }
		// System.out.println("---" + msg + "---");
		uri = msg.substring(msg.indexOf("/"), msg.indexOf("HTTP/1.1") - 1);
		System.out.println("---" + uri + "---");
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
