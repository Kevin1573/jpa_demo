# jpa_demo

#### 介绍
这是一个jpa demo项目，并添加karate test测试

# feature
1. 添加karate test测试
2. 打印请求参数和请求地址（拦截器）
3. 修改token style jwt -> bearer
4. 添加请求的记录，根据请求记录还原现场
5. 修改日志的路径到{project_root}/logs
6. 测试一个项目可以调用自身的api接口，使用RestTemplate(**已验证**)

```bash
docker run --hostname=548dbca3f0b4 --env=POSTGRES_PASSWORD=kevin1234 --env=POSTGRES_DB=zero --env=POSTGRES_USER=zero --env=PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/postgresql/15/bin --env=GOSU_VERSION=1.17 --env=LANG=en_US.utf8 --env=PG_MAJOR=15 --env=PG_VERSION=15.7-1.pgdg120+1 --env=PGDATA=/var/lib/postgresql/data --volume=/root/docker/pgdata:/var/lib/postgresql/data --volume=/var/lib/postgresql/data --network=zero -p 5432:5432 --restart=no --runtime=runc -d postgres:15.7
```