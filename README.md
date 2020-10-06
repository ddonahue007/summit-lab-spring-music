ums-poc
============

build and package
```
> mvn clean package
```

create docker image
```
> docker build -t <user_name>/spring-trades .
```

To run docker image locally
```
docker run -p 8080:8080 --name spring-trades <user_name>/spring-trades:latest
```

push docker image
```
docker push <user_name>/spring-trades
```

