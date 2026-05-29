# Docker Build & Deployment Anleitung

## Voraussetzungen
- DevContainer mit Docker-outside-of-Docker konfiguriert (siehe `.devcontainer/devcontainer.json`)
- Nach Änderungen an `devcontainer.json`: DevContainer neu starten (Rebuild Container)



**Änderungen**

1. **`.devcontainer/devcontainer.json`** erweitert:
   - ✅ Docker-outside-of-Docker Feature hinzugefügt (`ghcr.io/devcontainers/features/docker-outside-of-docker:1`)
   - ✅ Docker-Socket mount konfiguriert (`/var/run/docker.sock`)
   - ✅ Docker Buildx und Docker Compose v2 aktiviert
   - ✅ Alle bestehenden Scala-Tools bleiben erhalten (Scala 3.8.3, sbt 1.12.11, Java 25, Metals, Git)

2. **`DOCKER-BUILD.md`** aktualisiert:
   - ✅ Anleitung für Docker-Build im DevContainer
   - ✅ Troubleshooting-Hinweise
   - ✅ Erklärung des Docker-outside-of-Docker Konzepts

**Deine nächsten Schritte:**

```bash
# 1. Änderungen committen & pushen
git add .devcontainer/devcontainer.json DOCKER-BUILD.md
git commit -m "Add Docker-outside-of-Docker to DevContainer"
git push

# 2. VS Code beenden, lokales DevContainer-Image löschen
# 3. Projekt erneut aus GitHub im DevContainer öffnen
# 4. Nach Rebuild:
sbt --client assembly
docker build -t hello-scala:latest .
docker run -p 8080:8080 hello-scala:latest
```

Nach dem Rebuild hast du Docker direkt im DevContainer verfügbar, und alle Images werden automatisch auf dem Host-Docker registriert!

## Build-Workflow

### 1. Im DevContainer (VS Code Terminal)

**Schritt 1: FAT JAR erstellen**
```bash
# FAT JAR erstellen
sbt --client assembly

# Prüfen, ob JAR existiert
ls -lh target/scala-3.8.3/hello-scala-assembly-0.1.0-SNAPSHOT.jar
```

**Schritt 2: Docker-Image bauen**
```bash
# Docker-Image bauen (nutzt Host-Docker-Daemon)
docker build -t hello-scala:latest .

# Image prüfen
docker images | grep hello-scala

# Container starten
docker run -d -p 8080:8080 --name hello-scala-app hello-scala:latest

# Logs anzeigen
docker logs -f hello-scala-app

# Testen
curl http://localhost:8080/api/health
# oder im Browser: http://localhost:8080/
```

### 2. Auf Windows Host (Optional - zur Verifikation)
```powershell
# Image ist automatisch verfügbar (Docker-outside-of-Docker)
docker images | findstr hello-scala

# Container starten (falls nicht bereits im DevContainer gestartet)
docker run -p 8080:8080 hello-scala:latest
```

### 3. Container verwalten
```powershell
# Container stoppen
docker stop hello-scala-app

# Container entfernen
docker rm hello-scala-app

# Image entfernen
docker rmi hello-scala:latest
```

## Erwartete Image-Größe
- **FAT JAR**: ~32 MB
- **Docker-Image** (mit Java 25 JRE): ~150-180 MB

## Deployment auf anderem Rechner

### Option 1: Docker Registry (empfohlen)
```powershell
# Tag setzen
docker tag hello-scala:latest registry.example.com/hello-scala:latest

# Push
docker push registry.example.com/hello-scala:latest

# Auf Zielrechner:
docker pull registry.example.com/hello-scala:latest
docker run -p 8080:8080 registry.example.com/hello-scala:latest
```

### Option 2: Image exportieren
```powershell
# Export
docker save hello-scala:latest -o hello-scala.tar

# Auf Zielrechner:
docker load -i hello-scala.tar
docker run -p 8080:8080 hello-scala:latest
```

## Troubleshooting

### "docker: command not found" im DevContainer
❌ DevContainer wurde nicht neu gebaut nach Änderung der `devcontainer.json`.
**Lösung:** VS Code Command Palette (Ctrl+Shift+P) → "Dev Containers: Rebuild Container"

### "Cannot connect to Docker daemon"
❌ Docker-Socket nicht gemountet oder Docker läuft nicht auf dem Host.
**Lösung:** Überprüfe, ob Docker Desktop auf Windows läuft.

### "COPY failed: file not found"
❌ FAT JAR wurde nicht erstellt. Führe im DevContainer aus: `sbt --client assembly`

### Port 8080 bereits belegt
```bash
# Anderen Port verwenden
docker run -p 9090:8080 hello-scala:latest
# App ist dann unter http://localhost:9090 erreichbar
```

## Wie funktioniert Docker-outside-of-Docker?

- DevContainer installiert Docker CLI, aber **keinen** Docker-Daemon
- Der Host-Docker-Socket (`/var/run/docker.sock`) wird in den Container gemountet
- Alle `docker`-Befehle im DevContainer werden vom **Host-Docker-Daemon** ausgeführt
- ✅ Images sind automatisch auf Host verfügbar (keine Export/Import nötig)
- ✅ Schneller und ressourcenschonender als Docker-in-Docker
