# 💰 Moduł Płatności – System warsztatowy

## 🛠 Kontekst projektu

Projekt zrealizowany w ramach zajęć **Inżynierii oprogramowania – Aplikacje korporacyjne** (WSIiZ x Asseco).  
Stanowi część większego systemu informatycznego wspierającego obsługę sieci warsztatów samochodowych.  
Moduł `payments` odpowiada za **zarządzanie fakturami, rozliczeniami i płatnościami klientów**.

---

## 🎯 Zakres modułu

Moduł umożliwia:
- Tworzenie i przegląd faktur (`Invoice`)
- Grupowanie faktur w rozliczenia (`Settlement`)
- Określenie metod płatności (`PaymentMethod`)
- Obsługę monitów przypominających o zaległościach
- Interakcję z użytkownikiem przez interfejs webowy (Vaadin)

---

## 📦 Struktura projektu

Projekt oparty jest na architekturze typu **modular / feature-based**, z podziałem na:

```
payments/
├── application/              <- logika aplikacyjna
│   ├── InvoiceController.java
│   └── SettlementController.java
├── domain/                   <- logika dziedzinowa
│   ├── invoice/
│   │   ├── Invoice.java
│   │   ├── InvoiceItem.java
│   │   ├── InvoiceStatus.java
│   │   └── InvoiceRepository.java
│   └── settlement/
│       ├── Settlement.java
│       ├── PaymentMethod.java
│       └── SettlementRepository.java
├── ui/                       <- interfejs użytkownika (Vaadin)
│   ├── invoice/
│   │   ├── InvoiceForm.java
│   │   ├── InvoiceListView.java
│   │   └── InvoiceFilters.java
│   └── settlement/
│       ├── SettlementForm.java
│       ├── SettlementListView.java
│       └── SettlementFilters.java
```

---

## ⚙️ Technologie

- Java **23** (via toolchain)
- Spring Boot **3.4.4**
- Gradle
- Vaadin **24.7.5**
- Spring Data JPA
- H2 (baza testowa)
- Lombok

---

## 🧪 Uruchomienie projektu

Wymagania:
- JDK 23 (lub obsługa `toolchain` w Gradle)
- Gradle (wrapper dołączony do repo)

Kroki:

```bash
./gradlew clean build
./gradlew bootRun
```

Aplikacja będzie dostępna pod adresem:  
http://localhost:8080

---

## 🧾 Przykładowy przebieg

1. Użytkownik wystawia fakturę zawierającą usługę i/lub części zamienne.
2. Faktury mogą zostać pogrupowane w rozliczenie (`Settlement`) z informacją o metodzie i dacie płatności.
3. W razie opóźnień możliwe jest wysłanie monitu (e-mail).

---

## 📮 REST API

### Faktury (`InvoiceController`)

- **GET /payments/invoices**  
  Zwraca listę wszystkich faktur.

- **GET /payments/invoices/{id}**  
  Zwraca szczegóły faktury o danym `id` (404, jeśli brak).

- **POST /payments/invoices**  
  Tworzy nową fakturę. Przykład body:
  ```json
  {
    "customer": { "id": 123 },
    "items": [
      { "description": "Wymiana klocków", "netPrice": 200.0 }
    ]
  }
  ```
- Odpowiedź: `201 Created`, nagłówek `Location: /payments/invoices/{noweId}`.

- **PUT /payments/invoices/{id}/pay**  
  Oznacza fakturę jako `PAID` (404, jeśli faktura nie istnieje, w przeciwnym razie 204).

- **PUT /payments/invoices/{id}/cancel**  
  Oznacza fakturę jako `CANCELED`.

---

### Rozliczenia (`SettlementController`)

- **GET /payments/settlements**  
  Zwraca listę wszystkich rozliczeń.

- **GET /payments/settlements/{id}**  
  Pobiera szczegóły rozliczenia o danym `id`.

- **GET /payments/settlements/invoice/{invoiceId}**  
  Pobiera rozliczenie zawierające fakturę o `invoiceId`.

- **POST /payments/settlements**  
  Tworzy nowe rozliczenie. Przykład body:
  ```json
  {
    "paymentDate": "2025-06-01",
    "paymentMethod": "BANK_TRANSFER",
    "invoices": [ { "id": 10 }, { "id": 11 } ]
  }
  ```
  - Jeśli którakolwiek faktura ma status `PENDING` → 400 Bad Request  
  - Jeśli OK → `201 Created` z nagłówkiem `Location`.

---

## 🧠 Warstwa serwisowa (kluczowe metody)

### `InvoiceService`
- `List<Invoice> findAll()`
- `Optional<Invoice> findById(Long id)`
- `Invoice save(Invoice invoice)`
- `boolean markAsPaid(Long id)`
- `boolean cancel(Long id)`

### `SettlementService`
- `List<Settlement> findAll()`
- `Optional<Settlement> findById(Long id)`
- `Optional<Settlement> findByInvoiceId(Long invoiceId)`
- `Settlement save(Settlement settlement)`
  - Walidacja:  
    1. Jeśli `paymentDate != null` → wszystkie faktury muszą być `PAID`, inaczej `IllegalStateException`.  
    2. Jeśli `paymentDate == null` i wszystkie faktury mają `PAID` → automatycznie `settlement.setPaymentDate(LocalDate.now())`.

---

## 📚 Powiązane wymagania domenowe

Moduł płatności odpowiada na wymagania z dokumentacji projektowej:
- **Ewidencja usług i części** — przekłada się na pozycje faktury
- **Rejestracja płatności** — rozliczenia klientów
- **Raportowanie zaległości** — funkcjonalność monitów

---

## 🌱 Możliwość dalszego rozwoju

- Integracja z kartoteką klientów i magazynem części
- Eksport faktur do PDF
- Wysyłka faktur i monitów e-mailem
- Zewnętrzne API dla rozliczeń (np. REST/JSON)

---

## 👥 Autorzy

Moduł opracowany przez zespół **Płatności**, w ramach kursu prowadzonego przez **Centrum Studiów Podyplomowych WSIiZ**
przy wsparciu **Asseco Poland**.
