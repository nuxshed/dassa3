# Plan: Phase 2 (Data Layer) + Phase 3 (Screens & Navigation)

## Context

Phase 1 (design system) is complete: Color.kt, Type.kt, Theme.kt, Icons.kt, and 11 shared composables. Now building the full app: Room DB with seed data, navigation, and all 10 screens for Dashboard, Timetable, and Student Details modules.

---

## Phase 2: Data Layer

### 2.1 Entities — `data/local/entity/`

11 entity files. Key fields only (all use `@PrimaryKey val id: Long`):

| Entity | Key fields |
|--------|-----------|
| `UserEntity` | name, email, phone, role ("admin"/"student"), isactive |
| `StudentEntity` | userid→User FK, roll, batchid, courseid, gpa, category (UG/PG), status, dob, enrollmentdate, gender |
| `EmployeeEntity` | userid→User FK, department, designation |
| `GuardianEntity` | studentid→Student FK, name, relation, phone, isemergency |
| `CourseEntity` | name, code, department |
| `SubjectEntity` | name, code (matches subjectcolors keys), courseid FK, iselective, credits |
| `BatchEntity` | name ("CSE-2024"), courseid FK, year |
| `TimetableEntity` | batchid FK, name, semester, isactive |
| `SlotEntity` | timetableid FK, subjectid FK, employeeid FK, day (0-5), period (0-4), room, type, isrecurring, batchname (denormalized) |
| `EnrollmentEntity` | studentid FK, subjectid FK, semester, grade (Int?) |
| `NewsEntity` | title, category, timestamp (Long), content |

### 2.2 Join data classes — `data/local/Joins.kt`

- `StudentWithUser` — student + user name/email/phone + batchname
- `SlotWithDetails` — slot + subjectname/code + employeename
- `EnrollmentWithSubject` — enrollment + subjectname/code/iselective

### 2.3 DAOs — `data/local/dao/`

11 DAOs. Key queries:

- **StudentDao**: `getall()` (join users+batches), `getbyid(id)`, `search(q)` (name/roll LIKE), `update()`
- **SlotDao**: `getbytimetable(ttid)` (join subjects+employees), `insert/update/delete`, `countsubjectslots(ttid, subid)`, `countemployeeslots(ttid, empid)`
- **EnrollmentDao**: `getbystudent(sid)` (join subjects)
- **NewsDao**: `getall()` ordered by timestamp desc
- Others: simple `getall()`, `getbyid()`, `insertall()`

### 2.4 Database — `data/local/ImsDatabase.kt`

Room database with all 11 entities. Singleton via `companion object`. Registers `SeedCallback`.

### 2.5 Seed data — `data/seed/SeedCallback.kt`

`RoomDatabase.Callback.onCreate` populates matching mockup data exactly:

- **Users**: Anita (admin) + 7 students (Aarav, Advika, Bhavya, Chirag, Diya, Eshan, Farhan) + 6 faculty
- **Batches**: CSE-2024, ECE-2024, CSE-2023, CSD-2024 (+ CSE-MS for Farhan PG)
- **Courses**: Computer Science, Electronics
- **Subjects**: OS, ALG, HCI (elective), DBMS, CN, SE, MATH — codes match `subjectcolors` keys
- **Timetable**: 1 active, slots matching the GRID from `timetable.jsx` (~20 slots)
- **Enrollments**: Aarav → OS 78%, ALG 92%, HCI 65%, DBMS 84%
- **Guardians**: R. Sharma (father) for Aarav
- **News**: 3 items (mid-sem, library, campus wifi)

### 2.6 Repositories — `data/repository/`

10 thin classes wrapping DAOs. No interfaces (not needed, no over-abstraction). `SlotRepository` adds `checksubjectlimit()` and `checkemployeeworkload()`.

### 2.7 DI + App — `data/AppContainer.kt` + `ImsApp.kt`

Manual DI: `AppContainer` creates DB + all repos. `ImsApp : Application()` holds `container`. Register in AndroidManifest: `android:name=".ImsApp"`.

### 2.8 Role state — `data/RoleState.kt`

```kotlin
enum class UserRole { ADMIN, STUDENT }
class RoleState {
    var role by mutableStateOf(UserRole.ADMIN)
    var currentuserid by mutableStateOf(1L) // 1=Anita, 2=Aarav
    fun switchrole() { /* toggle role + userid */ }
}
val LocalRole = staticCompositionLocalOf { RoleState() }
```

---

## Phase 3: Navigation + Screens

### 3.1 Navigation — `navigation/ImsNavHost.kt`

Routes as string constants:
```
login, dashboard, search, timetable,
timetable/create/{day}/{period}, timetable/edit/{slotid},
students, students/{studentid},
stub/{module}
```

BottomNavBar tab mapping:
- HOME → dashboard, SEARCH → search, SCHEDULE → timetable
- PEOPLE → students (admin) or students/{selfid} (student)
- ME → students/{currentuserid}

### 3.2 MainActivity update

Replace placeholder with: `ImsApp` container access, `NavController`, `CompositionLocalProvider(LocalRole)`, `ImsNavHost` inside `ImsTheme`.

### 3.3 Screens (10 total)

#### LoginScreen (`ui/login/`) — no ViewModel
Two cards: Admin / Student. Sets `LocalRole`, navigates to dashboard.

#### DashboardScreen + ViewModel (`ui/dashboard/`)
- **Admin (D-01-A)**: logo bar + avatar, SearchField (tap→search), greeting "Good morning, Anita", SectionHeader("Modules"), 2-col grid of 10 ModuleCards (2 impl → navigate, 8 stubs)
- **Student (D-01-S)**: "My Home" topbar, greeting "Hi, Aarav", SearchField, "Next up" card (next class from slots), "My classes" list (enrollment grades), "Latest news" list
- ViewModel loads: current user, news, enrollments (student), next slot (student)

#### SearchScreen + ViewModel (`ui/search/`)
Full-screen overlay. Top search bar with autofocus. Results grouped: Pages, People, Actions. Searches across students/batches/subjects. No BottomNavBar.

#### StubScreen (`ui/stub/`) — no ViewModel
Back chevron + module name. Centered icon (72dp) + name + "not available yet" message. BottomNavBar.

#### TimetableScreen + ViewModel (`ui/timetable/`)
- Header: "TIMETABLE" label, "Week 14 · Nov 18", Grid/List toggle
- Alert banner (admin): "OS exceeds weekly limit (4/3)" if conflicts
- 6×5 grid: time labels (mono, 40dp col), day headers, cells with SlotPills
- Empty cells: admin gets dashed + icon, student gets nothing
- FREE cells: diagonal stripe pattern + "free" text
- Cell tap → ModalBottomSheet showing slot details (edit/delete for admin)
- Admin tray: horizontal scroll of unassigned slots + "Add slot" card
- ViewModel: loads active timetable + slots, computes warnings, handles CRUD

#### SlotEditScreen + ViewModel (`ui/timetable/`)
- Top bar: Cancel / "New slot" / Save (accent)
- Form: Subject, Faculty, Room, Batch (selector fields with chevron), Type (4 toggle chips)
- Info banner: "Day & period pre-filled from selected cell"
- Save validates + inserts/updates slot, pops back

#### StudentListScreen + ViewModel (`ui/studentdetails/`)
- TopBar: "People" + search/filter icons
- Title: "All students" + result count
- Active filter chips (FlowRow, dismissable)
- LazyColumn of StudentRows → tap navigates to profile
- ModalBottomSheet filter: Batch/Year/Gender/Programme chips + GPA RangeSlider + Reset/Apply buttons

#### StudentProfileScreen + ViewModel (`ui/studentdetails/`)
- TopBar: back chevron + msg/more icons
- Header: Avatar(64dp) + name + roll/batch/category + active badge
- Admin: 3 ActionButtons (Message stub, Edit stub, Transfer stub)
- Collapsible sections via SectionHeader:
  - Contact (PropertyRows: email, phone, dob, guardian, advisor, enrolled)
  - Academics (subject pills + grades from enrollments)
  - Attendance (collapsed stub)
  - Fees (collapsed stub)

### 3.4 ViewModel factory — `ui/ViewModelFactory.kt`

Single factory class. Gets `AppContainer` via `(context.applicationContext as ImsApp).container`. Creates all ViewModels with appropriate repos.

---

## File list (new files only)

```
data/
  AppContainer.kt
  RoleState.kt
  local/
    ImsDatabase.kt
    Joins.kt
    entity/  (11 files)
    dao/     (11 files)
  repository/ (10 files)
  seed/
    SeedCallback.kt
navigation/
  ImsNavHost.kt
ui/
  ViewModelFactory.kt
  login/LoginScreen.kt
  dashboard/DashboardScreen.kt, DashboardViewModel.kt
  search/SearchScreen.kt, SearchViewModel.kt
  timetable/TimetableScreen.kt, TimetableViewModel.kt, SlotEditScreen.kt, SlotEditViewModel.kt
  studentdetails/StudentListScreen.kt, StudentListViewModel.kt, StudentProfileScreen.kt, StudentProfileViewModel.kt
  stub/StubScreen.kt
```

**Modified**: `MainActivity.kt`, `AndroidManifest.xml` (add `android:name=".ImsApp"`)

**Total**: ~45 new files + 2 modified

---

## Implementation order

**Phase 2** (data, bottom-up):
1. All 11 entity files
2. Joins.kt
3. All 11 DAOs
4. ImsDatabase.kt
5. SeedCallback.kt
6. All 10 repositories
7. AppContainer.kt + ImsApp.kt + RoleState.kt
8. Update AndroidManifest.xml

**Phase 3** (screens, dependency order):
1. ViewModelFactory.kt
2. ImsNavHost.kt + update MainActivity.kt
3. LoginScreen.kt
4. StubScreen.kt
5. DashboardScreen.kt + DashboardViewModel.kt
6. SearchScreen.kt + SearchViewModel.kt
7. TimetableScreen.kt + TimetableViewModel.kt
8. SlotEditScreen.kt + SlotEditViewModel.kt
9. StudentListScreen.kt + StudentListViewModel.kt
10. StudentProfileScreen.kt + StudentProfileViewModel.kt

## Verification

- Build: `./gradlew assembleDebug` (user runs manually)
- Visual testing: user runs on emulator/device
- Check: seed data loads on first launch, role switching works, all navigation routes reachable, bottom nav consistent
