package org.apache.flink.statefun.sdk.rabbitmq.io.spec;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.flink.statefun.sdk.rabbitmq.io.Constants;
import org.apache.flink.statefun.sdk.EgressType;
import org.apache.flink.statefun.sdk.io.EgressIdentifier;
import org.apache.flink.statefun.sdk.io.EgressSpec;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;
import org.apache.flink.util.Preconditions;

@JsonDeserialize(builder = RabbitmqEgressSpec.Builder.class)
public class RabbitmqEgressSpec implements EgressSpec<TypedValue> {

  private final String host;
  private final Integer port;
  private final String virtualHost;
  private final String username;
  private final String password;
  private final Integer requestedHeartbeat;
  private final String exchange;
  private final String routingKey;
  private String queuename;


  @Override
  public EgressIdentifier<TypedValue> id() {
    return Constants.EGRESS_IDENTIFIER;
  }

  @Override
  public EgressType type() {
    return Constants.EGRESS_TYPE;
  }

  private RabbitmqEgressSpec(String host, Integer port, String virtualHost, String username, String password, Integer requestedHeartbeat, String exchange, String routingKey, String queuename) {
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
    this.requestedHeartbeat = requestedHeartbeat;
    this.exchange = exchange;
    this.routingKey = routingKey;
    this.queuename = queuename;
  }

  public String getHost() {
    return host;
  }

  public Integer getPort() {
    return port;
  }

  public String getVirtualHost() {
    return virtualHost;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Integer getRequestedHeartbeat() {
    return requestedHeartbeat;
  }

  public String getExchange() {
    return exchange;
  }

  public String getRoutingKey() {
    return routingKey;
  }

  public String getQueuename() {
    return queuename;
  }

  @JsonPOJOBuilder
  public static final class Builder {
    private final String host;
    private final Integer port;
    private final String virtualHost;
    private final String username;
    private final String password;
    private final Integer requestedHeartbeat;
    private final String exchange;
    private final String routingKey;
    private final String queuename;

    @JsonCreator
    private Builder(
            @JsonProperty("host") String host,
            @JsonProperty("port") Integer port,
            @JsonProperty("virtualHost") String virtualHost,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("requestedHeartbeat") Integer requestedHeartbeat,
            @JsonProperty("exchange") String exchange,
            @JsonProperty("routingKey") String routingKey,
            @JsonProperty("queuename") String queuename
    ) {
      this.host = host;
      this.port = port;
      this.virtualHost = virtualHost;
      this.username = username;
      this.password = password;
      this.requestedHeartbeat = requestedHeartbeat;
      this.exchange = exchange;
      this.routingKey = routingKey;
      this.queuename = queuename;
    }

    public RabbitmqEgressSpec build() {
      return new RabbitmqEgressSpec(
              host,
              port,
              virtualHost,
              username,
              password,
              requestedHeartbeat,
              exchange,
              routingKey,
              queuename
      );
    }
  }
}
