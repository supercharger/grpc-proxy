package io.grpc.examples.experimental.proxy;

import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.proxy.server.ProxyServerBuilder;

public class JavaProxyServer {
	private static final Logger logger = Logger.getLogger(JavaProxyServer.class.getName());
	
	private int port = 50051;
	private Server server;
	
	
	private void start() throws Exception {
		ProxyServerBuilder proxyServerBuilder = new ProxyServerBuilder.Builder(port)
					.addService(new GreeterServiceImpl())
					.build();
		
		server = proxyServerBuilder.startServer();
		logger.info("Server started, listening on " + port);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its
				// JVM shutdown hook.
				System.err
						.println("*** shutting down gRPC server since JVM is shutting down");
				JavaProxyServer.this.stopServer();
				System.err.println("*** server shut down");
			}
		});
		
		
	}
	
	
	private void stopServer() {
		if (server != null) {
			server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon
	 * threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}
	
	public static void main(String[] args) throws Exception {
		final JavaProxyServer server = new JavaProxyServer();
		server.start();
		server.blockUntilShutdown();
	}
	

}
