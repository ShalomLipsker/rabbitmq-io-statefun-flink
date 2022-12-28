package org.apache.flink.statefun.sdk.rabbitmq.io.spec;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.flink.statefun.sdk.rabbitmq.io.Constants;
import org.apache.flink.statefun.sdk.IngressType;
import org.apache.flink.statefun.sdk.io.IngressIdentifier;
import org.apache.flink.statefun.sdk.io.IngressSpec;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;
import org.apache.flink.util.Preconditions;

@JsonDeserialize(builder = RabbitmqIngressSpec.Builder.class)
public class RabbitmqIngressSpec implements IngressSpec<TypedValue> {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String forwardFunction;
    private String namespace;
    private String queuename;
    private String virtualHost;
    private Integer prefetchCount;
    private Integer requestedHeartbeat;
    private String messageKeyAsId;
    private String forwardFunctionId;

    @Override
    public IngressIdentifier<TypedValue> id() {
        return Constants.INGRESS_IDENTIFIER;
    }

    @Override
    public IngressType type() {
        return Constants.INGRESS_TYPE;
    }

    public RabbitmqIngressSpec(String host, Integer port, String virtualHost, String username, String password, String forwardFunction, String namespace, String queuename, Integer prefetchCount, Integer requestedHeartbeat, String messageKeyAsId, String forwardFunctionId) {
        Preconditions.checkNotNull(host, "host can not be null");
        Preconditions.checkNotNull(port, "port can not be null");
        Preconditions.checkNotNull(virtualHost, "virtualHost can not be null");
        Preconditions.checkNotNull(username, "username can not be null");
        Preconditions.checkNotNull(password, "password can not be null");

        this.host = host;
        this.port = port;
        this.virtualHost = virtualHost;
        this.username = username;
        this.password = password;
        this.forwardFunction = forwardFunction;
        this.namespace = namespace;
        this.queuename = queuename;
        this.prefetchCount = prefetchCount;
        this.requestedHeartbeat = requestedHeartbeat;
        this.messageKeyAsId = messageKeyAsId;
        this.forwardFunctionId = forwardFunctionId;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMessageKeyAsId() {
        return messageKeyAsId;
    }

    public String getForwardFunctionId() {
        return forwardFunctionId;
    }

    public String getQueuename() {
        return queuename;
    }

    public String getForwardFunction() {
        return forwardFunction;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public Integer getPrefetchCount() {
        return prefetchCount;
    }

    public Integer getRequestedHeartbeat() {
        return requestedHeartbeat;
    }

    @JsonPOJOBuilder
    public static final class Builder {

        private final String queuename;
        private String forwardFunction;
        private String namespace;
        private String host;
        private Integer port;
        private String virtualHost;
        private String username;
        private String password;
        private Integer prefetchCount;
        private Integer requestedHeartbeat;
        private String messageKeyAsId;
        private String forwardFunctionId;

        @JsonCreator
        private Builder(
                @JsonProperty("host") String host,
                @JsonProperty("port") Integer port,
                @JsonProperty("virtualHost") String virtualHost,
                @JsonProperty("username") String username,
                @JsonProperty("password") String password,
                @JsonProperty("forwardFunction") String forwardFunction,
                @JsonProperty("namespace") String namespace,
                @JsonProperty("queuename") String queuename,
                @JsonProperty("prefetchCount") Integer prefetchCount,
                @JsonProperty("requestedHeartbeat") Integer requestedHeartbeat,
                @JsonProperty("messageKeyAsId") String messageKeyAsId,
                @JsonProperty("forwardFunctionId") String forwardFunctionId
        ) {
            this.host = host;
            this.port = port;
            this.virtualHost = virtualHost;
            this.username = username;
            this.password = password;
            this.forwardFunction = forwardFunction;
            this.namespace = namespace;
            this.queuename = queuename;
            this.prefetchCount = prefetchCount;
            this.requestedHeartbeat = requestedHeartbeat;
            this.messageKeyAsId = messageKeyAsId;
            this.forwardFunctionId = forwardFunctionId;
        }

        public RabbitmqIngressSpec build() {
            return new RabbitmqIngressSpec(
                    host,
                    port,
                    virtualHost,
                    username,
                    password,
                    forwardFunction,
                    namespace,
                    queuename,
                    prefetchCount,
                    requestedHeartbeat,
                    messageKeyAsId,
                    forwardFunctionId
            );
        }
    }
}
