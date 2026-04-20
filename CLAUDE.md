# IMS — Institute Management System

Android app for DASS Assignment 3. Jetpack Compose, Kotlin, local in-memory/Room DB.

---

## Project Scope

### Modules to implement end-to-end
- **Dashboard** (mandatory)
- **Time Table** (Category A)
- **Student Details** (Category B)

All other modules exist as stubs in the UML / navigation only — no full implementation required.

### Users
Two roles: **Admin** and **Student**. The app supports easy role switching (no real auth server — simulated via a role toggle in settings or login screen).

---

## Architecture

```
app/
├── data/
│   ├── local/          # Room DB, DAOs, entities
│   ├── repository/     # repository interfaces + impls
│   └── seed/           # seed data for stubs
├── domain/
│   └── model/          # pure Kotlin domain models (mirrors UML)
├── ui/
│   ├── dashboard/
│   ├── timetable/
│   ├── studentdetails/
│   └── shared/         # shared composables, theme
├── navigation/         # NavHost + routes
└── MainActivity.kt
```

- **Pattern**: MVVM — `ViewModel` + `StateFlow` per screen
- **DB**: Room for persistence; seed data on first launch
- **No server** — all data is local, stubs return hardcoded/seeded values
- **Navigation**: single-activity, Jetpack Navigation Compose

---

## Code Style

- Simple, clean, minimal — no overengineering
- No redundant comments — only docstrings above functions when needed
- Concise docstrings: lowercase, no full stops
- **All lowercase naming, no camelCase, hyphens, or underscores**
  - `emailverify` not `email_verify`, `getuserrole` not `get_user_role`
  - applies to functions, variables, and where Kotlin allows — use judgment for class names (PascalCase is idiomatic Kotlin and is fine)
- Short, human-sounding names — no AI-like variable names
- No redundancies, consistent style throughout
- Minimal diffs — only change what's needed
- Avoid over-abstraction — if a helper is used once, inline it

---

## Design Handoff

Figma handoff is in `design/`. Read the README there before touching any UI.
Composables must match the high-fidelity mockups closely.
Use the design tokens (colors, typography, spacing) from the handoff — no hardcoded values.

WE NEED TO EXACTLY REPLICATE THE DESIGN HANDOFF PACKAGE
MAKE SURE TO ENSURE THIS TO THE BEST OF YOUR ABILITIES

---

## Documents

You have access to the
- assignment writeup (docs/assignment.md)
- uml design (docs/uml.md)
- answers to the doubts document (docs/answers.md)
- design document (docs/design.md)

THE DESIGN HANDOFF IN design/ takes priority over design.md

---

## Domain Model (from UML)

Key classes to implement as Room entities or domain models:

**User hierarchy**: `User` → `Student`, `Employee`, `Guardian`
**Academic**: `Course`, `Subject`, `Batch`, `Enrollment`, `Timetable`, `Slot`
**Student**: `AttendanceRecord`, `Leave` (StudentLeave/EmployeeLeave), `Result`
**Finance (stub)**: `Transaction` → `Fees`, `Payslip`, `Donation`, `Expense`
**Exam (stub)**: `Exam`, `Question`, `GradingScheme`, `GradeCutoff`, `Result`
**Comms (stub)**: `Message`, `News`, `Notification`
**HR (stub)**: `Department`, `LeaveApproval`
**Other**: `Role`, `Permissions`, `Dashboard`, `AdmissionForm`, `FormSubmission`

Stubs = class + function headers, no logic needed.

---

## Module Specs

### Dashboard
- Global search bar — navigates anywhere in the app (users, modules, screens)
- Role-aware widget layout (admin sees management widgets, student sees personal info)
- Latest news feed on login (seeded stub data fine)
- Quick nav cards to all modules (stubs for unimplemented ones)
- Settings: language toggle (mechanism only, no real i18n needed), timezone, country, currency
- General settings: grading system selector, auto unique ID toggle
- SMS alerts: UI only, stub the send action
- Admin: manage courses, batches, subjects (electives), batch transfers
- Admin: manage student categories, graduation

### Time Table
**Admin view**:
- Create/edit/delete timetables
- Drag-and-drop slot assignment (courses → time slots)
- Real-time conflict detection (subject limits/week, employee workload)
- Slot types: lecture, tutorial, lab, exam
- Recurring vs one-off slots
- Advance scheduling (future timetables)

**Student view**:
- View own timetable
- Add personal events to calendar
- Set reminders

### Student Details
**Admin view**:
- List students by batch
- Search existing + former students
- Advanced filters (batch, category, status, enrollment date, etc.)
- View/edit full student profile
- Previous education history
- Guardian details (multiple guardians, emergency contacts)
- Upload student photo (local file picker, store URI)
- Batch transfer

**Student view**:
- View own profile
- View own attendance, results (from stubs)

---

## Navigation Routes

```
/login
/dashboard
/timetable
/timetable/create
/timetable/{id}/edit
/students
/students/{id}
/students/{id}/edit
/<stub_module>   ← placeholder screens for all other modules
```

---

## Seeded Data

Seed on first launch:
- 2 roles: Admin, Student
- 1 admin user, 5 students across 2 batches
- 2 courses, 4 subjects (1 elective)
- 1 timetable with 6 slots
- 3 news items
- Basic grading scheme (percentage-based)

---

## BuildConfig

In `build.gradle.kts`:
```kotlin
buildConfigField("String", "APPIDENTIFIER", "\"<rollnumber>.0\"")
```
Replace with actual roll number(s) before submission.

---

## What NOT to do

- No Flutter, React Native, or XML layouts — Compose only
- No external servers or network calls
- No pushing build/ or generated files to git
- No verbose comments or docs
- No full implementation of stub modules
- No hardcoded colors/spacing — use theme tokens from design handoff

### Instructions
DO NOT RUN AN EMULATOR, ALL VISUAL TESTING WILL BE DONE BY ME MANUALLY.
