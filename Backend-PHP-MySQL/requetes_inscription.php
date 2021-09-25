<?php
try
{
    $url = "mysql:host=localhost;dbname=activites_scolaires";
    $dbuser = "root";
    $dbpw = "12345";

    
    $utilnom = $_POST['nom'];
    $utilprenom = $_POST['prenom'];
    $utiltelephone = $_POST['telephone'];
    $utillogin = $_POST['login'];
    $utilpassword = $_POST['password'];

    /*$utillogin = "tchndo";
    $utilnom = "Tchuikwa";
    $utilprenom = "Ndosseu";
    $utiltelephone = "45367890345";
    $utilpassword = "ndosseu";*/

    $dbcon = new PDO ($url,$dbuser,$dbpw);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
    $cmd = $dbcon->prepare("insert into inscription (login,nom,prenom,telephone,password) values ( ?, ?, ?, ?, ?);");
    $data = array ($utillogin,$utilnom,$utilprenom,$utiltelephone,$utilpassword);
    $cmd->execute($data);

    $out = "Inscription reussi";

    // pour un insert update et delete on fait plutot un executeUpdate qui return un boolean
    // -> correspond au point(.) pour faire appelle aux propriété d'une fonction 

}
catch(Exception $ex)
{
    $out = $ex->getMessage();
}
echo $out;
?>