# Sorce Career AI — Android Project Rules

You are an Expert Senior Android Developer.

Build this project as a production-quality, compile-ready, responsive native Android application for **Sorce Career AI** using:

- Kotlin
- Jetpack Compose
- Material 3
- Single Activity architecture
- MVVM / UDF
- immutable UI state
- StateFlow
- `collectAsStateWithLifecycle()`
- Compose Navigation

This `rules.md` governs the **Android implementation**. The shared `design.md` may also mention Flutter, but do not mix Flutter/Dart code into the Android project.

The supplied screen references, the current `design.md`, the actual local files shown in the project's `assets` folder, the existing Android source code, and the supplied HTML references define this project.

Do not introduce screens, assets, services, backend behavior, branding, copy, or visual patterns that are not grounded in those sources.

Follow these rules strictly.

---

# 1. SOURCE OF TRUTH

Before implementing or modifying any UI, inspect all available sources for that screen:

1. the supplied PNG screen reference;
2. the current `design.md`;
3. the real files inside the local `assets` folder;
4. the existing Android implementation;
5. the matching supplied HTML reference.

Use this priority when sources disagree:

## 1.1 Visual screen reference

Use the PNG reference as the primary authority for:

- overall composition;
- component hierarchy;
- visible component order;
- alignment and positioning;
- relative proportions;
- image crop and placement;
- selected / active visual states;
- visible labels and copy;
- whether a component is visually present or absent.

## 1.2 `design.md`

Use `design.md` as the primary authority for:

- Android theme colors;
- typography scale;
- spacing tokens;
- shape tokens;
- Material 3 semantic mapping;
- screen names;
- routes;
- architecture expectations;
- UDF / StateFlow requirements.

## 1.3 Local assets

The real local assets supplied by the user take priority over remote image URLs embedded in HTML.

## 1.4 Existing Android code

Preserve existing code when it:

- compiles;
- follows the current architecture;
- already matches the supplied design;
- does not contradict these rules.

Refactor only when necessary to satisfy the current project sources.

## 1.5 HTML references

Use the HTML only to understand:

- structural intent;
- interaction intent;
- deterministic demo content;
- exact visible strings when useful;
- simple micro-interactions.

The HTML is not production Android code.

Never copy product-specific assumptions from an older UiLover project.

The previous project's `rules.md` is only a structural example and is not a requirements source for this project.

---

# 2. PROJECT IDENTITY

This project is:

**Sorce Career AI — AI-assisted career discovery, job matching, job search, job detail, and career assistant mobile app.**

The brand shown in the supplied references is:

- Product name: `Sorce`
- Product badge: `AI`
- Primary domain: career discovery and job matching

Do not rename the product.

Do not convert this project into:

- an e-commerce app;
- a social network;
- a generic chat app;
- a resume editor suite;
- a real recruiting backend;
- an authentication product;
- an older UiLover project.

Do not invent new product features simply because they are common in job apps.

---

# 3. GROUNDED SCREEN REGISTRY

The current supplied project has these grounded screens:

1. `IntroOnboardingScreen`
2. `HomeScreen`
3. `JobSearchScreen`
4. `JobDetailScreen`
5. `AiAssistantChatScreen`

Use the current design routes:

- `onboarding`
- `home`
- `search`
- `job_detail/{jobId}`
- `ai_assistant`

Do not invent additional screens for:

- Saved
- Applications
- Profile
- Notifications
- Sign In
- Registration
- Resume Editor
- Filter Drawer / Filter Sheet
- Company Details
- Network / Alumni details

unless a real reference or an existing grounded implementation is supplied later.

The bottom navigation must still visually show the five referenced items:

- Home
- Search
- Saved
- Applications
- Profile

However, only create navigation destinations for screens that are actually grounded by the supplied references or already exist in the project.

For ungrounded bottom-navigation destinations, expose an action callback without fabricating a new screen.

---

# 4. START DESTINATION AND DEMO FLOW

For a fresh deterministic demo build, use `onboarding` as the start destination unless the existing project already contains a deliberate persisted onboarding-completion flow.

Grounded navigation flow:

- `onboarding` -> `home` from **Get Started**
- `onboarding` -> `home` from **Skip**
- `home` -> `search` from the search entry / relevant discovery CTA
- `home` -> `ai_assistant` from **Ask AI Career Assistant**
- job-card selection -> `job_detail/{jobId}` when the selected item has grounded detail data
- back actions on detail and assistant screens -> previous destination

The **Sign In** control is visible in the onboarding reference, but no Sign In screen is supplied.

Therefore:

- render the control;
- expose `OnSignInClick` / equivalent as an action;
- do not invent a Sign In screen;
- do not add authentication libraries, OAuth, Firebase Auth, or backend login behavior without an explicit requirement.

---

# 5. HTML REFERENCES ARE DESIGN REFERENCES ONLY

Never use any of the following as production Android UI:

- WebView
- raw HTML
- Tailwind CSS
- browser CSS
- JavaScript UI
- Material Symbols web font
- Google Fonts loaded from the web
- remote AIDA image URLs
- `lh3.googleusercontent.com` image URLs
- generated placeholder image websites

Translate the intended design into native Jetpack Compose.

All interactive behavior must be recreated with Kotlin state and events.

Do not copy the HTML's generated color tokens when they conflict with `design.md`.

---

# 6. DESIGN CONFLICT RESOLUTION

If a PNG reference, `design.md`, and HTML disagree, resolve the conflict as follows.

Use the PNG reference for:

- visual structure;
- relative size;
- component position;
- visible text;
- selected states;
- visual density;
- image crop.

Use `design.md` for:

- theme color values;
- type scale;
- spacing tokens;
- shape tokens;
- route names;
- architectural rules.

Use local assets for:

- logos;
- profile image;
- hero imagery;
- onboarding wallpaper.

Use HTML only for:

- interaction hints;
- demo strings not otherwise clear;
- simple state transitions.

Do not silently "fix" demo-data inconsistencies across screenshots.

For example, if a salary shown on a Home job card differs from the salary shown in Job Detail, preserve the screen-specific reference values unless the user explicitly asks for a unified domain model.

---

# 7. OFFICIAL DESIGN TOKENS

Centralize design values. Do not scatter raw values throughout composables.

## 7.1 Color tokens

The official project colors are:

- `primary = #0B63F6`
- `primaryContainer = #E0ECFF`
- `onPrimary = #FFFFFF`
- `onPrimaryContainer = #003899`
- `secondary = #00D284`
- `secondaryContainer = #E6FBF2`
- `surface = #F8FAFC`
- `surfaceVariant = #FFFFFF`
- `surfaceContainerHigh = #EEF2F6`
- `onSurface = #0F172A`
- `onSurfaceVariant = #64748B`
- `outline = #E2E8F0`
- `outlineVariant = #F1F5F9`
- `heroGradientStart = #0B63F6`
- `heroGradientEnd = #004ECC`

The HTML may contain similar but different blue / surface values.

Do not substitute the HTML-generated values for the official Android design tokens.

## 7.2 Typography

The intended font family is Inter with these weights where local font files exist:

- Regular
- Medium
- SemiBold
- Bold

Use the type scale defined in `design.md`:

- Display Small: 30sp / Bold / 36sp
- Headline Medium: 22sp / Bold / 28sp
- Headline Small: 18sp / SemiBold / 24sp
- Title Medium: 16sp / SemiBold / 22sp
- Body Large: 15sp / Regular / 22sp
- Body Medium: 13sp / Regular / 18sp
- Label Large: 14sp / SemiBold / 20sp
- Label Medium: 12sp / Medium / 16sp
- Label Small: 11sp / SemiBold / 14sp

Do not reference missing `R.font.*` resources because that breaks compilation.

If local Inter files are not present in the Android project:

- keep the type scale and weights;
- use `FontFamily.SansSerif` as the compile-safe fallback;
- do not add runtime web-font loading;
- replace the fallback with local Inter resources only when those files are actually available.

## 7.3 Spacing

Use the official spacing system:

```kotlin
object Spacing {
    val xxs = 4.dp
    val xs = 8.dp
    val sm = 12.dp
    val md = 16.dp
    val lg = 20.dp
    val xl = 24.dp
    val xxl = 32.dp
}
```

`Spacing.md = 16.dp` is the standard screen horizontal padding unless a supplied visual reference clearly requires a special edge-to-edge treatment.

## 7.4 Shapes

Use the official shape system:

```kotlin
object AppShapes {
    val TagChip = RoundedCornerShape(8.dp)
    val CardRegular = RoundedCornerShape(16.dp)
    val CardHero = RoundedCornerShape(24.dp)
    val Button = RoundedCornerShape(12.dp)
    val ButtonPill = RoundedCornerShape(50)
    val BottomSheet = RoundedCornerShape(
        topStart = 28.dp,
        topEnd = 28.dp,
    )
}
```

Pill-shaped controls shown in the screenshots may use a full / 50% rounded shape when that is visually required.

---

# 8. OFFICIAL LOCAL ASSET REGISTRY

The supplied `assets` folder screenshot establishes these project files:

## `sorce_ai_official_logo.png`

Semantic role:

`IMG_SORCE_LOGO`

Use for:

- Sorce brand mark on Home;
- Sorce brand mark on Search;
- Sorce logo in Job Detail header;
- Sorce logo in AI Career Assistant header;
- Sorce logo on Intro / Onboarding.

The adjacent `Sorce` title and `AI` pill may be native text when the screenshot shows them separately.

Do not replace this asset with a remote logo URL.

---

## `profile.png`

Semantic role:

`IMG_PROFILE`

Use for:

- top-right profile avatar on Home;
- top-right profile avatar on Search;
- top-right profile avatar on Job Detail;
- top-right profile avatar on AI Career Assistant;
- other user-avatar placements only where the reference clearly represents the current user.

Use a circular crop and preserve the face composition.

Do not use this asset as a company employee / alumni avatar unless the reference explicitly indicates that person is the current user.

---

## `ai_background.png`

Semantic role:

`IMG_AI_HERO`

Use for:

- the AI network / career radar artwork inside the Home discovery hero card.

Preserve the dark square artwork treatment visible in the reference.

Do not use it as the entire screen background.

---

## `intro_wallpaper.png`

Semantic role:

`IMG_INTRO_WALLPAPER`

Use for:

- the full-height onboarding visual background behind the floating stat pills.

Use a crop that fills the viewport similarly to the reference.

Do not stretch the bitmap disproportionately.

---

## `amazon_official_logo.png`

Semantic role:

`IMG_COMPANY_AMAZON`

Use for:

- Amazon hiring-company card;
- Amazon job cards;
- Amazon Job Detail company logo.

---

## `google_official_logo.png`

Semantic role:

`IMG_COMPANY_GOOGLE`

Use for:

- Google hiring-company card;
- Google job cards.

---

## `microsoft_official_logo.png`

Semantic role:

`IMG_COMPANY_MICROSOFT`

Use for:

- Microsoft hiring-company card;
- Microsoft job cards.

---

## `apple_official_logo.png`

Semantic role:

`IMG_COMPANY_APPLE`

Use for:

- Apple hiring-company card;
- Apple job cards.

---

# 9. ASSET IMPLEMENTATION RULES

Before using any image:

1. verify that the file actually exists;
2. map it to the correct semantic role;
3. use the local file instead of the HTML's remote URL;
4. preserve the intended crop / aspect ratio;
5. do not invent replacement photography.

The supplied filenames are already Android-safe lowercase resource names.

Prefer importing them into `res/drawable` without unnecessary renaming.

Expected drawable names:

- `sorce_ai_official_logo`
- `profile`
- `ai_background`
- `intro_wallpaper`
- `amazon_official_logo`
- `google_official_logo`
- `microsoft_official_logo`
- `apple_official_logo`

Never leave these in production UI code:

- `lh3.googleusercontent.com`
- `aida-public`
- temporary generated image URLs
- placeholder image URLs
- remote HTML image URLs

---

# 10. MISSING-ASSET RULES

The current local asset registry does not establish dedicated image files for every visual item present in the HTML / screenshots.

Do not download missing imagery just to fill those gaps.

## 10.1 Stripe

No dedicated Stripe asset is established.

If the Search screen requires the supplied Stripe card, build the logo treatment natively as shown:

- blue rounded-square container;
- centered white `S`;
- deterministic styling.

Do not fetch a Stripe logo from the network.

## 10.2 Meta and Netflix company cards

No dedicated Meta or Netflix local logo is established in the supplied asset folder.

If these Home carousel items are included, use the simple deterministic text / monogram treatment visible in the reference rather than downloading logos.

## 10.3 Alumni / "Meet the Seattle Squad"

No dedicated alumni portrait assets are established.

Do not use remote HTML portraits.

Preserve the overlapping-avatar composition with deterministic native placeholders such as:

- initials;
- neutral avatar icons;
- small generated color circles using theme-compatible local colors.

Keep the `+4` count badge as visible in the reference.

## 10.4 AI sparkle icon

The AI assistant avatar / sparkle motif is not a required bitmap asset.

Build it natively with a Material icon / vector inside the appropriate tinted circular container.

## 10.5 General icons

Use native Material Icons / local vector drawables for:

- Home
- Search
- Bookmark
- Applications
- Profile
- Notifications
- Back
- Location
- Schedule / Time
- Salary / Payment
- Arrow Forward
- Check / Verified
- Filter
- Clear
- Send
- Microphone
- Add Attachment
- Work / Briefcase
- AI sparkle

Do not use web icon fonts.

---

# 11. GLOBAL APP CHROME

## 11.1 Top app bars

Preserve the two visible app-bar patterns.

### Home / Search pattern

Include:

- Sorce logo;
- `Sorce` title;
- small `AI` badge;
- screen context label (`Home` or `Search`);
- notification icon with blue dot;
- circular `profile.png` avatar.

### Detail / Assistant pattern

Include:

- back button;
- Sorce logo;
- screen title (`Job Detail` or `Ai Career Assistant` as shown);
- circular `profile.png` avatar.

Back must use navigation state, not browser/history APIs.

## 11.2 Bottom navigation

Home and Search references show a five-item navigation bar:

- Home
- Search
- Saved
- Applications
- Profile

Use:

- white / surface container;
- subtle top elevation / separation;
- primary blue for active item;
- muted dark gray for inactive items;
- labels beneath icons.

Do not fabricate screens for ungrounded items.

The active item must match the current grounded destination.

---

# 12. INTRO / ONBOARDING SCREEN RULES

Implement `IntroOnboardingScreen` from the supplied reference.

Required visual structure:

- edge-to-edge `intro_wallpaper.png` background;
- top-left Sorce logo + `Sorce` + `AI` badge;
- top-right **Skip** pill;
- three floating white / glass stat pills;
- large rounded white bottom panel;
- centered two-line headline;
- descriptive subtitle;
- small pager indicator;
- full-width primary **Get Started** button;
- bottom **Already have an account? Sign In** action.

Grounded stat copy:

- `98% Match Precision`
- `50,000+ Top Tech Roles`
- `10x Faster Applications`

Grounded headline copy:

- `Find Your Dream Role`
- `With Pure AI Precision`

Grounded body copy:

`Sorce analyzes your tech stack, predicts culture fit, and applies to tier-1 roles automatically.`

Behavior:

- **Skip** -> `home` for the deterministic demo;
- **Get Started** -> `home` for the deterministic demo;
- **Sign In** -> action callback only until a real Sign In screen is supplied.

Do not invent a multi-page onboarding flow simply because a pager indicator is visible.

The current reference only grounds this onboarding screen.

---

# 13. HOME / CAREER DISCOVERY SCREEN RULES

Implement `HomeScreen` as the primary discovery dashboard.

Required sections, in order:

1. Sorce top app bar
2. Search entry with `AI Filter` pill
3. AI discovery hero
4. Top Hiring Companies
5. Popular Job Categories
6. Recent High Matches
7. Ask AI Career Assistant banner
8. bottom navigation

## 13.1 Search entry

Match the visible search affordance and AI Filter pill.

Do not require a real backend search service.

The entry may navigate to `search` and optionally prefill local search state.

## 13.2 AI discovery hero

Required content:

- `AI Radar Active` pill;
- `98% Match Rate` pill;
- `Your Next Job Awaits` title;
- subtitle describing AI matching;
- white `Find Jobs with AI` pill button;
- `ai_background.png` artwork;
- lower divider;
- `2.4k+ new roles this week` metric;
- `Auto-tailored pitch` metric.

Use the official hero gradient:

- start `#0B63F6`
- end `#004ECC`

Use `AppShapes.CardHero` / equivalent 24dp shape.

## 13.3 Top Hiring Companies

Grounded visible company data:

- Amazon — `12 open jobs`
- Google — `8 open jobs`
- Microsoft — `6 open jobs`
- Apple — `5 open jobs`
- Meta — `9 open jobs`
- Netflix — `3 open jobs`

Use local official assets where provided.

For companies without a local logo, use the missing-asset rule in this document.

Keep this section horizontally scrollable if needed to reproduce the reference.

## 13.4 Popular Job Categories

Grounded category data:

- Engineering — `2.4k roles`
- Data & AI — `1.2k roles`
- Design — `856 roles`
- Marketing — `742 roles`

Use the category accent treatments defined by `design.md`.

## 13.5 Recent High Matches

Grounded Home demo cards include:

### Amazon

- Company: `Amazon`
- Role: `Senior Software Engineer`
- Salary: `$120K – $160K / yr`
- Location: `Seattle, USA`
- Type: `Full-time`
- Tags: `Health Insurance`, `401(k) Match`, `Hybrid`
- Match: `98% Match`
- Match caption: `Strong skill resonance`

### Google

- Company: `Google`
- Role: `Staff UX Architect`
- Salary: `$155K – $190K / yr`
- Location: `Mountain View, CA`
- Work style: `Remote-first`
- Tags: `Design Systems`, `Flutter UI`, `Equity Included`
- Match: `94% Match`
- Match caption: `Portfolio aligned`

Bookmark state must be backed by UI state.

Do not mutate icon state directly inside a reusable card.

## 13.6 AI assistant banner

Include:

- assistant / chat icon;
- `Ask AI Career Assistant`;
- `Get live tips for your upcoming interviews`.

Primary behavior:

- navigate to `ai_assistant`.

---

# 14. SEARCH / EXPLORE JOBS SCREEN RULES

Implement `JobSearchScreen` from the supplied Search reference.

Required structure:

1. Sorce Search app bar
2. search field with clear action
3. filter button with active indicator dot
4. horizontal filter chips
5. job-count / AI-curated row
6. sort control
7. vertically stacked job cards
8. Autopilot Application banner
9. bottom navigation

## 14.1 Initial search state

Use the deterministic visible query:

`Senior Software`

## 14.2 Filter chips

Grounded chips include:

- `All 148`
- `Remote`
- `Full-time`
- `Tech & Eng`
- `Design`
- `High Salary ($100k+)`

The screenshot may show only a subset at once because the row is horizontally scrollable.

Keep selection in `JobSearchUiState`.

Do not fabricate a filter sheet until a filter-sheet reference is provided.

The filter icon may emit an action without creating an ungrounded screen.

## 14.3 Sort behavior

The HTML reference demonstrates these deterministic sort labels:

- Relevant
- Latest
- Salary (High)
- AI Match

Store the selected sort in state.

A popup / menu may be implemented natively if it stays consistent with Material 3 and the visual reference.

## 14.4 Search clear behavior

Clear action must:

- set the query to empty;
- preserve screen stability;
- keep focus behavior sensible;
- not perform a network request.

## 14.5 Grounded Search demo jobs

### Google — Staff Cloud Architect

- Salary: `$160K - $210K / yr`
- Posted: `2 days ago`
- Location: `Mountain View, CA`
- Work style: `Hybrid`
- Tags: `Full-time`, `Staff Level`, `GCP`
- Match: `96% Match`

### Microsoft — Senior Frontend Engineer

- Salary: `$130K - $175K / yr`
- Posted: `5 hours ago`
- Location: `Redmond, WA`
- Work style: `Remote`
- Tags: `Full-time`, `React / TS`, `AI Recommended`
- Match: `94% Match`

### Stripe — Product Designer II

- Salary: `$125K - $155K / yr`
- Posted: `Just now`
- Location: `San Francisco, CA`
- Work style: `On-site`
- Tags: `Full-time`, `Design Systems`, `Figma`
- Match: `89% Match`

### Apple — iOS Software Engineer

- Salary: `$145K - $190K / yr`
- Posted: `1 day ago`
- Location: `Cupertino, CA`
- Work style: `Hybrid`
- Tags: `Full-time`, `SwiftUI`, `Native`
- Match: `91% Match`

Bookmark state must be deterministic and local.

Job-card clicks may navigate to a grounded detail route when corresponding detail data exists.

Do not invent unique detail pages for every search result without references.

## 14.6 Autopilot banner

Grounded copy:

- `Autopilot Application`
- `Let Sorce AI auto-tailor your resume`
- `Enable`

The current project does not establish a real automatic-application service.

Therefore:

- render the banner;
- model enabled/disabled state locally if interaction is needed;
- do not add background job application automation, network APIs, or credential handling.

---

# 15. JOB DETAIL SCREEN RULES

Implement `JobDetailScreen` using the supplied Senior Software Engineer reference.

Required sections:

1. back / logo / `Job Detail` app bar
2. blue company header card
3. Perks & Benefits
4. AI Profile Match
5. Role Overview
6. Core Responsibilities
7. Key Technologies
8. Meet the Seattle Squad
9. sticky / bottom action area with bookmark and Apply button

## 15.1 Grounded company header

Use:

- Amazon logo from `amazon_official_logo.png`
- Company: `Amazon`
- Role: `Senior Software Engineer`
- status: `Actively Hiring`
- Location: `Seattle, WA (Headquarters)`
- Work: `Full-time • Hybrid On-site`
- Compensation: `$160,000 – $215,000 / year + equity`

Do not normalize this compensation value to the lower Home-card demo salary.

## 15.2 Perks & Benefits

Grounded visible benefit cards:

- Health Cover
- 401(k) Match
- Flex Time
- Remote Ready

Also show `12 total` as in the reference.

Do not invent the remaining eight benefit names unless more reference content is supplied.

## 15.3 AI Profile Match

Grounded content:

- title: `AI Profile Match`
- score: `92%`
- supporting copy about distributed backends and React ecosystems.

The score is deterministic demo data.

Do not perform real resume analysis or claim to calculate a live score.

## 15.4 Role Overview

Preserve the supplied role-overview copy and hierarchy.

## 15.5 Core Responsibilities

Preserve the three visible responsibilities from the reference.

Use native check icons.

## 15.6 Key Technologies

Grounded chips:

- TypeScript
- React & React Native
- AWS ECS / Lambda
- Distributed Systems
- Docker & K8s
- Node.js

## 15.7 Meet the Seattle Squad

Grounded copy:

- `Meet the Seattle Squad`
- `6 alumni from your network work here`

Use native placeholder avatar treatment because dedicated alumni photo assets are not supplied.

## 15.8 Bookmark behavior

Bookmark action must toggle local `isSaved` state and update the visual icon/container.

Do not add persistence unless the existing project already defines it.

## 15.9 Apply behavior

The HTML reference demonstrates this local sequence:

1. idle: `Apply with AI Sorce`
2. loading: `Preparing Match Profile...`
3. completed: `Application Sent!`

If this interaction is implemented, model it explicitly in UI state, for example:

```kotlin
sealed interface ApplyState {
    data object Idle : ApplyState
    data object Preparing : ApplyState
    data object Sent : ApplyState
}
```

Use a short deterministic local transition for the demo.

Do not claim that a real external job application was submitted.

Do not call a backend unless the user later supplies a real service requirement.

---

# 16. AI CAREER ASSISTANT SCREEN RULES

Implement `AiAssistantChatScreen` from the supplied assistant reference.

Required structure:

1. back / Sorce logo / `Ai Career Assistant` header / profile avatar
2. status row
3. assistant greeting bubble
4. visible timestamp
5. user question bubble
6. assistant answer bubble with skill checklist
7. CTA buttons
8. bottom message composer

## 16.1 Status row

Grounded labels:

- `Sorce Intelligence Online`
- `Private Career Vault`

These are product UI labels only.

Do not infer or implement real private-vault encryption, cloud storage, or privacy infrastructure from the label alone.

## 16.2 Grounded conversation

Assistant greeting:

`Hi! I'm your AI career assistant. I can help you find the best job matches, answer your questions, and even improve your resume.`

Prompt:

`What skills should I highlight for a Senior Software Engineer role at Amazon?`

Grounded suggested skills:

- System Design & Architecture
- React / Node.js / TypeScript
- AWS & Cloud Technologies
- Problem Solving & Leadership
- Team Collaboration

Grounded follow-up actions:

- `Update Resume with AI`
- `Show More Tips`

## 16.3 Composer behavior

The composer includes:

- add attachment button;
- text field with `Type a message...`;
- microphone button;
- send button.

The supplied HTML only establishes a local input/send micro-interaction.

Therefore:

- keep text in UI state;
- ignore blank submissions;
- clear the field after a valid send;
- optionally append the user's sent message locally if the existing implementation already supports local chat history;
- do not fabricate live AI responses;
- do not call a real LLM / API unless explicitly requested later.

Attachment and microphone controls should expose actions only.

Do not add file upload or speech recognition dependencies without a grounded requirement.

---

# 17. DETERMINISTIC DEMO DATA

Keep all supplied demo data deterministic and local.

Prefer a small repository / fake data source such as:

- `DemoJobRepository`
- `DemoCompanyRepository`
- `DemoCareerAssistantRepository`

or another equivalent project-consistent name.

The purpose is to keep composables free of large hardcoded data blocks while avoiding a fake network layer.

Do not add:

- Retrofit
- Ktor client
- Firebase
- Room
- DataStore persistence
- remote config
- real AI SDKs
- job-board APIs

unless the current Android project already requires them or the user explicitly requests them.

Static demo data does not need a pretend remote API abstraction.

---

# 18. NATIVE ANDROID ARCHITECTURE

Use:

- Kotlin
- Jetpack Compose
- Material 3
- Single Activity
- MVVM
- UDF
- immutable `UiState`
- explicit `Action` / `Event` types
- StateFlow
- `collectAsStateWithLifecycle()`
- Compose Navigation

Preferred public screen shape:

```kotlin
@Composable
fun HomeScreen(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit,
)
```

Apply the same pattern to every grounded screen.

ViewModels may coordinate state and screen-level events.

Reusable UI components must not obtain ViewModels directly.

Do not pass `NavController` deep into reusable composables.

Route-level composables may translate screen actions into navigation.

---

# 19. UI STATE REQUIREMENTS

Keep transient and interactive state explicit.

Examples:

## `HomeUiState`

May include:

- bookmarked job IDs;
- company list;
- category list;
- high-match job list.

## `JobSearchUiState`

Should include:

- `query`;
- selected filter IDs;
- selected sort option;
- bookmarked job IDs;
- visible job list;
- local Autopilot enabled state if implemented.

## `JobDetailUiState`

Should include:

- job detail model;
- `isSaved`;
- `applyState`.

## `AiAssistantUiState`

Should include:

- deterministic messages;
- composer text;
- local sending state only if needed.

## `IntroOnboardingUiState`

May remain minimal when the screen is purely static.

Do not use mutable state scattered across child cards when the state belongs to the screen.

Use `remember` only for purely local visual/transient concerns that do not need ViewModel ownership.

---

# 20. NAVIGATION RULES

Centralize route definitions.

Recommended route model:

```kotlin
sealed interface AppRoute {
    data object Onboarding : AppRoute
    data object Home : AppRoute
    data object Search : AppRoute
    data object AiAssistant : AppRoute
    data class JobDetail(val jobId: String) : AppRoute
}
```

An equivalent typed-navigation approach is acceptable.

Do not use raw route strings throughout the UI layer.

Do not invent routes for visual-only bottom-navigation items.

Do not navigate using browser concepts such as `history.back()`.

---

# 21. MATERIAL 3 AND COMPONENT RULES

Use Material 3 components where they can reproduce the supplied design without forcing a visibly different layout.

Prefer:

- `Scaffold`
- `NavigationBar`
- `NavigationBarItem`
- `Surface`
- `Card`
- `IconButton`
- `Button`
- `OutlinedTextField` / custom decorated `BasicTextField` when necessary
- `LazyColumn`
- `LazyRow`
- `Row`
- `Column`
- `Box`

Custom components are allowed when needed for fidelity, especially:

- hero card;
- match score badge / ring;
- job cards;
- category cards;
- floating onboarding stat pills;
- chat bubbles.

Do not over-abstract every one-off element.

Create reusable components only when repetition is real.

---

# 22. LAYOUT, RESPONSIVENESS, AND INSETS

The supplied references are phone layouts and must remain responsive across normal Android phone widths.

Rules:

- do not hardcode the entire UI to one screenshot width;
- use `fillMaxWidth()` with bounded paddings;
- use weights carefully for flexible rows;
- use `LazyColumn` for long scrolling content;
- use `LazyRow` for horizontal company / chip carousels;
- allow text to wrap where the reference does;
- use ellipsis only where truncation is visibly intended;
- avoid fixed heights for text-heavy cards unless the reference genuinely requires them;
- preserve sticky bottom actions without obscuring scrollable content;
- respect navigation-bar and status-bar insets;
- use `WindowInsets` / `safeDrawing` / Scaffold insets appropriately.

The onboarding screen may be edge-to-edge because the reference is visually edge-to-edge.

Do not place content under system bars unintentionally.

---

# 23. IMAGE SCALING RULES

Use intentional `ContentScale` per asset.

Recommended semantics:

- `intro_wallpaper.png`: `ContentScale.Crop`
- `profile.png`: `ContentScale.Crop` inside a circle
- company logos: `ContentScale.Fit`
- `sorce_ai_official_logo.png`: `ContentScale.Fit`
- `ai_background.png`: crop / fit according to the Home hero screenshot, preserving the square artwork

Never stretch logos.

Never recolor official company logos unless the supplied reference shows a monochrome treatment.

---

# 24. COPY RULES

Preserve visible English UI copy from the supplied references.

Do not "improve" wording, capitalization, salary formatting, job titles, labels, or timestamps unless the user asks for content editing.

Examples that must remain as designed include:

- `Sorce`
- `AI`
- `Your Next Job Awaits`
- `Find Jobs with AI`
- `Top Hiring Companies`
- `Popular Job Categories`
- `Recent High Matches`
- `AI Curated`
- `Autopilot Application`
- `AI Profile Match`
- `Apply with AI Sorce`
- `Ai Career Assistant`
- `Private Career Vault`

Do not globally correct `Ai Career Assistant` to `AI Career Assistant` if matching the supplied screen requires the exact visible title.

Use string resources for user-visible strings in production code.

---

# 25. INTERACTION RULES

Implement only grounded local interactions.

Grounded interactions include:

- navigation between supplied screens;
- back navigation;
- bookmark toggle;
- search query edit;
- search clear;
- filter chip toggle;
- sort selection / cycling;
- local Autopilot enable toggle if desired;
- local Apply state transition;
- assistant input send / clear;
- onboarding Skip / Get Started actions.

Do not invent:

- actual job submission;
- real resume updates;
- real job matching algorithms;
- real AI inference;
- network search;
- push notifications;
- account authentication;
- saved-job persistence;
- voice transcription;
- file attachments;
- background automation.

until specifically required.

---

# 26. LOADING, ERROR, AND EMPTY STATES

Do not add large custom loading, error, offline, or empty-state designs unless the project already contains them or the user supplies references.

When implementation requires transient feedback:

- use minimal state-driven progress indicators;
- keep them visually consistent with the current screen;
- avoid introducing new full-screen states.

The Job Detail Apply button's local preparing state is grounded by HTML and is allowed.

---

# 27. ACCESSIBILITY

Every clickable icon without visible text must have a meaningful `contentDescription` unless it is purely decorative.

Use adequate touch targets, normally at least 48dp where practical.

Respect text scaling.

Do not encode meaning only by color.

For match scores, include readable text such as `98% Match` in addition to visual rings or color.

Use semantic button roles for clickable card-like controls where appropriate.

Do not merge semantics in a way that hides essential job title, company, or match information from accessibility services.

---

# 28. PREVIEW RULES

Provide useful previews for grounded screens and important reusable components.

At minimum, support a Pixel 7-style phone preview for:

- `IntroOnboardingScreen`
- `HomeScreen`
- `JobSearchScreen`
- `JobDetailScreen`
- `AiAssistantChatScreen`

Where practical, also verify a larger font scale such as `1.25f`.

Preview data must come from deterministic demo factories.

Do not instantiate production ViewModels in previews.

---

# 29. COMPILE-SAFETY RULES

The project must remain compile-ready after each implementation step.

Do not:

- reference nonexistent drawables;
- reference nonexistent fonts;
- leave unresolved imports;
- leave placeholder `TODO()` in production execution paths;
- depend on web resources for required UI;
- call APIs without configured dependencies;
- add unnecessary libraries for trivial UI.

When a source asset is missing, use the fallback rules in this document rather than referencing a fake resource.

---

# 30. IMPLEMENTATION WORKFLOW

For every screen:

1. inspect the PNG reference;
2. inspect the matching HTML;
3. check the relevant `design.md` tokens;
4. verify all referenced local assets;
5. define deterministic screen data;
6. define immutable `UiState`;
7. define `Action` / `Event` types;
8. implement the stateless screen UI;
9. connect route / ViewModel state;
10. implement only grounded interactions;
11. compare the Android result against the PNG;
12. fix layout, typography, spacing, image crop, and active states;
13. confirm there are no remote image dependencies;
14. confirm the project compiles.

---

# 31. VISUAL QA CHECKLIST

Before considering a screen complete, verify:

- correct Sorce logo asset;
- correct company logo asset;
- correct profile avatar asset;
- correct onboarding wallpaper;
- correct Home AI artwork;
- official theme colors from `design.md`;
- consistent 16dp main horizontal padding where applicable;
- correct section order;
- correct visible copy;
- correct selected bottom-nav item;
- correct selected filter chips;
- correct bookmark state;
- correct job match score;
- correct card corner radii;
- no remote URLs;
- no accidental e-commerce leftovers;
- no invented screens;
- no invented backend behavior.

---

# 32. FORBIDDEN LEGACY / CROSS-PROJECT CONTENT

Remove or reject any leftover references to the previous e-commerce project, including concepts such as:

- products;
- product gallery;
- cart;
- checkout;
- promo code;
- flash deals;
- best sellers;
- shopping bag branding;
- e-commerce routes;
- old image-number registries such as `1.jpg` ... `11.jpg`;
- crimson commerce theme assumptions.

This project's domain is career discovery and job matching.

---

# 33. FINAL DEFINITION OF DONE

The Android implementation is complete only when:

- all five grounded screens are implemented or preserved if already correct;
- routes match the current project specification;
- local assets are used correctly;
- no HTML / WebView implementation remains;
- no remote image URL is required for the UI;
- colors, typography, spacing, and shapes follow `design.md`;
- screen-specific demo content matches the supplied references;
- local interactions are state-driven;
- no ungrounded screen or service has been invented;
- accessibility basics are present;
- previews are available for major screens;
- the project compiles successfully.

When uncertain, prefer the smallest implementation that faithfully reproduces the supplied Sorce references and remains compile-ready.
