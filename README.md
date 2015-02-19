#WebSocket application to notify notification on Android

## Server Run
Simple server is written in Play Framework.
```sh
$ ./activator run
```

Server run 9000 ports on your localhost. If you want to run another port :


```sh
./activator  -Dhttp.port=8080 run
```
## Android Client
Simple android client. Compile before application, don't forget replace websocket link start with "ws://".
