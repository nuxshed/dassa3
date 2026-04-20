# IMS — Specification

## Architecture

```
app/src/main/java/com/dass/ims/
├── data/
│   ├── local/
│   │   ├── entity/        # Room entities
│   │   ├── dao/           # DAOs
│   │   └── ImsDatabase.kt
│   ├── repository/        # repository interfaces + impls
│   └── seed/              # Seeder.kt
├── domain/
│   └── model/             # domain stubs (non-Room)
├── ui/
│   ├── shared/            # Theme, Color, Type, Icons, shared composables
│   ├── dashboard/         # DashboardViewModel, AdminDashboard, StudentDashboard, SearchOverlay, ModuleStub
│   ├── timetable/         # TimetableViewModel, TimetableGrid, CellDrawer, SlotEditScreen
│   ├── studentdetails/    # StudentListViewModel, StudentList, FilterSheet, StudentProfile
│   └── login/             # LoginScreen
├── navigation/            # NavRoutes, ImsNavHost
├── session/               # SessionManager
└── MainActivity.kt
```

- **pattern**: MVVM — ViewModel + StateFlow per screen
- **db**: Room for persistence, seed via RoomDatabase.Callback.onCreate
- **no server**: all data local, stubs return seeded values
- **navigation**: single-activity, Jetpack Navigation Compose, string-based routes

---

## Design System Tokens

all values from `design/project/mockups/primitives.jsx`

### Colors (Notion palette)

| Token | Hex | Usage |
|-------|-----|-------|
| `bg` | `#FFFFFF` | screen backgrounds |
| `surface` | `#F7F7F5` | cards, inputs, table headers, unimplemented module cards |
| `border` | `#EDEDEC` | dividers, input outlines, cell borders |
| `borderstrong` | `#E3E2E0` | drag handles, stronger borders, dashed borders |
| `text` | `#37352F` | primary text, headings |
| `textsecondary` | `#787774` | labels, captions, placeholders, icons |
| `textfaint` | `#9B9A97` | disabled text, hints, inactive nav |
| `hover` | `#F1F1EF` | chip backgrounds, pressed state |
| `selected` | `#E8E8E6` | selected state background |
| `accent` | `#2383E2` | save button text |
| `red` | `#E03E3E` | destructive, limit exceeded, trash icon |
| `green` | `#0F7B6C` | active status |
| `yellow` | `#DFAB01` | warning accents |
| `chipbg` | `#F1F1EF` | filter chips, tag backgrounds |
| `divider` | `#F1F1EF` | row separators |

### Additional UI Colors (from mockup context)

| Usage | Background | Text/Border |
|-------|-----------|-------------|
| active status badge | `#E0F0E5` | `#1E6B3B` |
| alert banner | `#FDECE5` | `#8B3A2C` (text), `#F5C9B6` (border) |
| info banner (slot edit) | `#FFF9E6` | `#7C5A00` (text), `#F3DFA2` (border) |
| limit exceeded tag | `#FDECE5` | `#8B3A2C` |

### Subject Colors

| Code | bg | fg |
|------|-----|-----|
| OS | `#FDECC8` | `#7C4A03` |
| ALG | `#DBEDDB` | `#1E6B3B` |
| HCI | `#E8DEEE` | `#5D3A80` |
| DBMS | `#D3E5EF` | `#1D4F6E` |
| CN | `#FFE2DD` | `#8B3A2C` |
| SE | `#EEE0DA` | `#6B4A3E` |
| MATH | `#E3E2E0` | `#3C3A36` |

### Avatar Colors (per student, from mockup)

| Student | bg | fg |
|---------|-----|-----|
| Aarav Sharma (AS) | `#E9E5DA` | `#6B5C35` |
| Advika Rao (AR) | `#E8DEEE` | `#5D3A80` |
| Bhavya Kumar (BK) | `#FDECC8` | `#7C4A03` |
| Chirag Reddy (CR) | `#DBEDDB` | `#1E6B3B` |
| Diya Sen (DS) | `#D3E5EF` | `#1D4F6E` |
| Eshan Patel (EP) | `#FFE2DD` | `#8B3A2C` |
| Farhan Mirza (FM) | `#EEE0DA` | `#6B4A3E` |
| Admin (AN) | `#E6DFD1` | `#6B5C35` |

### Typography

fonts: Inter (sans), JetBrains Mono (mono) — bundled as assets

| Style | Size | Weight | Font | Usage |
|-------|------|--------|------|-------|
| Display | 24sp | 700 | Inter | screen titles, greeting |
| Heading | 18–22sp | 600–700 | Inter | section headers, names |
| Body | 13–15sp | 400–500 | Inter | list items, detail text, property values |
| Label | 11sp | 500–600 | Inter | input labels, section headers, uppercase |
| Caption | 10–12sp | 400–500 | Inter | timestamps, metadata |
| Mono | 9–13sp | 400–700 | JetBrains Mono | roll numbers, IDs, time, code |

### Spacing

- base unit: 8dp
- screen padding: 16–20dp horizontal (20dp in most mockups)
- corner radius: 6–10dp (cards 10dp, inputs 8dp, chips 6dp, avatars circular, pills 4dp)
- borders: 1dp, no shadows (exception: segmented control 0 1px 2px rgba(0,0,0,0.04))
- row heights: ~40–56dp min

### Icons

25 Lucide-style SVG paths from `primitives.jsx` ICON object. convert to Kotlin ImageVector manually:

`search`, `x`, `chevdown`, `chevright`, `chevleft`, `plus`, `arrow`, `filter`, `more`, `check`, `pencil`, `trash`, `msg`, `cal`, `users`, `clip`, `file`, `bank`, `paper`, `brief`, `usercog`, `userplus`, `enter`, `grip`, `mappin`, `user`, `alert`, `clock`, `book`

bottom nav icons use separate paths (home, search, schedule, people, me) from the BottomNav component

icon rendering: stroke-only, stroke-width 1.75 (default), size 18dp (default)

---

## Database Schema

### Full Entities (Room)

**User**
- id: Long (PK, autoGenerate)
- name: String
- email: String
- phone: String
- profilephoto: String? (URI)
- role: String ("admin" | "student")
- isactive: Boolean

**Student**
- id: Long (PK, autoGenerate)
- userid: Long (FK → User)
- studentid: String (roll number)
- batchid: Long (FK → Batch)
- courseid: Long (FK → Course)
- enrollmentdate: String
- previouseducation: String?
- category: String ("UG" | "PG" | "PhD")
- isalumni: Boolean
- avatarbg: String (hex color)
- avatarfg: String (hex color)
- gpa: Double

**Guardian**
- id: Long (PK, autoGenerate)
- studentid: Long (FK → Student)
- name: String
- relation: String
- phone: String
- isemergencycontact: Boolean

**Course**
- id: Long (PK, autoGenerate)
- name: String
- description: String
- duration: Int (years)

**Subject**
- id: Long (PK, autoGenerate)
- courseid: Long (FK → Course)
- name: String
- code: String
- credits: Int
- iselective: Boolean
- colorbg: String (hex)
- colorfg: String (hex)

**Batch**
- id: Long (PK, autoGenerate)
- name: String
- startdate: String
- enddate: String?
- capacity: Int

**Enrollment**
- id: Long (PK, autoGenerate)
- studentid: Long (FK → Student)
- subjectid: Long (FK → Subject)
- grade: Double?
- iselective: Boolean
- status: String ("active" | "completed" | "dropped")
- enrollmentdate: String

**Timetable**
- id: Long (PK, autoGenerate)
- name: String
- createdat: String

**Slot**
- id: Long (PK, autoGenerate)
- timetableid: Long (FK → Timetable)
- subjectid: Long (FK → Subject)
- day: Int (0=Mon, 5=Sat)
- period: Int (0–4)
- room: String
- facultyname: String
- batchname: String
- slottype: String ("Lecture" | "Tutorial" | "Lab" | "Exam")
- isrecurring: Boolean
- date: String? (for exam/one-off)

**PersonalEvent**
- id: Long (PK, autoGenerate)
- studentid: Long (FK → Student)
- title: String
- startat: String
- endat: String
- reminderat: String?

**News**
- id: Long (PK, autoGenerate)
- title: String
- content: String
- source: String
- createdat: String
- ispublished: Boolean

**AttendanceRecord**
- id: Long (PK, autoGenerate)
- studentid: Long (FK → Student)
- subjectid: Long (FK → Subject)
- date: String
- status: String ("present" | "absent" | "late")
- remarks: String?

**GradingScheme**
- id: Long (PK, autoGenerate)
- name: String
- type: String ("percentage" | "gpa" | "letter")

**GradeCutoff**
- id: Long (PK, autoGenerate)
- schemeid: Long (FK → GradingScheme)
- minscore: Double
- maxscore: Double
- grade: String
- gradepoint: Double

### Domain-only Stubs (data classes, no Room table)

Employee, Leave, StudentLeave, EmployeeLeave, LeaveApproval, Transaction, Fees, Donation, Expense, Payslip, Exam, Question, Result, Message, Notification, Role, Permissions, AdmissionForm, FormSubmission, Department, Dashboard, Comments

these are just `data class` definitions matching the UML attributes — no logic, no DAO

---

## DAOs

### StudentDao
- `getall()`: Flow<List<Student>>
- `getbyid(id: Long)`: Student?
- `getbybatch(batchid: Long)`: Flow<List<Student>>
- `search(query: String)`: Flow<List<Student>> — name or roll LIKE
- `insert(student: Student)`: Long
- `update(student: Student)`
- `delete(student: Student)`

### UserDao
- `getbyid(id: Long)`: User?
- `getbyrole(role: String)`: Flow<List<User>>
- `insert(user: User)`: Long

### SlotDao
- `getbytimetable(timetableid: Long)`: Flow<List<Slot>>
- `getbydayandperiod(timetableid: Long, day: Int, period: Int)`: List<Slot>
- `countbysubject(timetableid: Long, subjectid: Long)`: Int — for limit checking
- `countbyfaculty(timetableid: Long, facultyname: String)`: Int — for workload checking
- `insert(slot: Slot)`: Long
- `update(slot: Slot)`
- `delete(slot: Slot)`

### TimetableDao
- `getall()`: Flow<List<Timetable>>
- `getbyid(id: Long)`: Timetable?
- `insert(timetable: Timetable)`: Long

### SubjectDao
- `getall()`: Flow<List<Subject>>
- `getbyid(id: Long)`: Subject?
- `getbycourse(courseid: Long)`: Flow<List<Subject>>

### EnrollmentDao
- `getbystudent(studentid: Long)`: Flow<List<Enrollment>>
- `getwithsubject(studentid: Long)`: Flow<List<EnrollmentWithSubject>> — join

### GuardianDao
- `getbystudent(studentid: Long)`: Flow<List<Guardian>>

### NewsDao
- `getall()`: Flow<List<News>>
- `getrecent(limit: Int)`: Flow<List<News>>

### BatchDao
- `getall()`: Flow<List<Batch>>
- `getbyid(id: Long)`: Batch?

### CourseDao
- `getall()`: Flow<List<Course>>

### AttendanceDao
- `getbystudent(studentid: Long)`: Flow<List<AttendanceRecord>>

### GradingSchemeDao
- `getall()`: Flow<List<GradingScheme>>
- `getcutoffs(schemeid: Long)`: Flow<List<GradeCutoff>>

---

## Repositories

### StudentRepository
- wraps StudentDao, UserDao, GuardianDao, EnrollmentDao
- `getallstudents()`: Flow<List<StudentWithUser>>
- `getstudent(id: Long)`: StudentProfile (joined user + guardian + enrollments)
- `searchstudents(query: String, filters: StudentFilters)`: Flow<List<StudentWithUser>>

### TimetableRepository
- wraps TimetableDao, SlotDao, SubjectDao
- `getcurrenttimetable()`: Flow<TimetableWithSlots>
- `getslots(timetableid: Long)`: Flow<List<SlotWithSubject>>
- `addslot(slot: Slot)`: ConflictResult
- `removeslot(slot: Slot)`
- `checkconflicts(slot: Slot)`: List<Conflict>

### SearchRepository
- wraps StudentDao, SubjectDao, BatchDao
- `search(query: String, role: String)`: SearchResults (pages + people + actions)

### NewsRepository
- wraps NewsDao
- `getrecentnews()`: Flow<List<News>>

### SessionRepository
- wraps SessionManager
- `getcurrentuser()`: StateFlow<CurrentUser>
- `setrole(role: String)`

---

## Navigation

### Routes (string-based)

```kotlin
object NavRoutes {
    const val LOGIN = "login"
    const val DASHBOARD = "dashboard"
    const val SEARCH = "search"
    const val TIMETABLE = "timetable"
    const val TIMETABLE_CREATE = "timetable/create"
    const val TIMETABLE_EDIT = "timetable/{id}/edit"
    const val STUDENTS = "students"
    const val STUDENT_DETAIL = "students/{id}"
    const val STUDENT_EDIT = "students/{id}/edit"
    const val STUB = "stub/{module}"
    const val ME = "me"
}
```

### Bottom Nav Mapping

| Tab | Route | Admin | Student |
|-----|-------|-------|---------|
| Home | dashboard | AdminDashboard | StudentDashboard |
| Search | search | SearchOverlay | SearchOverlay (no actions) |
| Schedule | timetable | TimetableGrid (admin) | TimetableGrid (student) |
| People | students | StudentList | StudentProfile (own) |
| Me | me | own profile | own profile |

---

## Shared Composables

### Phone-frame awareness
- design reference 390x844 — composables built at these proportions, adapt naturally via Compose layout

### Components

**Avatar(initials, size, bg, fg)**
- circle, initials centered, fontSize = size * 0.38, fontWeight 600

**SearchField(placeholder, value, onchange, compact)**
- 40dp height, 8dp radius, `surface` bg, `border` outline, search icon leading, x icon when filled

**SectionHeader(label, open, right)**
- uppercase label (11sp/600, letterSpacing 0.8), chevron toggle, full-row tap target, 14dp top / 6dp bottom padding

**BottomNavBar(active)**
- 5 tabs with SVG icon (22dp) + label (10sp), active: `text` color + strokeWidth 2 + fontWeight 600, inactive: `textfaint` + 1.6 + 400
- border-top 1dp `border`, padding 8dp top / 24dp bottom

**TopBar(title, leftslot, rightslot, mono, border)**
- 44dp top padding (status bar clearance) + 10dp bottom, flex between left/right, optional bottom border

**PropertyRow(label, value)**
- label 40% width (12sp, `textsecondary`), value 60% (13sp, `text`), 7dp vertical padding, bottom divider

**FilterChip(label, selected, ondismiss)**
- pill shape, 12dp radius, `chipbg` when active + x icon, 11sp/500

**ModuleCard(name, icon, description, implemented, onclick)**
- 10dp radius, border, 14dp padding, icon container (32dp, 8dp radius), name + description

**StudentRow(avatar, name, roll, batch, gpa, programme, onclick)**
- avatar (36dp) + text block + gpa/programme right, 10dp vertical padding, divider

**SlotPill(code, bg, fg, warn)**
- 4dp radius, 3px 6px padding, code text (10sp/700), optional red left border

**ActionButton(label, icon, onclick)**
- outlined, 6dp radius, `border` outline, 12sp/500, flex-1, icon + label centered

**CellDrawer (bottom sheet for timetable)**
- drag handle, header, slot rows, admin action buttons

**Snackbar(message)**
- not directly in mockups but specified for conflict alerts — `#37352F` bg, white text, 8dp radius, above nav

---

## SessionManager

```kotlin
object SessionManager {
    data class CurrentUser(
        val userid: Long,
        val name: String,
        val role: String, // "admin" | "student"
        val studentid: Long? = null,
        val batchname: String? = null,
    )

    private val _current = MutableStateFlow<CurrentUser?>(null)
    val current: StateFlow<CurrentUser?> = _current.asStateFlow()

    fun login(user: CurrentUser) { _current.value = user }
    fun logout() { _current.value = null }
    val isadmin: Boolean get() = _current.value?.role == "admin"
}
```

---

## Drag-and-Drop (Timetable Admin)

- `Modifier.pointerInput` + `detectDragGesturesAfterLongPress`
- on long-press: start drag, show ghost overlay at drag position
- ghost: semi-transparent SlotPill following finger
- grid cells register their bounds via `onGloballyPositioned`
- on drag move: highlight target cell if valid (empty + not free)
- on drop: hit-test grid cell bounds → if valid, call repository `addslot` → check conflicts → show snackbar if conflict
- invalid drop: no action (or shake animation)

---

## Conflict Detection

```kotlin
data class Conflict(
    val type: String, // "subjectlimit" | "facultyworkload"
    val message: String,
    val subjectid: Long?,
    val facultyname: String?,
)
```

- subject limit: configurable per-subject max periods/week (default 3)
- faculty workload: configurable max periods/week per faculty (default 5)
- check on slot add/move, return list of conflicts
- display: alert banner at top of timetable, red left border on affected cells

---

## Seed Data Details

### Users
| Name | Role | Roll/ID |
|------|------|---------|
| Anita (admin) | admin | — |
| Aarav Sharma | student | 2024101020 |
| Advika Rao | student | 2024101031 |
| Bhavya Kumar | student | 2024102004 |
| Chirag Reddy | student | 2024101055 |
| Diya Sen | student | 2023101120 |
| Eshan Patel | student | 2024103009 |
| Farhan Mirza | student | 2022201010 |

### Batches
CSE-2024 (cap 120), ECE-2024 (cap 60), CSE-2023 (cap 120), CSD-2024 (cap 60), CSE-MS (cap 30)

### Subjects
OS, ALG, HCI (elective), DBMS, CN, SE, MATH — with color pairs from mockups

### Timetable Grid
pre-populated slots matching the GRID data in timetable.jsx — multi-slot cells included

### News
- "Mid-sem schedule posted" (acads, 2h ago)
- "Library hours extended" (admin, yesterday)
- "Campus drive registration open" (placement, 3d ago)
