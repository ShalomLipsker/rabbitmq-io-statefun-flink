package org.apache.flink.statefun.sdk.rabbitmq.io.binders.ingress.v1;

import org.apache.flink.statefun.extensions.ComponentBinder;
import org.apache.flink.statefun.extensions.ComponentJsonObject;
import org.apache.flink.statefun.flink.io.datastream.SourceFunctionSpec;
import org.apache.flink.statefun.sdk.io.IngressSpec;
import org.apache.flink.statefun.sdk.rabbitmq.io.binders.TypedValueAsStringSchema;
import org.apache.flink.statefun.sdk.rabbitmq.io.binders.Utils;
import org.apache.flink.statefun.sdk.rabbitmq.io.spec.RabbitmqIngressSpec;
import org.apache.flink.statefun.sdk.TypeName;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;
import org.apache.flink.statefun.sdk.spi.StatefulFunctionModule;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSource;
import org.apache.flink.streaming.connectors.rabbitmq.common.RMQConnectionConfig;

public class RabbitmqIngressBinder implements ComponentBinder {

    static final RabbitmqIngressBinder INSTANCE = new RabbitmqIngressBinder();
    static final TypeName KIND_TYPE = TypeName.parseFrom("io.statefun.rabbitmq.v1/ingress");

    @Override
    public void bind(ComponentJsonObject component, StatefulFunctionModule.Binder remoteModuleBinder) {
        Utils.validateComponent(component, KIND_TYPE);
        final RabbitmqIngressSpec rabbitmqIngressSpec = Utils.parseJson(component.specJsonNode(), RabbitmqIngressSpec.class);

        final RMQConnectionConfig connectionConfig = new RMQConnectionConfig.Builder()
                .setHost(rabbitmqIngressSpec.getHost())
                .setPort(rabbitmqIngressSpec.getPort())
                .setVirtualHost(rabbitmqIngressSpec.getVirtualHost())
                .setUserName(rabbitmqIngressSpec.getUsername())
                .setPassword(rabbitmqIngressSpec.getPassword())
                .setPrefetchCount(rabbitmqIngressSpec.getPrefetchCount())
                .setRequestedHeartbeat(rabbitmqIngressSpec.getRequestedHeartbeat())
                .build();

        IngressSpec<TypedValue> spec = new SourceFunctionSpec<>(rabbitmqIngressSpec.id(), new RMQSource<>(
                connectionConfig,
                rabbitmqIngressSpec.getQueuename(),
                true,
                new TypedValueAsStringSchema()
        ));

        remoteModuleBinder.bindIngress(spec);
        remoteModuleBinder.bindIngressRouter(rabbitmqIngressSpec.id(), new RabbitmqRouter(rabbitmqIngressSpec));
    }
}
