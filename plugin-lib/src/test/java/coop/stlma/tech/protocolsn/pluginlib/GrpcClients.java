package coop.stlma.tech.protocolsn.pluginlib;

import coop.stlma.tech.protocolsn.nodemanager.RegistrationGrpc;
import io.grpc.ManagedChannel;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.grpc.annotation.GrpcChannel;
import io.micronaut.grpc.server.GrpcServerChannel;

@Factory
public class GrpcClients {

    @Bean
    HealthGrpc.HealthBlockingStub blockingStub(
            @GrpcChannel(GrpcServerChannel.NAME) ManagedChannel channel) {
        return HealthGrpc.newBlockingStub(channel);
    }

    @Bean
    HealthGrpc.HealthFutureStub futureStub(
            @GrpcChannel(GrpcServerChannel.NAME) ManagedChannel channel) {
        return HealthGrpc.newFutureStub(channel);
    }

    @Bean
    RegistrationGrpc.RegistrationBlockingStub registrationBlockingStub(
            @GrpcChannel(GrpcServerChannel.NAME) ManagedChannel channel) {
        return RegistrationGrpc.newBlockingStub(channel);
    }
}
