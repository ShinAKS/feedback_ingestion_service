#!/bin/bash

# Function to generate a random date between 25 February and 29 February
random_date() {
    start_date="2024-02-25"
    end_date="2024-02-29"
    days_diff=$((RANDOM % 5))  # Random number between 0 and 4
    random_seconds=$((RANDOM % 60))
    random_minutes=$((RANDOM % 60))
    random_hours=$((RANDOM % 24))
    echo $(date -d "$start_date + $days_diff days $random_hours hours $random_minutes minutes $random_seconds seconds" +%Y-%m-%dT%H:%M:%S)
}

# Array of possible postedBy values
postedBy_values=("Alice" "Bob" "Charlie" "David")

# Array of possible deviceId values
deviceId_values=("APPLE" "XIAOMI" "SAMSUNG" "GOOGLE")

# Array of possible client values
client_values=("client123" "client456" "client789")

for ((i=1; i<=10; i++)); do
    # Generate random values
    random_postedBy=${postedBy_values[$RANDOM % ${#postedBy_values[@]} ]}
    random_ticket_id=$((RANDOM % 1000000))
    random_subject="Subject $RANDOM"
    random_body="Body $RANDOM"
    random_createdAt=$(random_date)
    random_deviceId=${deviceId_values[$RANDOM % ${#deviceId_values[@]} ]}
    random_client=${client_values[$RANDOM % ${#client_values[@]} ]}

    # Construct JSON data
    json_data=$(cat <<EOF
    {
        "conversationId": "67801234",
        "postedBy" : "$random_postedBy",
        "postType" : "ZENDESK",
        "content":"{\"ticket_id\":\"$random_ticket_id\",\"ticket\":{\"subject\":\"$random_subject\",\"comment\":{\"body\":\"$random_body\"}}}",
        "createdAt": "$random_createdAt",
        "requestSourceAttribute":{
            "deviceId":"$random_deviceId",
            "client":"$random_client",
            "appVersion":"10.2.0",
            "country": "India",
            "latitude":"72.12",
            "longitude":"64.57"
        }
    }
EOF
    )

    # Send curl request
    curl --location 'localhost:8080/posts/create' \
    --header 'Content-Type: application/json' \
    --data "$json_data"

    echo "Request $i sent."
done
