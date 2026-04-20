# IMS — Implementation Timeline

## Phase 0: Project Setup

- [x] add dependencies to `libs.versions.toml` and `build.gradle.kts`:
  - Room + KSP (room 2.5.2, ksp 1.9.0-1.0.13)
  - Navigation Compose (2.7.7)
  - ViewModel Compose (lifecycle-viewmodel-compose)
- [x] add KSP plugin to project-level and app-level gradle
- [x] add `buildConfigField("String", "APPIDENTIFIER", "\"XXXXXXXXXX.0\"")` and enable `buildFeatures.buildConfig = true`
- [x] create package directory structure under `com.dass.ims/`
- [x] bundle Inter and JetBrains Mono font files in `res/font/`

## Phase 1: Design System & Theme

- [x] `ui/shared/Color.kt` — notion palette constants (bg, surface, border, text, etc. + subject colors + avatar colors)
- [x] `ui/shared/Type.kt` — Inter + JetBrains Mono font families, type scale (display, heading, body, label, caption, mono)
- [x] `ui/shared/Theme.kt` — light-only theme, custom ImsColors via CompositionLocal
- [x] `ui/shared/Icons.kt` — 25+ Lucide icon paths as ImageVector objects, plus bottom nav icon paths
- [x] shared composables:
  - [x] Avatar
  - [x] SearchField
  - [x] SectionHeader (collapsible)
  - [x] TopBar
  - [x] BottomNavBar
  - [x] PropertyRow
  - [x] FilterChip
  - [x] ModuleCard
  - [x] StudentRow
  - [x] SlotPill
  - [x] ActionButton

## Phase 2: Data Layer

- [ ] Room entities: User, Student, Guardian, Course, Subject, Batch, Enrollment, Timetable, Slot, PersonalEvent, News, AttendanceRecord, GradingScheme, GradeCutoff
- [ ] DAOs: StudentDao, UserDao, SlotDao, TimetableDao, SubjectDao, EnrollmentDao, GuardianDao, NewsDao, BatchDao, CourseDao, AttendanceDao, GradingSchemeDao
- [ ] `ImsDatabase.kt` — Room database class with all entities and DAOs
- [ ] repositories: StudentRepository, TimetableRepository, SearchRepository, NewsRepository, SessionRepository
- [ ] `session/SessionManager.kt` — singleton with StateFlow<CurrentUser>
- [ ] `seed/Seeder.kt` — seed callback in RoomDatabase.Callback.onCreate, populate all seed data
- [ ] `domain/model/` — domain stub data classes (Employee, Leave, Transaction, Exam, Message, etc.)

## Phase 3: Navigation Shell

- [ ] `navigation/NavRoutes.kt` — route constants
- [ ] `navigation/ImsNavHost.kt` — NavHost with all routes
- [ ] `ui/login/LoginScreen.kt` — role selection (Admin / Student toggle)
- [ ] `MainActivity.kt` — Scaffold + BottomNavBar + NavHost, observe SessionManager

## Phase 4: Dashboard Module

- [ ] `ui/dashboard/DashboardViewModel.kt` — current user, news, next class logic
- [ ] `ui/dashboard/AdminDashboard.kt` — D-01-A (logo, greeting, search bar, module grid)
- [ ] `ui/dashboard/StudentDashboard.kt` — D-01-S (greeting, next up, my classes, latest news)
- [ ] `ui/dashboard/SearchOverlay.kt` — D-02 (full-screen, grouped results, role-aware)
- [ ] `ui/dashboard/ModuleStub.kt` — D-03 (icon + name + "not available yet")

## Phase 5: Timetable Module

- [ ] `ui/timetable/TimetableViewModel.kt` — slots, conflicts, drag state, unassigned slots
- [ ] `ui/timetable/TimetableGrid.kt` — T-01 (grid layout, sticky headers, slot pills, free cells, admin vs student)
- [ ] `ui/timetable/SlotTray.kt` — unassigned slot cards, add slot card (admin only)
- [ ] drag-and-drop system: pointerInput + detectDragGesturesAfterLongPress, ghost overlay, grid hit-testing
- [ ] conflict detection: subject limit + faculty workload checks, alert banner, red cell borders, snackbar
- [ ] `ui/timetable/CellDrawer.kt` — T-02 (bottom sheet, slot details, admin edit/delete buttons)
- [ ] `ui/timetable/SlotEditScreen.kt` — T-03 (create/edit modal, form fields, type selector)

## Phase 6: Student Details Module

- [ ] `ui/studentdetails/StudentListViewModel.kt` — student list, search, filters
- [ ] `ui/studentdetails/StudentList.kt` — S-01 (search, filter chips, student rows, empty state)
- [ ] `ui/studentdetails/FilterSheet.kt` — S-01b (bottom sheet, multi-select chips, GPA slider, reset/apply)
- [ ] `ui/studentdetails/StudentProfile.kt` — S-02 (header, action buttons, property table, collapsible sections, admin vs student view)

## Phase 7: Polish

- [ ] wire all bottom nav tabs to correct routes per role
- [ ] verify role switching across all screens
- [ ] test all navigation flows (dashboard → module → detail → back)
- [ ] ensure search overlay works from both dashboard search bar and search tab
- [ ] verify timetable drag-and-drop + conflict alerts
- [ ] verify student filters apply/reset correctly
- [ ] clean up unused boilerplate from initial project template
- [ ] final visual review against mockups
