docker rm -f demo_app
#docker run -d --name database -image mysql --hostname=db_host -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -e MYSQL_DATABASE=demoDB -p 3306:3306  --net demo database

docker run -d --name demo_app -i demo_app:latest --link demo_db -e DB_HOST=demo_db -p 8082:8082 --net demo demo_app



#docker run -d --name heroes --link mongo --link rabbitmq -e MONGO_HOST=mongo -e RABBIT_HOST=rabbitmq -p 8081:8081  --net marvel heroes