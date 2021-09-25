<?php
try
{
    $url = "mysql:host=localhost;dbname=activites_scolaires";
    $dbuser = "root";
    $dbpw = "12345";

 

    $dbcon = new PDO ($url,$dbuser,$dbpw);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    $cmd = $dbcon->prepare("select * from activite ;");
    $cmd->execute();
    
    // pour un insert update et delete on fait plutot un executeUpdate qui return un boolean
    // -> correspond au point(.) pour faire appelle aux propriété d'une fonction 

    $out="";
    $line;
    while($line = $cmd->fetchObject())
    {
        $out .= "$line->codeact"."/"."$line->nomact"."/"."$line->descriptionact"."@";
    }
}
catch(Exception $ex)
{
    $out = $ex->getMessage();
}
echo $out;
?>