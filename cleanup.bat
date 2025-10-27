@echo off
echo 🧹 Cleaning up Docker containers...

docker-compose down -v
docker system prune -f

echo ✅ Cleanup completed!
pause