# Usa la imagen oficial de MySQL versión 8
FROM mariadb:latest

# Variable de entorno para deshabilitar contraseña de root
# Esto permite conexiones sin contraseña
ENV MYSQL_ALLOW_EMPTY_PASSWORD=true

# Variable de entorno para crear una base de datos por defecto
# Esto crea automáticamente la base de datos 'andaluciaskills' al iniciar el contenedor
ENV MYSQL_DATABASE=andaluciaskills

# Copia un script SQL de inicialización 
# Los scripts en /docker-entrypoint-initdb.d/ se ejecutan automáticamente 
# cuando se inicializa el contenedor por primera vez
COPY init.sql /docker-entrypoint-initdb.d/