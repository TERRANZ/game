package ru.terra.game.client.network.netty;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.*;
import ru.terra.game.client.network.packet.Packet;

public class ClientHandler extends SimpleChannelUpstreamHandler {

    private ClientWorkerThread worker;
    private final PacketFrameDecoder decoder;
    private final PacketFrameEncoder encoder;
    private final Logger log = Logger.getLogger(ClientHandler.class);

    public PacketFrameDecoder getDecoder() {
        return decoder;
    }

    public PacketFrameEncoder getEncoder() {
        return encoder;
    }

    public ClientHandler(PacketFrameDecoder decoder, PacketFrameEncoder encoder) {
        this.decoder = decoder;
        this.encoder = encoder;
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        // Событие вызывается при подключении клиента. Я создаю здесь Worker игрока — объект, который занимается обработкой данных игрока
        // непостредственно.
        // Я передаю ему канал игрока (функция e.getChannel()), чтобы он мог в него посылать пакеты
        worker = new ClientWorkerThread(this, e.getChannel());
        // log.info("channelConnected");
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        // Событие закрытия канала. Используется в основном, чтобы освободить ресурсы, или выполнить другие действия, которые происходят при
        // отключении пользователя. Если его не обработать, Вы можете и не заметить, что пользователь отключился, если он напрямую не сказал этого
        // серверу, а просто оборвался канал.
        // log.info("channelDisconnected");
        worker.disconnectedFromChannel();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        // Функция принимает уже готовые Packet'ы от игрока, поэтому их можно сразу посылать в worker. За их формирование отвечает другой обработчик.
        // log.info("messageReceived");
        if (e.getChannel().isOpen())
            worker.acceptPacket((Packet) e.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        // На канале произошло исключение. Выводим ошибку, закрываем канал.
        // Server.logger.log(Level.WARNING, "Exception from downstream", e.getCause());
        ctx.getChannel().close();
        log.info("exceptionCaught", e.getCause());
    }
}
