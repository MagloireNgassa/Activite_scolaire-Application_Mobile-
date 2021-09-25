<?php
try
{
    $url = "mysql:host=localhost;dbname=activites_scolaires";
    $dbuser = "root";
    $dbpw = "12345";

    //$utillogin ="ngamag";// en utilisant ceci j'ai afficher le resultat directement sur la page web
    //$utilpassword = "magloire";

    $utillogin = $_POST['login'];
    $utilpassword = $_POST['password'];

    $dbcon = new PDO ($url,$dbuser,$dbpw);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    $cmd = $dbcon->prepare("select inscription.nom from inscription where login = ? and password = ?;");
    $data = array ($utillogin,$utilpassword);
    $cmd->execute($data);
    
    $out="";
    $line;
    
        $line = $cmd->fetchObject();

        if ($line != null)
        {
            $out ="true/"."$line->nom"."/";
        }
        else
        {
            $out = "false/false";
        }
        
           
        
}
catch(Exception $ex)
{
    $out = $ex->getMessage();
}
echo $out;
 
?>