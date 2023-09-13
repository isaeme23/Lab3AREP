# Laboratorio AREP 4

Servidor construido para entregar páginas html e imagenes tipo PNG. También provee un framework IoC para la construcción de
aplicaciones web. 

## ¿Qué es necesario para usar este proyecto?
* Java
* Maven
* Git

## ¿Como puedo usar este proyecto?
Primero, debes descargar el contenido de este repositorio usando el siguiente comando en tu terminal local

    git clone https://github.com/isaeme23/AREMflix.git
Despues para compilar el contenido, usaremos la siguiente linea para compilar

    mvn package

Y por ultimo, para ver su funcionamiento usaremos

    mvn exec:java

La funcionalidad del codigo la podremos e a traves del puerto 35000. Despues de ejecutar el comando de maven para
correr el código, en nuestro navegador ingresaremos **localhost:35000** acompañado de alguna de las siguientes opciones:

    /file?id=index.html

Este anterior para ver como el codigo entrega páginas html o tambien:

    /image?id=imgpng.png

Para ver la entrega de una imagen en formato png.

## Documentacion

Para generar la documentacion de este proyecto solo debes ejecutar la siguiente linea en la terminal

    mvn javadoc:javadoc

Despues, en la ruta ./target/site/apidocs podras encontrar la documentacion de este proyecto.

## Autores 
Isabella Manrique :basecampy: :computer:

## Licencia
*GNU General Public License*

## Agradecimientos
- Profesor Luis Daniel Benavides Navarro