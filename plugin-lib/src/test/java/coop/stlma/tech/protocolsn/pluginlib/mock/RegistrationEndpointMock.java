package coop.stlma.tech.protocolsn.pluginlib.mock;

import coop.stlma.tech.protocolsn.nodemanager.PluginRegistration;
import coop.stlma.tech.protocolsn.nodemanager.RegistrationGrpc;
import io.grpc.stub.StreamObserver;
import io.micronaut.grpc.annotation.GrpcService;

@GrpcService
public class RegistrationEndpointMock extends RegistrationGrpc.RegistrationImplBase {

    public void registerPlugin(PluginRegistration request, StreamObserver<PluginRegistration> responseObserver) {
        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }
}
