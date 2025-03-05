# Usa la imagen oficial de Maven con OpenJDK 17 para la etapa de construcción
# El alias 'AS build' permite usar esta etapa en una construcción de múltiples etapas
FROM maven:3.8.4-openjdk-17 AS build

# Establece el directorio de trabajo dentro del contenedor como /app
# Todos los comandos siguientes se ejecutarán en este directorio
WORKDIR /app

# Copia todo el contenido del proyecto al directorio de trabajo
# Esto incluye el código fuente, pom.xml, y otros archivos necesarios
COPY . .

# Ejecuta Maven para limpiar el proyecto, compilar y empaquetar
# La bandera -DskipTests omite la ejecución de pruebas durante la compilación
# Esto acelera el proceso de construcción
RUN mvn clean package -DskipTests

# Segunda etapa: usa una imagen ligera de OpenJDK 17
# Esta imagen es más pequeña y solo contiene lo necesario para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Establece nuevamente el directorio de trabajo como /app
WORKDIR /app

# Copia el archivo JAR generado en la etapa de construcción
# El patrón *.jar copia cualquier archivo JAR generado en el directorio target
# Lo renombra como app.jar para simplicidad
COPY --from=build /app/target/*.jar app.jar

# Documenta que el contenedor escucha en el puerto 9005
# Esto es principalmente informativo y no abre realmente el puerto
EXPOSE 9005

# Especifica el punto de entrada para ejecutar la aplicación
# Cuando se inicie el contenedor, ejecutará el JAR con java
ENTRYPOINT ["java","-jar","app.jar"]