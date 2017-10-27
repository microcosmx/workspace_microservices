

build:
curl -L https://git.io/getLatestIstio | sh -
export DOCKER_GATEWAY=172.28.0.1
cd /opt/istio/istio...
docker-compose -f install/consul/istio.yaml up -d
docker ps -a
docker-compose -f install/consul/istio.yaml down

istioctl context-create --api-server http://localhost:8080