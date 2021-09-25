<?php
try
{
    $url = "mysql:host=localhost;dbname=activites_scolaires";
    $dbuser = "root";
    $dbpw = "12345";

     
    /*$utillogin = "ngamag";
    $utilnom = "yankou";
    $utilprenom = "";
    $utiltelephone = "45367890345";
    $utilpassword = "ndosseu";*/

    $utillogin = $_POST['login'];
    $utilnom = $_POST['nom'];
    $utilprenom = $_POST['prenom'];
    $utiltelephone = $_POST['telephone'];
    $utilpassword = $_POST['password'];

    $dbcon = new PDO ($url,$dbuser,$dbpw);
    $dbcon->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);

    if($utilnom !=null)
    {
        $cmd = $dbcon->prepare("update inscription set nom = ? where login = ? ;");
        $data = array ($utilnom,$utillogin);
        $cmd->execute($data);
        $out="Mise a jour  reussi";
    }
    if($utilprenom !=null)
    {
        $cmd = $dbcon->prepare("update inscription set prenom = ? where login = ?;");
        $data = array ($utilprenom,$utillogin);
        $cmd->execute($data);
        $out="Mise a jour  reussi";
    }
    if($utiltelephone !=null)
    {
        $cmd = $dbcon->prepare("update inscription set telephone = ? where login = ?;");
        $data = array ($utiltelephone,$utillogin);
        $cmd->execute($data);
        $out="Mise a jour  reussi";
    }
    if($utilpassword !=null)
    {
        $cmd = $dbcon->prepare("update inscription set password = ? where login = ?;");
        $data = array ($utilpassword,$utillogin);
        $cmd->execute($data);
        $out="Mise a jour reussi";   
    }
    

   
    
    
    // pour un insert update et delete on fait plutot un executeUpdate qui return un boolean
    // -> correspond au point(.) pour faire appelle aux propriété d'une fonction 

  
}
catch(Exception $ex)
{
    $out = $ex->getMessage();
}
echo $out;
?>