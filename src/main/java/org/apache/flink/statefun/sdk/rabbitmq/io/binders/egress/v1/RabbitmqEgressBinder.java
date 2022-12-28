package org.apache.flink.statefun.sdk.rabbitmq.io.binders.egress.v1;

import com.rabbitmq.client.AMQP;
import org.apache.flink.statefun.extensions.ComponentBinder;
import org.apache.flink.statefun.extensions.ComponentJsonObject;
import org.apache.flink.statefun.flink.io.datastream.SinkFunctionSpec;
import org.apache.flink.statefun.sdk.io.EgressSpec;
import org.apache.flink.statefun.sdk.rabbitmq.io.binders.TypedValueAsStringSchema;
import org.apache.flink.statefun.sdk.rabbitmq.io.binders.Utils;
import org.apache.flink.statefun.sdk.rabbitmq.io.spec.RabbitmqEgressSpec;
import org.apache.flink.statefun.sdk.TypeName;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;
import org.apache.flink.statefun.sdk.spi.StatefulFunctionModule;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSink;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSinkPublishOptions;
import org.apache.flink.streaming.connectors.rabbitmq.common.RMQConnectionConfig;

public class RabbitmqEgressBinder implements ComponentBinder {

    static final RabbitmqEgressBinder INSTANCE = new RabbitmqEgressBinder();
    static final TypeName KIND_TYPE = TypeName.parseFrom("io.statefun.rabbitmq.v1/egress");

    @Override
    public void bind(ComponentJsonObject component, StatefulFunctionModule.Binder remoteModuleBinder) {
        Utils.validateComponent(component, KIND_TYPE);
        final RabbitmqEgressSpec rabbitmqEgressSpec = Utils.parseJson(component.specJsonNode(), RabbitmqEgressSpec.class);

        final RMQConnectionConfig connectionConfig = new RMQConnectionConfig.Builder()
                .setHost(rabbitmqEgressSpec.getHost())
                .setPort(rabbitmqEgressSpec.getPort())
                .setVirtualHost(rabbitmqEgressSpec.getVirtualHost())
                .setUserName(rabbitmqEgressSpec.getUsername())
                .setPassword(rabbitmqEgressSpec.getPassword())
                .setRequestedHeartbeat(rabbitmqEgressSpec.getRequestedHeartbeat())
                .build();

        EgressSpec<TypedValue> spec = new SinkFunctionSpec<>(rabbitmqEgressSpec.id(), new RMQSink<>(
                connectionConfig,
                rabbitmqEgressSpec.getQueuename(),
                new TypedValueAsStringSchema()
                /* for future feature: route by exchange and routing key, remove the second argument queuename and use this class:
                new RMQSinkPublishDefaultOptions(rabbitmqEgressSpec)
                last time this was not work because a serialization issue */
        ));

        remoteModuleBinder.bindEgress(spec);
    }
}
