<?php
try
{
    $url = "mysql:host=localhost;dbname=activites_scolaires";
    $dbuser = "root";
    $dbpw = "12345";

    $utilcodeact = $_POST['codeact'];
    $utillogin = $_POST['login'];
    $utilnoteact = 0;

    //$utilcodeact = "course3";
    //$utillogin = "ngamag";
    
    

    $dbcon = new PDO ($url,$dbuser,$dbpw);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    $cmd = $dbcon->prepare("insert into inscription_activite (codeact,login,noteact) values ( ?, ?, ?);");
    $data = array ($utilcodeact,$utillogin,$utilnoteact);
    $cmd->execute($data);

    $out = "inscription reussi";

    // pour un insert update et delete on fait plutot un executeUpdate qui return un boolean
    // -> correspond au point(.) pour faire appelle aux propriété d'une fonction 

}
catch(Exception $ex)
{
    $out = $ex->getMessage();
}
echo $out;
?>