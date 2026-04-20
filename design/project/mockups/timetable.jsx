// Timetable — admin grid with multi-slot cells, tray, and add-slot

const DAYS = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
const PERIODS = [
  '8:30 – 9:55',
  '10:05 – 11:30',
  '11:40 – 1:05',
  '2:00 – 3:25',
  '3:35 – 5:00',
];

const subjectStyle = {
  OS:   { bg: '#FDECC8', fg: '#7C4A03' },
  ALG:  { bg: '#DBEDDB', fg: '#1E6B3B' },
  HCI:  { bg: '#E8DEEE', fg: '#5D3A80' },
  DBMS: { bg: '#D3E5EF', fg: '#1D4F6E' },
  CN:   { bg: '#FFE2DD', fg: '#8B3A2C' },
  SE:   { bg: '#EEE0DA', fg: '#6B4A3E' },
  MATH: { bg: '#E3E2E0', fg: '#3C3A36' },
};

const GRID = [
  // Mon
  [
    [{ code: 'OS', full: 'Operating Systems', room: 'H-105', prof: 'Menon', type: 'Lecture', batch: 'CSE-24' },
     { code: 'DBMS', full: 'DBMS', room: 'H-204', prof: 'Rao', type: 'Lecture', batch: 'ECE-24' }],
    [{ code: 'ALG', full: 'Algorithms', room: 'H-101', prof: 'Iyer', type: 'Lecture', batch: 'CSE-24' }],
    [],
    [{ code: 'HCI', full: 'HCI', room: 'H-301', prof: 'Das', type: 'Lecture', batch: 'CSE-24' }],
    [],
  ],
  // Tue
  [
    [{ code: 'DBMS', full: 'DBMS', room: 'H-204', prof: 'Rao', type: 'Lecture', batch: 'CSE-24' }],
    [{ code: 'OS', full: 'Operating Systems', room: 'H-105', prof: 'Menon', type: 'Lecture', batch: 'CSE-24' },
     { code: 'CN', full: 'Networks', room: 'H-110', prof: 'Kapoor', type: 'Lecture', batch: 'ECE-24' }],
    [{ code: 'ALG', full: 'Algorithms', room: 'H-101', prof: 'Iyer', type: 'Lecture', batch: 'CSE-24' }],
    [],
    [{ code: 'SE', full: 'Software Eng.', room: 'H-206', prof: 'Shah', type: 'Lecture', batch: 'CSE-23' }],
  ],
  // Wed
  [
    [{ code: 'MATH', full: 'Linear Algebra', room: 'H-003', prof: 'Rao', type: 'Lecture', batch: 'All' }],
    [{ code: 'OS', full: 'Operating Systems', room: 'H-105', prof: 'Menon', type: 'Lecture', batch: 'CSE-24' }],
    [{ code: 'HCI', full: 'HCI', room: 'H-301', prof: 'Das', type: 'Lecture', batch: 'CSE-24' }],
    'FREE', 'FREE',
  ],
  // Thu
  [
    [{ code: 'ALG', full: 'Algorithms', room: 'H-101', prof: 'Iyer', type: 'Lecture', batch: 'CSE-24' }],
    [{ code: 'DBMS', full: 'DBMS', room: 'H-204', prof: 'Rao', type: 'Lecture', batch: 'CSE-24' }],
    [{ code: 'OS', full: 'Operating Systems', room: 'H-105', prof: 'Menon', type: 'Lecture', batch: 'CSE-24', warn: true }],
    [],
    [{ code: 'HCI', full: 'HCI', room: 'H-301', prof: 'Das', type: 'Lecture', batch: 'CSE-24' }],
  ],
  // Fri
  [
    [{ code: 'OS', full: 'OS Lab', room: 'Lab-2', prof: 'Menon', type: 'Lab', batch: 'CSE-24' }],
    [{ code: 'OS', full: 'OS Lab', room: 'Lab-2', prof: 'Menon', type: 'Lab', batch: 'CSE-24' }],
    [{ code: 'ALG', full: 'Algorithms Tut', room: 'H-101', prof: 'Iyer', type: 'Tutorial', batch: 'CSE-24' }],
    [],
    [],
  ],
  // Sat
  [
    [{ code: 'SE', full: 'Software Eng.', room: 'H-206', prof: 'Shah', type: 'Lecture', batch: 'CSE-23' }],
    [],
    [{ code: 'MATH', full: 'Linear Algebra', room: 'H-003', prof: 'Rao', type: 'Lecture', batch: 'All' }],
    'FREE', 'FREE',
  ],
];

// Slim pill — just subject code, no room
function SlotPill({ slot, onClick }) {
  const s = subjectStyle[slot.code] || { bg: notion.chipBg, fg: notion.text };
  return (
    <div
      onClick={onClick}
      style={{
        background: s.bg, color: s.fg,
        borderRadius: 4,
        padding: '3px 6px',
        fontSize: 10, fontWeight: 700,
        lineHeight: 1.2,
        cursor: onClick ? 'pointer' : 'default',
        borderLeft: slot.warn ? `2px solid ${notion.red}` : undefined,
        whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis',
      }}>
      {slot.code}
    </div>
  );
}

// Cell drawer — lists all slots for a period+day
function CellDrawer({ slots, period, day, onClose, adminMode }) {
  if (!slots || !slots.length) return null;
  return (
    <div style={{
      position: 'absolute', inset: 0, zIndex: 50,
      display: 'flex', flexDirection: 'column', justifyContent: 'flex-end',
    }}>
      {/* scrim */}
      <div onClick={onClose} style={{ position: 'absolute', inset: 0, background: 'rgba(15,15,15,0.35)' }} />
      {/* sheet */}
      <div style={{
        position: 'relative',
        background: notion.bg, borderTopLeftRadius: 16, borderTopRightRadius: 16,
        boxShadow: '0 -8px 24px rgba(0,0,0,0.12)',
        padding: '10px 20px 28px',
      }}>
        <div style={{ width: 36, height: 4, background: notion.borderStrong, borderRadius: 2, margin: '0 auto 14px' }} />
        <div style={{ fontSize: 13, color: notion.textSecondary, marginBottom: 14, fontWeight: 500 }}>
          {day} · {period}
        </div>
        <div style={{ fontSize: 15, fontWeight: 700, marginBottom: 14 }}>
          {slots.length} class{slots.length > 1 ? 'es' : ''} this slot
        </div>
        {slots.map((slot, i) => {
          const st = subjectStyle[slot.code] || {};
          return (
            <div key={i} style={{
              padding: '12px 0',
              borderBottom: i < slots.length - 1 ? `1px solid ${notion.divider}` : 'none',
              display: 'flex', alignItems: 'flex-start', gap: 12,
            }}>
              <div style={{
                padding: '3px 8px', borderRadius: 4,
                background: st.bg, color: st.fg,
                fontSize: 11, fontWeight: 700, flexShrink: 0, marginTop: 2,
              }}>{slot.code}</div>
              <div style={{ flex: 1 }}>
                <div style={{ fontSize: 14, fontWeight: 600, color: notion.text }}>{slot.full}</div>
                <div style={{ fontSize: 12, color: notion.textSecondary, marginTop: 3, display: 'flex', gap: 10 }}>
                  <span>{slot.room}</span>
                  <span>·</span>
                  <span>Prof. {slot.prof}</span>
                  <span>·</span>
                  <span>{slot.batch}</span>
                </div>
                <div style={{ marginTop: 4 }}>
                  <span style={{
                    fontSize: 10, padding: '1px 6px', borderRadius: 10,
                    background: notion.surface, color: notion.textSecondary, fontWeight: 500,
                  }}>{slot.type || 'Lecture'}</span>
                  {slot.warn && <span style={{
                    marginLeft: 6, fontSize: 10, padding: '1px 6px', borderRadius: 10,
                    background: '#FDECE5', color: '#8B3A2C', fontWeight: 500,
                  }}>⚠ limit exceeded</span>}
                </div>
              </div>
              {adminMode && (
                <div style={{ display: 'flex', gap: 10, paddingTop: 2 }}>
                  <Icon d={ICON.pencil} size={15} color={notion.textSecondary} />
                  <Icon d={ICON.trash} size={15} color={notion.red} />
                </div>
              )}
            </div>
          );
        })}
        {adminMode && (
          <button style={{
            width: '100%', marginTop: 14, padding: '10px 0',
            border: `1px dashed ${notion.borderStrong}`, borderRadius: 8,
            background: notion.bg, fontSize: 13, fontWeight: 500,
            color: notion.textSecondary, display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 6,
            fontFamily: fontSans,
          }}>
            <Icon d={ICON.plus} size={15} />Add another class here
          </button>
        )}
      </div>
    </div>
  );
}

function TimetableGrid({ adminMode = true }) {
  const [drawer, setDrawer] = React.useState(null); // {slots, period, day}
  const periodColW = 40;

  const handleCell = (slots, ci, ri) => {
    if (!slots || !slots.length) return;
    setDrawer({ slots, day: DAYS[ci], period: PERIODS[ri] });
  };

  return (
    <Phone label={adminMode ? 'T-01 · Timetable — Admin' : 'T-01-S · Timetable — Student (read-only)'}>
      <StatusBar />

      <div style={{ padding: '8px 20px 10px' }}>
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
          <div>
            <div style={{ fontSize: 11, color: notion.textSecondary, textTransform: 'uppercase', letterSpacing: 0.8, fontWeight: 600 }}>
              {adminMode ? 'Timetable' : 'My Schedule'}
            </div>
            <div style={{ fontSize: 20, fontWeight: 700, letterSpacing: -0.4, marginTop: 2 }}>Week 14 · Nov 18</div>
          </div>
          <div style={{
            display: 'flex', background: notion.surface, padding: 2, borderRadius: 6,
            border: `1px solid ${notion.border}`, fontSize: 11, fontWeight: 500,
          }}>
            <span style={{ padding: '4px 10px', background: '#fff', borderRadius: 4, boxShadow: '0 1px 2px rgba(0,0,0,0.04)' }}>Grid</span>
            <span style={{ padding: '4px 10px', color: notion.textSecondary }}>List</span>
          </div>
        </div>
      </div>

      {adminMode && (
        <div style={{
          margin: '0 16px 8px', padding: '8px 10px',
          background: '#FDECE5', border: `1px solid #F5C9B6`, borderRadius: 6,
          display: 'flex', alignItems: 'center', gap: 8, fontSize: 11, color: '#8B3A2C',
        }}>
          <Icon d={ICON.alert} size={13} strokeWidth={2} />
          <span style={{ flex: 1 }}>OS exceeds weekly limit (4/3)</span>
          <span style={{ fontWeight: 600, textDecoration: 'underline' }}>View</span>
        </div>
      )}

      <div style={{ flex: 1, overflow: 'hidden', padding: '0 12px', position: 'relative' }}>
        <div style={{ border: `1px solid ${notion.border}`, borderRadius: 8, overflow: 'hidden', background: notion.bg }}>
          {/* Day header */}
          <div style={{ display: 'flex', background: notion.surface, borderBottom: `1px solid ${notion.border}` }}>
            <div style={{ width: periodColW, flexShrink: 0 }} />
            {DAYS.map((d, i) => (
              <div key={i} style={{
                flex: 1, textAlign: 'center', padding: '6px 0',
                fontSize: 10, fontWeight: 600, color: notion.textSecondary,
                letterSpacing: 0.4, textTransform: 'uppercase',
                borderLeft: i === 0 ? 'none' : `1px solid ${notion.border}`,
              }}>{d}</div>
            ))}
          </div>

          {PERIODS.map((p, ri) => (
            <div key={ri} style={{
              display: 'flex',
              borderBottom: ri === PERIODS.length - 1 ? 'none' : `1px solid ${notion.border}`,
              minHeight: adminMode ? 54 : 62,
            }}>
              <div style={{
                width: periodColW, flexShrink: 0,
                padding: '5px 4px', fontSize: 8, color: notion.textFaint,
                textAlign: 'right', fontFamily: fontMono, lineHeight: 1.3,
                background: notion.surface, borderRight: `1px solid ${notion.border}`,
              }}>
                {p.split(' – ').map((t, i) => <div key={i}>{t}</div>)}
              </div>
              {DAYS.map((_, ci) => {
                const cell = GRID[ci][ri];
                const isFree = cell === 'FREE';
                const slots = Array.isArray(cell) ? cell : [];
                const hasSlots = slots.length > 0;

                return (
                  <div
                    key={ci}
                    onClick={() => hasSlots && handleCell(slots, ci, ri)}
                    style={{
                      flex: 1, minWidth: 0,
                      borderLeft: ci === 0 ? 'none' : `1px solid ${notion.border}`,
                      padding: 3,
                      background: isFree
                        ? 'repeating-linear-gradient(45deg, #FAFAF9 0 4px, transparent 4px 8px)'
                        : notion.bg,
                      display: 'flex', flexDirection: 'column', gap: 2,
                      cursor: hasSlots ? 'pointer' : 'default',
                      transition: 'background 0.1s',
                    }}>
                    {isFree && (
                      <div style={{ flex: 1, display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 8, color: notion.textFaint, fontStyle: 'italic' }}>
                        free
                      </div>
                    )}
                    {slots.map((s, si) => (
                      <SlotPill key={si} slot={s} />
                    ))}
                    {!isFree && !hasSlots && adminMode && (
                      <div style={{ flex: 1, display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                        <div style={{ width: 14, height: 14, borderRadius: '50%', border: `1px dashed ${notion.borderStrong}`, display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                          <Icon d={ICON.plus} size={8} color={notion.textFaint} strokeWidth={2.5} />
                        </div>
                      </div>
                    )}
                  </div>
                );
              })}
            </div>
          ))}
        </div>

        {/* Admin tray */}
        {adminMode && (
          <div style={{ marginTop: 10 }}>
            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: '2px 4px 6px' }}>
              <div style={{ fontSize: 11, color: notion.textSecondary, textTransform: 'uppercase', letterSpacing: 0.8, fontWeight: 600 }}>Unassigned</div>
              <span style={{ fontSize: 11, color: notion.textFaint }}>drag onto grid</span>
            </div>
            <div style={{ display: 'flex', gap: 6 }}>
              {[
                { code: 'DBMS', full: 'DBMS', room: 'H-204', prof: 'Rao', batch: 'CSE-24' },
                { code: 'CN', full: 'Networks', room: 'H-110', prof: 'Kapoor', batch: 'CSE-24' },
              ].map((s, i) => {
                const st = subjectStyle[s.code];
                return (
                  <div key={i} style={{
                    width: 120, flexShrink: 0, border: `1px solid ${notion.border}`,
                    borderRadius: 8, padding: '8px 10px', background: notion.bg,
                    boxShadow: '0 1px 2px rgba(0,0,0,0.04)',
                    display: 'flex', flexDirection: 'column', gap: 4,
                  }}>
                    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                      <div style={{ padding: '1px 5px', borderRadius: 3, background: st.bg, color: st.fg, fontSize: 9, fontWeight: 700 }}>{s.code}</div>
                      <Icon d={ICON.grip} size={11} color={notion.textFaint} />
                    </div>
                    <div style={{ fontSize: 11, fontWeight: 600, color: notion.text }}>{s.full}</div>
                    <div style={{ fontSize: 9, color: notion.textSecondary, fontFamily: fontMono }}>{s.room} · {s.prof}</div>
                  </div>
                );
              })}
              <div style={{
                width: 90, flexShrink: 0, border: `1px dashed ${notion.borderStrong}`,
                borderRadius: 8, padding: '8px 10px',
                display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', gap: 4,
                color: notion.textSecondary,
              }}>
                <Icon d={ICON.plus} size={16} strokeWidth={2} />
                <div style={{ fontSize: 11, fontWeight: 500 }}>Add slot</div>
              </div>
            </div>
          </div>
        )}

        {/* Drawer overlay */}
        {drawer && (
          <CellDrawer
            slots={drawer.slots}
            period={drawer.period}
            day={drawer.day}
            onClose={() => setDrawer(null)}
            adminMode={adminMode}
          />
        )}
      </div>

      <BottomNav active="sched" />
    </Phone>
  );
}

function TimetableAdmin() { return <TimetableGrid adminMode={true} />; }
function TimetableStudent() { return <TimetableGrid adminMode={false} />; }

function SlotDetailSheet() {
  return (
    <Phone label="T-02 · Slot Detail drawer (open state)">
      <StatusBar />
      <div style={{ flex: 1, background: notion.surface, position: 'relative' }}>
        {/* faint grid bg */}
        <div style={{ position: 'absolute', inset: 0, opacity: 0.45 }}>
          <div style={{ padding: '14px 16px', fontSize: 14, fontWeight: 600 }}>Timetable · Week 14</div>
          <div style={{ padding: '0 16px' }}>
            {[0,1,2,3].map(i => (
              <div key={i} style={{ display: 'flex', gap: 4, marginBottom: 4 }}>
                {[0,1,2,3,4,5].map(j => (
                  <div key={j} style={{
                    flex: 1, height: 38,
                    background: (j === 0 && i === 0) ? '#FDECC8' : notion.bg,
                    border: `1px solid ${notion.border}`, borderRadius: 3,
                  }} />
                ))}
              </div>
            ))}
          </div>
        </div>
        <div style={{ position: 'absolute', inset: 0, background: 'rgba(15,15,15,0.32)' }} />

        {/* sheet */}
        <div style={{
          position: 'absolute', bottom: 0, left: 0, right: 0,
          background: notion.bg, borderTopLeftRadius: 16, borderTopRightRadius: 16,
          boxShadow: '0 -8px 24px rgba(0,0,0,0.12)', padding: '10px 20px 24px',
        }}>
          <div style={{ width: 36, height: 4, background: notion.borderStrong, borderRadius: 2, margin: '0 auto 14px' }} />
          <div style={{ fontSize: 12, color: notion.textSecondary, marginBottom: 12, fontWeight: 500 }}>Mon · 8:30 – 9:55 · 2 classes</div>
          <div style={{ fontSize: 15, fontWeight: 700, marginBottom: 12 }}>2 classes this slot</div>

          {[
            { code: 'OS', full: 'Operating Systems', room: 'H-105', prof: 'Menon', batch: 'CSE-24', type: 'Lecture' },
            { code: 'DBMS', full: 'DBMS', room: 'H-204', prof: 'Rao', batch: 'ECE-24', type: 'Lecture' },
          ].map((slot, i) => {
            const st = subjectStyle[slot.code];
            return (
              <div key={i} style={{
                padding: '11px 0',
                borderBottom: i === 0 ? `1px solid ${notion.divider}` : 'none',
                display: 'flex', alignItems: 'flex-start', gap: 12,
              }}>
                <div style={{ padding: '3px 8px', borderRadius: 4, background: st.bg, color: st.fg, fontSize: 11, fontWeight: 700, flexShrink: 0, marginTop: 2 }}>{slot.code}</div>
                <div style={{ flex: 1 }}>
                  <div style={{ fontSize: 14, fontWeight: 600 }}>{slot.full}</div>
                  <div style={{ fontSize: 12, color: notion.textSecondary, marginTop: 3 }}>{slot.room} · Prof. {slot.prof} · {slot.batch}</div>
                  <span style={{ marginTop: 4, display: 'inline-block', fontSize: 10, padding: '1px 6px', borderRadius: 10, background: notion.surface, color: notion.textSecondary, fontWeight: 500 }}>{slot.type}</span>
                </div>
                <div style={{ display: 'flex', gap: 10, paddingTop: 2 }}>
                  <Icon d={ICON.pencil} size={15} color={notion.textSecondary} />
                  <Icon d={ICON.trash} size={15} color={notion.red} />
                </div>
              </div>
            );
          })}
          <button style={{
            width: '100%', marginTop: 12, padding: '10px 0',
            border: `1px dashed ${notion.borderStrong}`, borderRadius: 8,
            background: notion.bg, fontSize: 13, fontWeight: 500,
            color: notion.textSecondary, display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 6,
            fontFamily: fontSans,
          }}>
            <Icon d={ICON.plus} size={14} />Add another class here
          </button>
        </div>
      </div>
      <BottomNav active="sched" />
    </Phone>
  );
}

function SlotEditModal() {
  return (
    <Phone label="T-03 · Create / Edit Slot">
      <StatusBar />
      <div style={{ padding: '10px 16px', display: 'flex', alignItems: 'center', justifyContent: 'space-between', borderBottom: `1px solid ${notion.border}` }}>
        <span style={{ fontSize: 14, color: notion.textSecondary }}>Cancel</span>
        <span style={{ fontSize: 15, fontWeight: 600 }}>New slot</span>
        <span style={{ fontSize: 14, fontWeight: 600, color: notion.accent }}>Save</span>
      </div>
      <div style={{ flex: 1, overflow: 'hidden', padding: '18px 20px 0' }}>
        {[
          ['Subject', 'Operating Systems'],
          ['Faculty', 'Prof. R. Menon'],
          ['Room', 'H-105'],
          ['Batch', 'CSE-2024'],
        ].map(([l, v]) => (
          <div key={l} style={{ marginBottom: 16 }}>
            <div style={{ fontSize: 11, color: notion.textSecondary, fontWeight: 500, marginBottom: 5, textTransform: 'uppercase', letterSpacing: 0.6 }}>{l}</div>
            <div style={{
              padding: '10px 12px', background: notion.surface,
              border: `1px solid ${notion.border}`, borderRadius: 8,
              fontSize: 14, display: 'flex', alignItems: 'center', justifyContent: 'space-between',
            }}>
              <span>{v}</span>
              <Icon d={ICON.chevRight} size={14} color={notion.textFaint} />
            </div>
          </div>
        ))}
        <div style={{ marginBottom: 16 }}>
          <div style={{ fontSize: 11, color: notion.textSecondary, fontWeight: 500, marginBottom: 5, textTransform: 'uppercase', letterSpacing: 0.6 }}>Type</div>
          <div style={{ display: 'flex', gap: 6 }}>
            {['Lecture', 'Tutorial', 'Lab', 'Exam'].map((t, i) => (
              <span key={t} style={{
                padding: '6px 10px', borderRadius: 6,
                border: `1px solid ${i === 0 ? notion.text : notion.border}`,
                background: i === 0 ? notion.text : notion.bg,
                color: i === 0 ? '#fff' : notion.text,
                fontSize: 12, fontWeight: 500,
              }}>{t}</span>
            ))}
          </div>
        </div>
        <div style={{
          padding: 10, background: '#FFF9E6', border: `1px solid #F3DFA2`, borderRadius: 8,
          display: 'flex', gap: 8, fontSize: 12, color: '#7C5A00',
        }}>
          <Icon d={ICON.alert} size={14} strokeWidth={2} />
          <span>Day &amp; period pre-filled from selected cell (Mon · 8:30).</span>
        </div>
      </div>
    </Phone>
  );
}

Object.assign(window, { TimetableAdmin, TimetableStudent, SlotDetailSheet, SlotEditModal });
