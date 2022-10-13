<?php

function notify($to,$data){

    $api_key="AAAAtQJI3Zo:APA91bGDG_tj9L9Ex1i3mc6YNOBvV3p3fX4rLxtaBUib3HXJhPuQvkWbMU4-WqUegB1VHvXr5JdLR8urlk1EGHO6xvNZpd669gdGmjMtLO8i6jVBO9KzmrcFeMk1-vQ0H69qMWoPyv30";
    $url="https://fcm.googleapis.com/fcm/send";
    $fields=json_encode(array('to'=>$to,'notification'=>$data));

    // Generated by curl-to-PHP: http://incarnate.github.io/curl-to-php/
    $ch = curl_init();

    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_POSTFIELDS, ($fields));

    $headers = array();
    $headers[] = 'Authorization: key ='.$api_key;
    $headers[] = 'Content-Type: application/json';
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);

    $result = curl_exec($ch);
    if (curl_errno($ch)) {
        echo 'Error:' . curl_error($ch);
    }
    curl_close($ch);
}

$to="cc4u9-6NRhWSgpxzl2IPP-:APA91bEHuALVOa5soBt73ONIwtmOh0bEGiW7oMqze71rFDum8mlqBxDalrTGGgL7uM-WLQvDW7wbeTsnbtyaxtFTK-XeUfSDUSzyvaqNQIg9_PWHWNzr6gS3Gx8xy24k3mJ9xV7KtP6C";
$data=array(
    'title'=> htmlspecialchars($_GET['title']),
    'body'=> htmlspecialchars($_GET['body'])
);

notify($to,$data);
echo "Notification Sent";

?>