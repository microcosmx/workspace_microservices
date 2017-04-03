

import docker
client = docker.from_env()
# client = docker.DockerClient(base_url='unix://var/run/docker.sock')
# client = docker.APIClient(base_url='unix://var/run/docker.sock',version='1.26')
print client.version()

print client.containers.run("alpine", ["echo", "hello", "world"])

# running container instance
print client.containers.list()

# container = client.containers.get('45e6d2de7c54')
# container.logs()
# container.stop()
# for container in client.containers.list():
# 	container.stop()

print client.images.list()
# client.images.pull('nginx')