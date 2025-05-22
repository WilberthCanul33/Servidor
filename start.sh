#!/bin/bash
# Compila todos los archivos .java en la carpeta server
javac server/*.java

# Ejecuta el servidor desde la clase ServerMain
java server.ServerMain
