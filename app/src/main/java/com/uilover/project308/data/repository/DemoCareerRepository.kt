package com.uilover.project308.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.ui.graphics.Color
import com.uilover.project308.R
import com.uilover.project308.data.model.Company
import com.uilover.project308.data.model.JobCategory
import com.uilover.project308.data.model.JobDetail
import com.uilover.project308.data.model.JobMatch
import com.uilover.project308.data.model.JobPerk
import com.uilover.project308.data.model.JobTechnology
import com.uilover.project308.ui.theme.CategoryDataAiBg
import com.uilover.project308.ui.theme.CategoryDataAiIcon
import com.uilover.project308.ui.theme.CategoryDesignBg
import com.uilover.project308.ui.theme.CategoryDesignIcon
import com.uilover.project308.ui.theme.CategoryEngineeringBg
import com.uilover.project308.ui.theme.CategoryEngineeringIcon
import com.uilover.project308.ui.theme.CategoryMarketingBg
import com.uilover.project308.ui.theme.CategoryMarketingIcon
import com.uilover.project308.ui.theme.Primary

/**
 * Deterministic Mock Repository for Sorce Career AI per rules.md §13 and §17.
 * Provides grounded mock data without network calls or external dependencies.
 */
object DemoCareerRepository {

    fun getTopHiringCompanies(): List<Company> = listOf(
        Company(
            id = "amazon",
            name = "Amazon",
            openJobsCount = 12,
            logoRes = R.drawable.amazon_official_logo
        ),
        Company(
            id = "google",
            name = "Google",
            openJobsCount = 8,
            logoRes = R.drawable.google_official_logo
        ),
        Company(
            id = "microsoft",
            name = "Microsoft",
            openJobsCount = 6,
            logoRes = R.drawable.microsoft_official_logo
        ),
        Company(
            id = "apple",
            name = "Apple",
            openJobsCount = 5,
            logoRes = R.drawable.apple_official_logo
        ),
        Company(
            id = "meta",
            name = "Meta",
            openJobsCount = 9,
            monogramText = "Meta",
            monogramColor = Primary
        ),
        Company(
            id = "netflix",
            name = "Netflix",
            openJobsCount = 3,
            monogramText = "N",
            monogramColor = Color(0xFFE50914)
        )
    )

    fun getPopularJobCategories(): List<JobCategory> = listOf(
        JobCategory(
            id = "engineering",
            title = "Engineering",
            openRolesCount = "2.4k roles",
            icon = Icons.Outlined.Code,
            containerColor = CategoryEngineeringBg,
            iconTint = CategoryEngineeringIcon
        ),
        JobCategory(
            id = "data_ai",
            title = "Data & AI",
            openRolesCount = "1.2k roles",
            icon = Icons.Outlined.BarChart,
            containerColor = CategoryDataAiBg,
            iconTint = CategoryDataAiIcon
        ),
        JobCategory(
            id = "design",
            title = "Design",
            openRolesCount = "856 roles",
            icon = Icons.Outlined.Palette,
            containerColor = CategoryDesignBg,
            iconTint = CategoryDesignIcon
        ),
        JobCategory(
            id = "marketing",
            title = "Marketing",
            openRolesCount = "742 roles",
            icon = Icons.Outlined.Campaign,
            containerColor = CategoryMarketingBg,
            iconTint = CategoryMarketingIcon
        )
    )

    private val savedJobIds = mutableSetOf<String>()

    fun isBookmarked(jobId: String): Boolean = savedJobIds.contains(jobId)

    fun toggleBookmark(jobId: String): Boolean {
        return if (savedJobIds.contains(jobId)) {
            savedJobIds.remove(jobId)
            false
        } else {
            savedJobIds.add(jobId)
            true
        }
    }

    fun setBookmarked(jobId: String, isBookmarked: Boolean) {
        if (isBookmarked) {
            savedJobIds.add(jobId)
        } else {
            savedJobIds.remove(jobId)
        }
    }

    fun getRecentHighMatches(): List<JobMatch> = listOf(
        JobMatch(
            id = "amazon_senior_swe",
            companyName = "Amazon",
            companyLogoRes = R.drawable.amazon_official_logo,
            isVerified = true,
            roleTitle = "Senior Software Engineer",
            salaryRange = "$160K – $215K / yr",
            location = "Seattle, USA",
            employmentType = "Full-time",
            perks = listOf("Health Insurance", "401(k) Match", "Hybrid"),
            matchScore = 98,
            matchCaption = "Strong skill resonance",
            isBookmarked = isBookmarked("amazon_senior_swe")
        ),
        JobMatch(
            id = "google_staff_ux",
            companyName = "Google",
            companyLogoRes = R.drawable.google_official_logo,
            isVerified = true,
            roleTitle = "Staff UX Architect",
            salaryRange = "$155K – $190K / yr",
            location = "Mountain View, CA",
            employmentType = "Remote-first",
            perks = listOf("Design Systems", "Flutter UI", "Equity Included"),
            matchScore = 94,
            matchCaption = "Portfolio aligned",
            isBookmarked = isBookmarked("google_staff_ux")
        )
    )

    fun getAllJobs(): List<JobMatch> = listOf(
        JobMatch(
            id = "amazon_senior_swe",
            companyName = "Amazon",
            companyLogoRes = R.drawable.amazon_official_logo,
            isVerified = true,
            roleTitle = "Senior Software Engineer",
            salaryRange = "$160K – $215K / yr",
            location = "Seattle, USA",
            employmentType = "Full-time",
            perks = listOf("Health Insurance", "401(k) Match", "Hybrid"),
            matchScore = 98,
            matchCaption = "Strong skill resonance",
            isBookmarked = isBookmarked("amazon_senior_swe")
        ),
        JobMatch(
            id = "google_staff_ux",
            companyName = "Google",
            companyLogoRes = R.drawable.google_official_logo,
            isVerified = true,
            roleTitle = "Staff UX Architect",
            salaryRange = "$155K – $190K / yr",
            location = "Mountain View, CA",
            employmentType = "Remote-first",
            perks = listOf("Design Systems", "Flutter UI", "Equity Included"),
            matchScore = 94,
            matchCaption = "Portfolio aligned",
            isBookmarked = isBookmarked("google_staff_ux")
        ),
        JobMatch(
            id = "google_staff_cloud",
            companyName = "Google",
            companyLogoRes = R.drawable.google_official_logo,
            isVerified = true,
            roleTitle = "Staff Cloud Architect",
            salaryRange = "$160K – $210K / yr",
            location = "Mountain View, CA",
            employmentType = "Hybrid",
            perks = listOf("GCP Infra", "Kubernetes", "Health Cover"),
            matchScore = 96,
            matchCaption = "Enterprise cloud lead",
            isBookmarked = isBookmarked("google_staff_cloud")
        ),
        JobMatch(
            id = "microsoft_senior_frontend",
            companyName = "Microsoft",
            companyLogoRes = R.drawable.microsoft_official_logo,
            isVerified = true,
            roleTitle = "Senior Frontend Engineer",
            salaryRange = "$130K – $175K / yr",
            location = "Redmond, WA",
            employmentType = "Remote",
            perks = listOf("React 19", "Fluent UI", "Copilot Integration"),
            matchScore = 94,
            matchCaption = "AI Copilot UX",
            isBookmarked = isBookmarked("microsoft_senior_frontend")
        ),
        JobMatch(
            id = "apple_ios_swe",
            companyName = "Apple",
            companyLogoRes = R.drawable.apple_official_logo,
            isVerified = true,
            roleTitle = "iOS Software Engineer",
            salaryRange = "$145K – $190K / yr",
            location = "Cupertino, CA",
            employmentType = "Hybrid",
            perks = listOf("Swift 6", "ProMotion 120Hz", "Apple Silicon"),
            matchScore = 91,
            matchCaption = "High performance graphics",
            isBookmarked = isBookmarked("apple_ios_swe")
        ),
        JobMatch(
            id = "meta_ai_engineer",
            companyName = "Meta",
            companyLogoRes = R.drawable.sorce_ai_official_logo,
            isVerified = true,
            roleTitle = "AI Research Engineer",
            salaryRange = "$175K – $230K / yr",
            location = "Menlo Park, CA",
            employmentType = "Hybrid",
            perks = listOf("LLaMA", "PyTorch", "Distributed Training"),
            matchScore = 95,
            matchCaption = "LLM Fine-tuning",
            isBookmarked = isBookmarked("meta_ai_engineer")
        ),
        JobMatch(
            id = "netflix_senior_backend",
            companyName = "Netflix",
            companyLogoRes = R.drawable.sorce_ai_official_logo,
            isVerified = true,
            roleTitle = "Senior Platform Engineer",
            salaryRange = "$200K – $250K / yr",
            location = "Los Gatos, CA",
            employmentType = "Flexible",
            perks = listOf("All Cash Pay", "AWS Cloud", "High Concurrency"),
            matchScore = 93,
            matchCaption = "Streaming architecture",
            isBookmarked = isBookmarked("netflix_senior_backend")
        )
    )

    private val allJobDetails: Map<String, JobDetail> by lazy {
        mapOf(
            "amazon_senior_swe" to JobDetail(
                id = "amazon_senior_swe",
                companyName = "Amazon",
                companyLogoRes = R.drawable.amazon_official_logo,
                isVerified = true,
                roleTitle = "Senior Software Engineer",
                hiringStatus = "Actively Hiring",
                location = "Seattle, WA (Headquarters)",
                workStyle = "Full-time • Hybrid On-site",
                compensation = "$160,000 – $215,000",
                compensationSuffix = "/ year + equity",
                perksTotalCount = "12 total",
                perks = listOf(
                    JobPerk(
                        id = "health_cover",
                        title = "Health Cover",
                        icon = Icons.Outlined.HealthAndSafety
                    ),
                    JobPerk(
                        id = "401k_match",
                        title = "401(k) Match",
                        icon = Icons.Outlined.AccountBalanceWallet
                    ),
                    JobPerk(
                        id = "flex_time",
                        title = "Flex Time",
                        icon = Icons.Outlined.CalendarToday
                    ),
                    JobPerk(
                        id = "remote_ready",
                        title = "Remote Ready",
                        icon = Icons.Outlined.HomeWork
                    )
                ),
                aiMatchScore = 98,
                aiMatchDescription = "Strong fit based on your experience with distributed backends, scalable APIs, and React ecosystems.",
                roleOverview = "As a Senior Software Engineer at Amazon, you will architect cutting-edge cloud infrastructure, craft resilient distributed systems, and mentor engineers across high-velocity teams. You'll translate complex customer challenges into ultra-scalable real-time services.",
                responsibilities = listOf(
                    "Spearhead end-to-end technical system architecture for high-throughput event processing.",
                    "Maintain latency standards (<15ms) across multi-region microservice deployments.",
                    "Direct sprint grooming, tech debriefs, and collaborative product roadmap alignment."
                ),
                keyTechnologies = listOf(
                    JobTechnology("TypeScript", isPrimary = true),
                    JobTechnology("React & React Native", isPrimary = true),
                    JobTechnology("AWS ECS / Lambda", isPrimary = true),
                    JobTechnology("Distributed Systems", isPrimary = true),
                    JobTechnology("Docker & K8s", isPrimary = false),
                    JobTechnology("Node.js", isPrimary = false)
                ),
                squadTitle = "Meet the Seattle Squad",
                squadDescription = "6 alumni from your network work here",
                alumniCount = 6,
                isBookmarked = false
            ),

            "google_staff_ux" to JobDetail(
                id = "google_staff_ux",
                companyName = "Google",
                companyLogoRes = R.drawable.google_official_logo,
                isVerified = true,
                roleTitle = "Staff UX Architect",
                hiringStatus = "Actively Hiring",
                location = "Mountain View, CA",
                workStyle = "Remote-first • Full-time",
                compensation = "$155,000 – $190,000",
                compensationSuffix = "/ year + equity",
                perksTotalCount = "14 total",
                perks = listOf(
                    JobPerk(
                        id = "design_systems",
                        title = "Design Systems",
                        icon = Icons.Outlined.Palette
                    ),
                    JobPerk(
                        id = "flutter_ui",
                        title = "Flutter UI",
                        icon = Icons.Outlined.Code
                    ),
                    JobPerk(
                        id = "equity_incl",
                        title = "Equity Included",
                        icon = Icons.Outlined.AccountBalanceWallet
                    ),
                    JobPerk(
                        id = "wellness",
                        title = "Health & Wellness",
                        icon = Icons.Outlined.HealthAndSafety
                    )
                ),
                aiMatchScore = 94,
                aiMatchDescription = "Portfolio strongly aligned with Google Material Design 3, accessibility guidelines, and multi-platform design systems.",
                roleOverview = "As a Staff UX Architect at Google, you will lead the vision for intuitive, accessible developer and consumer experiences across Google's next-generation platform products. You will partner with cross-functional directors to evolve design systems at global scale.",
                responsibilities = listOf(
                    "Define the end-to-end UX architecture and design system specifications across Android and Web.",
                    "Lead user research synthesis and prototype high-fidelity motion-rich interaction models.",
                    "Drive accessibility, localization, and ergonomic UI standards across products reaching billions of users."
                ),
                keyTechnologies = listOf(
                    JobTechnology("Figma & Tokens", isPrimary = true),
                    JobTechnology("Material You / M3", isPrimary = true),
                    JobTechnology("Flutter UI", isPrimary = true),
                    JobTechnology("Design Systems", isPrimary = true),
                    JobTechnology("User Research", isPrimary = false),
                    JobTechnology("Motion Design", isPrimary = false)
                ),
                squadTitle = "Meet the Mountain View Design Squad",
                squadDescription = "4 alumni from your network work here",
                alumniCount = 4,
                isBookmarked = false
            ),

            "google_staff_cloud" to JobDetail(
                id = "google_staff_cloud",
                companyName = "Google",
                companyLogoRes = R.drawable.google_official_logo,
                isVerified = true,
                roleTitle = "Staff Cloud Architect",
                hiringStatus = "Actively Hiring",
                location = "Mountain View, CA",
                workStyle = "Hybrid • Full-time",
                compensation = "$160,000 – $210,000",
                compensationSuffix = "/ yr + equity",
                perksTotalCount = "16 total",
                perks = listOf(
                    JobPerk(
                        id = "cloud_credits",
                        title = "Cloud Credits",
                        icon = Icons.Outlined.HomeWork
                    ),
                    JobPerk(
                        id = "health_wellness",
                        title = "Health & Wellness",
                        icon = Icons.Outlined.HealthAndSafety
                    ),
                    JobPerk(
                        id = "401k_match",
                        title = "401(k) Match",
                        icon = Icons.Outlined.AccountBalanceWallet
                    ),
                    JobPerk(
                        id = "learning_stipend",
                        title = "Learning Stipend",
                        icon = Icons.Outlined.CalendarToday
                    )
                ),
                aiMatchScore = 96,
                aiMatchDescription = "Exceptional alignment with distributed Google Cloud Platform (GCP) and Kubernetes cluster management.",
                roleOverview = "As a Staff Cloud Architect at Google Cloud, you will architect mission-critical enterprise cloud infrastructures, architect zero-trust security postures, and innovate real-time distributed data pipelines for Tier-1 clients worldwide.",
                responsibilities = listOf(
                    "Design enterprise-grade multi-region cloud infrastructures on Google Cloud Platform.",
                    "Benchmark and optimize distributed throughput, resilience, and disaster recovery SLA.",
                    "Collaborate with global technical teams on Google Kubernetes Engine (GKE) topologies."
                ),
                keyTechnologies = listOf(
                    JobTechnology("Google Cloud (GCP)", isPrimary = true),
                    JobTechnology("Kubernetes / GKE", isPrimary = true),
                    JobTechnology("Terraform & IaC", isPrimary = true),
                    JobTechnology("Distributed Systems", isPrimary = true),
                    JobTechnology("Go / Golang", isPrimary = false),
                    JobTechnology("Zero-Trust Security", isPrimary = false)
                ),
                squadTitle = "Meet the Bay Area Cloud Squad",
                squadDescription = "5 alumni from your network work here",
                alumniCount = 5,
                isBookmarked = false
            ),

            "microsoft_senior_frontend" to JobDetail(
                id = "microsoft_senior_frontend",
                companyName = "Microsoft",
                companyLogoRes = R.drawable.microsoft_official_logo,
                isVerified = true,
                roleTitle = "Senior Frontend Engineer",
                hiringStatus = "AI Recommended",
                location = "Redmond, WA",
                workStyle = "Remote • Full-time",
                compensation = "$130,000 – $175,000",
                compensationSuffix = "/ yr + bonus",
                perksTotalCount = "10 total",
                perks = listOf(
                    JobPerk(
                        id = "home_office",
                        title = "Home Office",
                        icon = Icons.Outlined.HomeWork
                    ),
                    JobPerk(
                        id = "health_cover",
                        title = "Health Cover",
                        icon = Icons.Outlined.HealthAndSafety
                    ),
                    JobPerk(
                        id = "401k_match",
                        title = "401(k) Match",
                        icon = Icons.Outlined.AccountBalanceWallet
                    ),
                    JobPerk(
                        id = "flex_time",
                        title = "Flex Time",
                        icon = Icons.Outlined.CalendarToday
                    )
                ),
                aiMatchScore = 94,
                aiMatchDescription = "Top percentile match for TypeScript, modern React, Copilot UX integrations, and Fluent UI design language.",
                roleOverview = "As a Senior Frontend Engineer at Microsoft, you will develop responsive, blazing-fast interfaces for Microsoft 365 and Copilot AI experiences, ensuring seamless real-time collaboration across millions of enterprise users worldwide.",
                responsibilities = listOf(
                    "Build high-performance web applications using modern React, TypeScript, and Fluent UI.",
                    "Implement AI-assisted workflows and interactive conversational widgets into productivity tools.",
                    "Optimize core web vitals, rendering lifecycles, and cross-browser accessibility."
                ),
                keyTechnologies = listOf(
                    JobTechnology("React 19", isPrimary = true),
                    JobTechnology("TypeScript", isPrimary = true),
                    JobTechnology("Fluent UI", isPrimary = true),
                    JobTechnology("WebSockets / Realtime", isPrimary = true),
                    JobTechnology("Next.js", isPrimary = false),
                    JobTechnology("Jest & Cypress", isPrimary = false)
                ),
                squadTitle = "Meet the Redmond Frontend Squad",
                squadDescription = "8 alumni from your network work here",
                alumniCount = 8,
                isBookmarked = false
            ),

            "stripe_product_designer" to JobDetail(
                id = "stripe_product_designer",
                companyName = "Stripe",
                companyLogoRes = null,
                monogramText = "S",
                monogramColor = Primary,
                isVerified = true,
                roleTitle = "Product Designer II",
                hiringStatus = "Actively Hiring",
                location = "San Francisco, CA",
                workStyle = "On-site • Full-time",
                compensation = "$125,000 – $155,000",
                compensationSuffix = "/ yr + equity",
                perksTotalCount = "15 total",
                perks = listOf(
                    JobPerk(
                        id = "catered_meals",
                        title = "Catered Meals",
                        icon = Icons.Outlined.HealthAndSafety
                    ),
                    JobPerk(
                        id = "commuter",
                        title = "Commuter Benefit",
                        icon = Icons.Outlined.CalendarToday
                    ),
                    JobPerk(
                        id = "wellness",
                        title = "Wellness Stipend",
                        icon = Icons.Outlined.AccountBalanceWallet
                    ),
                    JobPerk(
                        id = "remote_gear",
                        title = "Remote Gear",
                        icon = Icons.Outlined.HomeWork
                    )
                ),
                aiMatchScore = 89,
                aiMatchDescription = "Aligned with your focus on fintech checkout UX, high-polish typography, and developer-centric workflows.",
                roleOverview = "As a Product Designer II at Stripe, you will craft the future of global economic infrastructure. You'll obsess over every micro-interaction, typography detail, and developer dashboard workflow that powers millions of global businesses.",
                responsibilities = listOf(
                    "Design intuitive user flows and checkout interfaces for global payment methods.",
                    "Maintain and expand Stripe's signature design system and visual precision.",
                    "Conduct rapid prototype experiments and quantitative A/B design validation."
                ),
                keyTechnologies = listOf(
                    JobTechnology("Figma & Tokens", isPrimary = true),
                    JobTechnology("Design Systems", isPrimary = true),
                    JobTechnology("Interactive Prototyping", isPrimary = true),
                    JobTechnology("FinTech UX", isPrimary = true),
                    JobTechnology("Motion Design", isPrimary = false),
                    JobTechnology("Typography", isPrimary = false)
                ),
                squadTitle = "Meet the SF Design Guild",
                squadDescription = "3 alumni from your network work here",
                alumniCount = 3,
                isBookmarked = false
            ),

            "apple_ios_swe" to JobDetail(
                id = "apple_ios_swe",
                companyName = "Apple",
                companyLogoRes = R.drawable.apple_official_logo,
                isVerified = true,
                roleTitle = "iOS Software Engineer",
                hiringStatus = "Actively Hiring",
                location = "Cupertino, CA",
                workStyle = "Hybrid • Full-time",
                compensation = "$145,000 – $190,000",
                compensationSuffix = "/ yr + RSU",
                perksTotalCount = "18 total",
                perks = listOf(
                    JobPerk(
                        id = "apple_discounts",
                        title = "Apple Discounts",
                        icon = Icons.Outlined.AccountBalanceWallet
                    ),
                    JobPerk(
                        id = "healthcare",
                        title = "Premium Health",
                        icon = Icons.Outlined.HealthAndSafety
                    ),
                    JobPerk(
                        id = "wellness_center",
                        title = "Wellness Center",
                        icon = Icons.Outlined.CalendarToday
                    ),
                    JobPerk(
                        id = "401k_match",
                        title = "401(k) Match",
                        icon = Icons.Outlined.HomeWork
                    )
                ),
                aiMatchScore = 91,
                aiMatchDescription = "Strong match for native mobile engineering, Swift concurrency, and high-performance UI rendering on Apple Silicon.",
                roleOverview = "As an iOS Software Engineer at Apple, you will build world-class mobile experiences integrated into iOS ecosystem apps. You will leverage modern Swift, SwiftUI, and Apple silicon optimizations to deliver fluid 120Hz ProMotion graphics.",
                responsibilities = listOf(
                    "Develop native iOS features using Swift, SwiftUI, and modern concurrency.",
                    "Profile and optimize app memory footprints, power consumption, and launch times.",
                    "Collaborate with Apple Human Interface Designers to craft fluid gesture-driven interactions."
                ),
                keyTechnologies = listOf(
                    JobTechnology("Swift 6", isPrimary = true),
                    JobTechnology("SwiftUI", isPrimary = true),
                    JobTechnology("CoreAnimation", isPrimary = true),
                    JobTechnology("iOS Architecture", isPrimary = true),
                    JobTechnology("Combine", isPrimary = false),
                    JobTechnology("Instruments Profiling", isPrimary = false)
                ),
                squadTitle = "Meet the Cupertino iOS Squad",
                squadDescription = "7 alumni from your network work here",
                alumniCount = 7,
                isBookmarked = false
            ),

            "meta_ai_engineer" to JobDetail(
                id = "meta_ai_engineer",
                companyName = "Meta",
                companyLogoRes = null,
                monogramText = "Meta",
                monogramColor = Primary,
                isVerified = true,
                roleTitle = "AI Research Engineer",
                hiringStatus = "Actively Hiring",
                location = "Menlo Park, CA",
                workStyle = "Hybrid • Full-time",
                compensation = "$175,000 – $230,000",
                compensationSuffix = "/ year + RSU",
                perksTotalCount = "14 total",
                perks = listOf(
                    JobPerk(
                        id = "gpu_clusters",
                        title = "GPU Clusters",
                        icon = Icons.Outlined.Code
                    ),
                    JobPerk(
                        id = "health_cover",
                        title = "Health Coverage",
                        icon = Icons.Outlined.HealthAndSafety
                    ),
                    JobPerk(
                        id = "401k_match",
                        title = "401(k) Match",
                        icon = Icons.Outlined.AccountBalanceWallet
                    ),
                    JobPerk(
                        id = "wellness",
                        title = "Wellness Stipend",
                        icon = Icons.Outlined.CalendarToday
                    )
                ),
                aiMatchScore = 95,
                aiMatchDescription = "High resonance with LLM fine-tuning, PyTorch model deployment, and distributed inference systems.",
                roleOverview = "As an AI Research Engineer at Meta, you will push the boundaries of open-source artificial intelligence, optimizing LLaMA-based architectures and scaling foundational models to billions of users worldwide.",
                responsibilities = listOf(
                    "Train and evaluate large language models across distributed GPU clusters.",
                    "Deploy high-throughput inference engines with low latency guarantees.",
                    "Partner with FAIR researchers to convert state-of-the-art papers into production systems."
                ),
                keyTechnologies = listOf(
                    JobTechnology("PyTorch", isPrimary = true),
                    JobTechnology("Python & C++", isPrimary = true),
                    JobTechnology("Distributed Training", isPrimary = true),
                    JobTechnology("CUDA / Triton", isPrimary = true),
                    JobTechnology("LLaMA Models", isPrimary = false),
                    JobTechnology("Ray / Slurm", isPrimary = false)
                ),
                squadTitle = "Meet the Menlo Park AI Lab",
                squadDescription = "5 alumni from your network work here",
                alumniCount = 5,
                isBookmarked = false
            ),

            "netflix_senior_backend" to JobDetail(
                id = "netflix_senior_backend",
                companyName = "Netflix",
                companyLogoRes = null,
                monogramText = "N",
                monogramColor = Color(0xFFE50914),
                isVerified = true,
                roleTitle = "Senior Platform Engineer",
                hiringStatus = "Actively Hiring",
                location = "Los Gatos, CA",
                workStyle = "Flexible • Full-time",
                compensation = "$200,000 – $250,000",
                compensationSuffix = "/ year (All Cash)",
                perksTotalCount = "12 total",
                perks = listOf(
                    JobPerk(
                        id = "top_market_pay",
                        title = "Top Market Pay",
                        icon = Icons.Outlined.AccountBalanceWallet
                    ),
                    JobPerk(
                        id = "open_vacation",
                        title = "Open Vacation",
                        icon = Icons.Outlined.CalendarToday
                    ),
                    JobPerk(
                        id = "health_wellness",
                        title = "Comprehensive Health",
                        icon = Icons.Outlined.HealthAndSafety
                    ),
                    JobPerk(
                        id = "remote_flex",
                        title = "Remote Flexibility",
                        icon = Icons.Outlined.HomeWork
                    )
                ),
                aiMatchScore = 93,
                aiMatchDescription = "Superb alignment with fault-tolerant cloud microservices, chaos engineering, and streaming APIs.",
                roleOverview = "As a Senior Platform Engineer at Netflix, you will build and evolve the high-availability cloud control plane that powers seamless global streaming and studio production workflows for over 260 million members.",
                responsibilities = listOf(
                    "Design resilient, highly concurrent backend microservices on AWS infrastructure.",
                    "Implement chaos testing practices and real-time observability dashboards.",
                    "Collaborate across autonomous engineering teams with Netflix's freedom and responsibility culture."
                ),
                keyTechnologies = listOf(
                    JobTechnology("Java / Kotlin", isPrimary = true),
                    JobTechnology("Spring Boot & gRPC", isPrimary = true),
                    JobTechnology("AWS Cloud", isPrimary = true),
                    JobTechnology("Distributed Systems", isPrimary = true),
                    JobTechnology("Kafka", isPrimary = false),
                    JobTechnology("Cassandra", isPrimary = false)
                ),
                squadTitle = "Meet the Los Gatos Streaming Squad",
                squadDescription = "4 alumni from your network work here",
                alumniCount = 4,
                isBookmarked = false
            )
        )
    }

    fun getJobDetail(jobId: String = "amazon_senior_swe"): JobDetail {
        val normalizedId = jobId.trim().lowercase()

        // 1. Direct key lookup
        val matchedJob = allJobDetails[jobId]
            ?: allJobDetails[normalizedId]
            // 2. Company shortcut / alias lookup
            ?: when {
                normalizedId == "amazon" || normalizedId.contains("amazon") -> allJobDetails["amazon_senior_swe"]
                normalizedId == "google" || normalizedId.contains("ux") -> allJobDetails["google_staff_ux"]
                normalizedId.contains("cloud") -> allJobDetails["google_staff_cloud"]
                normalizedId == "microsoft" || normalizedId.contains("microsoft") || normalizedId.contains("frontend") -> allJobDetails["microsoft_senior_frontend"]
                normalizedId == "stripe" || normalizedId.contains("stripe") || normalizedId.contains("designer") -> allJobDetails["stripe_product_designer"]
                normalizedId == "apple" || normalizedId.contains("apple") || normalizedId.contains("ios") -> allJobDetails["apple_ios_swe"]
                normalizedId == "meta" || normalizedId.contains("meta") -> allJobDetails["meta_ai_engineer"]
                normalizedId == "netflix" || normalizedId.contains("netflix") -> allJobDetails["netflix_senior_backend"]
                else -> null
            }
            // 3. Fallback dynamically generated job detail for any arbitrary ID
            ?: createFallbackJobDetail(jobId)

        return matchedJob.copy(
            isBookmarked = isBookmarked(matchedJob.id)
        )
    }

    private fun createFallbackJobDetail(jobId: String): JobDetail {
        val cleanName = jobId.replace("_", " ").replace("-", " ")
            .split(" ")
            .filter { it.isNotBlank() }
            .joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

        return JobDetail(
            id = jobId,
            companyName = cleanName.split(" ").firstOrNull() ?: "Tech Lead",
            companyLogoRes = R.drawable.sorce_ai_official_logo,
            isVerified = true,
            roleTitle = cleanName,
            hiringStatus = "Actively Hiring",
            location = "San Francisco, CA",
            workStyle = "Full-time • Hybrid",
            compensation = "$140,000 – $185,000",
            compensationSuffix = "/ year + equity",
            perksTotalCount = "12 total",
            perks = listOf(
                JobPerk("health", "Health Cover", Icons.Outlined.HealthAndSafety),
                JobPerk("401k", "401(k) Match", Icons.Outlined.AccountBalanceWallet),
                JobPerk("flex", "Flex Time", Icons.Outlined.CalendarToday),
                JobPerk("remote", "Remote Ready", Icons.Outlined.HomeWork)
            ),
            aiMatchScore = 90,
            aiMatchDescription = "Matches your current career path, skill profile, and work preferences.",
            roleOverview = "Lead impactful initiatives for $cleanName, collaborate with top engineers and designers, and build scalable modern products.",
            responsibilities = listOf(
                "Collaborate with cross-functional partners to plan and execute roadmap features.",
                "Maintain high performance, reliability, and code quality standards.",
                "Mentor teammates and champion technical best practices."
            ),
            keyTechnologies = listOf(
                JobTechnology("Kotlin / Java", isPrimary = true),
                JobTechnology("Jetpack Compose", isPrimary = true),
                JobTechnology("System Architecture", isPrimary = true),
                JobTechnology("Cloud APIs", isPrimary = false)
            ),
            squadTitle = "Meet the Engineering Squad",
            squadDescription = "Colleagues in your network work here",
            alumniCount = 4,
            isBookmarked = false
        )
    }
}
