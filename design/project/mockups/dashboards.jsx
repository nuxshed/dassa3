// Dashboard screens — Admin + Student + Search overlay + Module stub

function AdminDashboard() {
  const modules = [
    { name: 'Timetable', icon: 'cal', impl: true, sub: 'weekly schedule' },
    { name: 'Student Details', icon: 'users', impl: true, sub: 'directory & profiles' },
    { name: 'Attendance', icon: 'clip', impl: false, sub: 'coming soon' },
    { name: 'Examinations', icon: 'file', impl: false, sub: 'coming soon' },
    { name: 'Finance', icon: 'bank', impl: false, sub: 'coming soon' },
    { name: 'Messages', icon: 'msg', impl: false, sub: 'coming soon' },
    { name: 'News', icon: 'paper', impl: false, sub: 'coming soon' },
    { name: 'Human Resources', icon: 'brief', impl: false, sub: 'coming soon' },
    { name: 'Manage Users', icon: 'userCog', impl: false, sub: 'coming soon' },
    { name: 'Admission', icon: 'userPlus', impl: false, sub: 'coming soon' },
  ];

  return (
    <Phone label="D-01-A · Dashboard (Admin)">
      <StatusBar />
      <div style={{ padding: '8px 20px 8px', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 10 }}>
          <div style={{
            width: 28, height: 28, borderRadius: 6, background: '#111',
            color: '#fff', display: 'flex', alignItems: 'center', justifyContent: 'center',
            fontSize: 13, fontWeight: 700, fontFamily: notion.fontMono || '"JetBrains Mono", monospace',
          }}>i</div>
          <span style={{ fontSize: 16, fontWeight: 700, letterSpacing: -0.3 }}>IMS</span>
        </div>
        <Avatar initials="AN" size={32} bg="#E6DFD1" fg="#6B5C35" />
      </div>

      <SearchField placeholder="Search students, slots, modules…" />

      <div style={{ padding: '6px 20px 10px' }}>
        <div style={{ fontSize: 24, fontWeight: 700, letterSpacing: -0.5, color: notion.text }}>Good morning, Anita</div>
        <div style={{ fontSize: 13, color: notion.textSecondary, marginTop: 2 }}>Tuesday · 2 pending approvals · 1 schedule conflict</div>
      </div>

      <SectionHeader label="Modules" />

      <div style={{ flex: 1, overflow: 'hidden', padding: '0 16px 8px' }}>
        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 8 }}>
          {modules.map(m => (
            <div key={m.name} style={{
              padding: '14px 12px 14px',
              background: m.impl ? notion.bg : notion.surface,
              border: `1px solid ${notion.border}`,
              borderRadius: 10,
              display: 'flex', flexDirection: 'column', gap: 10,
              minHeight: 92,
              opacity: m.impl ? 1 : 0.82,
            }}>
              <div style={{
                width: 32, height: 32, borderRadius: 8,
                background: m.impl ? '#F1F1EF' : '#FAFAF9',
                color: m.impl ? notion.text : notion.textFaint,
                display: 'flex', alignItems: 'center', justifyContent: 'center',
              }}>
                <ModuleIcon name={m.icon} size={18} />
              </div>
              <div>
                <div style={{ fontSize: 13, fontWeight: 600, color: m.impl ? notion.text : notion.textSecondary, lineHeight: 1.25 }}>{m.name}</div>
                <div style={{ fontSize: 11, color: notion.textFaint, marginTop: 2 }}>{m.sub}</div>
              </div>
            </div>
          ))}
        </div>
      </div>

      <BottomNav active="home" />
    </Phone>
  );
}

function StudentDashboard() {
  return (
    <Phone label="D-01-S · Dashboard (Student)">
      <StatusBar />
      <div style={{ padding: '8px 20px 8px', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <span style={{ fontSize: 15, fontWeight: 600, color: notion.text }}>My Home</span>
        <div style={{ fontSize: 11, color: notion.textSecondary, fontFamily: fontMono }}>student · CSE-2024</div>
      </div>

      <div style={{ padding: '14px 20px 8px' }}>
        <div style={{ fontSize: 28, fontWeight: 700, letterSpacing: -0.6, color: notion.text }}>Hi, Aarav 👋</div>
        <div style={{ fontSize: 13, color: notion.textSecondary, marginTop: 4 }}>Tuesday · 3 classes · 1 assignment due</div>
      </div>

      <SearchField placeholder="Search classes, people, notes…" />

      <div style={{ flex: 1, overflow: 'hidden' }}>
        <SectionHeader label="Next up" />
        <div style={{ margin: '0 20px', padding: '14px 16px', border: `1px solid ${notion.borderStrong}`, borderRadius: 10, background: notion.bg }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
            <div>
              <div style={{ fontSize: 12, color: notion.textSecondary, fontFamily: fontMono }}>10:00 – 11:30</div>
              <div style={{ fontSize: 16, fontWeight: 600, marginTop: 4 }}>Operating Systems</div>
              <div style={{ fontSize: 12, color: notion.textSecondary, marginTop: 2 }}>Lecture · Prof. R. Menon</div>
            </div>
            <div style={{
              padding: '4px 8px', background: notion.chipBg,
              borderRadius: 6, fontSize: 11, fontWeight: 500, color: notion.text,
            }}>H-105</div>
          </div>
        </div>

        <SectionHeader label="My classes" />
        <div style={{ padding: '0 20px' }}>
          {[
            ['Operating Systems', 78, 'OS'],
            ['Algorithms', 92, 'AL'],
            ['HCI', 65, 'HCI', true],
          ].map(([n, g, tag, elect]) => (
            <div key={n} style={{
              padding: '10px 4px',
              display: 'flex', alignItems: 'center', gap: 12,
              borderBottom: `1px solid ${notion.divider}`,
            }}>
              <div style={{
                width: 26, height: 26, borderRadius: 6, background: notion.surface,
                display: 'flex', alignItems: 'center', justifyContent: 'center',
                fontSize: 10, fontWeight: 600, color: notion.textSecondary,
                fontFamily: fontMono,
              }}>{tag}</div>
              <div style={{ flex: 1, fontSize: 14 }}>
                {n} {elect && <span style={{ color: notion.textFaint, fontSize: 12 }}>(elective)</span>}
              </div>
              <div style={{ fontSize: 12, color: notion.textSecondary, fontFamily: fontMono, fontVariantNumeric: 'tabular-nums' }}>{g}%</div>
            </div>
          ))}
        </div>

        <SectionHeader label="Latest news" />
        <div style={{ padding: '0 20px 8px' }}>
          <div style={{ padding: '8px 0', borderBottom: `1px solid ${notion.divider}` }}>
            <div style={{ fontSize: 13, fontWeight: 600 }}>Mid-sem schedule posted</div>
            <div style={{ fontSize: 11, color: notion.textFaint, marginTop: 2 }}>acads · 2h ago</div>
          </div>
          <div style={{ padding: '8px 0' }}>
            <div style={{ fontSize: 13, fontWeight: 600 }}>Library hours extended</div>
            <div style={{ fontSize: 11, color: notion.textFaint, marginTop: 2 }}>admin · yesterday</div>
          </div>
        </div>
      </div>

      <BottomNav active="home" />
    </Phone>
  );
}

function SearchOverlay() {
  const pages = [
    ['CSE-2024 — Batch page', 'Batches'],
    ['CSE-2024 Timetable', 'Timetable'],
  ];
  const people = [
    ['Aarav Sharma', '2024101020', 'AS'],
    ['Advika Rao', '2024101031', 'AR'],
    ['Prof. Meera Iyer', 'advisor', 'MI'],
  ];
  const actions = [
    ['Go to Batch CSE-2024', 'arrow'],
    ['New admission · CSE-2024', 'plus'],
    ['Message Batch CSE-2024', 'msg'],
  ];

  return (
    <Phone label="D-02 · Universal Search">
      <StatusBar />
      <div style={{
        padding: '12px 16px',
        display: 'flex', alignItems: 'center', gap: 12,
        borderBottom: `1px solid ${notion.border}`,
      }}>
        <Icon d={ICON.search} size={18} color={notion.textSecondary} strokeWidth={2} />
        <input
          defaultValue="batch cse 2024"
          style={{
            flex: 1, border: 'none', outline: 'none', fontSize: 16,
            background: 'transparent', fontFamily: fontSans, color: notion.text,
          }}
        />
        <span style={{
          fontSize: 10, padding: '2px 7px', borderRadius: 4,
          background: notion.surface, border: `1px solid ${notion.border}`,
          fontFamily: fontMono, color: notion.textSecondary,
        }}>esc</span>
      </div>

      <div style={{ flex: 1, overflow: 'hidden' }}>
        <div style={{ padding: '12px 16px 4px', fontSize: 11, color: notion.textSecondary, textTransform: 'uppercase', letterSpacing: 0.8, fontWeight: 600 }}>Pages</div>
        {pages.map(([l, r]) => (
          <div key={l} style={{
            padding: '10px 16px', display: 'flex', alignItems: 'center', gap: 12,
          }}>
            <div style={{
              width: 28, height: 28, borderRadius: 6, background: notion.surface,
              display: 'flex', alignItems: 'center', justifyContent: 'center',
              color: notion.textSecondary,
            }}><Icon d={ICON.file} size={15} strokeWidth={1.75} /></div>
            <span style={{ flex: 1, fontSize: 14 }}>{l}</span>
            <span style={{ fontSize: 11, color: notion.textFaint }}>{r}</span>
          </div>
        ))}

        <div style={{ padding: '14px 16px 4px', fontSize: 11, color: notion.textSecondary, textTransform: 'uppercase', letterSpacing: 0.8, fontWeight: 600 }}>People</div>
        {people.map(([n, meta, init]) => (
          <div key={n} style={{
            padding: '8px 16px', display: 'flex', alignItems: 'center', gap: 12,
          }}>
            <Avatar initials={init} size={28} />
            <div style={{ flex: 1 }}>
              <div style={{ fontSize: 14 }}>{n}</div>
              <div style={{ fontSize: 11, color: notion.textFaint, fontFamily: fontMono }}>{meta}</div>
            </div>
            <span style={{ fontSize: 11, color: notion.textFaint }}>Student</span>
          </div>
        ))}

        <div style={{ padding: '14px 16px 4px', fontSize: 11, color: notion.textSecondary, textTransform: 'uppercase', letterSpacing: 0.8, fontWeight: 600 }}>Actions</div>
        {actions.map(([l, ic]) => (
          <div key={l} style={{
            padding: '10px 16px', display: 'flex', alignItems: 'center', gap: 12,
          }}>
            <div style={{
              width: 28, height: 28, borderRadius: 6, background: notion.surface,
              display: 'flex', alignItems: 'center', justifyContent: 'center',
              color: notion.textSecondary,
            }}><Icon d={ICON[ic]} size={15} /></div>
            <span style={{ flex: 1, fontSize: 14 }}>{l}</span>
          </div>
        ))}
      </div>

      <div style={{
        padding: '10px 16px', borderTop: `1px solid ${notion.border}`,
        display: 'flex', gap: 16, fontSize: 11, color: notion.textSecondary,
      }}>
        <span><span style={{ fontFamily: fontMono, padding: '1px 5px', background: notion.surface, borderRadius: 3, marginRight: 4 }}>↑↓</span>navigate</span>
        <span><span style={{ fontFamily: fontMono, padding: '1px 5px', background: notion.surface, borderRadius: 3, marginRight: 4 }}>↵</span>open</span>
      </div>
    </Phone>
  );
}

function ModuleStub() {
  return (
    <Phone label="D-03 · Module Stub">
      <StatusBar />
      <div style={{ padding: '8px 20px 8px', display: 'flex', alignItems: 'center', gap: 10 }}>
        <Icon d={ICON.chevLeft} size={20} color={notion.textSecondary} />
        <span style={{ fontSize: 15, fontWeight: 600 }}>Finance</span>
      </div>
      <div style={{ flex: 1, display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', padding: 40 }}>
        <div style={{
          width: 72, height: 72, borderRadius: 16,
          background: notion.surface, border: `1px solid ${notion.border}`,
          display: 'flex', alignItems: 'center', justifyContent: 'center',
          color: notion.textSecondary, marginBottom: 20,
        }}>
          <Icon d={ICON.bank} size={32} strokeWidth={1.5} />
        </div>
        <div style={{ fontSize: 18, fontWeight: 600, marginBottom: 6 }}>Finance</div>
        <div style={{ fontSize: 13, color: notion.textSecondary, textAlign: 'center', maxWidth: 240, lineHeight: 1.5 }}>
          This module is not available yet. Check back in a future release.
        </div>
      </div>
      <BottomNav active="home" />
    </Phone>
  );
}

Object.assign(window, { AdminDashboard, StudentDashboard, SearchOverlay, ModuleStub });
