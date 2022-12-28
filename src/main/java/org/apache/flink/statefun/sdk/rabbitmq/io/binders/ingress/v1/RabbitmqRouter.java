package org.apache.flink.statefun.sdk.rabbitmq.io.binders.ingress.v1;

import com.google.gson.reflect.TypeToken;
import org.apache.flink.statefun.sdk.FunctionType;
import org.apache.flink.statefun.sdk.io.Router;
import org.apache.flink.statefun.sdk.rabbitmq.io.spec.RabbitmqIngressSpec;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import java.util.Map;

public class RabbitmqRouter implements Router<TypedValue> {
    RabbitmqIngressSpec rabbitmqIngressSpec;

    public RabbitmqRouter(RabbitmqIngressSpec _rabbitmqIngressSpec) {
        rabbitmqIngressSpec = _rabbitmqIngressSpec;
    }

    @Override
    public void route(TypedValue message, org.apache.flink.statefun.sdk.io.Router.Downstream<TypedValue> ds) {
        String name = rabbitmqIngressSpec.getForwardFunction();
        String namespace = rabbitmqIngressSpec.getNamespace();
        String defaultId = "default";
        String id = null;

        if (rabbitmqIngressSpec.getForwardFunctionId() != null) {
            defaultId = rabbitmqIngressSpec.getForwardFunctionId();
        }

        if (rabbitmqIngressSpec.getMessageKeyAsId() != null) {
            id = getMessageValueByKey(message, rabbitmqIngressSpec.getMessageKeyAsId());
        }

        FunctionType ft = new FunctionType(namespace, name);

        ds.forward(ft, id != null ? id : defaultId, message);
    }

    private String getMessageValueByKey(TypedValue message, String key) {
        try {
            String s = message.getValue().toStringUtf8();
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();

            Map<String, Object> map = gson.fromJson(s, type);
            Object o = map.get(key);

            if (o instanceof String) {
                return (String) o;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("execption: " + e.getMessage()); // TODO: to logger
            return null;
        }
    }
}
