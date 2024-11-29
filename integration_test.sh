#!/bin/bash
echo "Intergation test........"

aws --version

Data=$(aws ec2 describe-instances)
echo "Data - "$Data
PublichIPAddress=$(aws ec2 describe-instances | jq -r ' .Reservations[].Instances[] | select(.Tags[].Value == "dev-deploy") | .PublicIpAddress')
echo "PublichIPAddress - "$PublichIPAddress

if [[ "$PublichIPAddress" != '' ]]; then
    http_code=$(curl -s -o /dev/null -w "%{http_code}" http://$PublichIPAddress:8080/user/all)
        echo "http_code - "$http_code

    if [[ "$http_code" -eq 200 ]]; 
        then
            echo "HTTP Status Code is 200 Tests Passed"
        else
            echo "Intergation test failed"
            exit 1;
    fi;

else
        echo "Could not fetch a token/PublichIPAddress; Check/Debug line 8"
        exit 1;
fi;