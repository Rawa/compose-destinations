package com.ramcosta.composedestinations.wear

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.navigation
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.WearNavigator
import androidx.wear.compose.navigation.composable
import com.ramcosta.composedestinations.annotation.InternalDestinationsApi
import com.ramcosta.composedestinations.manualcomposablecalls.DestinationLambda
import com.ramcosta.composedestinations.scope.DestinationScopeImpl
import com.ramcosta.composedestinations.manualcomposablecalls.ManualComposableCalls
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.spec.*

/**
 * Returns the default [SwipeDismissableNavHost] to be used with normal Wear OS apps.
 */
@Composable
fun rememberWearNavHostEngine(): NavHostEngine = remember {
    DefaultWearNavHostEngine()
}

internal class DefaultWearNavHostEngine : NavHostEngine {

    override val type = NavHostEngine.Type.DEFAULT

    @Composable
    override fun rememberNavController(
        vararg navigators: Navigator<out NavDestination>
    ) =
        androidx.navigation.compose.rememberNavController(WearNavigator(), *navigators)

    @Composable
    override fun NavHost(
        modifier: Modifier,
        route: String,
        startRoute: Route,
        navController: NavHostController,
        builder: NavGraphBuilder.() -> Unit
    ) {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = startRoute.route,
            modifier = modifier,
            route = route,
            builder = builder
        )
    }

    override fun NavGraphBuilder.navigation(
        navGraph: NavGraphSpec,
        builder: NavGraphBuilder.() -> Unit
    ) {
        navigation(
            startDestination = navGraph.startRoute.route,
            route = navGraph.route,
            builder = builder
        )
    }

    @OptIn(ExperimentalAnimationApi::class, InternalDestinationsApi::class)
    override fun <T> NavGraphBuilder.composable(
        destination: DestinationSpec<T>,
        navController: NavHostController,
        dependenciesContainerBuilder: @Composable DependenciesContainerBuilder<*>.() -> Unit,
        manualComposableCalls: ManualComposableCalls,
    ) {
        when (val destinationStyle = destination.style) {
            is DestinationStyle.Runtime,
            is DestinationStyle.Default -> {
                addComposable(
                    destination,
                    navController,
                    dependenciesContainerBuilder,
                    manualComposableCalls
                )
            }

            is DestinationStyle.Dialog -> {
                addDialogComposable(
                    destination,
                    navController,
                    dependenciesContainerBuilder,
                    manualComposableCalls
                )
            }

            is DestinationStyle.Activity -> {
                addActivityDestination(destination as ActivityDestinationSpec)
            }

            is DestinationStyle.Animated,
            is DestinationStyle.BottomSheet -> {
                throw IllegalStateException("You need to use 'rememberAnimatedNavHostEngine' to get an engine that can use ${destinationStyle.javaClass.simpleName} and pass that into the 'DestinationsNavHost' ")
            }
        }
    }

    private fun <T> NavGraphBuilder.addComposable(
        destination: DestinationSpec<T>,
        navController: NavHostController,
        dependenciesContainerBuilder: @Composable DependenciesContainerBuilder<*>.() -> Unit,
        manualComposableCalls: ManualComposableCalls,
    ) {
        @SuppressLint("RestrictedApi")
        val contentLambda = manualComposableCalls[destination.baseRoute]

        composable(
            route = destination.route,
            arguments = destination.arguments,
            deepLinks = destination.deepLinks
        ) { navBackStackEntry ->
            CallComposable(
                destination,
                navController,
                navBackStackEntry,
                dependenciesContainerBuilder,
                contentLambda
            )
        }
    }

    private fun <T> NavGraphBuilder.addDialogComposable(
        destination: DestinationSpec<T>,
        navController: NavHostController,
        dependenciesContainerBuilder: @Composable DependenciesContainerBuilder<*>.() -> Unit,
        manualComposableCalls: ManualComposableCalls
    ) {
        @SuppressLint("RestrictedApi")
        val contentLambda = manualComposableCalls[destination.baseRoute]

        // TODO look if we should wrap this with
        // androidx.wear.compose.material.dialog.Dialog
        // Note: no wear navigation dialog dsl exists
        composable(
            destination.route,
            destination.arguments,
            destination.deepLinks,
        ) { navBackStackEntry ->
            CallComposable(
                destination,
                navController,
                navBackStackEntry,
                dependenciesContainerBuilder,
                contentLambda
            )
        }
    }

    internal class Default<T>(
        destination: DestinationSpec<T>,
        navBackStackEntry: NavBackStackEntry,
        navController: NavController,
    ) : DestinationScopeImpl<T>(
        destination,
        navBackStackEntry,
        navController,
    )

    @Suppress("UNCHECKED_CAST")
    @Composable
    private fun <T> CallComposable(
        destination: DestinationSpec<T>,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        dependenciesContainerBuilder: @Composable DependenciesContainerBuilder<*>.() -> Unit,
        contentLambda: DestinationLambda<*>?
    ) {
        val scope = remember {
            Default(
                destination,
                navBackStackEntry,
                navController
            )
        }

        if (contentLambda == null) {
            with(destination) { scope.Content(dependenciesContainerBuilder) }
        } else {
            contentLambda as DestinationLambda<T>
            contentLambda(scope)
        }
    }

    companion object {
        internal fun <T> NavGraphBuilder.addActivityDestination(destination: ActivityDestinationSpec<T>) {
            activity(destination.route) {
                targetPackage = destination.targetPackage
                activityClass = destination.activityClass?.kotlin
                action = destination.action
                data = destination.data
                dataPattern = destination.dataPattern

                destination.deepLinks.forEach { deepLink ->
                    deepLink {
                        action = deepLink.action
                        uriPattern = deepLink.uriPattern
                        mimeType = deepLink.mimeType
                    }
                }

                destination.arguments.forEach { navArg ->
                    argument(navArg.name) {
                        if (navArg.argument.isDefaultValuePresent) {
                            defaultValue = navArg.argument.defaultValue
                        }
                        type = navArg.argument.type
                        nullable = navArg.argument.isNullable
                    }
                }
            }
        }
    }
}