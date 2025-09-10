package coop.stlma.tech.protocolsn.health.api;

import coop.stlma.tech.protocolsn.commonlib.async.AsyncTranslator;
import coop.stlma.tech.protocolsn.pluginlib.Empty;
import coop.stlma.tech.protocolsn.pluginlib.HealthGrpc;
import coop.stlma.tech.protocolsn.pluginlib.HealthResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.reactivestreams.Publisher;

import java.util.concurrent.Executor;

/**
 * Client for getting health information of a registered plugin
 *
 * @author John Meyerin
 */
public class HealthClient {

    private final HealthGrpc.HealthFutureStub healthStub;
    private final HealthGrpc.HealthBlockingStub healthBlockingStub;
    private final Executor executor;
    private final ManagedChannel channel;

    public HealthClient(ManagedChannel channel,
                        Executor executor) {
        this.channel = channel;
        this.healthStub = HealthGrpc.newFutureStub(channel);
        this.healthBlockingStub = HealthGrpc.newBlockingStub(channel);
        this.executor = executor;
    }

    /**
     * Create instance of health client
     * @param location  remote host of the grpc server
     * @param port      port of the grpc server
     * @param executor  executor on which the client runs
     * @return          Singleton instance of this client
     */
    public static HealthClient create(String location, int port, Executor executor) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(location, port)
                .usePlaintext()
                .build();
        return new HealthClient(channel, executor);
    }

    /**
     * Create instance of health client
     * @param channel   Channel to create the client on
     * @param executor  executor on which the client runs
     * @return          Singleton instance of this client
     */
    public static HealthClient create(ManagedChannel channel, Executor executor) {
        return new HealthClient(channel, executor);
    }

    /**
     * Close the channel
     */
    public void closeChannel() {
        this.channel.shutdown();
    }

    /**
     * Check the health of the plugin
     * @return  The health of the plugin
     */
    public Publisher<HealthResponse> healthCheck() {
        return AsyncTranslator.toPublisher(healthStub.healthCheck(Empty.getDefaultInstance()), executor);
    }

    /**
     * Check the health of the plugin, but blocking. Should only be used for testing
     * @return  The health of the plugin
     */
    public HealthResponse healthCheckBlocking() {
        return healthBlockingStub.healthCheck(Empty.getDefaultInstance());
    }
}
