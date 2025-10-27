@echo off
echo 🚀 Building Bank CQRS Project...

:: Build پروژه
call mvn clean package -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Build failed!
    exit /b 1
)

echo ✅ Build completed!
echo 🐳 Starting Docker containers...

:: اجرای Docker
docker-compose down
docker-compose up --build -d

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Docker compose failed!
    exit /b 1
)

echo 🎉 Deployment completed!
echo 📊 Services:
echo    Command Service: http://localhost:8081
echo    Query Service:   http://localhost:8082
echo    PostgreSQL:      localhost:5432
echo    MongoDB:         localhost:27017
echo    Kafka:           localhost:9092

echo.
echo 📝 Test commands:
echo    curl -X POST http://localhost:8081/api/accounts -H "Content-Type: application/json" -d "{\"type\":\"OPEN_ACCOUNT\",\"aggregateId\":\"test-001\",\"accountHolder\":\"Test User\",\"initialBalance\":1000.0}"
echo    curl http://localhost:8082/api/accounts

pause