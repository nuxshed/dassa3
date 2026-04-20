// Notion-style primitives for IMS hi-fi mockups

const PHONE_W = 390;
const PHONE_H = 844;

const notion = {
  bg: '#ffffff',
  surface: '#F7F7F5',
  border: '#EDEDEC',
  borderStrong: '#E3E2E0',
  text: '#37352F',
  textSecondary: '#787774',
  textFaint: '#9B9A97',
  hover: '#F1F1EF',
  selected: '#E8E8E6',
  accent: '#2383E2',
  red: '#E03E3E',
  green: '#0F7B6C',
  yellow: '#DFAB01',
  chipBg: '#F1F1EF',
  divider: '#F1F1EF',
};

const fontSans = '"Inter", -apple-system, BlinkMacSystemFont, "Segoe UI", system-ui, sans-serif';
const fontMono = '"JetBrains Mono", "SF Mono", ui-monospace, Menlo, monospace';

function Phone({ label, children, scrollable = false }) {
  return (
    <DCArtboard label={label} width={PHONE_W} height={PHONE_H} style={{
      borderRadius: 38,
      border: '8px solid #111',
      boxShadow: '0 20px 50px rgba(0,0,0,0.18), 0 4px 12px rgba(0,0,0,0.08)',
      background: '#111',
      padding: 0,
    }}>
      <div style={{
        width: PHONE_W - 16, height: PHONE_H - 16,
        borderRadius: 30,
        background: notion.bg,
        color: notion.text,
        fontFamily: fontSans,
        fontSize: 15,
        position: 'relative',
        overflow: 'hidden',
        display: 'flex', flexDirection: 'column',
      }}>
        {/* Notch */}
        <div style={{
          position: 'absolute', top: 8, left: '50%', transform: 'translateX(-50%)',
          width: 110, height: 28, background: '#111', borderRadius: 16, zIndex: 20,
        }} />
        {children}
      </div>
    </DCArtboard>
  );
}

function StatusBar() {
  return (
    <div style={{
      height: 44, padding: '0 24px',
      display: 'flex', alignItems: 'center', justifyContent: 'space-between',
      fontSize: 14, fontWeight: 600, color: notion.text,
      flexShrink: 0,
    }}>
      <span style={{ fontFeatureSettings: '"tnum"' }}>9:41</span>
      <div style={{ width: 110 }} />
      <div style={{ display: 'flex', alignItems: 'center', gap: 5, fontSize: 12 }}>
        <span>􀙇</span><span>􀛨</span><span>􀛪</span>
      </div>
    </div>
  );
}

function BottomNav({ active }) {
  const tabs = [
    ['home', 'Home', 'M3 12l9-9 9 9M5 10v10h14V10'],
    ['search', 'Search', 'M21 21l-4.3-4.3M10.5 18a7.5 7.5 0 1 1 0-15 7.5 7.5 0 0 1 0 15z'],
    ['sched', 'Schedule', 'M3 9h18M5 5h14a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V7a2 2 0 0 1 2-2zM8 3v4M16 3v4'],
    ['people', 'People', 'M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2M9 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM22 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75'],
    ['me', 'Me', 'M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2M12 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8z'],
  ];
  return (
    <div style={{
      marginTop: 'auto',
      padding: '8px 8px 24px',
      borderTop: `1px solid ${notion.border}`,
      background: notion.bg,
      display: 'flex',
      flexShrink: 0,
    }}>
      {tabs.map(([k, lbl, d]) => {
        const on = active === k;
        return (
          <div key={k} style={{
            flex: 1, display: 'flex', flexDirection: 'column', alignItems: 'center', gap: 3,
            padding: '6px 0',
            color: on ? notion.text : notion.textFaint,
          }}>
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth={on ? 2 : 1.6} strokeLinecap="round" strokeLinejoin="round">
              <path d={d} />
            </svg>
            <span style={{ fontSize: 10, fontWeight: on ? 600 : 400 }}>{lbl}</span>
          </div>
        );
      })}
    </div>
  );
}

function TopBar({ title, leftSlot, rightSlot, mono = false, border = true }) {
  return (
    <div style={{
      padding: '44px 20px 10px',
      display: 'flex', alignItems: 'center', justifyContent: 'space-between',
      borderBottom: border ? `1px solid ${notion.border}` : 'none',
      flexShrink: 0,
    }}>
      <div style={{ fontSize: 15, fontWeight: 700, fontFamily: mono ? fontMono : fontSans, letterSpacing: mono ? -0.3 : -0.2 }}>
        {leftSlot ?? title}
      </div>
      <div>{rightSlot}</div>
    </div>
  );
}

function Avatar({ initials, size = 32, bg = '#E9E5DA', fg = '#8B7E5C' }) {
  return (
    <div style={{
      width: size, height: size, borderRadius: '50%',
      background: bg, color: fg,
      display: 'flex', alignItems: 'center', justifyContent: 'center',
      fontSize: size * 0.38, fontWeight: 600, flexShrink: 0,
    }}>{initials}</div>
  );
}

function Icon({ d, size = 18, color = 'currentColor', strokeWidth = 1.75, fill = 'none' }) {
  return (
    <svg width={size} height={size} viewBox="0 0 24 24" fill={fill} stroke={color} strokeWidth={strokeWidth} strokeLinecap="round" strokeLinejoin="round">
      {typeof d === 'string' ? <path d={d} /> : d}
    </svg>
  );
}

// Lucide-like paths we'll reuse
const ICON = {
  search: 'M21 21l-4.3-4.3M10.5 18a7.5 7.5 0 1 1 0-15 7.5 7.5 0 0 1 0 15z',
  x: 'M18 6L6 18M6 6l12 12',
  chevDown: 'M6 9l6 6 6-6',
  chevRight: 'M9 6l6 6-6 6',
  chevLeft: 'M15 6l-6 6 6 6',
  plus: 'M12 5v14M5 12h14',
  arrow: 'M5 12h14M13 5l7 7-7 7',
  filter: 'M3 6h18M6 12h12M10 18h4',
  more: 'M12 13a1 1 0 1 0 0-2 1 1 0 0 0 0 2zM19 13a1 1 0 1 0 0-2 1 1 0 0 0 0 2zM5 13a1 1 0 1 0 0-2 1 1 0 0 0 0 2z',
  check: 'M20 6L9 17l-5-5',
  pencil: 'M17 3a2.828 2.828 0 0 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z',
  trash: 'M3 6h18M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2M19 6l-1 14a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2L5 6',
  msg: 'M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z',
  cal: 'M3 9h18M5 5h14a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V7a2 2 0 0 1 2-2zM8 3v4M16 3v4',
  users: 'M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2M9 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM22 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75',
  clip: 'M9 11h6M9 15h6M9 2h6a2 2 0 0 1 2 2v16a2 2 0 0 1-2 2H9a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2zM11 4h2',
  file: 'M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8zM14 2v6h6',
  bank: 'M3 10h18M5 6l7-4 7 4M4 10v8M8 10v8M16 10v8M20 10v8M2 21h20',
  paper: 'M4 22h16a2 2 0 0 0 2-2V8l-6-6H6a2 2 0 0 0-2 2v4M14 2v6h6M18 14v8M14 18h8',
  brief: 'M16 20V4a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16M2 13h20M4 7h16a2 2 0 0 1 2 2v9a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V9a2 2 0 0 1 2-2z',
  userCog: 'M10 15H6a4 4 0 0 0-4 4v2M14 3a4 4 0 1 1 0 8 4 4 0 0 1 0-8zM19 16l-2 3M19 22l-2-3M22 19l-3-2M22 19l-3 2M19 16a3 3 0 1 0 0 6 3 3 0 0 0 0-6z',
  userPlus: 'M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2M9 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8zM20 8v6M17 11h6',
  enter: 'M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4M10 17l5-5-5-5M15 12H3',
  grip: 'M9 5a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM9 12a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM9 19a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM17 5a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM17 12a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM17 19a1 1 0 1 1-2 0 1 1 0 0 1 2 0z',
  mapPin: 'M20 10c0 7-8 12-8 12s-8-5-8-12a8 8 0 0 1 16 0zM12 13a3 3 0 1 0 0-6 3 3 0 0 0 0 6z',
  user: 'M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2M12 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8z',
  alert: 'M12 9v4M12 17h.01M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z',
  clock: 'M12 22a10 10 0 1 0 0-20 10 10 0 0 0 0 20zM12 6v6l4 2',
  book: 'M4 19.5A2.5 2.5 0 0 1 6.5 17H20M4 19.5A2.5 2.5 0 0 0 6.5 22H20V2H6.5A2.5 2.5 0 0 0 4 4.5v15z',
};

function ModuleIcon({ name, size = 20 }) {
  return <Icon d={ICON[name]} size={size} strokeWidth={1.75} />;
}

// Notion-style collapsible section header
function SectionHeader({ label, open = true, right }) {
  return (
    <div style={{
      padding: '14px 20px 6px',
      display: 'flex', alignItems: 'center', justifyContent: 'space-between',
    }}>
      <div style={{ display: 'flex', alignItems: 'center', gap: 6, color: notion.textSecondary, fontSize: 11, fontWeight: 600, letterSpacing: 0.8, textTransform: 'uppercase' }}>
        <Icon d={open ? ICON.chevDown : ICON.chevRight} size={12} strokeWidth={2.5} />
        <span>{label}</span>
      </div>
      {right}
    </div>
  );
}

function SearchField({ placeholder, value, onChange, compact = false }) {
  return (
    <div style={{
      margin: compact ? '8px 16px' : '10px 20px',
      height: 40,
      padding: '0 12px',
      display: 'flex', alignItems: 'center', gap: 10,
      background: notion.surface,
      border: `1px solid ${notion.border}`,
      borderRadius: 8,
      fontSize: 14,
      color: value ? notion.text : notion.textFaint,
    }}>
      <Icon d={ICON.search} size={16} color={notion.textSecondary} strokeWidth={2} />
      <span style={{ flex: 1 }}>{value || placeholder}</span>
      {value && <Icon d={ICON.x} size={14} color={notion.textFaint} strokeWidth={2} />}
    </div>
  );
}

Object.assign(window, {
  PHONE_W, PHONE_H, notion, fontSans, fontMono,
  Phone, StatusBar, BottomNav, TopBar, Avatar, Icon, ICON, ModuleIcon,
  SectionHeader, SearchField,
});
