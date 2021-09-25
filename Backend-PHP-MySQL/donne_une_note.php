<?php
try
{
    $url = "mysql:host=localhost;dbname=activites_scolaires";
    $dbuser = "root";
    $dbpw = "12345";

     
    /*$utilnoteact = 60;
    $utillogin = "ngamag";
    $utilcodeact = "volet2";*/

    $utilnoteact = $_POST['noteact'];
    $utillogin = $_POST['login'];
    $utilcodeact = $_POST['codeact'];
    

    $dbcon = new PDO ($url,$dbuser,$dbpw);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    $cmd = $dbcon->prepare("update inscription_activite set noteact = ? where codeact = ? and login = ?;");
    $data = array ($utilnoteact,$utilcodeact,$utillogin);
    $cmd->execute($data);

    $out="Ajout de la note fait avec success";
    

    // -> correspond au point(.) pour faire appelle aux propriété d'une fonction 

  
}
catch(Exception $ex)
{
    $out = $ex->getMessage();
}
echo $out;
?>