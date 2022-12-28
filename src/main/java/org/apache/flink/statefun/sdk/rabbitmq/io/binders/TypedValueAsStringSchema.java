package org.apache.flink.statefun.sdk.rabbitmq.io.binders;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.statefun.sdk.rabbitmq.io.Constants;
import org.apache.flink.statefun.sdk.reqreply.generated.TypedValue;
import com.google.protobuf.ByteString;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import java.io.IOException;



public class TypedValueAsStringSchema implements DeserializationSchema<TypedValue>, SerializationSchema<TypedValue> {
    @Override
    public TypedValue deserialize(byte[] bytes) throws IOException {
        ByteString bs = ByteString.copyFrom(bytes);
        TypedValue typedValue =
                TypedValue
                        .newBuilder()
                        .setValue(bs).
                        setHasValue(true).
                        setTypename(Constants.DEFAULT_INGRESS_TYPE)
                        .build();
        return typedValue;
    }

    @Override
    public byte[] serialize(TypedValue s) {
        return s.getValue().toByteArray();
    }

    @Override
    public boolean isEndOfStream(TypedValue typedValue) {
        return typedValue == null;
    }

    @Override
    public TypeInformation<TypedValue> getProducedType() {
        return TypeInformation.of(TypedValue.class);
    }
}
