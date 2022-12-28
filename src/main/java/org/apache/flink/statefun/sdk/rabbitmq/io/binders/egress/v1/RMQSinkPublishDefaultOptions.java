package org.apache.flink.statefun.sdk.rabbitmq.io.binders.egress.v1;

import com.rabbitmq.client.AMQP;
import org.apache.flink.statefun.sdk.rabbitmq.io.spec.RabbitmqEgressSpec;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;
import org.apache.flink.streaming.connectors.rabbitmq.RMQSinkPublishOptions;

public class RMQSinkPublishDefaultOptions implements RMQSinkPublishOptions<TypedValue> {
    RabbitmqEgressSpec rabbitmqEgressSpec;

    public RMQSinkPublishDefaultOptions(RabbitmqEgressSpec _rabbitmqEgressSpec) {
        rabbitmqEgressSpec = _rabbitmqEgressSpec;
    }

    @Override
    public String computeRoutingKey(TypedValue o) {
        return rabbitmqEgressSpec.getRoutingKey();
    }

    @Override
    public AMQP.BasicProperties computeProperties(TypedValue o) {
        return null;
    }

    @Override
    public String computeExchange(TypedValue o) {
        return rabbitmqEgressSpec.getExchange();
    }
};
