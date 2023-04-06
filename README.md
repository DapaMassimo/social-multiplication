1. Start tomcat9 service:
    - Open PS with admin privileges
    - ```net start tomcat9``` (\bin installation folder must be in PATH env var)

2. Start RabbitMQ:
    - Open PS with admin privileges
    - Start RabbitMQ server: ```rabbitmq-server start```
    - Start RabbitMQ node: ```rabbitmqctl stop_app```

3. Start multiplication-ms
4. Start gamification-ms