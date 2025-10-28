markdown
# 🏦 Bank Account CQRS & Event Sourcing System

یک سیستم کامل بانکی مبتنی بر معماری **CQRS** و **Event Sourcing** که با **Spring Boot** و **Docker** پیاده‌سازی شده است.

## 🎯 معماری سیستم
┌─────────────┐ Commands ┌─────────────┐ Events ┌─────────────┐
│ Client │ ──────────────►│ Command │ ──────────────►│ Kafka │
│ │ │ Service │ │ │
│ │ ◄──────────────│ │ │ │
│ │ Queries │ │ │ │
└─────────────┘ └─────────────┘ └─────────────┘
│
│ Events
▼
┌─────────────┐ Queries ┌─────────────┐ ┌─────────────┐
│ Client │ ◄──────────────│ Query │ ◄─────────────│ MongoDB │
│ │ │ Service │ │ │
└─────────────┘ └─────────────┘ └─────────────┘

text

## 🏗️ ساختار پروژه
bank-account-cqrs/
├── 📦 domain-module/ # مدل‌های مشترک
│ ├── command/ # Commandها
│ ├── event/ # Eventها
│ └── query/ # Queryها و DTOها
├── ⚡ command-service/ # سرویس نوشتن
│ ├── controller/ # REST APIs
│ ├── commandhandler/ # Command Handlers
│ ├── aggregate/ # Business Logic
│ ├── eventstore/ # Event Store
│ └── publisher/ # Kafka Publisher
├── 📊 query-service/ # سرویس خواندن
│ ├── controller/ # REST APIs
│ ├── eventhandler/ # Event Handlers
│ ├── repository/ # MongoDB Repository
│ └── service/ # Query Services
├── 🐳 docker-compose.yml # Infrastructure
├── 🚀 deploy.bat # اسکریپت اجرا (ویندوز)
├── 🧹 cleanup.bat # اسکریپت پاکسازی
└── 📄 README.md

text

## 📋 Domain Models

### Commands
- `OpenAccountCommand` - افتتاح حساب
- `DepositFundsCommand` - واریز وجه
- `WithdrawFundsCommand` - برداشت وجه  
- `CloseAccountCommand` - بستن حساب

### Events
- `AccountOpenedEvent` - حساب افتتاح شد
- `FundsDepositedEvent` - وجه واریز شد
- `FundsWithdrawnEvent` - وجه برداشت شد
- `AccountClosedEvent` - حساب بسته شد

### Queries
- `FindAllAccountsQuery` - پیدا کردن همه حساب‌ها
- `FindAccountByIdQuery` - پیدا کردن حساب با ID
- `FindAccountsWithBalanceQuery` - پیدا کردن حساب با محدوده موجودی

## 🛠️ تکنولوژی‌ها

### Backend
- **Java 17** - زبان برنامه‌نویسی
- **Spring Boot 3.2.0** - فریمورک اصلی
- **Spring Web** - REST APIs
- **Spring Data JPA** - دسترسی به داده‌ها
- **Spring Kafka** - پیام‌رسانی
- **Lombok** - کاهش boilerplate code
- **Validation** - اعتبارسنجی داده‌ها

### Database
- **PostgreSQL** - Event Store (Write Database)
- **MongoDB** - Read Database (Query Database)

### Messaging
- **Apache Kafka** - Message Broker
- **Zookeeper** - Kafka Coordination

### Infrastructure
- **Docker** - Containerization
- **Docker Compose** - Orchestration

## 🚀 راه‌اندازی سریع

### پیش‌نیازها
- Java 17+
- Maven 3.6+
- Docker & Docker Compose

### ۱. کلون کردن پروژه
```bash
git clone <repository-url>
cd bank-account-cqrs
۲. ساخت و اجرا (ویندوز)
bash
deploy.bat
۳. ساخت و اجرا (Linux/Mac)
bash
chmod +x deploy.sh
./deploy.sh
۴. اجرای دستی
bash
# ساخت پروژه
mvn clean package -DskipTests

# اجرای سرویس‌ها
docker-compose up -d
📡 API Documentation
Command Service (Port 8081)
افتتاح حساب
http
POST /api/accounts
Content-Type: application/json

{
  "type": "OPEN_ACCOUNT",
  "aggregateId": "acc-001",
  "accountHolder": "علی محمدی",
  "initialBalance": 1000.0
}
واریز وجه
http
PUT /api/accounts/{accountId}/deposit
Content-Type: application/json

{
  "type": "DEPOSIT_FUNDS",
  "amount": 500.0
}
برداشت وجه
http
PUT /api/accounts/{accountId}/withdraw  
Content-Type: application/json

{
  "type": "WITHDRAW_FUNDS",
  "amount": 200.0
}
بستن حساب
http
DELETE /api/accounts/{accountId}
Query Service (Port 8082)
دریافت همه حساب‌ها
http
GET /api/accounts
دریافت حساب خاص
http
GET /api/accounts/{accountId}
جستجوی حساب با موجودی
http
GET /api/accounts/search?minBalance=1000&maxBalance=5000
🧪 تست سیستم
تست End-to-End
bash
# افتتاح حساب
curl -X POST http://localhost:8081/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"type":"OPEN_ACCOUNT","aggregateId":"test-001","accountHolder":"تست کاربر","initialBalance":10000.0}'

# واریز وجه
curl -X PUT http://localhost:8081/api/accounts/test-001/deposit \
  -H "Content-Type: application/json" \
  -d '{"type":"DEPOSIT_FUNDS","amount":5000.0}'

# خواندن حساب‌ها
curl http://localhost:8082/api/accounts
🔍 مانیتورینگ و دیباگ
مشاهده لاگ‌ها
bash
# همه لاگ‌ها
docker-compose logs -f

# لاگ سرویس خاص
docker logs bank-command-service -f
docker logs bank-query-service -f
بررسی دیتابیس‌ها
bash
# Event Store (PostgreSQL)
docker exec bank-postgres psql -U reza -d mydb -c "SELECT * FROM event_store;"

# Read Database (MongoDB)  
docker exec bank-mongodb mongo -u admin -p admin --eval "db.bank_accounts.find().pretty()" bank_query_db
بررسی Kafka
bash
# لیست Topicها
docker exec bank-kafka kafka-topics --list --bootstrap-server localhost:9092

# مشاهده پیام‌ها
docker exec bank-kafka kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic bank-account-events \
  --from-beginning
🏗️ جریان داده
مسیر نوشتن (Command Flow)
✅ کاربر Command می‌فرستد

✅ Command Handler اعتبارسنجی و پردازش می‌کند

✅ Aggregate Event تولید می‌کند

✅ Event در PostgreSQL ذخیره می‌شود

✅ Event به Kafka publish می‌شود

مسیر خواندن (Query Flow)
✅ Query Service Eventها را از Kafka consume می‌کند

✅ Event Handler داده را در MongoDB آپدیت می‌کند

✅ کاربر Query می‌فرستد

✅ داده直接从 MongoDB خوانده می‌شود

🎯 مزایای معماری
CQRS Benefits
🚀 مقیاس‌پذیری - خواندن و نوشتن جداگانه scale می‌شوند

⚡ بهینه‌سازی - هر دیتابیس برای workload خاصی بهینه شده

🔧 انعطاف‌پذیری - امکان اضافه کردن projectionهای مختلف

Event Sourcing Benefits
📊 تاریخچه کامل - audit trail کامل از تمام تغییرات

🔄 قابلیت replay - امکان بازسازی state از Eventها

🐛 Debuggability - ردیابی آسان مشکلات

🔧 فایل‌های پیکربندی
docker-compose.yml
yaml
version: '3.8'
services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: mydb
      POSTGRES_USER: reza
      POSTGRES_PASSWORD: secret123
    ports: ["5432:5432"]
    
  mongodb:
    image: mongo:4.4
    environment:
      MONGO_INITDB_ROOT_USERNAME: admin
      MONGO_INITDB_ROOT_PASSWORD: admin
    ports: ["27017:27017"]
    
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    
  kafka:
    image: confluentinc/cp-kafka:7.5.0
    environment:
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:9092
    ports: ["9092:9092"]
    
  command-service:
    build: ./command-service
    ports: ["8081:8081"]
    depends_on: [postgres, kafka]
    
  query-service:
    build: ./query-service  
    ports: ["8082:8082"]
    depends_on: [mongodb, kafka]
application-docker.properties
properties
# Command Service
spring.datasource.url=jdbc:postgresql://postgres:5432/mydb
spring.kafka.bootstrap-servers=kafka:9092

# Query Service  
spring.data.mongodb.uri=mongodb://admin:admin@mongodb:27017/bank_query_db
spring.kafka.bootstrap-servers=kafka:9092
🚀 اسکریپت‌های کمکی
deploy.bat (ویندوز)
batch
@echo off
echo 🚀 Building Bank CQRS Project...
call mvn clean package -DskipTests
echo 🐳 Starting Docker containers...
docker-compose down
docker-compose up --build -d
echo 🎉 Deployment completed!
cleanup.bat (ویندوز)
batch
@echo off
echo 🧹 Cleaning up Docker containers...
docker-compose down -v
docker system prune -f
echo ✅ Cleanup completed!
🔮 بهبودهای آینده
🗃️ اضافه کردن Caching (Redis)

📊 اضافه کردن Monitoring (Prometheus + Grafana)

🔐 پیاده‌سازی Authentication/Authorization

⚡ اضافه کردن Circuit Breaker

🖥️ ایجاد Dashboard برای مشاهده Eventها

🧪 اضافه کردن Unit Tests و Integration Tests

🔍 اضافه کردن Distributed Tracing

📈 اضافه کردن Metrics و Health Checks

🤝 مشارکت
Fork the project

Create your feature branch (git checkout -b feature/AmazingFeature)

Commit your changes (git commit -m 'Add some AmazingFeature')

Push to the branch (git push origin feature/AmazingFeature)

Open a Pull Request

📄 لایسنس
این پروژه تحت لایسنس MIT منتشر شده است.

👥 توسعه‌دهندگان
Reza Valipour

⭐ اگر این پروژه رو دوست داشتید، star بدید!

❓ سوالات متداول
❓ چرا از دو دیتابیس مختلف استفاده شده؟
PostgreSQL برای ذخیره Eventها (Write-optimized)

MongoDB برای خواندن داده (Read-optimized)

❓ Event Sourcing چه مزایایی دارد؟
امکان بازسازی state از Eventها

تاریخچه کامل تمام تغییرات

قابلیت دیباگ آسان

❓ چگونه می‌توانم سیستم را توسعه دهم؟
مدل‌های Domain را در domain-module اضافه کنید

Command و Event مربوطه را ایجاد کنید

Handlerها را در سرویس‌ها پیاده‌سازی کنید

APIهای جدید اضافه کنید

🎉 با تشکر از شما برای استفاده از این پروژه!

text

این فایل README یکپارچه شامل:

- 🏗️ **معماری و ساختار** کامل پروژه
- 🚀 **دستورات نصب و اجرا** برای تمام پلتفرم‌ها
- 📡 **مستندات API** کامل با مثال‌های عملی
- 🧪 **راهنمای تست** end-to-end
- 🔍 **ابزارهای مانیتورینگ** و دیباگ
- 🎯 **مزایای معماری** و توجیه فنی
- 🔧 **فایل‌های پیکربندی** مهم
- 🔮 **راهکارهای توسعه** آینده
- ❓ **سوالات متداول** و راهنمایی

همه چیزی که برای فهم، اجرا و توسعه پروژه نیاز دارید در یک فایل گردآوری شده است!
