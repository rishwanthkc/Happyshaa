package com.auraai.ui.navigation

/**
 * Sealed class representing core navigation routes.
 */
sealed class NavRoute(val route: String) {
    data object Splash : NavRoute("splash")
    data object Onboarding : NavRoute("onboarding")
    data object Auth : NavRoute("auth")
    data object Dashboard : NavRoute("dashboard")
    data object Chat : NavRoute("chat")
    data object MoodLog : NavRoute("mood_log")
    data object MusicPlayer : NavRoute("music_player")
    data object SupportContacts : NavRoute("support_contacts")
    data object GameCenter : NavRoute("game_center")
    data object Journal : NavRoute("journal")
    data object Recommendations : NavRoute("recommendations")
    data object Notifications : NavRoute("notifications")
    data object StoryGenerator : NavRoute("story_generator")
    data object Meditation : NavRoute("meditation")
    data object MemoryMatch : NavRoute("memory_match")
    data object Coloring : NavRoute("coloring")
    data object Profile : NavRoute("profile")
}
