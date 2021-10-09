package com.ramcosta.composedestinations.codegen.templates

import com.ramcosta.composedestinations.codegen.commons.*

//region anchors
const val ADDITIONAL_IMPORTS_BLOCK = "[ADDITIONAL_IMPORTS_BLOCK]"
const val NAV_GRAPHS_DECLARATION = "[NAV_GRAPHS_DECLARATION]"
const val DEFAULT_NAV_CONTROLLER_PLACEHOLDER = "[DEFAULT_NAV_CONTROLLER_PLACEHOLDER]"
const val EXPERIMENTAL_API_PLACEHOLDER = "[EXPERIMENTAL_API_PLACEHOLDER]"
const val ANIMATION_DEFAULT_PARAMS_PLACEHOLDER = "[ANIMATION_DEFAULT_PARAMS_PLACEHOLDER]"
const val ANIMATION_PARAMS_TO_INNER_PLACEHOLDER_1 = "[ANIMATION_PARAMS_TO_INNER_PLACEHOLDER_1]"
const val ANIMATION_PARAMS_TO_INNER_PLACEHOLDER_2 = "[ANIMATION_PARAMS_TO_INNER_PLACEHOLDER_2]"
const val ANIMATED_NAV_HOST_CALL_PARAMETERS_START = "[ANIMATED_NAV_HOST_CALL_PARAMETERS_START]"
const val ANIMATED_NAV_HOST_CALL_PARAMETERS_END = "[ANIMATED_NAV_HOST_CALL_PARAMETERS_END]"
const val INNER_NAV_HOST_CALL_ANIMATED_PARAMETERS_START = "[INNER_NAV_HOST_CALL_ANIMATED_PARAMETERS_START]"
const val INNER_NAV_HOST_CALL_ANIMATED_PARAMETERS_END = "[INNER_NAV_HOST_CALL_ANIMATED_PARAMETERS_END]"
const val NAV_HOST_METHOD_NAME = "[NAV_HOST_METHOD_NAME]"
const val ADD_ANIMATED_COMPOSABLE_START = "[ADD_ANIMATED_COMPOSABLE_START]"
const val ADD_ANIMATED_COMPOSABLE_END = "[ADD_ANIMATED_COMPOSABLE_END]"
const val ADD_BOTTOM_SHEET_COMPOSABLE_START = "[ADD_BOTTOM_SHEET_COMPOSABLE_START]"
const val ADD_BOTTOM_SHEET_COMPOSABLE_END = "[ADD_BOTTOM_SHEET_COMPOSABLE_END]"
const val NAVIGATION_ANIMATION_FUNCTIONS_START = "[NAVIGATION_ANIMATION_FUNCTIONS_START]"
const val NAVIGATION_ANIMATION_FUNCTIONS_END = "[NAVIGATION_ANIMATION_FUNCTIONS_END]"
const val NAVIGATION_BOTTOM_SHEET_FUNCTIONS_START = "[NAVIGATION_BOTTOM_SHEET_FUNCTIONS_START]"
const val NAVIGATION_BOTTOM_SHEET_FUNCTIONS_END = "[NAVIGATION_BOTTOM_SHEET_FUNCTIONS_END]"
const val ADD_COMPOSABLE_WHEN_ELSE_START = "[ADD_COMPOSABLES_WHEN_ELSE_START]"
const val ADD_COMPOSABLE_WHEN_ELSE_END = "[ADD_COMPOSABLES_WHEN_ELSE_END]"
const val BOTTOM_SHEET_COMPOSABLE_WRAPPER = " = BottomSheet(navController)"

const val SCAFFOLD_FUNCTION_START = "//region Scaffold"
const val SCAFFOLD_FUNCTION_END = "//endregion Scaffold"

const val START_ACCOMPANIST_NAVIGATION_IMPORTS = "//region accompanist navigation"
const val END_ACCOMPANIST_NAVIGATION_IMPORTS = "//endregion accompanist navigation"

const val START_ACCOMPANIST_MATERIAL_IMPORTS = "//region accompanist material"
const val END_ACCOMPANIST_MATERIAL_IMPORTS = "//endregion accompanist material"

//endregion

val destinationsObjectTemplate = """
package $PACKAGE_NAME

import androidx.compose.animation.*
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import kotlin.reflect.KClass
$START_ACCOMPANIST_NAVIGATION_IMPORTS
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment
import androidx.navigation.NavBackStackEntry
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.navigation
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
$END_ACCOMPANIST_NAVIGATION_IMPORTS
$START_ACCOMPANIST_MATERIAL_IMPORTS
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import androidx.navigation.plusAssign
$END_ACCOMPANIST_MATERIAL_IMPORTS
$ADDITIONAL_IMPORTS_BLOCK

/**
 * Class generated if any Composable is annotated with `@$DESTINATION_ANNOTATION`.
 * It aggregates all [$GENERATED_DESTINATION]s and has 
 * [DestinationsNavHost]/[DestinationsScaffold] equivalent methods which
 * will relay the destinations to be used in the navigation graph.
 */
object $DESTINATIONS_AGGREGATE_CLASS_NAME {

    //region NavGraphs
$NAV_GRAPHS_DECLARATION
    //endregion NavGraphs

    //region NavHost
    /**
     * Like [androidx.navigation.compose.NavHost] but uses composables annotated with
     * `@$DESTINATION_ANNOTATION` to pass in as the destinations available.
     *
     * @see [androidx.navigation.compose.NavHost]
     */
    $EXPERIMENTAL_API_PLACEHOLDER@Composable
    fun NavHost(
        navController: NavHostController = $DEFAULT_NAV_CONTROLLER_PLACEHOLDER,
        modifier: Modifier = Modifier,
        startDestination: $GENERATED_DESTINATION = ${GENERATED_NAV_GRAPH}s.root.startDestination,$ANIMATION_DEFAULT_PARAMS_PLACEHOLDER
    )$BOTTOM_SHEET_COMPOSABLE_WRAPPER {
        InnerDestinationsNavHost(
            navController = navController,
            modifier = modifier,
            startDestination = startDestination,
            situationalParametersProvider = { mutableMapOf() },$ANIMATION_PARAMS_TO_INNER_PLACEHOLDER_1
        )
    }
    //endregion NavHost

    $SCAFFOLD_FUNCTION_START
    /**
     * Like [androidx.compose.material.Scaffold] but it adds a navigation graph using [$DESTINATIONS_AGGREGATE_CLASS_NAME.NavHost].
     *
     * Also, composables that can depend on the current [$GENERATED_DESTINATION] are
     * passed the current [$GENERATED_DESTINATION] so that they can update when the user
     * navigates through the app.
     *
     * Lastly, [modifierForPaddingValues] is used to let callers determine
     * a [Modifier] to set on the [$DESTINATIONS_AGGREGATE_CLASS_NAME.NavHost] for a combination
     * of [PaddingValues] (given from this [Scaffold]) and current [$GENERATED_DESTINATION].
     *
     * @see [androidx.compose.material.Scaffold]
     */
    $EXPERIMENTAL_API_PLACEHOLDER@Composable
    fun Scaffold(
        modifier: Modifier = Modifier,
        startDestination: $GENERATED_DESTINATION = ${GENERATED_NAV_GRAPH}s.root.startDestination,
        navController: NavHostController = $DEFAULT_NAV_CONTROLLER_PLACEHOLDER,
        scaffoldState: ScaffoldState = rememberScaffoldState(),$ANIMATION_DEFAULT_PARAMS_PLACEHOLDER
        topBar: (@Composable ($GENERATED_DESTINATION) -> Unit) = {},
        bottomBar: @Composable ($GENERATED_DESTINATION) -> Unit = {},
        snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
        floatingActionButton: @Composable ($GENERATED_DESTINATION) -> Unit = {},
        floatingActionButtonPosition: FabPosition = FabPosition.End,
        isFloatingActionButtonDocked: Boolean = false,
        drawerContent: @Composable (ColumnScope.($GENERATED_DESTINATION) -> Unit)? = null,
        drawerGesturesEnabled: Boolean = true,
        drawerShape: Shape = MaterialTheme.shapes.large,
        drawerElevation: Dp = DrawerDefaults.Elevation,
        drawerBackgroundColor: Color = MaterialTheme.colors.surface,
        drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
        drawerScrimColor: Color = DrawerDefaults.scrimColor,
        backgroundColor: Color = MaterialTheme.colors.background,
        contentColor: Color = contentColorFor(backgroundColor),
        modifierForDestination: ($GENERATED_DESTINATION, PaddingValues) -> Modifier = { _, _ -> Modifier }
    )$BOTTOM_SHEET_COMPOSABLE_WRAPPER {
        val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()

        val destination = (currentBackStackEntryAsState?.destination?.route
            ?.let { ${GENERATED_NAV_GRAPH}s.root.findDestination(it) }
            ?: startDestination) as $GENERATED_DESTINATION

        Scaffold(
            modifier,
            scaffoldState,
            { topBar(destination) },
            { bottomBar(destination) },
            snackbarHost,
            { floatingActionButton(destination) },
            floatingActionButtonPosition,
            isFloatingActionButtonDocked,
            drawerContent?.let{ { drawerContent.invoke(this, destination) } },
            drawerGesturesEnabled,
            drawerShape,
            drawerElevation,
            drawerBackgroundColor,
            drawerContentColor,
            drawerScrimColor,
            backgroundColor,
            contentColor,
        ) { paddingValues ->
            InnerDestinationsNavHost(
                navController = navController,
                modifier = modifierForDestination(destination, paddingValues),
                startDestination = startDestination,
                situationalParametersProvider = {
                    mutableMapOf(ScaffoldState::class to scaffoldState)
                },$ANIMATION_PARAMS_TO_INNER_PLACEHOLDER_2
            )
        }
    }
    $SCAFFOLD_FUNCTION_END
}

//region internals
${EXPERIMENTAL_API_PLACEHOLDER}@Composable
private fun InnerDestinationsNavHost(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: Destination,
    situationalParametersProvider: (Destination) -> MutableMap<KClass<*>, Any> = { mutableMapOf() },
    ${INNER_NAV_HOST_CALL_ANIMATED_PARAMETERS_START}contentAlignment: Alignment = Alignment.Center,
    enterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition)?,
    exitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition)?,
    popEnterTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> EnterTransition)?,
    popExitTransition: (AnimatedContentScope<String>.(initial: NavBackStackEntry, target: NavBackStackEntry) -> ExitTransition)?,$INNER_NAV_HOST_CALL_ANIMATED_PARAMETERS_END
) {
    $NAV_HOST_METHOD_NAME(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
        route = $DESTINATIONS_AGGREGATE_CLASS_NAME.${GENERATED_NAV_GRAPH}s.root.route,
        ${ANIMATED_NAV_HOST_CALL_PARAMETERS_START}contentAlignment = contentAlignment,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,$ANIMATED_NAV_HOST_CALL_PARAMETERS_END
    ) {
        addNavGraphDestinations(
            navGraphSpec = $DESTINATIONS_AGGREGATE_CLASS_NAME.${GENERATED_NAV_GRAPH}s.root,
            navController = navController,
            addNavigation = addNavigation(),
            addComposable = addComposable(navController, situationalParametersProvider)
        )
    }
}

${EXPERIMENTAL_API_PLACEHOLDER}private fun addComposable(
    navController: NavHostController,
    situationalParametersProvider: ($GENERATED_DESTINATION) -> MutableMap<KClass<*>, Any>
): NavGraphBuilder.($CORE_DESTINATION_SPEC) -> Unit {
    return { destination ->
        destination as $GENERATED_DESTINATION
        val destinationStyle = destination.style
        when (destinationStyle) {
            is DestinationStyle.Default -> {
                addComposable(
                    destination,
                    navController,
                    situationalParametersProvider
                )
            }

            is DestinationStyle.Dialog -> {
                addDialogComposable(
                    destinationStyle,
                    destination,
                    navController,
                    situationalParametersProvider
                )
            }
$ADD_ANIMATED_COMPOSABLE_START
            is DestinationStyle.Animated<*> -> {
                addAnimatedComposable(
                    destinationStyle as AnimatedDestinationStyle,
                    destination,
                    navController,
                    situationalParametersProvider
                )
            }
$ADD_ANIMATED_COMPOSABLE_END$ADD_BOTTOM_SHEET_COMPOSABLE_START
            is DestinationStyle.BottomSheet -> {
                addBottomSheetComposable(
                    destination,
                    navController,
                    situationalParametersProvider
                )
            }
$ADD_BOTTOM_SHEET_COMPOSABLE_END$ADD_COMPOSABLE_WHEN_ELSE_START
            else -> throw RuntimeException("Should be impossible! Code gen should have failed if using a style for which you don't have the dependency")$ADD_COMPOSABLE_WHEN_ELSE_END
        }
    }
}

${EXPERIMENTAL_API_PLACEHOLDER}private fun NavGraphBuilder.addComposable(
    destination: $GENERATED_DESTINATION,
    navController: NavHostController,
    situationalParametersProvider: ($GENERATED_DESTINATION) -> MutableMap<KClass<*>, Any>
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks
    ) { navBackStackEntry ->
        destination.Content(
            navController,
            navBackStackEntry,
            situationalParametersProvider(destination)
        )
    }
}

private fun NavGraphBuilder.addDialogComposable(
    dialogStyle: DestinationStyle.Dialog,
    destination: $GENERATED_DESTINATION,
    navController: NavHostController,
    situationalParametersProvider: ($GENERATED_DESTINATION) -> MutableMap<KClass<*>, Any>
) {
    dialog(
        destination.route,
        destination.arguments,
        destination.deepLinks,
        dialogStyle.properties
    ) {
        destination.Content(
            navController = navController,
            navBackStackEntry = it,
            situationalParameters = situationalParametersProvider(destination)
        )
    }
}

${EXPERIMENTAL_API_PLACEHOLDER}private fun addNavigation(): NavGraphBuilder.($CORE_NAV_GRAPH_SPEC, NavGraphBuilder.() -> Unit) -> Unit {
    return { navGraph, builder ->
        navigation(
            navGraph.startDestination.route,
            navGraph.route
        ) {
            this.builder()
        }
    }
}

${NAVIGATION_BOTTOM_SHEET_FUNCTIONS_START}@ExperimentalMaterialNavigationApi
private fun NavGraphBuilder.addBottomSheetComposable(
    destination: $GENERATED_DESTINATION,
    navController: NavHostController,
    situationalParametersProvider: ($GENERATED_DESTINATION) -> MutableMap<KClass<*>, Any>
) {
    bottomSheet(
        destination.route,
        destination.arguments,
        destination.deepLinks
    ) { navBackStackEntry ->
        destination.Content(
            navController,
            navBackStackEntry,
            situationalParametersProvider(destination).apply {
                this[ColumnScope::class] = this@bottomSheet
            }
        )
    }
}

@ExperimentalMaterialNavigationApi
@Composable
private fun BottomSheet(navController: NavHostController, content: @Composable () -> Unit) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    ModalBottomSheetLayout(bottomSheetNavigator) {
        content()
    }
}$NAVIGATION_BOTTOM_SHEET_FUNCTIONS_END

${NAVIGATION_ANIMATION_FUNCTIONS_START}@ExperimentalAnimationApi
private fun NavGraphBuilder.addAnimatedComposable(
    animatedStyle: AnimatedDestinationStyle,
    destination: Destination,
    navController: NavHostController,
    situationalParametersProvider: ($GENERATED_DESTINATION) -> MutableMap<KClass<*>, Any>
) = with(animatedStyle) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks,
        enterTransition = { i, t -> enterTransition(i.toDest(), t.toDest()) },
        exitTransition = { i, t -> exitTransition(i.toDest(), t.toDest()) },
        popEnterTransition = { i, t -> popEnterTransition(i.toDest(), t.toDest()) },
        popExitTransition = { i, t -> popExitTransition(i.toDest(), t.toDest()) }
    ) { navBackStackEntry ->
        destination.Content(
            navController,
            navBackStackEntry,
            situationalParametersProvider(destination).apply {
                this[AnimatedVisibilityScope::class] = this@composable
            }
        )
    }
}

@ExperimentalAnimationApi
private fun NavBackStackEntry.toDest() = destination.route?.let { $DESTINATIONS_AGGREGATE_CLASS_NAME.${GENERATED_NAV_GRAPH}s.root.findDestination(it) as $GENERATED_DESTINATION }
$NAVIGATION_ANIMATION_FUNCTIONS_END//endregion
""".trimIndent()