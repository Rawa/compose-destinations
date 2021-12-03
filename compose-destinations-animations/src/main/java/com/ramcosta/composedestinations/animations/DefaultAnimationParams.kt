package com.ramcosta.composedestinations.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment

/**
 * Class that can be used to define the default animation for all Destinations with no
 * specific style set.
 * You can create your own and pass it to the `DestinationsNavHost` call.
 *
 * @see [com.google.accompanist.navigation.animation.AnimatedNavHost] for a parameters explanation
 */
@ExperimentalAnimationApi
class DefaultAnimationParams(
    val contentAlignment: Alignment = Alignment.Center,
    val enterTransition: DestinationEnterTransition? = DestinationEnterTransition { _, _ -> EnterTransition.None },
    val exitTransition: DestinationExitTransition? = DestinationExitTransition { _, _ -> ExitTransition.None },
    val popEnterTransition: DestinationEnterTransition? = enterTransition,
    val popExitTransition: DestinationExitTransition? = exitTransition,
) {

    companion object {
        val ACCOMPANIST_FADING by lazy {
            DefaultAnimationParams(
                enterTransition = { _, _ -> fadeIn(animationSpec = tween(700)) },
                exitTransition = { _, _ -> fadeOut(animationSpec = tween(700)) }
            )
        }
    }
}