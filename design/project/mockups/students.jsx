// Student list, filter sheet, and student profile

function StudentList() {
  const rows = [
    ['AS', 'Aarav Sharma', '2024101020', 'CSE-2024', 'UG', 8.4, '#E9E5DA', '#6B5C35'],
    ['AR', 'Advika Rao', '2024101031', 'CSE-2024', 'UG', 9.1, '#E8DEEE', '#5D3A80'],
    ['BK', 'Bhavya Kumar', '2024102004', 'ECE-2024', 'UG', 7.6, '#FDECC8', '#7C4A03'],
    ['CR', 'Chirag Reddy', '2024101055', 'CSE-2024', 'UG', 8.0, '#DBEDDB', '#1E6B3B'],
    ['DS', 'Diya Sen', '2023101120', 'CSE-2023', 'UG', 8.9, '#D3E5EF', '#1D4F6E'],
    ['EP', 'Eshan Patel', '2024103009', 'CSD-2024', 'UG', 7.2, '#FFE2DD', '#8B3A2C'],
    ['FM', 'Farhan Mirza', '2022201010', 'CSE-MS', 'PG', 8.7, '#EEE0DA', '#6B4A3E'],
  ];
  return (
    <Phone label="S-01 · Student List">
      <StatusBar />
      <TopBar
        leftSlot={<span style={{ fontSize: 15, fontWeight: 700 }}>People</span>}
        rightSlot={<div style={{ display: 'flex', gap: 14, color: notion.textSecondary }}>
          <Icon d={ICON.search} size={20} />
          <Icon d={ICON.filter} size={20} />
        </div>}
      />

      <div style={{ padding: '14px 20px 4px' }}>
        <div style={{ fontSize: 24, fontWeight: 700, letterSpacing: -0.5 }}>All students</div>
        <div style={{ fontSize: 12, color: notion.textSecondary, marginTop: 2 }}>
          <span style={{ fontFamily: fontMono }}>247</span> results · 3 filters active
        </div>
      </div>

      <div style={{ padding: '10px 20px', display: 'flex', gap: 6, flexWrap: 'wrap' }}>
        {['Batch: CSE-2024', 'Year: 2', 'GPA: 7.0+'].map(c => (
          <div key={c} style={{
            padding: '3px 8px 3px 10px',
            background: notion.chipBg,
            borderRadius: 12, fontSize: 11, fontWeight: 500,
            display: 'flex', alignItems: 'center', gap: 6,
            color: notion.text,
          }}>
            <span>{c}</span>
            <Icon d={ICON.x} size={10} color={notion.textSecondary} strokeWidth={2.5} />
          </div>
        ))}
      </div>

      <div style={{ flex: 1, overflow: 'hidden' }}>
        {rows.map(([init, name, roll, batch, prog, gpa, bg, fg]) => (
          <div key={roll} style={{
            padding: '10px 20px',
            display: 'flex', alignItems: 'center', gap: 12,
            borderBottom: `1px solid ${notion.divider}`,
          }}>
            <Avatar initials={init} size={36} bg={bg} fg={fg} />
            <div style={{ flex: 1, minWidth: 0 }}>
              <div style={{ fontSize: 14, fontWeight: 500, color: notion.text }}>{name}</div>
              <div style={{ fontSize: 11, color: notion.textSecondary, marginTop: 1 }}>
                <span style={{ fontFamily: fontMono }}>{roll}</span> · {batch}
              </div>
            </div>
            <div style={{ textAlign: 'right' }}>
              <div style={{ fontSize: 12, fontFamily: fontMono, color: notion.text, fontWeight: 500 }}>{gpa.toFixed(1)}</div>
              <div style={{ fontSize: 10, color: notion.textFaint }}>{prog}</div>
            </div>
          </div>
        ))}
      </div>

      <BottomNav active="people" />
    </Phone>
  );
}

function FilterSheet() {
  return (
    <Phone label="S-01b · Filters (bottom sheet)">
      <StatusBar />
      <div style={{ flex: 1, background: 'rgba(15,15,15,0.35)' }} />

      <div style={{
        background: notion.bg, borderTopLeftRadius: 16, borderTopRightRadius: 16,
        boxShadow: '0 -8px 24px rgba(0,0,0,0.1)',
        padding: '10px 20px 28px',
      }}>
        <div style={{ width: 36, height: 4, background: notion.borderStrong, borderRadius: 2, margin: '0 auto 14px' }} />
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: 16 }}>
          <span style={{ fontSize: 16, fontWeight: 600 }}>Filters</span>
          <span style={{ fontSize: 12, color: notion.textSecondary }}>3 active</span>
        </div>

        {[
          ['Batch', ['CSE-2024', 'CSE-2023', 'ECE-2024', 'CSD-2024'], [true, false, false, false]],
          ['Year', ['1', '2', '3', '4', '5'], [false, true, false, false, false]],
          ['Gender', ['Male', 'Female', 'Other'], [false, false, false]],
          ['Programme', ['UG', 'PG', 'PhD'], [false, false, false]],
        ].map(([label, opts, sel]) => (
          <div key={label} style={{ marginBottom: 16 }}>
            <div style={{ fontSize: 11, color: notion.textSecondary, fontWeight: 500, marginBottom: 8, textTransform: 'uppercase', letterSpacing: 0.6 }}>{label}</div>
            <div style={{ display: 'flex', gap: 6, flexWrap: 'wrap' }}>
              {opts.map((o, i) => (
                <span key={o} style={{
                  padding: '5px 10px', borderRadius: 6,
                  border: `1px solid ${sel[i] ? notion.text : notion.border}`,
                  background: sel[i] ? notion.text : notion.bg,
                  color: sel[i] ? '#fff' : notion.text,
                  fontSize: 12, fontWeight: 500,
                }}>{o}</span>
              ))}
            </div>
          </div>
        ))}

        <div style={{ marginBottom: 16 }}>
          <div style={{
            display: 'flex', justifyContent: 'space-between',
            fontSize: 11, color: notion.textSecondary, fontWeight: 500, marginBottom: 8,
            textTransform: 'uppercase', letterSpacing: 0.6,
          }}>
            <span>GPA</span>
            <span style={{ fontFamily: fontMono, color: notion.text }}>7.0 – 10.0</span>
          </div>
          <div style={{ position: 'relative', height: 20, padding: '0 6px' }}>
            <div style={{ position: 'absolute', top: 9, left: 6, right: 6, height: 2, background: notion.borderStrong, borderRadius: 1 }} />
            <div style={{ position: 'absolute', top: 9, left: '40%', right: '6px', height: 2, background: notion.text, borderRadius: 1 }} />
            <div style={{ position: 'absolute', top: 4, left: '40%', width: 12, height: 12, borderRadius: '50%', background: '#fff', border: `2px solid ${notion.text}`, boxShadow: '0 1px 2px rgba(0,0,0,0.1)' }} />
            <div style={{ position: 'absolute', top: 4, right: 6, width: 12, height: 12, borderRadius: '50%', background: '#fff', border: `2px solid ${notion.text}`, boxShadow: '0 1px 2px rgba(0,0,0,0.1)' }} />
          </div>
        </div>

        <div style={{ display: 'flex', gap: 8, marginTop: 12 }}>
          <button style={{
            flex: 1, padding: '12px 0', border: `1px solid ${notion.border}`,
            borderRadius: 8, background: notion.bg, fontSize: 14, fontWeight: 500,
            color: notion.textSecondary, fontFamily: fontSans,
          }}>Reset all</button>
          <button style={{
            flex: 2, padding: '12px 0', border: 'none',
            borderRadius: 8, background: notion.text, fontSize: 14, fontWeight: 600,
            color: '#fff', fontFamily: fontSans,
          }}>Apply filters</button>
        </div>
      </div>
    </Phone>
  );
}

function StudentProfile() {
  return (
    <Phone label="S-02 · Student Profile (Admin view)">
      <StatusBar />
      <div style={{ padding: '8px 20px 8px', display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
        <Icon d={ICON.chevLeft} size={22} color={notion.text} />
        <div style={{ display: 'flex', gap: 14, color: notion.textSecondary }}>
          <Icon d={ICON.msg} size={20} />
          <Icon d={ICON.more} size={20} strokeWidth={2.5} />
        </div>
      </div>

      {/* Header */}
      <div style={{ padding: '8px 20px 16px', display: 'flex', gap: 14, alignItems: 'flex-start' }}>
        <Avatar initials="AS" size={64} bg="#E9E5DA" fg="#6B5C35" />
        <div style={{ flex: 1, paddingTop: 2 }}>
          <div style={{ fontSize: 22, fontWeight: 700, letterSpacing: -0.4 }}>Aarav Sharma</div>
          <div style={{ fontSize: 12, color: notion.textSecondary, marginTop: 2 }}>
            <span style={{ fontFamily: fontMono }}>2024101020</span> · CSE-2024 · UG
          </div>
          <div style={{ display: 'inline-flex', alignItems: 'center', gap: 5, marginTop: 6, padding: '2px 7px', borderRadius: 10, background: '#E0F0E5', color: '#1E6B3B', fontSize: 10, fontWeight: 500 }}>
            <span style={{ width: 5, height: 5, borderRadius: '50%', background: '#1E6B3B' }} />
            Active
          </div>
        </div>
      </div>

      <div style={{ padding: '0 20px 10px', display: 'flex', gap: 6 }}>
        {[
          ['Message', 'msg'],
          ['Edit', 'pencil'],
          ['Transfer', 'enter'],
        ].map(([l, ic]) => (
          <button key={l} style={{
            flex: 1, padding: '7px 0', border: `1px solid ${notion.border}`,
            borderRadius: 6, background: notion.bg, fontSize: 12, fontWeight: 500,
            display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 4,
            color: notion.text, fontFamily: fontSans,
          }}><Icon d={ICON[ic]} size={13} />{l}</button>
        ))}
      </div>

      <div style={{ flex: 1, overflow: 'hidden' }}>
        <SectionHeader label="Contact" />
        {[
          ['Email', 'aarav.sharma@iiit.ac.in'],
          ['Phone', '+91 98•• •• ••12'],
          ['Date of birth', '12 Mar 2006'],
          ['Guardian', 'R. Sharma (father)'],
          ['Advisor', 'Dr. Meera Iyer'],
          ['Enrolled', '04 Aug 2024'],
        ].map(([l, v]) => (
          <div key={l} style={{
            padding: '7px 20px',
            display: 'flex', alignItems: 'center',
            borderBottom: `1px solid ${notion.divider}`,
            fontSize: 13,
          }}>
            <span style={{ width: '40%', color: notion.textSecondary, fontSize: 12 }}>{l}</span>
            <span style={{ flex: 1, color: notion.text }}>{v}</span>
          </div>
        ))}

        <SectionHeader label="Academics" />
        <div style={{ padding: '0 20px' }}>
          {[
            ['Operating Systems', 78, 'OS'],
            ['Algorithms', 92, 'ALG'],
            ['HCI (elective)', 65, 'HCI'],
            ['DBMS', 84, 'DBMS'],
          ].map(([n, g, code]) => {
            const st = subjectStyle[code] || {};
            return (
              <div key={n} style={{
                padding: '8px 0',
                display: 'flex', alignItems: 'center', gap: 10,
                borderBottom: `1px solid ${notion.divider}`,
              }}>
                <div style={{
                  padding: '2px 6px', borderRadius: 3,
                  background: st.bg, color: st.fg, fontSize: 9, fontWeight: 700,
                  minWidth: 34, textAlign: 'center',
                }}>{code}</div>
                <div style={{ flex: 1, fontSize: 13 }}>{n}</div>
                <div style={{ fontSize: 12, fontFamily: fontMono, color: notion.textSecondary, fontVariantNumeric: 'tabular-nums' }}>{g}%</div>
              </div>
            );
          })}
        </div>

        <SectionHeader label="Attendance" open={false} />
        <SectionHeader label="Fees" open={false} />
      </div>

      <BottomNav active="people" />
    </Phone>
  );
}

Object.assign(window, { StudentList, FilterSheet, StudentProfile });
