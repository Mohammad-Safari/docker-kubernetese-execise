# Orchestration

## dependencies

The Orchestration is based on `kindest/node:1.25.3@sha256:f52781bc` which is an image including all k8s modules, and it's easy to manage them inside one machine by ``kind`` cli.

> ⚠ Notice that this tool is just used for _development purposes_, and it's **not optimized** for _production_ environment.

## setup

### deployment configuration

At first, there's a minimum of configuration needed to run pod[s] of our application inside the node.

```yaml
apiVersion: apps/v1
kind: Deployment
spec:
  selector:
      matchLabels:
        app: httpService
  template:
      spec:
        containers:
          - name: http-service
            image: e01-spring:0.0.1
```

This deployment manifest will set up and run 1 replica of our pod containing our application in container, with a selector, for future references to pods that this deployment configuration set up.

Some other configuration which might be needed to provide in manifest(specifically container specs mapped to pod with selector metadata) are:
* `port` for port mapping of our app container out of pod
* `livenessProbe` and `readinessProbe` as our pod health check service
* `resources.limits` for controlling resources usage of our container apps
* `lifecycle.preStop` for handling pod's graceful shutdown
* `imagePullPolicy` as may locally-built image is that might not be available in central registry, it's better to set it to `Never` or `IfNotPresent` so that pod doesn't encounter image pulling error.

By `replicas`, its possible to load balance between pods of the same deployment(referenced by its selector).
Note `restartPolicy` can be set for pods for any case of error happens to pod.
Finally `terminationGracePeriodSeconds` sets time out for pod graceful shutdown, after it, pod will forcefully be killed.

### service configuration

As the second step, the layer which manages network access to pods, must be configured for user/services in the need of communicating with them.

```yaml
apiVersion: v1
kind: Service
spec:
  type: NodePort
  selector:
    app: httpService
  ports:
    - name : http
      port: 8080
      nodePort: 32180
```

Here, a referred pod, is exposed through relevant nodes by `nodePort` type Service. Also, it's possible to set network protocol(`TCP` is used as default protocol).

Note that `targetPort` of service is the port of pod at which service guide the traffic. If not provided, it is the same as service port.

### cluster configuration

At last, a configured cluster should be created, that has one control-plane node and exposes the inside-cluster service node port to its host.

```yaml
kind: Cluster
apiVersion: kind.x-k8s.io/v1alpha4
name: e02
nodes:
- role: control-plane
  extraPortMappings:
    - containerPort: 32180
      hostPort: 8080
      protocol: TCP
```

> ⚠ this is a kind-specific manifest

### cluster startup

In four steps we can start the cluster up with provided manifests:

```bash
# setup cluster
kind create cluster --config src/main/kubernetese/clusterConfig/e02-kind-cluster.yaml
# load application image from local docker image into cluster image registry
kind load docker-image e02-spring:0.0.1 --name e02
# pod configuration manifests
kubectl apply -f src/main/kubernetese/manifest/deployment.yaml
kubectl apply -f src/main/kubernetese/manifest/service.yaml
# finally pod logs
kubectl logs <pod_name>
```
we can find out pod name and ip with `kubectls`