# Real-Time Collaborative Document System POC

POC implementation with:
- **Backend:** Spring Boot 3, Java 17, MongoDB, Elasticsearch, STOMP WebSocket
- **Frontend:** Angular (standalone), RxJS, SockJS + STOMP

## Project Structure

- `backend/` Spring Boot API + WebSocket server
- `frontend/` Angular UI (document editor, version history, search)
- `sample-data/` sample request payloads

## Backend Setup

1. Start MongoDB and Elasticsearch locally (default ports).
2. From `backend/`:

```bash
./mvnw spring-boot:run
```

Backend runs on `http://localhost:8080`.

## Frontend Setup

From `frontend/`:

```bash
npm install
npm start
```

Frontend runs on `http://localhost:4200`.

## Implemented Features

- Document creation/update with permissions (`PUBLIC`, `SINGLE_USER`, `MULTIPLE_USERS`)
- Document-level locking with 5 minute stale-lock cleanup
- Real-time updates via `/ws-document` and `/topic/document/{id}`
- Versioning on save
- Audit logging for CREATE/EDIT/DOWNLOAD/LOCK/UNLOCK
- Full-text search using Elasticsearch (`documents` index)
- Angular editor, version history, and search UI

## API Endpoints

### Document APIs
- `POST /api/documents`
- `GET /api/documents/{id}`
- `PUT /api/documents/{id}`
- `POST /api/documents/{id}/lock`
- `POST /api/documents/{id}/unlock`

### Version APIs
- `GET /api/documents/{id}/versions`
- `GET /api/versions/{versionId}`

### Audit APIs
- `GET /api/documents/{id}/audit`

### Search API
- `GET /api/search?q=keyword`

## WebSocket

- Endpoint: `/ws-document` (SockJS)
- Topic: `/topic/document/{id}`
- App destination prefix: `/app`
- Send edit message to `/app/document/{id}/edit`

## Test Scenarios

1. Owner creates document with `PUBLIC` access.
2. Another user opens and requests lock.
3. Lock grant broadcast updates all clients.
4. Editor changes content; updates broadcast in real-time.
5. Save creates new version and updates Elasticsearch index.
6. View version history and retrieve specific versions.
7. Search by filename/content/owner.
8. Verify stale lock auto-clears after 5 minutes.

