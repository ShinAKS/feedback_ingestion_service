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
postedBy_values=("Ayush" "John" "Alice" "Bob")

# Array of possible deviceId values
deviceId_values=("APPLE" "XIAOMI" "SAMSUNG" "GOOGLE")

# Array of possible client values
client_values=("client123" "client456" "client789")

# Array of possible appVersion values
appVersion_values=("10.2.0" "10.3.0" "10.4.0")

# Array of possible country values
country_values=("India" "USA" "UK")

for ((i=1; i<=10; i++)); do
    # Generate random values
    random_postedBy=${postedBy_values[$RANDOM % ${#postedBy_values[@]} ]}
    random_tweet_id=$((RANDOM % 1000000000))
    random_message="Random message $RANDOM"
    random_likes=$((RANDOM % 100))
    random_views=$((RANDOM % 1000))
    random_createdAt=$(random_date)
    random_deviceId=${deviceId_values[$RANDOM % ${#deviceId_values[@]} ]}
    random_client=${client_values[$RANDOM % ${#client_values[@]} ]}
    random_appVersion=${appVersion_values[$RANDOM % ${#appVersion_values[@]} ]}
    random_country=${country_values[$RANDOM % ${#country_values[@]} ]}
    random_latitude=$(printf "%.2f" $(bc -l <<< "scale=2; $RANDOM/3276.7"))
    random_longitude=$(printf "%.2f" $(bc -l <<< "scale=2; $RANDOM/3276.7"))

    # Construct JSON data
    json_data=$(cat <<EOF
    {
        "conversationId": "123456789",
        "postedBy" : "$random_postedBy",
        "postType" : "TWEET",
        "content":"{\"tweet_id\": \"$random_tweet_id\",\"message\": \"$random_message\",\"likes\": $random_likes,\"views\": $random_views}",
        "createdAt": "$random_createdAt",
        "requestSourceAttribute":{
            "deviceId":"$random_deviceId",
            "client":"$random_client",
            "appVersion":"$random_appVersion",
            "country": "$random_country",
            "latitude":"$random_latitude",
            "longitude":"$random_longitude"
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
