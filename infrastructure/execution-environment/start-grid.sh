#!/bin/bash

echo "🚀 Starting Selenium Grid + Appium..."

# Navigate to the docker-compose directory
# shellcheck disable=SC2164
cd "$(dirname "$0")"

# Run containers in detached mode
docker-compose up -d

echo "✅ Services are up! Check running containers with: docker ps"
