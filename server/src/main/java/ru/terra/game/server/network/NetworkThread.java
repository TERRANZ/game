package ru.terra.game.server.network;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import ru.terra.game.server.game.GameManager;
import ru.terra.game.server.network.netty.ServerPipelineFactory;

public class NetworkThread implements Runnable
{

	private Logger log = Logger.getLogger(NetworkThread.class);

	public NetworkThread()
	{
		GameManager.getGameManager().start();
		log.info("Starting network thread...");
	}

	@Override
	public void run()
	{
		ExecutorService bossExec = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS);
		ExecutorService ioExec = new OrderedMemoryAwareThreadPoolExecutor(4 /* число рабочих потоков */, 400000000, 2000000000, 60, TimeUnit.SECONDS);
		ServerBootstrap networkServer = new ServerBootstrap(
				new NioServerSocketChannelFactory(bossExec, ioExec, 4 /* то же самое число рабочих потоков */));
		networkServer.setOption("backlog", 500);
		networkServer.setOption("connectTimeoutMillis", 10000);
		networkServer.setPipelineFactory(new ServerPipelineFactory());
		Channel channel = networkServer.bind(new InetSocketAddress("0.0.0.0", 12345));
	}

}
