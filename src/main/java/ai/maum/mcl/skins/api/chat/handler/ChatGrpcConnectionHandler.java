package ai.maum.mcl.skins.api.chat.handler;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rag_service.rag_module.RagServiceGrpc;
import rag_service.rag_module.RagServiceGrpc.RagServiceStub;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatGrpcConnectionHandler {

    @Value("${service.grpc.chat.host}")
    private String host;

    @Value("${service.grpc.chat.port}")
    private int port;

    @Getter
    @Setter
    public class SummarizeRequestObject {
        private ManagedChannel channel;
        private RagServiceGrpc.RagServiceBlockingStub stub;

        SummarizeRequestObject(ManagedChannel channel, RagServiceGrpc.RagServiceBlockingStub stub) {
            this.channel = channel;
            this.stub = stub;
        }
    }

    private ConcurrentHashMap<String, SummarizeRequestObject> summarizeRequestMap = new ConcurrentHashMap<>();

    public SummarizeRequestObject createSummarizeRequestObject(Long userKey) {
        ManagedChannel channel = createChannel();
        if (channel == null)
            return null;

        RagServiceGrpc.RagServiceBlockingStub stub = RagServiceGrpc.newBlockingStub(channel);
        if (stub == null)
            return null;

        SummarizeRequestObject summarizeRequestObject = new SummarizeRequestObject(channel, stub);
        summarizeRequestMap.put(String.valueOf(userKey), summarizeRequestObject);

        return summarizeRequestObject;
    }

    private ManagedChannel createChannel() {
        log.debug("gRPC:createChannel!!!" + host + ":" + port);
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(host, port);
        if (port != 443)
            channelBuilder.usePlaintext();
        return channelBuilder.build();
    }

    public void closeChannel(String userKey) {
        SummarizeRequestObject summarizeRequestObject = summarizeRequestMap.get(userKey);
        if (summarizeRequestObject != null) {
            ManagedChannel channel = summarizeRequestObject.getChannel();
            shutdownChannel(channel);
            summarizeRequestMap.remove(userKey);
        }
    }

    private void shutdownChannel(ManagedChannel channel) {
        channel.shutdown();
        try {
            log.info("shutdown channel - awaitTermination");
            if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                log.info("shutdown channel - shutdownnow");
                channel.shutdownNow();
                if (!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                    log.info("channel did not terminate");
                }
            }
        } catch (InterruptedException e) {
            log.info("shutdown channel exception - shutdownnow");
            channel.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

