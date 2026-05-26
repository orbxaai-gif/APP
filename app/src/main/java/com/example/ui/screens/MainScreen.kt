package com.example.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Person
import com.example.ui.components.AIAssistantPanel
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.*

sealed class Nav(val route: String, val title: String, val icon: ImageVector) {
    object Dash : Nav("dash", "Dashboard", Icons.Filled.Home)
    object Props : Nav("props", "Properties", Icons.AutoMirrored.Filled.List)
    object Clients : Nav("clients", "Clients", Icons.Filled.Person)
    object Profile : Nav("profile", "Settings", Icons.Filled.AccountBox)
}
val navItems = listOf(Nav.Dash, Nav.Props, Nav.Clients, Nav.Profile)

@Composable
fun MainScreen(viewModel: AppViewModel = viewModel()) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("main") { MainAppNavHost(viewModel) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppNavHost(viewModel: AppViewModel) {
    val navController = rememberNavController()
    var showAIAssistant by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = DeepBlack,
        floatingActionButton = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (currentDestination?.route == Nav.Dash.route) {
                FloatingActionButton(
                    onClick = { showAIAssistant = true },
                    containerColor = ElectricBlue,
                    contentColor = DeepBlack
                ) {
                    Icon(Icons.Filled.AutoAwesome, contentDescription = "AI Assistant")
                }
            }
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val isVisible = navItems.any { it.route == currentDestination?.route }
            
            if (isVisible) {
                NavigationBar(
                    containerColor = DarkGray,
                    contentColor = TextMuted
                ) {
                    navItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.title) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = DeepBlack,
                                selectedTextColor = PremiumGold,
                                indicatorColor = PremiumGold,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Nav.Dash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Nav.Dash.route) { DashboardScreen(navController, viewModel) }
            composable(Nav.Props.route) { PropertiesScreen(navController, viewModel) }
            composable(Nav.Clients.route) { ClientsScreen(navController, viewModel) }
            composable(Nav.Profile.route) { ProfileScreen(navController, viewModel) }
            composable("add") { AddPropertyScreen(navController, viewModel) }
            composable("details/{id}") { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("id")
                PropertyDetailsScreen(id, navController, viewModel)
            }
        }

        if (showAIAssistant) {
            ModalBottomSheet(
                onDismissRequest = { showAIAssistant = false },
                containerColor = Color.Transparent,
                scrimColor = DeepBlack.copy(alpha=0.5f),
                dragHandle = null
            ) {
                AIAssistantPanel { showAIAssistant = false }
            }
        }
    }
}
