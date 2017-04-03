

import docker

# docker_cert_path="/Users/admin/.docker/machine/machines/default"
# tls_config = docker.tls.TLSConfig(
#   client_cert=(docker_cert_path + '/cert.pem', docker_cert_path + '/key.pem'),
#   ca_cert=docker_cert_path + '/ca.pem'
# )
# client = docker.DockerClient(base_url='tcp://192.168.99.100:2376', tls=tls_config)
# client = docker.DockerClient(base_url='tcp://10.141.212.21:2376')

# low level api
client = docker.APIClient(base_url='tcp://10.141.212.21:2376',version='1.24')


container_id = client.create_container(
    'busybox', 'ls', ports=[1111, 2222],
    host_config=client.create_host_config(port_bindings={
        1111: 4567,
        2222: None
    })
)

print client.containers()
print client.images()