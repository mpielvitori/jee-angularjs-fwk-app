# App
### Build de aplicación
En la raiz del proyecto, ejecutar la siguiente sentencia:
```sh
$ mvn package
```
### Configuración de JBoss EAP
  -- Iniciar JBoss:
```sh
$ sh standalone.sh --admin-only
```
  -- Conectarse a la CLI pasando como parámetro el archivo de propiedades del Stage correspondiente:
```sh
$ sh jboss-cli.sh --connect --properties=<project.path>/App/jboss_scripts/<stage>.properties
```
  -- Con el siguiente comando se agregan variables de sistema reemplazando variables(${}) con las propiedades indicadas en el archivo <stage>.properties:
```sh
[standalone@localhost:9990 /] run-batch --file=<project.path>/App/jboss_scripts/app.jbosscli
```
### Ejecución de pruebas de integración

  -- Configurar la variable de entorno JBOSS_HOME apuntando a la ruta donde se encuentra el JBoss EAP
```sh
$ export JBOSS_HOME=/C/Appweb/jboss-eap-7.0
```
  -- Ejecutar maven con el comando verify
```sh
$ mvn verify
```
