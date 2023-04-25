1. Run the RabbitMQ server (if not yet running): 
   - Open PS with admin privileges
   - Start RabbitMQ server: ```rabbitmq-server start```
   - Start RabbitMQ node: ```rabbitmqctl start_app```

2. Run the service registry microservice.

3. Run the gateway microservice.

4. Run the multiplication microservice.

5. Run the gamification microservice.

6. Start tomcat9 service:
    - Open PS with admin privileges
    - ```net start tomcat9``` (\bin installation folder must be in PATH env var)


&nbsp;


Eureka dashboard: localhost:8761

tomcat FE service: localhost:9090