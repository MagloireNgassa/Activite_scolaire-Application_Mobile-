<?php
try
{
    $url = "mysql:host=localhost;dbname=activites_scolaires";
    $dbuser = "root";
    $dbpw = "12345";

    
    //$utilcodeact = "course3";

    $utilcodeact = $_POST['codeact'];

    $dbcon = new PDO ($url,$dbuser,$dbpw);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    $cmd = $dbcon->prepare("select COUNT(login) from inscription_activite where codeact = ?;");
    $data = array ($utilcodeact);
    $cmd->execute($data);
    
    
    // pour un insert update et delete on fait plutot un executeUpdate qui return un boolean
    // -> correspond au point(.) pour faire appelle aux propriété d'une fonction 

    
    
    $line = $cmd->fetch(PDO::FETCH_ASSOC);

    $out = $line['COUNT(login)'];
       
}
catch(Exception $ex)
{
    $out = $ex->getMessage();
}
echo $out;

?>