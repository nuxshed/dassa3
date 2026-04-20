# IMS — UI/UX Design & Requirements Document

**Modules:** Dashboard · Timetable · Student Details  
**Platform:** Android (Jetpack Compose)  
**Version:** 1.1

---

## 1. Overview

This document specifies the UI/UX design for the IMS Android app prototype. It covers three modules — Dashboard, Timetable, and Student Details — with full screen-by-screen specifications, component behavior, visual design system, and usability rationale.

The app supports two roles: **Admin** and **Student**. The same screen shell adapts its affordances based on role. Role context is set at session start via a simple role selector (prototype only — no auth flow).

---

## 2. Design System

### 2.1 Visual Language

Plain, document-like, no decoration. No gradients, no shadows, no colored accents. Feels like a well-structured note-taking tool rather than a branded product. Every element earns its place through function. Inspired directly by Notion's desktop and mobile layout language.

### 2.2 Color Tokens

| Token | Value | Usage |
|---|---|---|
| `background` | `#FFFFFF` | All screen backgrounds |
| `surface` | `#F9FAFB` | Cards, input fields, table headers |
| `border` | `#E5E7EB` | Dividers, input outlines, cell borders |
| `text-primary` | `#111111` | Body text, headings |
| `text-secondary` | `#6B7280` | Labels, captions, placeholders |
| `text-disabled` | `#D1D5DB` | Disabled states |
| `destructive` | `#EF4444` | Workload alerts, warnings |
| `success` | `#22C55E` | Confirmation toasts |

No accent color. Interactive elements are distinguished by weight, underline, or border — not color.

### 2.3 Typography

Typeface: **Inter** (variable). Single family, all weights.

| Style | Size | Weight | Usage |
|---|---|---|---|
| Display | 24sp | 700 | Screen titles, student name on profile |
| Heading | 18sp | 600 | Section headers, module card titles |
| Body | 15sp | 400 | List items, detail text |
| Label | 13sp | 500 | Input labels, table column headers, tags |
| Caption | 12sp | 400 | Timestamps, secondary metadata |
| Mono | 13sp | 400 | Roll numbers, IDs (Inter Mono) |

### 2.4 Spacing & Layout

- Base unit: **8dp**. All spacing is a multiple of 8.
- Screen padding: 16dp horizontal, 16dp top.
- Corner radius: **8dp** universally.
- Borders: 1dp, `#E5E7EB`. No shadows.
- Dividers: 1dp, `#F3F4F6`, full-bleed.

### 2.5 Iconography

Lucide icon set. Stroke width 1.5, size 20dp. Stroke-only — no filled icons. Never used as a standalone button without a label in primary flows.

### 2.6 Interactive States

| State | Treatment |
|---|---|
| Default | Transparent background |
| Pressed | `#F3F4F6` background (ripple) |
| Focused (input) | Border → `#111111` |
| Disabled | 40% opacity, non-interactive |
| Selected | 1dp border `#111111`, `#F9FAFB` background |

### 2.7 Section Labels

Collapsible section headers (used on profile and student home) follow this pattern:

- All-caps, Label style, `text-secondary`
- Chevron icon left of label: `▼` expanded, `▶` collapsed
- Tap anywhere on the row to toggle
- 16dp vertical padding on the row

---

## 3. Design Inspirations

### 3.1 Notion

Primary reference for overall layout, information density, and interaction patterns.

- Full-bleed content blocks, no card shadows
- Divider-based section separation
- Property-row layout for detail views (label left, value right)
- Collapsible section headers with inline chevrons
- Inline action menus appearing on demand rather than always-visible

### 3.2 Google Calendar

Reference for the Timetable grid.

- Fixed day-column headers that stay during vertical scroll
- Time labels pinned to the left axis
- Occupied vs empty cell contrast
- Drag handle affordance for rescheduling

### 3.3 Raycast

Reference for the universal search overlay.

- Command-palette style: one input field surfaces all entity types
- Result rows grouped by type (Pages, People, Actions) with a category label
- Each row: icon + primary label + secondary metadata string, minimal chrome
- Keyboard hints at the bottom of the overlay (translated to gesture hints on mobile)

---

## 4. Usability Principles

### 4.1 Visibility of System Status (Nielsen #1)

Every async action gives immediate feedback. Drag-and-drop on the timetable shows a ghost at drag origin and a highlighted target cell. Slot limit alerts appear as snackbars within 200ms of violation. Student list search shows a loading skeleton, not a blank screen.

### 4.2 Recognition over Recall (Nielsen #6)

The dashboard shows all modules at a glance. The timetable slot creation form pre-fills day and period from the cell tapped. Active filters appear as chips above the student list so the user always sees what is applied. Search results are grouped by type so users recognize result categories without reading carefully.

### 4.3 Aesthetic and Minimalist Design (Nielsen #8)

No screen surfaces information that isn't immediately relevant. Module cards show only a name and icon. Timetable cells show only subject + room. Student list rows show only name, batch, and roll number. Full detail is always one tap away.

---

## 5. Navigation

**Model:** Bottom Navigation Bar, 5 tabs, always visible (hides during active timetable drag, reappears on drop).

| Tab | Icon | Destination | Notes |
|---|---|---|---|
| Home | `house` | Dashboard (role-aware) | — |
| Search | `search` | Universal search overlay | Opens directly to search input |
| Schedule | `calendar-days` | Timetable grid | — |
| People | `users` | Student list (admin) / own profile (student) | — |
| Me | `user` | Own profile | — |

Within Timetable, a segmented control at the top switches between **Grid** and **Slot List** views. This is not a separate tab.

---

## 6. Dashboard — Admin

**Screen ID:** D-01-A

### Layout

- Top bar: `IMS` wordmark (Inter, 18sp, 600) left · user avatar right (32dp circle, initials)
- Search bar: full-width, 44dp height, `search` icon leading, placeholder `Search students, slots, modules…`
- Section label: `MODULES` below search bar
- 2-column grid of module cards, equal height, vertically scrollable

### Module Cards

Each card:
- 16dp internal padding
- `lucide` icon 24dp top-left
- Module name (Heading style) below icon
- One-line description (Caption, `text-secondary`) below name
- Tap → navigates to module or stub screen

| Module | Icon | Status |
|---|---|---|
| Timetable | `calendar-days` | Implemented |
| Student Details | `users` | Implemented |
| Attendance | `clipboard-check` | Stub |
| Examinations | `file-text` | Stub |
| Finance | `banknote` | Stub |
| Messages | `message-square` | Stub |
| News | `newspaper` | Stub |
| Human Resources | `briefcase` | Stub |
| Manage Users | `user-cog` | Stub |
| Admission | `user-plus` | Stub |

Implemented modules appear first in the grid.

---

## 7. Dashboard — Student

**Screen ID:** D-01-S

The student dashboard is a distinct layout from the admin dashboard. Same shell (top bar, bottom nav, search bar) but content below search is contextual rather than a module grid.

### Layout

```
[top bar: "My Home"  |  student · CSE-2024]

Hi, Aarav
Tue · 3 classes · 1 assignment due          ← Caption, text-secondary

[search bar: "Search classes, people, notes…"]

NEXT UP
┌──────────────────────────────────────┐
│  10:00  OS — Lecture          H-105  │
└──────────────────────────────────────┘

▼ MY CLASSES
  □ Operating Systems              78%
  □ Algorithms                     92%
  □ HCI (elective)                 65%

▼ LATEST NEWS
  Mid-sem schedule posted
  acads · 2h ago

▶ QUICK LINKS
```

### Component Details

**Top bar:** `My Home` wordmark left (monospace, 15sp) · batch context right (`student · CSE-2024`, Caption, `text-secondary`).

**Greeting block:**
- `Hi, [First name]` — Display style, bold
- Day · class count · assignment due summary — Caption, `text-secondary`
- No avatar on this screen (it's the home, not profile)

**Next Up card:**
- Border, 8dp radius, 16dp padding
- Time (18sp, 700, mono) + subject — Lecture type · Room right-aligned (Caption, `text-secondary`)
- If no next class: card reads `No more classes today` in Caption, `text-secondary`

**My Classes (collapsible):**
- Section label pattern (see 2.7)
- Each row: small checkbox-style icon left · subject name (Body) · grade % right (Caption, `text-secondary`)
- Electives tagged with `(elective)` inline

**Latest News (collapsible):**
- Each row: news title (Body, bold) · source + timestamp below (Caption, `text-secondary`)
- Stub content for prototype — 2–3 hardcoded items

**Quick Links (collapsible):**
- Stub — collapsed by default, empty when expanded in prototype

---

## 8. Universal Search Overlay

**Screen ID:** D-02  
**Triggered by:** Tapping search bar on any dashboard, or Search tab in bottom nav.

### Layout

Full-screen overlay, slides up from bottom (300ms ease). White background.

```
┌─────────────────────────────────────┐
│  🔍  batch cse 2024  |         esc  │
├─────────────────────────────────────┤
│ PAGES                               │
│  □  CSE-2024 — Batch page  Batches  │
│  □  CSE-2024 Timetable  Timetable   │
│                                     │
│ PEOPLE                              │
│  ○  Aarav Sharma · 2024101020  Student│
│  ○  Advika Rao · 2024101031    Student│
│  ○  Prof. Meera Iyer (advisor) Faculty│
│                                     │
│ ACTIONS                             │
│  →  Go to Batch CSE-2024            │
│  +  New admission · CSE-2024        │
│  □  Message Batch CSE-2024          │
└─────────────────────────────────────┘
```

### Behavior

- Input auto-focused on open
- Results grouped: **Pages**, **People**, **Actions** — each group has a Label-style all-caps category header
- Each result row: icon · primary label · secondary metadata right-aligned (Caption, `text-secondary`)
- Empty state: `No results` — Caption, centered, no illustration
- Dismiss: `esc` button top-right, or back gesture
- Student role: Actions group hidden (read-only)

---

## 9. Timetable — Grid View

**Screen ID:** T-01

### Structure

| Dimension | Values |
|---|---|
| Days | Mon · Tue · Wed · Thu · Fri · Sat |
| Periods | 8:30–9:55 · 10:05–11:30 · 11:40–1:05 · 2:00–3:25 · 3:35–5:00 |
| Free slots | Wed 2:00–5:00 · Sat 2:00–5:00 (last 2 periods, always locked) |

### Layout

- Sticky left column: time range labels, 56dp wide
- Sticky top row: day abbreviations (Mon–Sat)
- Grid body: scrolls horizontally (days) and vertically (periods)
- Cell size: 80dp wide × 72dp tall

### Cell States

| State | Appearance |
|---|---|
| Empty | Dashed border `#D1D5DB`, no label |
| Filled | Subject (Body, bold) + room (Caption, `text-secondary`) |
| Free | Solid border `#E5E7EB`, `#F3F4F6` diagonal stripe fill, `Free` label (Caption) |
| Drag-over (valid) | Solid 1dp border `#111111` |
| Drag-over (invalid) | No highlight, shake on drop attempt |
| Limit exceeded | Red left border 3dp `#EF4444` |

### Slot Tray (Admin only)

Horizontally scrollable row below the grid.

- Each slot card: 160dp wide, border, 8dp radius, subject (Body, bold) + room (Caption)
- Long-press → activates drag mode
- During drag: card at origin becomes 50% opacity ghost; solid preview follows finger
- Valid target: empty, non-free cell
- Invalid target: filled or free cell — drop rejected, shake animation
- Successful drop: slot placed in cell, card removed from tray
- Last item in tray: dashed-border `+ Add slot` card → opens T-03

### Alerts

- **Subject limit exceeded:** snackbar — `[Subject] exceeds weekly limit (N periods)`
- **Employee workload exceeded:** snackbar — `[Employee] is overloaded this week`
- Both triggered on drop, duration 4s, dismissable, appear above bottom nav
- Affected cells get red left border until the slot is moved or removed

### Role Differences

| Feature | Admin | Student |
|---|---|---|
| Slot tray | Visible | Hidden |
| Drag & drop | Enabled | Disabled |
| Tap filled cell | Opens detail sheet + Edit/Remove | Opens detail sheet (read-only) |
| Long-press filled cell | Context menu: Edit, Remove | No action |

---

## 10. Timetable — Slot Detail Sheet

**Screen ID:** T-02  
**Triggered by:** Tapping a filled cell.

Bottom sheet (not a new screen). Drag handle at top, 16dp top corners radius.

| Field | Notes |
|---|---|
| Subject | Full name, Heading style |
| Room | Body |
| Day + Period | e.g. `Monday · 8:30–9:55`, Caption |

Admin sees **Edit** and **Remove** text buttons at the bottom of the sheet.  
Student sees the sheet as read-only with no action buttons.

---

## 11. Timetable — Create / Edit Slot

**Screen ID:** T-03  
**Triggered by:** `+ Add slot` card in tray, or Edit on T-02.

Full-screen modal (slides up). Top bar: `Cancel` text button left · `Save` text button right. No filled buttons.

| Field | Input | Validation |
|---|---|---|
| Subject | Text field | Required, max 60 chars |
| Room | Text field | Required, max 20 chars |

On save: slot card appears in tray (create), or cell updates in place (edit). Modal closes.

---

## 12. Student List

**Screen ID:** S-01  
**Access:** People tab (admin) · Module card (admin)

### Layout

- Search bar: `Search by name or roll number`
- Filter button top-right (`sliders-horizontal` icon) → opens filter sheet
- Active filter chips below search bar, each with `×` to remove individually
- Student list, vertically scrollable

### Student Row

```
[avatar]  Aarav Sharma                    →
          2024101020 · CSE-2024
```

- Avatar: 40dp circle, initials, `#F3F4F6` fill, `#6B7280` text
- Name: Body, bold
- Roll · Batch: Caption, Mono for roll number, `text-secondary`
- Tap → S-02

### Advanced Filter Sheet

| Filter | Input |
|---|---|
| Batch | Multi-select chips |
| Year | Multi-select chips (1 · 2 · 3 · 4 · 5) |
| Gender | Multi-select chips (Male · Female · Other) |
| GPA range | Dual-handle range slider (0.0–10.0, step 0.1) |
| Degree programme | Multi-select chips |

- `Reset all` text button left · `Apply` text button right at sheet bottom
- Results update on Apply only, not live

### Empty State

`No students match your filters` — Caption, centered. Shows active filter summary below.

---

## 13. Student Profile

**Screen ID:** S-02  
**Access:** Tap row on S-01 (admin) · People tab (student) · Me tab (student)

Two variations of the same screen. Admin view includes action buttons. Student self-view does not.

### Header

```
┌─────┐
│  A  │   Aarav Sharma                    ← Display, 700
└─────┘   2024101020 · CSE-2024 · UG      ← Caption, Mono + Label

         [Message]  [Edit]  [Transfer batch]  [···]   ← Admin only
```

- Avatar: 64dp circle, initials, `#F3F4F6` fill, `#6B7280` text
- Name: Display (24sp, 700)
- Roll number: Mono, Caption, `text-secondary` · Batch · Degree — inline, separated by `·`
- Action buttons (admin view only): outlined text buttons, Label style, 8dp radius, border `#E5E7EB`, horizontally scrollable row. Buttons: **Message**, **Edit**, **Transfer batch**, **···** (overflow)

### Property Table

Rendered below header, above collapsible sections. Notion-style property rows: label left (`text-secondary`, Label style, fixed 40% width) · value right (Body, `text-primary`).

Full-width, 1dp bottom border on each row (`#F3F4F6`), 44dp min row height, 16dp horizontal padding.

**Contact & Personal**

| Label | Value |
|---|---|
| Email | aarav.sharma@iiit.ac.in (tappable mailto) |
| Phone | +91 98•• •• ••12 (tappable tel) |
| Date of birth | 12 Mar 2006 |
| Category | UG · General |
| Enrolled | 04 Aug 2024 |
| Guardian | Rakesh Sharma (father) |
| Advisor | Dr. Meera Iyer |
| Status | Active |

### Collapsible Sections

Below the property table. Follow section label pattern (2.7).

**▼ ACADEMICS**

Each row: small `□` icon left · subject name (Body) · grade % right (Caption, `text-secondary`).

```
  □  Operating Systems          78%
  □  Algorithms                 92%
  □  HCI (elective)             65%
```

**▶ ATTENDANCE** — Stub. Collapsed by default in prototype.

**▶ FEES** — Stub. Collapsed by default in prototype.

### Role Differences

| Element | Admin view (S-02 via S-01) | Student self-view (Me tab) |
|---|---|---|
| Action buttons | Visible (Message, Edit, Transfer batch, ···) | Hidden |
| Property table | Full | Full |
| Collapsible sections | Academics expanded, others collapsed | Academics expanded, others collapsed |
| Back button | Returns to S-01 | Not shown (tab root) |

---

## 14. Stub Screens

Messages tab and all non-implemented module cards lead to a consistent stub:

- Module icon centered, 48dp
- Module name, Heading, centered
- `This module is not available yet` — Caption, `text-secondary`, centered

No buttons, no navigation away. No error iconography.

---

## 15. Component Specifications

| Component | Spec |
|---|---|
| `SearchBar` | Full-width, 44dp height, 8dp radius, border `#E5E7EB`, leading `search` icon 20dp, trailing `×` when filled |
| `ModuleCard` | Border, 8dp radius, 16dp padding, icon + title + description, tap ripple, equal grid height |
| `TimetableCell` | 80×72dp, states: empty / filled / free / drag-over / limit-exceeded |
| `SlotCard` | 160dp wide, border, 8dp radius, subject bold + room caption, long-press drag |
| `StudentRow` | Full-width, 56dp min height, avatar + name + roll + batch, tap navigates |
| `FilterChip` | Inline, 8dp radius, border, Label text, `×` when active |
| `PropertyRow` | Full-width, 44dp min height, label 40% / value 60%, 1dp bottom border |
| `Snackbar` | Full-width minus 16dp margin, 8dp radius, `#111111` bg, white text, 4s auto-dismiss, above nav bar |
| `BottomSheet` | Drag handle top-center, 16dp top radius, white bg, modal scrim `#00000033` |
| `Avatar` | Circle, initials from first+last, `#F3F4F6` fill, `#6B7280` text — 40dp (list), 64dp (profile) |
| `SectionHeader` | All-caps Label, `text-secondary`, chevron left, full-row tap target, 16dp vertical padding |
| `ActionButton` | Outlined, Label style, 8dp radius, border `#E5E7EB`, 12dp horizontal padding, 36dp height |

---

## 16. Screen Inventory

| ID | Name | Access | Role |
|---|---|---|---|
| D-01-A | Dashboard — Admin | Home tab | Admin |
| D-01-S | Dashboard — Student | Home tab | Student |
| D-02 | Search Overlay | Search bar tap / Search tab | Both |
| D-03 | Module Stub | Stub module card tap | Both |
| T-01 | Timetable Grid | Schedule tab | Both |
| T-02 | Slot Detail Sheet | Tap filled cell on T-01 | Both |
| T-03 | Create / Edit Slot | Add slot card or Edit on T-02 | Admin |
| S-01 | Student List | People tab (admin) | Admin |
| S-02 | Student Profile | Row on S-01 / People tab (student) / Me tab | Both |
| M-01 | Messages Stub | Messages tab | Both |

---

## 17. Figma Notes

- **Frame size:** 390×844pt (standard Android / iPhone 14 baseline)
- **Low-fi wireframes:** all 10 screens, greyscale, Auto Layout, no styling, annotations on behavior
- **High-fi mockups:** full design system applied, Inter font loaded, realistic stub content
- **Components to build first:** SearchBar · ModuleCard · TimetableCell · SlotCard · StudentRow · FilterChip · PropertyRow · Snackbar · BottomSheet · Avatar · SectionHeader · ActionButton — each with all state variants
- **Prototype flows:** D-01-A→D-02, D-01-A→T-01, D-01-A→S-01, T-01→T-02, T-01→T-03, S-01→S-02, all bottom nav tabs bidirectional
- **Usability principle callouts:** annotate at least 3 frames in Figma showing which principle applies and how

---

*IMS · DASS A3 · UI/UX Design Document · v1.1*
