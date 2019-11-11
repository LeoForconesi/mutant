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
  <li>Published url: <b>http://ec2-3-132-199-4.us-east-2.compute.amazonaws.com:8081/mutant</b></li>
</ul> 
</div>

<h3>Notes</h3>
<p>When running this code locally, using the default application.yml included in this example. this app
will run an in-memory Mongo database for testing purposes. This means that if the application is stopped,
all data in that database will be lost.</p>
<p>If this is not a desired behaviour, please replace in application.yml the info for your local Mongo database,
or use a remote database uri instead.</p>


