# Usa la imagen oficial de Node.js versión 18 como base para la etapa de construcción
# El alias 'AS build' permite referenciar esta etapa en construcciones de varias etapas
FROM node:18 AS build

# Establece el directorio de trabajo dentro del contenedor como /app
# Todos los comandos siguientes se ejecutarán en este directorio
WORKDIR /app

# Copia solo los archivos de configuración de paquetes 
# Esto optimiza la caché de capas de Docker
COPY package*.json ./

# Instala las dependencias del proyecto
# Se ejecuta antes de copiar el resto de los archivos para aprovechar la caché de Docker
RUN npm install

# Copia todo el código fuente al directorio de trabajo
COPY . .

# Construye la aplicación y lista el contenido del directorio de distribución
# El comando 'ls -la' es útil para verificar que los archivos se hayan generado correctamente
RUN npm run build && ls -la dist/


# Segunda etapa: usa Nginx Alpine como imagen base
# Esta etapa es más ligera y solo contiene lo necesario para servir la aplicación
FROM nginx:alpine

# Copia los archivos generados en la etapa de build 
# desde el directorio /app/dist/ al directorio de html de Nginx
COPY --from=build /app/dist/* /usr/share/nginx/html/

# Documenta que el contenedor escucha en el puerto 80
EXPOSE 80

# Comando para iniciar Nginx en primer plano
# 'daemon off' evita que Nginx se ejecute en segundo plano
CMD ["nginx", "-g", "daemon off;"]