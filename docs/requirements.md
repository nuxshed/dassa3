# IMS — Requirements

## Global / Cross-cutting

- two roles: admin and student, simulated via role toggle (no real auth)
- local-only data using Room DB, seed on first launch
- jetpack compose only, MVVM + StateFlow per screen
- single-activity architecture with jetpack navigation compose
- BuildConfig field `APPIDENTIFIER` set to `"XXXXXXXXXX.0"` (placeholder)
- design system from `design/` handoff — notion-inspired palette and tokens
- bottom navigation: Home, Search, Schedule, People, Me
- phone frame reference: 390x844pt

---

## Dashboard (mandatory)

### D-01-A — Admin Dashboard

- top bar: IMS logo (black rounded square with "i" in JetBrains Mono) + "IMS" wordmark + user avatar (32dp)
- greeting block: "Good morning, [Name]" (24sp/700) + context line (pending approvals, schedule conflicts)
- search bar: full-width, 40dp, `#F7F7F5` bg, `#EDEDEC` border, search icon leading, placeholder text
- section header: "MODULES" (collapsible, uppercase, 11sp/600)
- 2-column grid of 10 module cards:
  - **implemented** (bg `#FFFFFF`, full opacity): Timetable (`cal`), Student Details (`users`)
  - **stubs** (bg `#F7F7F5`, 0.82 opacity): Attendance (`clip`), Examinations (`file`), Finance (`bank`), Messages (`msg`), News (`paper`), Human Resources (`brief`), Manage Users (`userCog`), Admission (`userPlus`)
- each card: 32dp icon container (8dp radius), module name (13sp/600), one-line description (11sp), 10dp radius border, min-height 92dp
- implemented cards appear first in grid

### D-01-S — Student Dashboard

- top bar: "My Home" (15sp/600) left, "student . CSE-2024" (11sp, mono) right
- greeting: "Hi, [FirstName]" (28sp/700) + day/class/assignment summary (13sp)
- search bar: same component, different placeholder
- **Next Up** section: bordered card (10dp radius) with time (12sp, mono), subject (16sp/600), lecture type + professor (12sp), room chip (`#F1F1EF` bg, 6dp radius)
  - empty state: "No more classes today"
- **My Classes** (collapsible): subject tag (26dp square, mono), subject name (14sp), elective label inline, grade % right (12sp, mono, tabular nums)
- **Latest News** (collapsible): title (13sp/600) + source/timestamp (11sp)
- **Quick Links** (collapsible, collapsed by default): empty in prototype

### D-02 — Universal Search Overlay

- full-screen overlay, white background
- top: search icon + text input (16sp) + "esc" badge
- results grouped by category headers (11sp, uppercase, 600):
  - **Pages**: file icon (28dp container) + label + metadata right
  - **People**: avatar (28dp) + name + roll/meta (mono) + role label
  - **Actions**: icon container + action label (admin-only group, hidden for students)
- bottom hints bar: keyboard navigation hints (mono)
- empty state: "No results" centered
- dismiss: esc button or back gesture

### D-03 — Module Stub

- top bar: back chevron + module name (15sp/600)
- centered content: 72dp icon container (16dp radius, `#F7F7F5` bg, `#EDEDEC` border), module name (18sp/600), "This module is not available yet. Check back in a future release." (13sp)
- bottom nav visible

### Role Switching

- login/role selection screen at app start
- admin or student selection → sets session role
- SessionManager singleton with MutableStateFlow

---

## Timetable (Category A)

### T-01 — Timetable Grid

- header: "TIMETABLE" / "MY SCHEDULE" label (11sp uppercase) + "Week 14 . Nov 18" (20sp/700)
- grid/list segmented control (top-right): "Grid" selected (white bg, shadow), "List" inactive
- **grid structure**: 6 days (Mon–Sat) x 5 periods
  - periods: 8:30–9:55, 10:05–11:30, 11:40–1:05, 2:00–3:25, 3:35–5:00
  - free slots: Wed & Sat last 2 periods (diagonal stripe pattern, "free" label italic)
- period column: 40dp wide, `#F7F7F5` bg, time split across 2 lines (8sp, mono)
- day header row: `#F7F7F5` bg, 10sp uppercase
- **cell states**:
  - empty (admin): dashed circle with `+` icon center
  - empty (student): blank
  - filled: SlotPill(s) — subject code, colored bg/fg, 4dp radius, 10sp/700
  - free: diagonal stripe fill, "free" italic label
  - limit exceeded: red left border 2dp (`#E03E3E`)
- **multi-slot cells**: multiple SlotPills stacked vertically (2dp gap)
- tap filled cell → opens cell drawer (T-02)

### T-01 Admin-only Features

- **alert banner**: `#FDECE5` bg, `#F5C9B6` border, warning icon, "[Subject] exceeds weekly limit (N/M)", "View" link underlined
- **unassigned tray** (below grid):
  - header: "UNASSIGNED" (11sp uppercase) + "drag onto grid" hint
  - slot cards: 120dp wide, border, 8dp radius, subject code pill + full name + room/prof (mono, 9sp)
  - grip icon on each card
  - last item: dashed border "+ Add slot" card → opens T-03
- drag-and-drop: long-press card → drag to grid cell

### T-02 — Cell Drawer (Bottom Sheet)

- drag handle (36x4dp, `#E3E2E0`)
- header: day + period (13sp, `#787774`) + "N classes this slot" (15sp/700)
- per-slot row: subject code pill (colored) + full name (14sp/600) + room/prof/batch (12sp) + type tag (10sp, pill)
  - warning tag for limit exceeded: `#FDECE5` bg, `#8B3A2C` text
- admin: pencil + trash icons per slot, "+ Add another class here" dashed button
- student: read-only, no action buttons

### T-03 — Create / Edit Slot (Modal)

- top bar: "Cancel" (14sp, `#787774`) + "New slot" (15sp/600) + "Save" (14sp/600, `#2383E2`)
- form fields (each: uppercase label 11sp + input container with `#F7F7F5` bg, `#EDEDEC` border, 8dp radius, chevron right):
  - Subject, Faculty, Room, Batch
- type selector: horizontal chips — Lecture (selected: `#37352F` bg, white text), Tutorial, Lab, Exam
- info banner: `#FFF9E6` bg, `#F3DFA2` border, "Day & period pre-filled from selected cell"

### Subject Colors (from mockups)

| Code | Background | Foreground |
|------|-----------|-----------|
| OS   | `#FDECC8` | `#7C4A03` |
| ALG  | `#DBEDDB` | `#1E6B3B` |
| HCI  | `#E8DEEE` | `#5D3A80` |
| DBMS | `#D3E5EF` | `#1D4F6E` |
| CN   | `#FFE2DD` | `#8B3A2C` |
| SE   | `#EEE0DA` | `#6B4A3E` |
| MATH | `#E3E2E0` | `#3C3A36` |

### Conflict Detection

- subject limit: alert if subject exceeds N periods/week
- faculty workload: alert if faculty is overloaded
- triggered on slot assignment, shown as banner + red cell border

---

## Student Details (Category B)

### S-01 — Student List

- top bar: "People" (15sp/700) + search & filter icons (20dp)
- heading: "All students" (24sp/700) + "[N] results . [N] filters active" (12sp)
- **filter chips** (below heading): pill-shaped (`#F1F1EF` bg, 12dp radius, 11sp/500), each with x to remove
- **student rows**: avatar (36dp, colored bg/fg per student) + name (14sp/500) + roll (mono, 11sp) + batch + GPA (12sp, mono) + programme label (10sp)
  - rows separated by `#F1F1EF` divider
  - tap → S-02

### S-01b — Filter Sheet (Bottom Sheet)

- drag handle + "Filters" header (16sp/600) + "N active" count
- filter groups (each: uppercase label 11sp + chip options):
  - **Batch**: multi-select chips (CSE-2024, CSE-2023, ECE-2024, CSD-2024)
  - **Year**: 1–5
  - **Gender**: Male, Female, Other
  - **Programme**: UG, PG, PhD
  - **GPA**: dual-handle range slider (0.0–10.0)
- selected chip: `#37352F` bg, white text; unselected: `#FFFFFF` bg, border
- buttons: "Reset all" (outlined) + "Apply filters" (filled `#37352F`)

### S-02 — Student Profile

- top bar: back chevron + message & more icons (admin) / no icons (student)
- **header**: avatar (64dp) + name (22sp/700) + roll (mono) + batch + programme + status badge ("Active": green `#E0F0E5` bg, `#1E6B3B` dot+text)
- **action buttons** (admin only): Message (`msg`), Edit (`pencil`), Transfer (`enter`) — outlined, equal flex, 6dp radius
- **Contact section** (collapsible): property rows — label 40% (12sp, `#787774`), value 60% (13sp, `#37352F`)
  - fields: Email, Phone, Date of birth, Guardian, Advisor, Enrolled
  - rows separated by `#F1F1EF` divider
- **Academics section** (collapsible, expanded by default): subject code pill (colored) + name (13sp) + grade % (12sp, mono)
- **Attendance section** (collapsible, collapsed — stub)
- **Fees section** (collapsible, collapsed — stub)
- student self-view: same layout, no action buttons, no back button (tab root)

---

## Stub Modules (UI only)

screens for: Attendance, Examinations, Finance, Messages, News, Human Resources, Manage Users, Admission — all use D-03 stub template

---

## Navigation Routes

| Route | Screen | Role |
|-------|--------|------|
| `login` | role selector | both |
| `dashboard` | D-01-A or D-01-S | both |
| `search` | D-02 | both |
| `timetable` | T-01 | both |
| `timetable/create` | T-03 | admin |
| `timetable/{id}/edit` | T-03 | admin |
| `students` | S-01 | admin |
| `students/{id}` | S-02 | both |
| `students/{id}/edit` | S-02 edit | admin |
| `stub/{module}` | D-03 | both |
| `me` | S-02 self | student |

---

## Seed Data

- 2 roles: Admin, Student
- 1 admin user (Anita), 5+ students across 2+ batches (CSE-2024, ECE-2024, CSE-2023, CSD-2024, CSE-MS)
  - students: Aarav Sharma, Advika Rao, Bhavya Kumar, Chirag Reddy, Diya Sen, Eshan Patel, Farhan Mirza
- 2 courses, 7 subjects (OS, ALG, HCI, DBMS, CN, SE, MATH — HCI is elective)
- 1 timetable with slots matching the grid data from mockups
- 3 news items (mid-sem schedule, library hours, etc.)
- guardians per student, enrollment records, attendance stubs
- grading scheme (percentage-based)
- avatar colors per student from mockup data
