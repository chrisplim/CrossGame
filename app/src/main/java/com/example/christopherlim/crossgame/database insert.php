<?php
$con=mysqli_connect("crossgamedb.c3kiqal1ftyd.us-west-2.rds.amazonaws.com ","ssscustodio","crossgame","Crossgame");
$sql="INSERT INTO Users (PersonID, LastName, FirstName, Age, Gender, LookingFor, PhoneNumber, TagLine) VALUES ('1', 'Custodio', 'Sean', '22', 'male', 'female', '9099965835', 'I'm looking for women with 3 eyes.  Grace's sister I'm looking at you' )";
if (mysqli_query($con,$sql))
{
   echo "Values have been inserted successfully";
}
?>