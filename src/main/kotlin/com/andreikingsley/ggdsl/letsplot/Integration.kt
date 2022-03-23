package com.andreikingsley.ggdsl.letsplot

import org.jetbrains.kotlinx.jupyter.api.annotations.JupyterLibrary
import org.jetbrains.kotlinx.jupyter.api.libraries.*

@JupyterLibrary
internal class Integration : JupyterIntegration() {

    override fun Builder.onLoaded() {
        import("com.andreikingsley.ggdsl.letsplot.*")
        import("com.andreikingsley.ggdsl.letsplot.facet.*")
        import("com.andreikingsley.ggdsl.letsplot.layers.*")
        // todo scales
        import("com.andreikingsley.ggdsl.util.symbol.*")
    }

}
