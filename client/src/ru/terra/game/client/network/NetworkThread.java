package ru.terra.game.client.network;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import ru.terra.game.client.network.netty.ClientPipelineFactory;

public class NetworkThread implements Runnable
{
	@Override
	public void run()
	{
		ChannelFactory factory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap(factory);
		bootstrap.setPipelineFactory(new ClientPipelineFactory());
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
		ChannelFuture c = bootstrap.connect(new InetSocketAddress("localhost", 12345));
		NetworkManager.getInstance().setChannel(c.getChannel());
	}
}
