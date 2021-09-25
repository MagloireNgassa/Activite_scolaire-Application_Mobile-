<?php
try
{
    $url = "mysql:host=localhost;dbname=activites_scolaires";
    $dbuser = "root";
    $dbpw = "12345";

    $utilcodeact = $_POST['codeact'];
    $utilnomact = $_POST['nomact'];
    $utildescription = $_POST['descriptionact'];
     

    //$utilcodeact = "tchndo";
    //$utilnomact = "Tchuikwa";
    //$utildescription = "Ndosseu............";

    $dbcon = new PDO ($url,$dbuser,$dbpw);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    $cmd = $dbcon->prepare("insert into activite (codeact,nomact,descriptionact) values ( ?, ?, ?);");
    $data = array ($utilcodeact,$utilnomact,$utildescription);
    $cmd->execute($data);

    $out = "Ajout de l'activite reussi";
      
    // pour un insert update et delete on fait plutot un executeUpdate qui return un boolean
    // -> correspond au point(.) pour faire appelle aux propriété d'une fonction 

}
catch(Exception $ex)
{
    $out = $ex->getMessage();
}
echo $out;
?>