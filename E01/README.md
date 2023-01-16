# Instructions

## dependencies

The Http Service is based on ```dotnet-sdk 7.0```, ```dotnet-runtime 7.0``` and ```aspnet-runtime 7.0```

## development

```bash
dotnet restore
dotnet run
```

## production

```bash
dotnet restore
dotnet publish -c Release
dotnet /bin/Release/publish/E01.dll
```

## container

```bash
docker build -t e01:0.1 -f Dockerfile .
docker run -p <port>:<port> e01:0.1
```

## http port setting

* default on 8080
* ```PROGRAM_PORT``` variable
* ```--program-port``` command line option

## logs

Application logs are formatted in Json and written in standard output. Besides, http logging of the service is powered by w3c logging format, and stored in ``/var/log/e01`` directory.
