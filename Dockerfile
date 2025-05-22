FROM openjdk:17

# Crea un directorio para tu código
WORKDIR /app

# Copia el contenido del proyecto a la imagen
COPY . .

# Compila el código Java
RUN javac server/*.java

# Expón el puerto donde escuchará el servidor (ajústalo si usas otro)
EXPOSE 12345

# Comando que se ejecuta al iniciar el contenedor
CMD ["java", "server.ServerMain"]
