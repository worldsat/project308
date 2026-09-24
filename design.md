# DESIGN SYSTEM SPECIFICATION: SORCE CAREER AI
**Target Framework:** Android Jetpack Compose (Material 3) & Flutter  
**Version:** 1.0.0 (Production-Ready Spec for AI Coding Agents)

---

## 1. GLOBAL DESIGN TOKENS

### 1.1 Color Palette (M3 Semantic Mappings)

All colors are strictly mapped to Jetpack Compose `androidx.compose.material3.ColorScheme` tokens.

| Role | Token Name | Hex Code | Compose Literal | Usage Description |
| :--- | :--- | :--- | :--- | :--- |
| **Primary** | `primary` | `#0B63F6` | `Color(0xFF0B63F6)` | Brand electric blue: Primary CTA buttons, key active indicators, highlighted badges |
| **Primary Container** | `primaryContainer` | `#E0ECFF` | `Color(0xFFE0ECFF)` | Light tint tint for chips, active category backgrounds, match score pills |
| **On Primary** | `onPrimary` | `#FFFFFF` | `Color(0xFFFFFFFF)` | Text & icons on Primary CTA backgrounds |
| **On Primary Container** | `onPrimaryContainer` | `#003899` | `Color(0xFF003899)` | Text/icons placed over `primaryContainer` |
| **Secondary / Accent** | `secondary` | `#00D284` | `Color(0xFF00D284)` | Match percentage indicator, positive skill resonance, success states |
| **Secondary Variant** | `secondaryContainer`| `#E6FBF2` | `Color(0xFFE6FBF2)` | Light green container for high match score badges |
| **Surface** | `surface` | `#F8FAFC` | `Color(0xFFF8FAFC)` | Root background for screens and scaffold container |
| **Surface Variant** | `surfaceVariant` | `#FFFFFF` | `Color(0xFFFFFFFF)` | Elevated card surfaces, search bars, filter items |
| **Surface Container High**| `surfaceContainerHigh`| `#EEF2F6` | `Color(0xFFEEF2F6)` | Secondary pill badges, unselected category icons, chip backgrounds |
| **On Surface** | `onSurface` | `#0F172A` | `Color(0xFF0F172A)` | High-emphasis headers, titles, primary labels (`Slate 900`) |
| **On Surface Variant**| `onSurfaceVariant` | `#64748B` | `Color(0xFF64748B)` | Medium-emphasis subheadings, metadata text, timestamp labels (`Slate 500`) |
| **Outline** | `outline` | `#E2E8F0` | `Color(0xFFE2E8F0)` | Subtle borders on cards, input text field strokes, divider lines (`Slate 200`) |
| **Outline Variant** | `outlineVariant` | `#F1F5F9` | `Color(0xFFF1F5F9)` | Extremely soft hair-line dividers between list items |
| **Hero Gradient Start**| `heroGradientStart`| `#0B63F6` | `Color(0xFF0B63F6)` | Linear gradient start for Discovery Hero Card |
| **Hero Gradient End** | `heroGradientEnd` | `#004ECC` | `Color(0xFF004ECC)` | Linear gradient end for Discovery Hero Card |

---

### 1.2 Typography (Material 3 Type Scale)

**Font Family:** `FontFamily(Font(R.font.inter_regular), Font(R.font.inter_medium, FontWeight.Medium), Font(R.font.inter_semibold, FontWeight.SemiBold), Font(R.font.inter_bold, FontWeight.Bold))`

| M3 Style | Size (sp) | Weight | Line Height (sp) | Tracking (sp) | Usage |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Display Small** | 30.sp | Bold (`W700`) | 36.sp | 0.sp | Hero Banner title ("Your Next Job Awaits") |
| **Headline Medium** | 22.sp | Bold (`W700`) | 28.sp | 0.sp | Section titles ("Top Hiring Companies", "Recent High Matches") |
| **Headline Small** | 18.sp | SemiBold (`W600`)| 24.sp | 0.15.sp | Job card titles, company modal headers |
| **Title Medium** | 16.sp | SemiBold (`W600`)| 22.sp | 0.15.sp | TopAppBar title, Chat bubble sender name |
| **Body Large** | 15.sp | Regular (`W400`)| 22.sp | 0.25.sp | Job description paragraphs, Chat assistant responses |
| **Body Medium** | 13.sp | Regular (`W400`)| 18.sp | 0.25.sp | Metadata (Salary range, location, contract type) |
| **Label Large** | 14.sp | SemiBold (`W600`)| 20.sp | 0.1.sp | CTA buttons ("Apply Now", "Find Jobs with AI") |
| **Label Medium** | 12.sp | Medium (`W500`) | 16.sp | 0.4.sp | Tag chips (Health Insurance, 401(k), Hybrid) |
| **Label Small** | 11.sp | SemiBold (`W600`)| 14.sp | 0.5.sp | Bottom navigation labels, "AI Powered" pill badge |

---

### 1.3 Spacing & Shapes

#### Spacing System
```kotlin
object Spacing {
    val xxs = 4.dp
    val xs = 8.dp
    val sm = 12.dp
    val md = 16.dp     // Standard screen horizontal padding
    val lg = 20.dp     // Inter-card vertical spacing
    val xl = 24.dp     // Section vertical margin
    val xxl = 32.dp    // Screen header to content margin
}
```

#### Shape System (Corner Radii)
```kotlin
object AppShapes {
    val TagChip = RoundedCornerShape(8.dp)          // Filter tags, feature pills
    val CardRegular = RoundedCornerShape(16.dp)     // Job cards, category tiles, company cards
    val CardHero = RoundedCornerShape(24.dp)        // Top banner cards with internal artwork
    val Button = RoundedCornerShape(12.dp)          // Primary & secondary CTAs
    val ButtonPill = RoundedCornerShape(50)         // Circular badges, search bar, icon containers
    val BottomSheet = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
}
```

---

## 2. SCREEN REFERENCE GALLERY

| Screen Name | Technical Title | Architecture & Role |
| :--- | :--- | :--- |
| **Discovery / Home** | `HomeScreen` | **Route: `home`**<br>Entry point with interactive search bar, AI-powered promo banner, hiring companies carousel, colored category grid, and high-match job stream. |
| **Job Details** | `JobDetailScreen` | **Route: `job_detail/{jobId}`**<br>Full job profile featuring company header banner, salary and location metrics, benefit chips, AI match resonance score circle, and floating bottom action bar. |
| **AI Assistant** | `AiAssistantChatScreen`| **Route: `ai_assistant`**<br>Interactive conversational agent for resume improvement, interview prep, skill recommendations, and direct AI actions. |
| **Search & Filter** | `JobSearchScreen` | **Route: `search`**<br>Dynamic search interface with instant keyword suggestions, live category chips, and filter drawer integration. |
| **Intro & Onboarding**| `IntroOnboardingScreen`| **Route: `onboarding`**<br>Visual welcome screen with 3D fluid graphic, value proposition carousel, and "Get Started" auth gateway. |

---

## 3. COMPONENT-LEVEL SPECIFICATIONS

### 3.1 Global Navigation Chrome
- **TopAppBar (`SorceTopAppBar`):**
    - Container: `Box` with fixed height `56.dp`, padding `16.dp` horizontal.
    - Content: Row with `Arrangement.SpaceBetween`.
    - Leading: Sorce Brand Logo (`{{DATA:IMAGE:IMAGE_28}}`) + App Title ("Sorce" with AI chip badge).
    - Trailing: Row with notification bell `IconButton` with dot indicator + circular profile avatar (`size = 36.dp`, `clip = CircleShape`).

- **BottomNavigationBar (`SorceBottomNavBar`):**
    - Layout: `NavigationBar` with `containerColor = Color.White`, elevation `8.dp`.
    - 5 Items: **Home** (`Icons.Default.Home`), **Search** (`Icons.Default.Search`), **Saved** (`Icons.Default.BookmarkBorder`), **Applications** (`Icons.Default.WorkOutline`), **Profile** (`Icons.Default.PersonOutline`).
    - Active color: `Color(0xFF0B63F6)`, Inactive color: `Color(0xFF94A3B8)`.

### 3.2 Discovery Hero Banner (`AiDiscoveryHeroCard`)
- **Container:** `Card` with `shape = RoundedCornerShape(24.dp)`, `height = 180.dp`.
- **Background:** `Brush.linearGradient(listOf(Color(0xFF0B63F6), Color(0xFF004ECC)))`.
- **Layout:** `Box` containing:
    - Left column: `Column` with `weight(0.6f)` containing AI pill badge, title ("Your Next Job Awaits"), subtitle text, and white pill button ("Find Jobs with AI" with search icon).
    - Right asset: Isolated developer figure overlay (`{{DATA:IMAGE:IMAGE_6}}` or `{{DATA:IMAGE:IMAGE_5}}`), vertically aligned to bottom with `contentScale = ContentScale.Crop`.

### 3.3 Popular Job Categories (`CategoryGrid`)
- **Layout:** 2x2 Grid or vertical Column of 2 Rows (`Arrangement.spacedBy(12.dp)`).
- **Category Card Spec:**
    - Background: `Color.White`, border `1.dp` solid `Color(0xFFF1F5F9)`, shape `RoundedCornerShape(16.dp)`.
    - Content: `Row` with `Arrangement.SpaceBetween`, `Alignment.CenterVertically`.
    - Left group: Colored icon container (`size = 40.dp`, `shape = RoundedCornerShape(10.dp)`) + Column (Title in `W600`, Open Roles count in `Slate 500`).
    - Color Tokens for Category Icons:
        - **Engineering:** Background `Color(0xFFEEF2FF)`, Icon Tint `Color(0xFF4F46E5)`.
        - **Data & AI:** Background `Color(0xFFECFDF5)`, Icon Tint `Color(0xFF10B981)`.
        - **Design:** Background `Color(0xFFFAF5FF)`, Icon Tint `Color(0xFFA855F7)`.
        - **Marketing:** Background `Color(0xFFFFF7ED)`, Icon Tint `Color(0xFFF97316)`.

### 3.4 Job Match Item (`JobCard`)
- **Container:** `Surface` with `shape = RoundedCornerShape(16.dp)`, `color = Color.White`, shadow elevation `1.dp`, border `1.dp` solid `Color(0xFFE2E8F0)`.
- **Header:** Row with Company Logo in white square container (`44.dp`), Title + Company name, Trailing Bookmark `IconButton`.
- **Meta Chips:** Horizontal scrollable `Row(spacedBy = 8.dp)` with Benefit pills (Health Insurance, 401(k), Hybrid).
- **Footer:** Row with:
    - Left: Circular percentage indicator or Pill with Match score (e.g., `98% Match`).
    - Right: `Button` with `containerColor = Color(0xFF0B63F6)`, text "Apply Now" with trailing arrow icon.

---

## 4. FIXED IMAGE ASSET REGISTRY

| Asset Identifier | Asset Type | Compose Resource Spec | Semantic UI Location |
| :--- | :--- | :--- | :--- |
| `{{DATA:IMAGE:IMAGE_28}}` | SVG / PNG | `painterResource(R.drawable.ic_sorce_logo)` | Top App Bar brand mark (Leading item) |
| `{{DATA:IMAGE:IMAGE_9}}` | Vector / PNG | `painterResource(R.drawable.ic_google_logo)` | Google job item company logo container |
| `{{DATA:IMAGE:IMAGE_10}}` | Vector / PNG | `painterResource(R.drawable.ic_amazon_logo)` | Amazon job item & header company logo |
| `{{DATA:IMAGE:IMAGE_13}}` | 3D Graphic | `painterResource(R.drawable.bg_fluid_waves)` | Background hero art for `IntroOnboardingScreen` |
| `{{DATA:IMAGE:IMAGE_27}}` | Photograph | `painterResource(R.drawable.avatar_user_profile)`| TopAppBar trailing avatar & Profile tab |
| `{{DATA:IMAGE:IMAGE_5}}` | Cutout / Photo | `painterResource(R.drawable.img_engineer_banner)` | Right side of `AiDiscoveryHeroCard` |
| `{{DATA:IMAGE:IMAGE_3}}` | 3D Tech Asset | `painterResource(R.drawable.ic_career_radar)` | AI Career Radar node card & empty states |

---

## 5. AI IMPLEMENTATION SPECIAL INSTRUCTIONS (Cursor / Claude / Copilot Prompt)

```kotlin
// ============================================================================
// SYSTEM INSTRUCTION FOR JETPACK COMPOSE AI CODE AGENTS
// ============================================================================
// 1. ARCHITECTURE PATTERN:
//    - Strictly enforce Android Modern Architecture (MVI / MVVM with StateFlow).
//    - Every Screen Composable MUST accept a single UI State object and an
//      (Event) -> Unit lambda: e.g. HomeScreen(uiState: HomeUiState, onAction: (HomeAction) -> Unit).
//    - Never instantiate ViewModels directly inside reusable sub-components.
//
// 2. DESIGN TOKENS ENFORCEMENT:
//    - Primary CTA Button background color MUST strictly be Color(0xFF0B63F6).
//    - Background of standard screens MUST be Color(0xFFF8FAFC).
//    - Never hardcode raw hex values inside composable bodies; reference the
//      centralized AppTheme tokens or the constants defined in Section 1.
//
// 3. LAYOUT & CONTAINERS:
//    - Top-level screen container MUST be a Scaffold with contentWindowInsets.
//    - Main scrollable content MUST use LazyColumn with standard horizontal padding:
//      Modifier.padding(horizontal = 16.dp).
//    - Ensure all clickable cards have proper ripple indication and semantic role:
//      Modifier.clickable(role = Role.Button) { ... }.
//
// 4. PREVIEW SPECIFICATION:
//    - Provide @Preview(showBackground = true, device = Devices.PIXEL_7) for every
//      screen in both Light Mode and FontScale = 1.25f to verify accessibility.
// ============================================================================
```
