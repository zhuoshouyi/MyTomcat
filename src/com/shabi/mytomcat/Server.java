package com.shabi.mytomcat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务类
 * 
 * @author myViolin
 *
 */
public class Server {
	// 计数器
	private static int count = 0;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 提升作用域
		ServerSocketChannel serverSocketChannel = null;
		SocketChannel socketChannel = null;

		try {
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(8081));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String cTime = format.format(new Date());

			System.out.println("服务器已初始化，等待客户端连接中...");
			while (true) {
				socketChannel = serverSocketChannel.accept();
				ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
				count++;
				System.out.println("第" + count + "次连接服务器");

				// ==================读取网页发来的信息=====================
				// 用Buffer来装载网页传输来的信息，并将长度给len用来控制循环次数
				Request request = new Request(socketChannel);
				// System.out.println("---" + request.getUri() + "---");

				// ================反馈给网页信息=========================
				Response response = new Response();
				// <meta http-equiv=\"Content-Type\" content=\"text/html;
				// charset=utf-8\" />
				// String html =
				// "<html><head><tilte>中秋快乐</title></head><body><br/>服务器回复：中秋快乐~~~</body></html>";
				// webBuffer.clear();
				// webBuffer = ByteBuffer.wrap(html.getBytes());
				// socketChannel.write(webBuffer);
				// socketChannel.close();

				// ===============业务逻辑=============================
				String uri = request.getUri();
				if (isStatic(uri)) {
					sendBuffer = response.writeFile(uri.substring(1));
					socketChannel.write(sendBuffer);
				}
				// 自定义后缀类型data为数据库调用类型
				else if (uri.endsWith(".data")) {
					sendBuffer = Database.showTable();
					socketChannel.write(sendBuffer);
				} else {
					System.out.println("not find this file.");
				}
				// 判断这个是否为请求静态资源 例如 .html css img jpg jpeg js

				// 此处如果不关闭它会直接进行第二次连接
				socketChannel.close();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isStatic(String uri) {
		String[] suffixs = { "htm", "html", "css", "js", "jpg", "jpeg", "png", "ico" };
		// 默认
		boolean isStatic = false;
		for (String suffix : suffixs) {
			if (uri.endsWith("." + suffix)) {
				// 如果成立则为静态资源
				isStatic = true;
				break;
			}
		}

		return isStatic;
	}
}
