package com.andreikingsley.ggdsl.letsplot

import org.jetbrains.kotlinx.jupyter.api.annotations.JupyterLibrary
import org.jetbrains.kotlinx.jupyter.api.libraries.*

@JupyterLibrary
internal class Integration : JupyterIntegration() {

    override fun Builder.onLoaded() {
        import("com.andreikingsley.ggdsl.letsplot.*")
        import("com.andreikingsley.ggdsl.letsplot.facet.*")
        import("com.andreikingsley.ggdsl.letsplot.layers.*")
        // todo add scales
        import("com.andreikingsley.ggdsl.letsplot.position.*")
        import("com.andreikingsley.ggdsl.letsplot.util.linetype.*")
    }

}
