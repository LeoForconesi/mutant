<h1> Mutant detector</h1>
<hr>

[![Build Status](https://travis-ci.com/LeoForconesi/mutant.svg?branch=master)](https://travis-ci.com/LeoForconesi/mutant/builds) [![Coverage Status](https://coveralls.io/repos/github/LeoForconesi/mutant/badge.svg?branch=master)](https://coveralls.io/github/LeoForconesi/mutant?branch=master)

<hr>
<div>
  <p>
    Project to detect a human or mutant DNA.
  </p>
</div>
<div>

<ul>
  <li>run app locally using: <p>mvn spring-boot:run</p></li>
  <li>Published urls: 
    <ul>
      <li><b>[Post] http://ec2-3-132-199-4.us-east-2.compute.amazonaws.com:8081/mutant</b></li>
      <li><b>[Get] http://ec2-3-132-199-4.us-east-2.compute.amazonaws.com:8081/stats</b></li>        
    </ul>   
  </li>
</ul> 
</div>

<h3>Notes</h3>
<p>When running this code locally, using the default application.yml included in this example. this app
will run an in-memory Mongo database for testing purposes. This means that if the application is stopped,
all data in that database will be lost.</p>
<p>If this is not a desired behaviour, please replace in application.yml the info for your local Mongo database,
or use a remote database uri instead.</p>

<h3>Project Description</h3>
<p>Magneto quiere reclutar la mayor cantidad de mutantes para poder luchar
contra los X-Mens.
Te ha contratado a ti para que desarrolles un proyecto que detecte si un
humano es mutante basándose en su secuencia de ADN.
Para eso te ha pedido crear un programa con un método o función con la siguiente firma:
boolean isMutant(String[] dna);
En donde recibirás como parámetro un array de Strings que representan cada fila de una tabla
de (NxN) con la secuencia del ADN. Las letras de los Strings solo pueden ser: (A,T,C,G), las
cuales representa cada base nitrogenada del ADN.
</p>

<p>Sabrás si un humano es mutante, si encuentras ​ más de una secuencia de cuatro letras
iguales​ , de forma oblicua, horizontal o vertical.</p>

<p>Ejemplo (Caso mutante):
String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
En este caso el llamado a la función isMutant(dna) devuelve “true”.
Desarrolla el algoritmo de la manera más eficiente posible</p>
<hr>
<h3>Desafios</h3>
<b>Nivel 1:</b>
<p>Programa (en cualquier lenguaje de programación) que cumpla con el método pedido por
Magneto.</p>
<b>Nivel 2:</b>
<p>Crear una API REST, hostear esa API en un cloud computing libre (Google App Engine,
Amazon AWS, etc), crear el servicio “/mutant/” en donde se pueda detectar si un humano es
mutante enviando la secuencia de ADN mediante un HTTP POST con un Json el cual tenga el
siguiente formato:</p>
<p>POST → /mutant/
{
“dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}</p>
<p>En caso de verificar un mutante, debería devolver un HTTP 200-OK, en caso contrario un
403-Forbidden</p>
<b>Nivel 3:</b>
<p>Anexar una base de datos, la cual guarde los ADN’s verificados con la API.</p>
<p>Solo 1 registro por ADN.</p>
<p>Exponer un servicio extra “/stats” que devuelva un Json con las estadísticas de las
verificaciones de ADN: {“count_mutant_dna”: 40, “count_human_dna”: 100: “ratio”: 0.4}</p>
<p>Tener en cuenta que la API puede recibir fluctuaciones agresivas de tráfico (Entre 100 y 1
millón de peticiones por segundo).</p>
<p>Test-Automáticos, Code coverage > 80%, Diagrama de Secuencia / Arquitectura del sistema.</p>
