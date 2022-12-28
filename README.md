*example config*

```
kind: io.statefun.endpoints.v2/http
spec:
  functions: example/*
  urlPathTemplate: http://functions:8000/statefun
#  transport:
#    type: io.statefun.transports.v1/async
---
kind: io.statefun.rabbitmq.v1/ingress
spec: 
  host: whale.rmq.cloudamqp.com
  port: 5672
  virtualHost: bbfxtbex
  username: bbfxtbex
  password: 8xCn
  forwardFunction: person
  namespace: example
  queuename: try1
  prefetchCount: 1
  requestedHeartbeat: 60
  forwardFunctionId: deep_wisdom # for static forwardFunction id
  messageKeyAsId: name # for dynamic forwardFunction id
---
kind: io.statefun.rabbitmq.v1/egress
spec:
  host: whale.rmq.cloudamqp.com
  port: 5672
  virtualHost: bbfxtbex
  username: bbfxtbex
  password: 8xCn
  queuename: rk
  requestedHeartbeat: 60
 ```
