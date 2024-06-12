package ai.maum.mcl.skins.api.chat.handler;

//import ai.maum.mcl.proai.util.LogUtil;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import rag_service.rag_module.Rag;
import rag_service.rag_module.Rag.ChatRequest;
import rag_service.rag_module.RagServiceGrpc;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter
@Setter
public class ChatStreamObserverHandler implements StreamObserver<ChatRequest> {
    private final StreamObserver<ChatRequest> baseStreamObserver;
    private String question;
    private String memberId;

    public void setBaseStreamObserver() {

    }

    private void setQuestion(String question) {
    }

    public ChatStreamObserverHandler(StreamObserver<ChatRequest> baseStreamObserver) {
        this.baseStreamObserver = baseStreamObserver;
    }

    public ChatStreamObserverHandler(ManagedChannel channel, RagServiceGrpc.RagServiceStub ragServiceStub, String roomId ) {
        this.baseStreamObserver = ragServiceStub.chatHandler(new StreamObserver<Rag.ChatResponse>() {
            StringBuffer stringBuffer = new StringBuffer();
            @Override
            public void onNext(Rag.ChatResponse chatResponse) {
                log.debug("protoTest Received response: " + chatResponse.getMsg());
                stringBuffer.append(chatResponse.getMsg());
                Rag.Status status = chatResponse.getStatus();
                log.debug("protoTest Received response: " + (status != null ? status.getCodeValue() : ""));
                try {
                    if (status != null && status.getCode() == Rag.Status.Code.STREAM_END) {
                        log.debug("protoTest Stream completed!!!(onNext)");
//                        scheduler.shutdown();
//                        scheduler.shutdownNow();
                        String answer = String.valueOf(stringBuffer.toString());

                        stringBuffer.setLength(0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable t) {
                // 에러 처리
                log.error("proto error (1) : " + t.getMessage());
//                    t.printStackTrace();
                log.info("scheduler shutdown on Error");
                chatScheduleTask.stop();
//                scheduler.shutdownNow();
                stringBuffer.setLength(0);
                sink.error(t);
                log.error("proto error (2) : sink.complete()");
                sink.complete();
                log.error("proto error (3) : channel.shutdown()");
                channel.shutdownNow();
            }

            @Override
            public void onCompleted() {
                // 스트림이 완료되었을 때의 처리
                log.debug("proto Stream completed!!!");
                stringBuffer.setLength(0);
                log.info("scheduler shutdown on complete");
                chatScheduleTask.stop();
//                scheduler.shutdownNow();
                sink.complete();
                channel.shutdownNow();
                chatService.sendSocketMessage(roomId, chatbot.getName(), "chat Complete");
//                closeChannel(roomId);
            }
        });

//
    }
//
    @Override
    public void onNext(ChatRequest chatRequest) {
        baseStreamObserver.onNext(chatRequest);

    }

    @Override
    public void onError(Throwable throwable) {
        baseStreamObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        baseStreamObserver.onCompleted();
    }


}
