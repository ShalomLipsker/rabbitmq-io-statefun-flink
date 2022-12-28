package org.apache.flink.statefun.sdk.rabbitmq.io.binders.ingress.v1;

import com.google.auto.service.AutoService;
import java.util.Map;
import org.apache.flink.statefun.extensions.ExtensionModule;

@AutoService(ExtensionModule.class)
public final class Module implements ExtensionModule {
  @Override
  public void configure(Map<String, String> globalConfigurations, Binder binder) {
    binder.bindExtension(RabbitmqIngressBinder.KIND_TYPE, RabbitmqIngressBinder.INSTANCE);
  }
}
