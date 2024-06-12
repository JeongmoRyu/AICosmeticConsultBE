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
    public class ChatRequestObject {
        private ManagedChannel chnnal;
        private RagServiceStub stub;
        private ChatStreamObserverHandler streamObserver;
        private long creationTime;
        private long chatbotId;
        private String userKey;
        private long chatroomId;

        ChatRequestObject(ManagedChannel channel, RagServiceStub stub, ChatStreamObserverHandler streamObserver,
                          Long chatbotId, String userKey, Long chatroomId) {
            this.chnnal = channel;
            this.stub = stub;
            this.streamObserver = streamObserver;
            this.creationTime = System.currentTimeMillis();
            this.chatbotId = chatbotId;
            this.userKey = userKey;
            this.chatroomId = chatroomId;
        }

        ChatRequestObject() {

        }
    }

    private ConcurrentHashMap<String, ChatRequestObject> chatRequestMap = new ConcurrentHashMap<>();


    public ConcurrentHashMap<String, ChatRequestObject> getConnectionMaps() {
        return chatRequestMap;
    }

    public int getConnectionSize() {
        return chatRequestMap.size();
    }

    public boolean connectionReset(Long chatroomId) {

        boolean rtn = false;

        ChatRequestObject chatRequestObject = chatRequestMap.get(String.valueOf(chatroomId));

        log.info("connection reset:" + chatroomId);

        if(chatRequestObject != null) {
            ManagedChannel channel = chatRequestObject.getChnnal();

            shutdownChannel(channel);
            chatRequestMap.remove(String.valueOf(chatroomId));
            rtn = true;
            log.info("connection reset OK:" + chatroomId);
        } else {
            log.info("connection reset FAIL not found:" + chatroomId);
        }

        return rtn;
    }
    public int connectionReset() {
        // chatRequestMap에서 모든 맵 항목을 가져와 특정 프로세스를 실행
        chatRequestMap.forEach((key, value) -> {
            log.info("reset Connection... " + key);
            ManagedChannel channel = value.getChnnal();
            shutdownChannel(channel);

            chatRequestMap.remove(key);
        });
        return chatRequestMap.size();
    }

    public Map<String, Object> getConnectoins() {
        Map<String, Object> returnMap = new HashMap<String,Object>();
        chatRequestMap.forEach( (key, value) -> {
            Map<String,Object> item = new HashMap<String,Object> ();
            item.put("chatbot_id", value.getChatbotId());
            item.put("creation_time", DateUtil.convertToStringByMs(value.getCreationTime()));
            item.put("user_key", value.getUserKey());
            item.put("chatroomId", value.getChatroomId());
            returnMap.put(key, item);
        });
        return returnMap;
    }

    public ChatStreamObserverHandler getStreamObserver(String roomId) {
        ChatRequestObject chatRequestObject = chatRequestMap.get(roomId);


        if(chatRequestObject != null) {
            ChatStreamObserverHandler chatStreamObserverHandler = chatRequestObject.getStreamObserver();
            RagServiceStub stub = chatRequestObject.getStub();
            ManagedChannel channel = chatRequestObject.getChnnal();
            log.debug("channel shutdown:" + channel.isShutdown() + ":terminated:" + channel.isTerminated());
            if(!channel.isTerminated() && !channel.isShutdown() && stub != null && chatStreamObserverHandler != null) {
                log.debug("channel OK");
                return chatRequestObject.getStreamObserver();
            }
            else {
                //유효하지 않은 connection 제거
                log.debug("channel remove:" + roomId);
                chatRequestMap.remove(roomId);
                return null;
            }
        }
        else {
            log.debug("chatRequestObject is null:" + roomId);
            return null;
        }
    }

    public ChatStreamObserverHandler createStreamObserver(Long userKey) {
        ManagedChannel channel = createChannel();
        if(channel == null)
            return null;

        RagServiceStub ragServiceStub = createStub(channel);
        if(ragServiceStub == null)
            return null;

        ChatStreamObserverHandler chatStreamObserverHandler = new ChatStreamObserverHandler(channel, ragServiceStub, roomId, chatbot);
        ChatRequestObject chatRequestObject = new ChatRequestObject(channel, ragServiceStub, chatStreamObserverHandler, chatbot.getId(), userKey, chatroomDetail.getRoomId());
        chatRequestMap.put(roomId, chatRequestObject);

        return chatStreamObserverHandler;
    }

    private RagServiceStub createStub(ManagedChannel channel) {

        return RagServiceGrpc.newStub(channel);
    }

    private ManagedChannel createChannel() {
        log.debug("gRPC:createChannel!!!" + host + ":" + port);

        ManagedChannelBuilder channelBuilder = ManagedChannelBuilder.forAddress(host, port);

        if(port != 443)
            channelBuilder.usePlaintext();

        return channelBuilder.build();
    }

    public void closeChannel(String roomId) {
        ChatRequestObject chatRequestObject = chatRequestMap.get(roomId);
        ManagedChannel channel = chatRequestObject.getChnnal();
        shutdownChannel(channel);
        chatRequestMap.remove(roomId);

    }

    public void resetChannelByChatbotId(long chatbotId) {
        chatRequestMap.forEach((key, value) -> {
            log.info("check channel by ChatbotId:" + chatbotId + ":" + key + ":" + value.getChatbotId());
            if(value.getChatbotId() == chatbotId) {
                log.info("reset Channel by ChatbotId!!!!" + key + ":" + chatbotId);
                ManagedChannel channel = value.getChnnal();
//                if(!channel.isTerminated())
//                    channel.shutdown();
                shutdownChannel(channel);
                // Remove the expired object
                chatRequestMap.remove(key);
            }
        });
    }

    private void shutdownChannel(ManagedChannel channel) {
        channel.shutdown();
        try {
            log.info("shutdown channel - awaitTermination");
            if(!channel.awaitTermination(5, TimeUnit.SECONDS)) {
                log.info("shutdown channel - shutdownnow");
                channel.shutdownNow();
                if(!channel.awaitTermination(5, TimeUnit.SECONDS)) {
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
