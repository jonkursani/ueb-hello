# Thëniet

> Shembull nga klasa (projekti `hello`). Kjo temë nuk mund të zgjidhet.
> Versioni 2, pas rishikimit.
> Grupi: Arta, Blerim, Dafina, Erion · Repository: `ueb-thenie`

## 1. Përshkrimi

Një faqe ku studentët ruajnë thënie që u pëlqejnë, me autorin e tyre.
Kushdo i lexon pasi hyn në llogari. Secili ndryshon thëniet e veta.
Administratori i menaxhon autorët dhe mund të ndryshojë ose fshijë çdo thënie.

## 2. Rolet

| Roli | Çka bën |
|---|---|
| USER | sheh listën e thënieve dhe autorët, shton thënie, ndryshon **vetëm thëniet e veta** |
| ADMIN | gjithçka që bën USER, plus: ndryshon dhe fshin çdo thënie, shton/ndryshon/fshin autorë |

Pa llogari: vetëm faqja e login-it.

**Faza 1:** përdorues in-memory, pa kontroll rolesh:

| Username | Roli (përdoret në F2) | Fjalëkalimi |
|---|---|---|
| `user` | USER | nga env var `APP_USER_PASSWORD` |
| `admin` | ADMIN | nga env var `APP_ADMIN_PASSWORD` |

Në F1 çdo përdorues i kyçur i qaset çdo faqeje, edhe `/admin/**`, dhe mund të ndryshojë çdo thënie.
Kufizimet sipas rolit dhe pronësisë hyjnë në F2.

**Faza 2:** përdoruesit në databazë (shih `User`), kontroll sipas rolit dhe pronësisë.

## 3. Entitetet

Entitetet nuk lidhen direkt me format. Format përdorin DTO (seksioni 4).

**Author** (F1)

| Fusha | Tipi | Shënim |
|---|---|---|
| id | Long | gjenerohet |
| name | String | `NOT NULL`, gjatësia max 80 |
| born | Integer | opsionale |
| quotes | List\<Quote\> | `@OneToMany(mappedBy = "author")`, pa cascade REMOVE |

**Quote** (F1)

| Fusha | Tipi | Shënim |
|---|---|---|
| id | Long | gjenerohet |
| text | String | `NOT NULL`, gjatësia max 300 |
| author | Author | `@ManyToOne`, `NOT NULL` |
| createdBy | String | username-i i atij që e shtoi, vendoset nga serveri |
| createdAt | LocalDateTime | vendoset nga serveri |

`createdBy` mbetet `String` (jo relacion me `User`) qëllimisht: thënia nuk prishet nëse përdoruesi fshihet.
Në F2 vlera e saj është gjithmonë `User.username` i përdoruesit të kyçur.

**User** (F2)

| Fusha | Tipi | Shënim |
|---|---|---|
| id | Long | gjenerohet |
| username | String | `NOT NULL`, unik, 3–30 shkronja |
| password | String | hash BCrypt, kurrë tekst i thjeshtë |
| role | enum | `ROLE_USER` ose `ROLE_ADMIN` |

Nuk ka regjistrim nga faqja. Në nisje, nëse tabela është bosh, krijohen `user` dhe `admin`
me fjalëkalimet nga env vars `APP_USER_PASSWORD` dhe `APP_ADMIN_PASSWORD`.

## 4. Format (DTO) dhe validimi

**AuthorForm** (F1)

| Fusha | Validimi |
|---|---|
| name | `@NotBlank`, `@Size(max = 80)` |
| born | opsionale, `@Min(0)`, `@Max(2026)` |

**QuoteForm** (F1, validatori custom në F2)

| Fusha | Validimi |
|---|---|
| id | `null` te Create, id-ja e thënies te Edit |
| text | `@NotBlank`, `@Size(min = 5, max = 300)` |
| authorId | `@NotNull` (zgjidhet me `<select>`) |

Gabimet shfaqen pranë fushës përkatëse në formë.
`createdBy` dhe `createdAt` nuk janë në formë: i vendos Service-i.

## 5. Relacioni

`Author` 1 → shumë `Quote` (`@OneToMany` te Author, `@ManyToOne` te Quote).
Një thënie ka saktësisht një autor; një autor ka zero ose shumë thënie.

Në faqe:
- **F1:** forma e thënies e zgjedh autorin me `<select>` (pa të, thënia s'mund të krijohet).
- **F1:** lista e thënieve tregon emrin e autorit.
- **F2:** faqja e autorit `/app/authors/{id}` tregon thëniet e tij.

**Fshirja e autorit:** nuk lejohet nëse autori ka thënie. Faqja kthehet te lista
e autorëve me mesazhin “Autori ka thënie. Fshini ose zhvendosni thëniet fillimisht.”

## 6. Faqet dhe URL-të

| URL | Metoda | Faqja / veprimi | Qasja F1 | Qasja F2 |
|---|---|---|---|---|
| `/` | GET | ridrejton te `/app/quotes` | i kyçur | USER, ADMIN |
| `/login` | GET, POST | login.html | të gjithë | të gjithë |
| `/logout` | POST | ridrejton te `/login?logout` | i kyçur | i kyçur |
| `/app/quotes` | GET | quotes/list.html (me kërkim, filtër, faqe, renditje në F2) | i kyçur | USER, ADMIN |
| `/app/quotes/new` | GET, POST | quotes/form.html | i kyçur | USER, ADMIN |
| `/app/quotes/{id}/edit` | GET, POST | quotes/form.html | i kyçur | pronari ose ADMIN |
| `/admin/quotes/{id}/delete` | POST | fshin, kthen te lista | i kyçur | ADMIN |
| `/admin/authors` | GET | authors/list.html | i kyçur | ADMIN |
| `/admin/authors/new` | GET, POST | authors/form.html | i kyçur | ADMIN |
| `/admin/authors/{id}/edit` | GET, POST | authors/form.html | i kyçur | ADMIN |
| `/admin/authors/{id}/delete` | POST | fshin (ose refuzon, shih 5), kthen te lista | i kyçur | ADMIN |
| `/app/authors/{id}` | GET | authors/detail.html (autori + thëniet) | — | USER, ADMIN |
| `/api/quotes/search` | GET | JSON (seksioni 9) | — | USER, ADMIN |
| `/access-denied` | GET | access-denied.html | — | të gjithë |

Fshirjet bëhen vetëm me POST (formë me CSRF token), asnjëherë me GET ose link.
Butonat “Ndrysho” dhe “Fshi” shfaqen vetëm kur përdoruesi ka të drejtë (F2),
por kontrolli bëhet edhe në server: qasja e ndaluar → `/access-denied`.
Id që nuk ekziston → faqja 404.

## 7. Kërkimi, filtrimi, pagination dhe sorting (F2)

`GET /app/quotes?q=commit&authorId=3&page=0&size=10&sort=createdAt,desc`

| Parametri | Kuptimi | Default |
|---|---|---|
| `q` | teksti i thënies e përmban `q` (pa dallim shkronjash) | bosh = të gjitha |
| `authorId` | vetëm thëniet e këtij autori (`<select>`) | bosh = të gjithë |
| `page`, `size` | faqja dhe numri për faqe | 0, 10 |
| `sort` | `createdAt`, `text` ose `author.name`, `asc`/`desc` | `createdAt,desc` |

Filtrat ruhen kur kalohet në faqen tjetër ose ndryshohet renditja.
Kërkimi bëhet në server (Repository), jo në browser.

## 8. Custom validator (F2)

`@UniqueQuoteText` vendoset **në klasën** `QuoteForm`, sepse i duhet edhe `id`-ja.
- E njëjta thënie nuk ruhet dy herë: krahasim pa dallim shkronjash të mëdha/të vogla,
  pa hapësira në fillim/fund.
- Te Edit, thënia që po ndryshohet nuk llogaritet si dublikatë e vetvetes.
- Gabimi lidhet me fushën `text` dhe shfaqet pranë saj.

Mesazhi: “Kjo thënie ekziston tashmë.”

## 9. REST endpoint + fetch (F2)

`GET /api/quotes/search?q=commit` → JSON, maksimum 10 rezultate, të renditura sipas tekstit:

```json
[{"id": 3, "text": "Commit early, commit often.", "author": "Arta"}]
```

- `q` më i shkurtër se 2 shkronja → lista bosh `[]`.
- Vetëm për përdorues të kyçur (USER, ADMIN).
- Kthen DTO, jo entitetin `Quote`.

E thërret `quotes/list.html` me `fetch` ndërsa përdoruesi shkruan në fushën e kërkimit (live search).
Klikimi mbi një rezultat hap `/app/quotes/{id}/edit` nëse ka të drejtë, përndryshe faqen e autorit.

## 10. Arkitektura

Që nga F1 (që të mos ristrukturohet në F2):
- Shtresat `Controller → Service → Repository`. Controller-i nuk e thërret Repository-n direkt.
- Format përdorin `QuoteForm` dhe `AuthorForm`, jo entitetet.
- Service-i vendos `createdBy` dhe `createdAt` dhe kontrollon rregullat (fshirja e autorit, pronësia në F2).
- Të gjitha faqet përdorin `layout.html` dhe `fragments.html` (header, menu, mesazhet).

## 11. Docker, konfigurimi dhe publikimi

**F1:** `compose.yaml` me shërbimin `db` (PostgreSQL) dhe volume për të dhënat.
Aplikacioni niset nga IDE-ja dhe lidhet me databazën në Docker.

**F2:**
- `Dockerfile` për aplikacionin dhe shërbimi `app` në `compose.yaml`.
- `docker compose up --build` e nis aplikacionin dhe databazën me një komandë.
- Të gjitha fjalëkalimet vijnë nga environment variables:
  `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `APP_USER_PASSWORD`, `APP_ADMIN_PASSWORD`.
- `.env` është në `.gitignore`. Në GitHub ruhet vetëm `.env.example` me vlera shembull.
- Aplikacioni publikohet në një host falas (zgjidhet deri në fund të fazës 1,
  sepse hostet falas ndryshojnë kushtet). Linku shkruhet në README.

## 12. Ndarja e punës

**Faza 1 (22.11)**

| Kush | Çka |
|---|---|
| Arta | projekti, `compose.yaml` për PostgreSQL, `Author` + `Quote` + relacioni, Repository-t |
| Blerim | `layout.html`, `fragments.html`, faqja 404, CSS, të dhëna fillestare për demo |
| Dafina | CRUD i thënieve: Controller, Service, `QuoteForm`, lista, forma me `<select>`, validimi |
| Erion | CRUD i autorëve (me rregullin e fshirjes); login-i custom me përdorues in-memory, logout |

**Faza 2 (janar)**

| Kush | Çka |
|---|---|
| Arta | `User` në databazë, BCrypt, rolet, rregullat e URL-ve, pronësia e thënieve, `/access-denied` |
| Blerim | kërkimi me 2 kritere, pagination, sorting; faqja e autorit me thëniet |
| Dafina | `@UniqueQuoteText`; REST endpoint dhe live search me `fetch` |
| Erion | `Dockerfile`, `compose.yaml` me `app` + `db`, env vars, publikimi në host |

Të gjithë: secili e rishikon PR-in e një tjetri para merge.

**Gati për F1 kur:** login-i custom dhe logout punojnë; autorët dhe thëniet krijohen,
listohen, ndryshohen dhe fshihen nga faqja; gabimet e validimit shfaqen pranë fushës;
fshirja e autorit me thënie refuzohet me mesazh; të gjitha faqet kanë layout-in e përbashkët.

## 13. Jashtë fushës

- Nuk ka regjistrim të përdoruesve nga faqja (krijohen në nisje, shih `User`).
- Nuk ka “like”, komente apo foto.
- Nuk ka e-mail, as “kam harruar fjalëkalimin”.
- Nuk ka aplikacion mobil, as frontend të veçantë: vetëm Thymeleaf (+ një `fetch` për live search).
- Spring AI (3.8, opsional): nuk bëhet.