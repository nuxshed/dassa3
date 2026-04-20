// IMS Wireframes — monospace, minimal, low-fi
// All screens: 300×600 phone frames (scaled-down 390x844 equiv for density)

const W = 300;
const H = 600;

const mono = '"JetBrains Mono", "IBM Plex Mono", ui-monospace, Menlo, monospace';
const ink = '#111';
const muted = '#888';
const faint = '#ccc';
const paper = '#fff';
const chip = '#f4f4f2';

// ─── primitives ───────────────────────────────────────────────
function Frame({ children, label, note }) {
  return (
    <DCArtboard label={label} width={W} height={H} style={{ borderRadius: 14, border: `1px solid ${ink}`, boxShadow: '2px 2px 0 #111, 0 1px 3px rgba(0,0,0,0.08)' }}>
      <div style={{ width: W, height: H, background: paper, fontFamily: mono, fontSize: 11, color: ink, position: 'relative', overflow: 'hidden', display: 'flex', flexDirection: 'column' }}>
        {children}
      </div>
    </DCArtboard>
  );
}

function StatusBar() {
  return (
    <div style={{ height: 22, padding: '0 12px', display: 'flex', alignItems: 'center', justifyContent: 'space-between', fontSize: 10, color: ink, borderBottom: `1px dashed ${faint}` }}>
      <span>9:41</span>
      <span style={{ letterSpacing: 1 }}>• • •</span>
      <span>100%</span>
    </div>
  );
}

function TopBar({ title, right, left }) {
  return (
    <div style={{ padding: '10px 14px', display: 'flex', alignItems: 'center', justifyContent: 'space-between', borderBottom: `1px solid ${ink}` }}>
      <span style={{ fontSize: 13, fontWeight: 700 }}>{left ?? title}</span>
      <span style={{ fontSize: 10, color: muted }}>{right}</span>
    </div>
  );
}

function BottomNav({ active }) {
  const tabs = ['home', 'search', 'sched', 'ppl', 'me'];
  return (
    <div style={{ marginTop: 'auto', display: 'flex', borderTop: `1px solid ${ink}`, background: paper }}>
      {tabs.map(t => (
        <div key={t} style={{ flex: 1, textAlign: 'center', padding: '8px 0', fontSize: 10, fontWeight: active === t ? 700 : 400, color: active === t ? ink : muted, borderRight: t !== 'me' ? `1px dashed ${faint}` : 'none', textDecoration: active === t ? 'underline' : 'none' }}>
          [{t}]
        </div>
      ))}
    </div>
  );
}

function SearchBar({ placeholder = 'search...', filled }) {
  return (
    <div style={{ margin: '10px 14px', padding: '8px 10px', border: `1px solid ${ink}`, borderRadius: 6, display: 'flex', alignItems: 'center', gap: 8, fontSize: 11, background: paper }}>
      <span>[Q]</span>
      <span style={{ color: filled ? ink : muted }}>{placeholder}</span>
      {filled && <span style={{ marginLeft: 'auto', color: muted }}>×</span>}
    </div>
  );
}

function SectionLabel({ children, chev = '▼' }) {
  return (
    <div style={{ padding: '10px 14px 6px', fontSize: 10, letterSpacing: 1.5, color: muted, fontWeight: 700, display: 'flex', alignItems: 'center', gap: 6 }}>
      <span style={{ fontSize: 8 }}>{chev}</span>
      <span>{children}</span>
    </div>
  );
}

function Divider() {
  return <div style={{ height: 1, background: faint, margin: '0 14px' }} />;
}

function Placeholder({ w, h, label, dashed = true, stripe }) {
  return (
    <div style={{
      width: w, height: h,
      border: `1px ${dashed ? 'dashed' : 'solid'} ${faint}`,
      display: 'flex', alignItems: 'center', justifyContent: 'center',
      fontSize: 9, color: muted,
      background: stripe ? 'repeating-linear-gradient(45deg, #f7f7f5 0 4px, transparent 4px 8px)' : paper,
    }}>{label}</div>
  );
}

// ─── D-01-A  Admin Dashboard ─────────────────────────────────
function AdminDash() {
  const mods = [
    ['TIMETABLE', 'schedule', true],
    ['STUDENTS', 'directory', true],
    ['ATTEND.', 'stub', false],
    ['EXAMS', 'stub', false],
    ['FINANCE', 'stub', false],
    ['MESSAGES', 'stub', false],
    ['NEWS', 'stub', false],
    ['HR', 'stub', false],
  ];
  return (
    <Frame label="D-01-A · Dashboard (Admin)">
      <StatusBar />
      <TopBar left="IMS" right="[A]" />
      <SearchBar placeholder="search students, slots, modules…" />
      <SectionLabel>MODULES</SectionLabel>
      <div style={{ padding: '0 14px', display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 8 }}>
        {mods.map(([n, d, impl]) => (
          <div key={n} style={{ border: `1px ${impl ? 'solid' : 'dashed'} ${impl ? ink : faint}`, borderRadius: 6, padding: '10px 10px 12px', minHeight: 70 }}>
            <div style={{ fontSize: 11, marginBottom: 14, color: impl ? ink : muted }}>[□]</div>
            <div style={{ fontSize: 11, fontWeight: 700, color: impl ? ink : muted }}>{n}</div>
            <div style={{ fontSize: 9, color: muted, marginTop: 2 }}>{d}</div>
          </div>
        ))}
      </div>
      <BottomNav active="home" />
    </Frame>
  );
}

// ─── D-01-S  Student Dashboard ───────────────────────────────
function StudentDash() {
  return (
    <Frame label="D-01-S · Dashboard (Student)">
      <StatusBar />
      <TopBar left="My Home" right="student · CSE-2024" />
      <div style={{ padding: '14px 14px 4px' }}>
        <div style={{ fontSize: 18, fontWeight: 700 }}>Hi, Aarav</div>
        <div style={{ fontSize: 10, color: muted, marginTop: 2 }}>Tue · 3 classes · 1 assignment due</div>
      </div>
      <SearchBar placeholder="search classes, people, notes…" />
      <SectionLabel chev="▼">NEXT UP</SectionLabel>
      <div style={{ margin: '0 14px', border: `1px solid ${ink}`, borderRadius: 6, padding: 10, display: 'flex', justifyContent: 'space-between' }}>
        <div>
          <div style={{ fontSize: 14, fontWeight: 700 }}>10:00</div>
          <div style={{ fontSize: 10, marginTop: 2 }}>OS — Lecture</div>
        </div>
        <div style={{ fontSize: 10, color: muted, alignSelf: 'flex-end' }}>H-105</div>
      </div>
      <SectionLabel chev="▼">MY CLASSES</SectionLabel>
      {[['Operating Systems', '78%'], ['Algorithms', '92%'], ['HCI (elective)', '65%']].map(([n, g]) => (
        <div key={n} style={{ padding: '6px 14px', display: 'flex', justifyContent: 'space-between', fontSize: 11 }}>
          <span>□ {n}</span><span style={{ color: muted }}>{g}</span>
        </div>
      ))}
      <SectionLabel chev="▼">LATEST NEWS</SectionLabel>
      <div style={{ padding: '0 14px' }}>
        <div style={{ fontSize: 11, fontWeight: 700 }}>Mid-sem schedule posted</div>
        <div style={{ fontSize: 9, color: muted }}>acads · 2h ago</div>
      </div>
      <SectionLabel chev="▶">QUICK LINKS</SectionLabel>
      <BottomNav active="home" />
    </Frame>
  );
}

// ─── D-02  Search Overlay ────────────────────────────────────
function SearchOverlay() {
  return (
    <Frame label="D-02 · Universal Search">
      <StatusBar />
      <div style={{ padding: '10px 14px', display: 'flex', alignItems: 'center', gap: 10, borderBottom: `1px solid ${ink}` }}>
        <span>[Q]</span>
        <span style={{ fontSize: 11 }}>batch cse 2024</span>
        <span style={{ marginLeft: 'auto', fontSize: 9, color: muted, border: `1px solid ${faint}`, padding: '2px 6px', borderRadius: 3 }}>esc</span>
      </div>
      <SectionLabel>PAGES</SectionLabel>
      {[['□ CSE-2024 — Batch page', 'Batches'], ['□ CSE-2024 Timetable', 'Timetable']].map(([l, r]) => (
        <div key={l} style={{ padding: '6px 14px', display: 'flex', justifyContent: 'space-between', fontSize: 11 }}>
          <span>{l}</span><span style={{ fontSize: 9, color: muted }}>{r}</span>
        </div>
      ))}
      <SectionLabel>PEOPLE</SectionLabel>
      {[['○ Aarav Sharma · 2024101020', 'Student'], ['○ Advika Rao · 2024101031', 'Student'], ['○ Prof. Meera Iyer', 'Faculty']].map(([l, r]) => (
        <div key={l} style={{ padding: '6px 14px', display: 'flex', justifyContent: 'space-between', fontSize: 11 }}>
          <span>{l}</span><span style={{ fontSize: 9, color: muted }}>{r}</span>
        </div>
      ))}
      <SectionLabel>ACTIONS</SectionLabel>
      {['→ Go to Batch CSE-2024', '+ New admission · CSE-2024', '□ Message Batch CSE-2024'].map(l => (
        <div key={l} style={{ padding: '6px 14px', fontSize: 11 }}>{l}</div>
      ))}
      <div style={{ marginTop: 'auto', padding: 10, borderTop: `1px dashed ${faint}`, fontSize: 9, color: muted, display: 'flex', justifyContent: 'space-between' }}>
        <span>↑↓ navigate</span><span>↵ open</span>
      </div>
    </Frame>
  );
}

// ─── T-01  Timetable Grid ────────────────────────────────────
function TimetableGrid() {
  const days = ['M', 'T', 'W', 'T', 'F', 'S'];
  const times = ['08:30', '10:05', '11:40', '14:00', '15:35'];
  const cells = {
    '0,0': ['OS', 'H-105'], '0,1': ['DM', 'H-202'], '1,0': ['ALG', 'H-101'],
    '2,0': ['HCI', 'H-301'], '2,3': ['free'], '2,4': ['free'],
    '3,1': ['ALG', 'H-101'], '4,2': ['OS', 'H-105', 'over'],
    '5,3': ['free'], '5,4': ['free'],
  };
  return (
    <Frame label="T-01 · Timetable (Admin)">
      <StatusBar />
      <TopBar left="Timetable" right="[Grid | Slots]" />
      <div style={{ display: 'flex', fontSize: 9 }}>
        <div style={{ width: 34, flexShrink: 0 }} />
        {days.map((d, i) => (
          <div key={i} style={{ flex: 1, textAlign: 'center', padding: '4px 0', borderBottom: `1px solid ${ink}`, fontWeight: 700 }}>{d}</div>
        ))}
      </div>
      <div style={{ flex: 1, overflow: 'hidden' }}>
        {times.map((t, ri) => (
          <div key={ri} style={{ display: 'flex', height: 36 }}>
            <div style={{ width: 34, flexShrink: 0, fontSize: 8, color: muted, textAlign: 'right', paddingRight: 4, paddingTop: 4, borderRight: `1px dashed ${faint}` }}>{t}</div>
            {days.map((_, ci) => {
              const c = cells[`${ci},${ri}`];
              const isFree = c && c[0] === 'free';
              const isOver = c && c[2] === 'over';
              return (
                <div key={ci} style={{
                  flex: 1, borderRight: `1px dashed ${faint}`, borderBottom: `1px dashed ${faint}`,
                  borderLeft: isOver ? `3px solid #EF4444` : undefined,
                  background: isFree ? 'repeating-linear-gradient(45deg, #f3f4f6 0 3px, transparent 3px 6px)' : paper,
                  padding: '3px 4px', fontSize: 8,
                }}>
                  {c && !isFree && <><div style={{ fontWeight: 700 }}>{c[0]}</div><div style={{ color: muted, fontSize: 7 }}>{c[1]}</div></>}
                  {isFree && <div style={{ color: muted, fontSize: 7 }}>free</div>}
                </div>
              );
            })}
          </div>
        ))}
      </div>
      <div style={{ padding: '6px 8px', borderTop: `1px solid ${ink}`, fontSize: 9, display: 'flex', gap: 6, overflowX: 'hidden' }}>
        <div style={{ border: `1px solid ${ink}`, borderRadius: 4, padding: '4px 6px', minWidth: 70 }}>
          <div style={{ fontWeight: 700 }}>DBMS</div>
          <div style={{ color: muted, fontSize: 7 }}>H-204</div>
        </div>
        <div style={{ border: `1px solid ${ink}`, borderRadius: 4, padding: '4px 6px', minWidth: 70 }}>
          <div style={{ fontWeight: 700 }}>CN</div>
          <div style={{ color: muted, fontSize: 7 }}>H-110</div>
        </div>
        <div style={{ border: `1px dashed ${muted}`, borderRadius: 4, padding: '4px 6px', minWidth: 70, color: muted, display: 'flex', alignItems: 'center' }}>+ add slot</div>
      </div>
      <BottomNav active="sched" />
    </Frame>
  );
}

// ─── T-02  Slot Detail ───────────────────────────────────────
function SlotDetail() {
  return (
    <Frame label="T-02 · Slot Detail (bottom sheet)">
      <StatusBar />
      <div style={{ flex: 1, background: 'rgba(0,0,0,0.15)' }} />
      <div style={{ background: paper, borderTop: `1px solid ${ink}`, borderTopLeftRadius: 12, borderTopRightRadius: 12, padding: '10px 14px 16px' }}>
        <div style={{ width: 32, height: 3, background: faint, margin: '0 auto 12px', borderRadius: 2 }} />
        <div style={{ fontSize: 14, fontWeight: 700 }}>Operating Systems</div>
        <div style={{ fontSize: 11, marginTop: 6 }}>Room: H-105</div>
        <div style={{ fontSize: 10, color: muted, marginTop: 4 }}>Monday · 8:30–9:55</div>
        <div style={{ marginTop: 14, display: 'flex', gap: 10, borderTop: `1px dashed ${faint}`, paddingTop: 10 }}>
          <span style={{ fontSize: 10, textDecoration: 'underline' }}>[ edit ]</span>
          <span style={{ fontSize: 10, textDecoration: 'underline', color: '#EF4444' }}>[ remove ]</span>
          <span style={{ marginLeft: 'auto', fontSize: 10, color: muted }}>admin</span>
        </div>
      </div>
    </Frame>
  );
}

// ─── T-03  Create/Edit Slot ──────────────────────────────────
function SlotEdit() {
  return (
    <Frame label="T-03 · Create / Edit Slot">
      <StatusBar />
      <div style={{ padding: '10px 14px', display: 'flex', justifyContent: 'space-between', borderBottom: `1px solid ${ink}`, fontSize: 11 }}>
        <span style={{ color: muted }}>Cancel</span>
        <span style={{ fontWeight: 700 }}>New slot</span>
        <span style={{ fontWeight: 700 }}>Save</span>
      </div>
      <div style={{ padding: 14, display: 'flex', flexDirection: 'column', gap: 14 }}>
        <div>
          <div style={{ fontSize: 10, color: muted, marginBottom: 4 }}>SUBJECT</div>
          <div style={{ borderBottom: `1px solid ${ink}`, padding: '4px 0', fontSize: 12 }}>Operating Systems_</div>
        </div>
        <div>
          <div style={{ fontSize: 10, color: muted, marginBottom: 4 }}>ROOM</div>
          <div style={{ borderBottom: `1px solid ${ink}`, padding: '4px 0', fontSize: 12 }}>H-105</div>
        </div>
        <div>
          <div style={{ fontSize: 10, color: muted, marginBottom: 4 }}>DAY · PERIOD</div>
          <div style={{ borderBottom: `1px dashed ${faint}`, padding: '4px 0', fontSize: 12, color: muted }}>Mon · 8:30–9:55 (from cell)</div>
        </div>
        <div style={{ fontSize: 10, color: muted, marginTop: 8, padding: 8, border: `1px dashed ${faint}`, borderRadius: 4 }}>
          ⚠ subject limit: 3/4 this week
        </div>
      </div>
    </Frame>
  );
}

// ─── S-01  Student List ──────────────────────────────────────
function StudentList() {
  const rows = [
    ['AS', 'Aarav Sharma', '2024101020', 'CSE-2024'],
    ['AR', 'Advika Rao', '2024101031', 'CSE-2024'],
    ['BK', 'Bhavya Kumar', '2024102004', 'ECE-2024'],
    ['CR', 'Chirag Reddy', '2024101055', 'CSE-2024'],
    ['DS', 'Diya Sen', '2023101120', 'CSE-2023'],
    ['EP', 'Eshan Patel', '2024103009', 'CSD-2024'],
  ];
  return (
    <Frame label="S-01 · Student List">
      <StatusBar />
      <TopBar left="People" right="[filter]" />
      <div style={{ padding: '8px 14px 0' }}>
        <div style={{ padding: '6px 10px', border: `1px solid ${ink}`, borderRadius: 6, fontSize: 11, color: muted }}>
          [Q] search by name or roll…
        </div>
      </div>
      <div style={{ padding: '8px 14px', display: 'flex', gap: 6, flexWrap: 'wrap' }}>
        {['batch: CSE-2024', 'year: 2', 'gpa: 7+'].map(c => (
          <div key={c} style={{ fontSize: 9, padding: '2px 6px', border: `1px solid ${ink}`, borderRadius: 3, background: chip }}>
            {c} ×
          </div>
        ))}
      </div>
      <Divider />
      <div style={{ flex: 1, overflow: 'hidden' }}>
        {rows.map(([i, n, r, b]) => (
          <div key={r} style={{ padding: '10px 14px', display: 'flex', alignItems: 'center', gap: 10, borderBottom: `1px dashed ${faint}` }}>
            <div style={{ width: 28, height: 28, borderRadius: '50%', border: `1px solid ${ink}`, display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 10, fontWeight: 700 }}>{i}</div>
            <div style={{ flex: 1 }}>
              <div style={{ fontSize: 12, fontWeight: 700 }}>{n}</div>
              <div style={{ fontSize: 9, color: muted }}>{r} · {b}</div>
            </div>
            <div style={{ fontSize: 12, color: muted }}>›</div>
          </div>
        ))}
      </div>
      <BottomNav active="ppl" />
    </Frame>
  );
}

// ─── S-01b Filter Sheet ──────────────────────────────────────
function FilterSheet() {
  return (
    <Frame label="S-01b · Filters (sheet)">
      <StatusBar />
      <div style={{ flex: 1, background: 'rgba(0,0,0,0.15)' }} />
      <div style={{ background: paper, borderTop: `1px solid ${ink}`, borderTopLeftRadius: 12, borderTopRightRadius: 12, padding: '10px 14px 16px' }}>
        <div style={{ width: 32, height: 3, background: faint, margin: '0 auto 12px', borderRadius: 2 }} />
        <div style={{ fontSize: 12, fontWeight: 700, marginBottom: 10 }}>Filter students</div>
        {[
          ['BATCH', ['CSE-2024', 'CSE-2023', 'ECE-2024', 'CSD-2024']],
          ['YEAR', ['1', '2', '3', '4', '5']],
          ['GENDER', ['M', 'F', 'Other']],
        ].map(([l, opts]) => (
          <div key={l} style={{ marginBottom: 10 }}>
            <div style={{ fontSize: 9, color: muted, marginBottom: 4 }}>{l}</div>
            <div style={{ display: 'flex', gap: 4, flexWrap: 'wrap' }}>
              {opts.map(o => (
                <span key={o} style={{ fontSize: 10, padding: '2px 6px', border: `1px solid ${faint}`, borderRadius: 3 }}>{o}</span>
              ))}
            </div>
          </div>
        ))}
        <div style={{ marginBottom: 10 }}>
          <div style={{ fontSize: 9, color: muted, marginBottom: 4 }}>GPA RANGE · 6.5 – 9.5</div>
          <div style={{ position: 'relative', height: 14 }}>
            <div style={{ position: 'absolute', top: 6, left: 0, right: 0, height: 1, background: faint }} />
            <div style={{ position: 'absolute', top: 6, left: '30%', right: '10%', height: 1, background: ink }} />
            <div style={{ position: 'absolute', top: 2, left: '30%', width: 10, height: 10, borderRadius: '50%', background: paper, border: `1px solid ${ink}` }} />
            <div style={{ position: 'absolute', top: 2, right: '10%', width: 10, height: 10, borderRadius: '50%', background: paper, border: `1px solid ${ink}` }} />
          </div>
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: 12, paddingTop: 10, borderTop: `1px dashed ${faint}`, fontSize: 11 }}>
          <span style={{ color: muted }}>Reset all</span>
          <span style={{ fontWeight: 700, textDecoration: 'underline' }}>Apply</span>
        </div>
      </div>
    </Frame>
  );
}

// ─── S-02  Student Profile ───────────────────────────────────
function StudentProfile() {
  return (
    <Frame label="S-02 · Student Profile">
      <StatusBar />
      <div style={{ padding: '12px 14px', display: 'flex', alignItems: 'center', gap: 12, borderBottom: `1px solid ${ink}` }}>
        <div style={{ width: 48, height: 48, borderRadius: '50%', border: `1px solid ${ink}`, display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 14, fontWeight: 700 }}>A</div>
        <div>
          <div style={{ fontSize: 16, fontWeight: 700 }}>Aarav Sharma</div>
          <div style={{ fontSize: 9, color: muted }}>2024101020 · CSE-2024 · UG</div>
        </div>
      </div>
      <div style={{ padding: '8px 14px', display: 'flex', gap: 6, overflowX: 'hidden' }}>
        {['Message', 'Edit', 'Transfer', '···'].map(b => (
          <span key={b} style={{ fontSize: 10, padding: '4px 8px', border: `1px solid ${ink}`, borderRadius: 4 }}>{b}</span>
        ))}
      </div>
      <Divider />
      {[
        ['Email', 'aarav@iiit.ac.in'],
        ['Phone', '+91 98•• ••12'],
        ['DOB', '12 Mar 2006'],
        ['Category', 'UG · General'],
        ['Guardian', 'R. Sharma (father)'],
        ['Advisor', 'Dr. M. Iyer'],
        ['Status', 'Active'],
      ].map(([l, v]) => (
        <div key={l} style={{ padding: '6px 14px', display: 'flex', borderBottom: `1px solid #f3f4f6`, fontSize: 11 }}>
          <span style={{ width: '38%', color: muted, fontSize: 10 }}>{l}</span>
          <span>{v}</span>
        </div>
      ))}
      <SectionLabel chev="▼">ACADEMICS</SectionLabel>
      {[['OS', '78%'], ['Algorithms', '92%'], ['HCI', '65%']].map(([n, g]) => (
        <div key={n} style={{ padding: '4px 14px', display: 'flex', justifyContent: 'space-between', fontSize: 11 }}>
          <span>□ {n}</span><span style={{ color: muted }}>{g}</span>
        </div>
      ))}
      <SectionLabel chev="▶">ATTENDANCE</SectionLabel>
      <SectionLabel chev="▶">FEES</SectionLabel>
      <BottomNav active="ppl" />
    </Frame>
  );
}

// ─── D-03  Stub ───────────────────────────────────────────────
function ModuleStub() {
  return (
    <Frame label="D-03 · Module Stub">
      <StatusBar />
      <TopBar left="Finance" right="" />
      <div style={{ flex: 1, display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', gap: 12, padding: 20 }}>
        <div style={{ fontSize: 32 }}>[ $ ]</div>
        <div style={{ fontSize: 14, fontWeight: 700 }}>Finance</div>
        <div style={{ fontSize: 11, color: muted, textAlign: 'center' }}>this module is not<br/>available yet</div>
      </div>
      <BottomNav active="home" />
    </Frame>
  );
}

Object.assign(window, {
  AdminDash, StudentDash, SearchOverlay,
  TimetableGrid, SlotDetail, SlotEdit,
  StudentList, FilterSheet, StudentProfile, ModuleStub,
});
