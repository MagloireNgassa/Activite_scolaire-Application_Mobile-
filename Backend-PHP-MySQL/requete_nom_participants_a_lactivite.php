<?php
try
{
    $url = "mysql:host=localhost;dbname=activites_scolaires";
    $dbuser = "root";
    $dbpw = "12345";

    //$utilcodeact = 'volet2';

   $utilcodeact = $_POST['codeact'];

    $dbcon = new PDO ($url,$dbuser,$dbpw);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    $cmd = $dbcon->prepare("select inscription.nom,inscription.prenom from inscription,activite,inscription_activite
                            where inscription.login = inscription_activite.login
                            and inscription_activite.codeact = activite.codeact
                            and inscription_activite.codeact = ?;");
    $data = array ($utilcodeact);
    $cmd->execute($data);
    
    // pour un insert update et delete on fait plutot un executeUpdate qui return un boolean
    // -> correspond au point(.) pour faire appelle aux propriété d'une fonction 

    $out="";
    $line;
    while($line = $cmd->fetchObject())
    {
        $out .= "$line->nom"." "."$line->prenom "."/";
    }
}
catch(Exception $ex)
{
    $out = $ex->getMessage();
}

echo $out;
?>