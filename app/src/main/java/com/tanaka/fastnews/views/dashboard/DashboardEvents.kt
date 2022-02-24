package com.tanaka.fastnews.views.dashboard

sealed class DashboardEvents {
    object ToggleUITheme: DashboardEvents()
    object ViewTopHeadlines: DashboardEvents()
    object ViewTechnology: DashboardEvents()
    object ViewHealth: DashboardEvents()
    object ViewSport: DashboardEvents()
}